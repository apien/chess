package com.github.chess.apien.domain

import com.github.chess.apien.domain.model.{Board, Coordinate, PieceColor, PieceType}

class QueenMoveDetermination extends MoveDetermination[PieceType.Queen] {
  override def validate(source: Coordinate, color: PieceColor)(implicit board: Board): Set[(Coordinate, MoveDetermination.MoveType)] =
    MoveDetermination.diagonally(source, color).toSet ++
      MoveDetermination.vertically(source, color, None).toSet ++
      MoveDetermination.horizontally(source, color, None).toSet
}