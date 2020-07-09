package com.github.chess.apien.domain

import com.github.chess.apien.domain.model.PieceType.Pawn
import com.github.chess.apien.domain.model._

trait MovementValidator[T <: PieceType] {

  def validate(board: Board)(piece: Piece, source: Coordinate, destination: Coordinate): Either[MoveError, MoveSuccess]
}

object MovementValidator {
  implicit lazy val pawnValidator: MovementValidator[Pawn] = new PawnMovementValidator
}
