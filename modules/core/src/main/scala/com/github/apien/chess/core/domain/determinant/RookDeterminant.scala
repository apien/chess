package com.github.apien.chess.core.domain.determinant

import com.github.apien.chess.core.domain.determinant.MoveDeterminant.AvailableMove
import com.github.apien.chess.core.domain.model.PieceType.Rook
import com.github.apien.chess.core.domain.model.{Board, Coordinate, PieceColor}

class RookDeterminant extends MoveDeterminant[Rook] {
  override def validate(source: Coordinate, color: PieceColor)(implicit board: Board): Set[AvailableMove] = {
    val result = MoveDeterminant.vertically(source, color, None) ++ MoveDeterminant.horizontally(source, color, None)
    result.toSet

  }
}
