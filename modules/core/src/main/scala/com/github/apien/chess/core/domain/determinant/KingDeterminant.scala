package com.github.apien.chess.core.domain.determinant

import cats.syntax.option._
import com.github.apien.chess.core.domain.determinant.MoveDeterminant.AvailableMove
import com.github.apien.chess.core.domain.model.PieceType.King
import com.github.apien.chess.core.domain.model.{Board, Coordinate, PieceColor}

class KingDeterminant extends MoveDeterminant[King] {
  override def validate(source: Coordinate, color: PieceColor)(implicit board: Board): Set[AvailableMove] = {
    val moveLimit = 1.some
    val result = MoveDeterminant.vertically(source, color, moveLimit) ++
      MoveDeterminant.horizontally(source, color, moveLimit) ++
      MoveDeterminant.diagonally(source, color, moveLimit)
    result.toSet

  }
}
