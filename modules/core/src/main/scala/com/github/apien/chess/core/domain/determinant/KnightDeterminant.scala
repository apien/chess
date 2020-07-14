package com.github.apien.chess.core.domain.determinant

import com.github.apien.chess.core.domain.determinant.KnightDeterminant.CoordShift
import com.github.apien.chess.core.domain.determinant.MoveDeterminant.MoveType
import com.github.apien.chess.core.domain.model.PieceType.Knight
import com.github.apien.chess.core.domain.model.{Board, Coordinate, PieceColor}

class KnightDeterminant extends MoveDeterminant[Knight] {
  override def validate(source: Coordinate, color: PieceColor)(implicit board: Board): Set[(Coordinate, MoveDeterminant.MoveType)] = {
    KnightDeterminant.possibles
      .map {
        case CoordShift(columnShift, rowShift) =>
          Coordinate.attempt(source.column.value + columnShift, source.row.value + rowShift)
      }
      .collect { case Some(cord) => cord }
      .map(cord => cord -> board.squares.get(cord))
      .filter { case (_, piece) => piece.fold(true)(_.color != color) }
      .map {
        case (cord, pieceOp) => cord -> pieceOp.fold[MoveType](MoveType.Vacant)(piece => MoveType.Capture(piece.kind))
      }

  }

}

object KnightDeterminant {

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
