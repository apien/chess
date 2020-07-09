package com.github.chess.apien.domain

sealed trait MoveError

object MoveError {

  case object IllegalMove extends MoveError

  case object EmptyField extends MoveError

  case object NotYourTurn extends MoveError

}
