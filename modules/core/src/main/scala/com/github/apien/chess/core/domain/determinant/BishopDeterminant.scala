package com.github.apien.chess.core.domain.determinant

import com.github.apien.chess.core.domain.model.PieceType.Bishop
import com.github.apien.chess.core.domain.model.{Board, Coordinate, PieceColor}

class BishopDeterminant extends MoveDeterminant[Bishop] {
  override def validate(source: Coordinate, color: PieceColor)(implicit board: Board): Set[(Coordinate, MoveDeterminant.MoveType)] =
    MoveDeterminant.diagonally(source, color).toSet
}
