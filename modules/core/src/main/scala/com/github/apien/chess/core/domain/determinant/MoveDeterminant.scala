package com.github.apien.chess.core.domain.determinant

import com.github.apien.chess.core.domain.Diagonal
import com.github.apien.chess.core.domain.determinant.MoveDeterminant.AvailableMove
import com.github.apien.chess.core.domain.model.PieceType._
import com.github.apien.chess.core.domain.model._

trait MoveDeterminant[T <: PieceType] {

  def validate(source: Coordinate, color: PieceColor)(implicit board: Board): Set[AvailableMove]
}

object MoveDeterminant {

  implicit val pawnMoveDetermination: MoveDeterminant[Pawn] = new PawnDeterminant
  implicit val knightMoveDetermination: MoveDeterminant[Knight] = new KnightDeterminant
  implicit val bishopMoveDetermination: MoveDeterminant[Bishop] = new BishopDeterminant
  implicit val rookMoveDetermination: MoveDeterminant[Rook] = new RookDeterminant
  implicit val queenMoveDetermination: MoveDeterminant[Queen] = new QueenDeterminant
  implicit val kingMoveDetermination: MoveDeterminant[King] = new KingDeterminant

  def getMoves[A <: PieceType](a: A, source: Coordinate, color: PieceColor, board: Board): Set[AvailableMove] = {
    //TODO get rid of this ugly pattern matching
    val md = a match {
      case Pawn() => pawnMoveDetermination
      case Knight() => knightMoveDetermination
      case Bishop() => bishopMoveDetermination
      case Rook() => rookMoveDetermination
      case Queen() => queenMoveDetermination
      case King() => kingMoveDetermination
    }
    md.validate(source, color)(board)
  }

  def horizontally(source: Coordinate, color: PieceColor, stepLimit: Option[Int] = None)(implicit board: Board): List[AvailableMove] = {
    val stepValidator: (Column, Column) => Boolean = stepLimit
      .fold((_: Column, _: Column) => true)(limit => Column.isBelongToRange(_, _, limit))

    val calculateMoves = getMoveTrack(_, color)

    val leftTotalPath = Column
      .fromBeginningTo(source.column)
      .filter(col => stepValidator(col, source.column))
      .map(Coordinate(_, source.row))
      .reverse
    val leftREsult = calculateMoves(leftTotalPath)

    val rightTotalPath = Column
      .toTheEnd(source.column)
      .filter(col => stepValidator(col, source.column))
      .map(Coordinate(_, source.row))
    val rightREsult = calculateMoves(rightTotalPath)

    leftREsult ++ rightREsult
  }

  def vertically(source: Coordinate, color: PieceColor, stepLimit: Option[Int] = None)(implicit board: Board): List[AvailableMove] = {
    val stepValidator: (Row, Row) => Boolean = stepLimit
      .fold((_: Row, _: Row) => true)(limit => Row.isBelongToRange(_, _, limit))
    val calculateMoves = getMoveTrack(_, color)

    val upperTotalPath = Row
      .fromBeginningTo(source.row)
      .filter(row => stepValidator(row, source.row))
      .map(Coordinate(source.column, _))
      .reverse
    val upperResult = calculateMoves(upperTotalPath)

    val downTotalPath = Row
      .toTheEnd(source.row)
      .filter(row => stepValidator(row, source.row))
      .map(Coordinate(source.column, _))
    val downResult = calculateMoves(downTotalPath)

    upperResult ++ downResult
  }

  def getMoveTrack(potentialPath: List[Coordinate], color: PieceColor)(implicit board: Board): List[AvailableMove] = {
    val availableToMove = takeFreeWalk(potentialPath)
    val firstOpponent = findFirstOpponentOneTheWalk(potentialPath, color)

    availableToMove.map(cord => cord -> MoveType.Vacant) ++
      firstOpponent.map { case (cord, piece) => cord -> MoveType.Capture(piece.kind) }
  }

  def diagonally(source: Coordinate, color: PieceColor, limit: Option[Int] = None)(implicit board: Board): List[AvailableMove] = {
    val topLeftDiagonalMoves = getMoveTrack(Diagonal.topLeft(source, limit), color)
    val downRightDiagonalMoves = getMoveTrack(Diagonal.downRight(source, limit), color)
    val topRightDiagonalMoves = getMoveTrack(Diagonal.topRight(source, limit), color)
    val downLeft = getMoveTrack(Diagonal.downLeft(source, limit), color)

    topLeftDiagonalMoves ++
      downRightDiagonalMoves ++
      topRightDiagonalMoves ++
      downLeft
  }

  def takeFreeWalk(path: List[Coordinate])(implicit board: Board): List[Coordinate] =
    path.takeWhile(cord => board.squares.get(cord).isEmpty)

  def findFirstOpponentOneTheWalk(path: List[Coordinate], color: PieceColor)(implicit board: Board): Option[(Coordinate, Piece)] = {
    path
      .flatMap(cord => board.squares.get(cord).map(piece => cord -> piece))
      .find { case (_, piece) => piece.color != color }
  }

  type AvailableMove = (Coordinate, MoveType)
  sealed trait MoveType

  object MoveType {

    /**
     * Square is vacant and the piece can to move to the square.
     */
    case object Vacant extends MoveType

    /**
     * Square is occupied by an enemy (other color) piece and you can capture it.
     *
     * @param pieceType Piece type which is threaten by capture.
     */
    case class Capture(pieceType: PieceType) extends MoveType

  }

}
