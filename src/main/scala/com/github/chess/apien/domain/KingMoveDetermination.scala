package com.github.chess.apien.domain

import cats.syntax.option._
import com.github.chess.apien.domain.MoveDetermination.AvailableMove
import com.github.chess.apien.domain.model.PieceType.King
import com.github.chess.apien.domain.model.{Board, Coordinate, PieceColor}

class KingMoveDetermination extends MoveDetermination[King] {
  override def validate(source: Coordinate, color: PieceColor)(implicit board: Board): Set[AvailableMove] = {
    val moveLimit = 1.some
    val result = MoveDetermination.vertically(source, color, moveLimit) ++
      MoveDetermination.horizontally(source, color, moveLimit) ++
      MoveDetermination.diagonally(source, color, moveLimit)
    result.toSet

  }
}
