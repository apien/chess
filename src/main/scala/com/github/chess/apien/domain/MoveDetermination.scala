package com.github.chess.apien.domain

import com.github.chess.apien.domain.MoveDetermination.AvailableMove
import com.github.chess.apien.domain.model._

trait MoveDetermination[T <: PieceType] {

  def validate(source: Coordinate, color: PieceColor)(implicit board: Board): Set[AvailableMove]

}

object MoveDetermination {

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

    availableToMove.map(cord => cord -> MoveType.Moved) ++
      firstOpponent.map { case (cord, piece) => cord -> MoveType.Captured(piece.kind) }
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
    case object Moved extends MoveType
    case class Captured(pieceType: PieceType) extends MoveType

  }

}
