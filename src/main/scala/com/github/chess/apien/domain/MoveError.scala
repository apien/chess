package com.github.chess.apien.domain

sealed trait MoveError

object MoveError {

  final case object IllegalMove extends MoveError

  final case object EmptyField extends MoveError

  final case object NotYourTurn extends MoveError

  final case object KingInCheck extends MoveError

}
