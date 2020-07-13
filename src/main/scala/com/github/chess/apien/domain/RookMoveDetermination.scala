package com.github.chess.apien.domain

import com.github.chess.apien.domain.MoveDetermination.AvailableMove
import com.github.chess.apien.domain.model.PieceType.Rook
import com.github.chess.apien.domain.model.{Board, Coordinate, PieceColor}

class RookMoveDetermination extends MoveDetermination[Rook] {
  override def validate(source: Coordinate, color: PieceColor)(implicit board: Board): Set[AvailableMove] = {
    val result = MoveDetermination.vertically(source, color, None) ++ MoveDetermination.horizontally(source, color, None)
    result.toSet

  }
}
