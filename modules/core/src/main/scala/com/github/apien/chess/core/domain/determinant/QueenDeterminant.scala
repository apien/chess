package com.github.apien.chess.core.domain.determinant

import com.github.apien.chess.core.domain.model.PieceType.Queen
import com.github.apien.chess.core.domain.model.{Board, Coordinate, PieceColor}

class QueenDeterminant extends MoveDeterminant[Queen] {
  override def validate(source: Coordinate, color: PieceColor)(implicit board: Board): Set[(Coordinate, MoveDeterminant.MoveType)] =
    MoveDeterminant.diagonally(source, color).toSet ++
      MoveDeterminant.vertically(source, color, None).toSet ++
      MoveDeterminant.horizontally(source, color, None).toSet
}
