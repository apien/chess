package com.github.chess.apien.domain.determinant

import com.github.chess.apien.domain.model.PieceType.Queen
import com.github.chess.apien.domain.model.{Board, Coordinate, PieceColor}

class QueenDeterminant extends MoveDeterminant[Queen] {
  override def validate(source: Coordinate, color: PieceColor)(implicit board: Board): Set[(Coordinate, MoveDeterminant.MoveType)] =
    MoveDeterminant.diagonally(source, color).toSet ++
      MoveDeterminant.vertically(source, color, None).toSet ++
      MoveDeterminant.horizontally(source, color, None).toSet
}
