package com.github.chess.apien.domain

import com.github.chess.apien.domain.KnightMoveDetermination.CoordShift
import com.github.chess.apien.domain.MoveDetermination.MoveType
import com.github.chess.apien.domain.model.PieceType.Knight
import com.github.chess.apien.domain.model.{Board, Coordinate, PieceColor}

class KnightMoveDetermination extends MoveDetermination[Knight] {
  override def validate(source: Coordinate, color: PieceColor)(implicit board: Board): Set[(Coordinate, MoveDetermination.MoveType)] = {
    KnightMoveDetermination.possibles
      .map {
        case CoordShift(columnShift, rowShift) =>
          Coordinate.attempt(source.column.value + columnShift, source.row.value + rowShift)
      }
      .collect { case Some(cord) => cord }
      .map(cord => cord -> board.squares.get(cord))
      .filter { case (_, piece) => piece.fold(true)(_.color != color) }
      .map {
        case (cord, pieceOp) => cord -> pieceOp.fold[MoveType](MoveType.Moved)(piece => MoveType.Captured(piece.kind))
      }

  }

}

object KnightMoveDetermination {

  private val possibles = Set(
    CoordShift(1, -2),
    CoordShift(2, -1),
    CoordShift(2, 1),
    CoordShift(1, 2),
    CoordShift(-1, 2),
    CoordShift(-2, 1),
    CoordShift(-2, -1),
    CoordShift(-1, -2)
  )

  private case class CoordShift(column: Int, row: Int)

}