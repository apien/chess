package com.github.chess.apien.domain

import com.github.chess.apien.domain.model.PieceType.Bishop
import com.github.chess.apien.domain.model.{Board, Coordinate, PieceColor}

class BishopMoveDetermination extends MoveDetermination[Bishop] {
  override def validate(source: Coordinate, color: PieceColor)(implicit board: Board): Set[(Coordinate, MoveDetermination.MoveType)] =
    MoveDetermination.diagonally(source, color).toSet
}