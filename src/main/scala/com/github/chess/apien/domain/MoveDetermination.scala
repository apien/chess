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

    val leftTotalPath = Column
      .fromBeginningTo(source.column)
      .filter(col => stepValidator(col, source.column))
      .map(Coordinate(_, source.row))
      .reverse

    val leftAvailablePath = takeFreeWalk(leftTotalPath)
    val firstOpponentOnLeft = findFirstOpponentOneTheWalk(leftTotalPath, color)
    val leftREsult = leftAvailablePath.map(cord => cord -> MoveType.Moved) ++
      firstOpponentOnLeft.map { case (cord, piece) => cord -> MoveType.Captured(piece.kind) }

    val rightTotalPath = Column
      .toTheEnd(source.column)
      .filter(col => stepValidator(col, source.column))
      .map(Coordinate(_, source.row))
    val rightAvailablePath = takeFreeWalk(rightTotalPath)
    val findFirstOpponentOnRight = findFirstOpponentOneTheWalk(rightTotalPath, color)
    val rightREsult = rightAvailablePath.map(cord => cord -> MoveType.Moved) ++
      findFirstOpponentOnRight.map { case (cord, piece) => cord -> MoveType.Captured(piece.kind) }

    leftREsult ++ rightREsult
  }

  def vertically(source: Coordinate, color: PieceColor, stepLimit: Option[Int] = None)(implicit board: Board): List[AvailableMove] = {
    val stepValidator: (Row, Row) => Boolean = stepLimit
      .fold((_: Row, _: Row) => true)(limit => Row.isBelongToRange(_, _, limit))

    val upperTotalPath = Row
      .fromBeginningTo(source.row)
      .filter(row => stepValidator(row, source.row))
      .map(Coordinate(source.column, _))
      .reverse
    val upperAvailablePath = takeFreeWalk(upperTotalPath)
    val firstUpperOpponent = findFirstOpponentOneTheWalk(upperTotalPath, color)

    val upperResult = upperAvailablePath.map(cord => cord -> MoveType.Moved) ++
      firstUpperOpponent.map { case (cord, piece) => cord -> MoveType.Captured(piece.kind) }

    val downTotalPath = Row
      .toTheEnd(source.row)
      .filter(row => stepValidator(row, source.row))
      .map(Coordinate(source.column, _))
    val downAvailablePath = takeFreeWalk(downTotalPath)
    val downFirstOpponent = findFirstOpponentOneTheWalk(downTotalPath, color)

    val downResult = downAvailablePath.map(cord => cord -> MoveType.Moved) ++
      downFirstOpponent.map { case (cord, piece) => cord -> MoveType.Captured(piece.kind) }

    upperResult ++ downResult
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
