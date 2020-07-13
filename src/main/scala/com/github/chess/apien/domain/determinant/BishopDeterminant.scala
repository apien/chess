package com.github.chess.apien.domain.determinant

import com.github.chess.apien.domain.model.PieceType.Bishop
import com.github.chess.apien.domain.model.{Board, Coordinate, PieceColor}

class BishopDeterminant extends MoveDeterminant[Bishop] {
  override def validate(source: Coordinate, color: PieceColor)(implicit board: Board): Set[(Coordinate, MoveDeterminant.MoveType)] =
    MoveDeterminant.diagonally(source, color).toSet
}
