package com.github.chess.apien.domain.determinant

import com.github.chess.apien.domain.determinant.MoveDeterminant.AvailableMove
import com.github.chess.apien.domain.model.PieceType.Rook
import com.github.chess.apien.domain.model.{Board, Coordinate, PieceColor}

class RookDeterminant extends MoveDeterminant[Rook] {
  override def validate(source: Coordinate, color: PieceColor)(implicit board: Board): Set[AvailableMove] = {
    val result = MoveDeterminant.vertically(source, color, None) ++ MoveDeterminant.horizontally(source, color, None)
    result.toSet

  }
}
