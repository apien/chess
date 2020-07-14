package com.github.apien.chess.core.domain

sealed trait MoveError

object MoveError {

  final case object IllegalMove extends MoveError

  final case object EmptyField extends MoveError

  final case object NotYourTurn extends MoveError

  final case object KingInCheck extends MoveError

}
