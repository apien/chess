package com.github.chess.apien.domain

sealed trait MoveSuccess

object MoveSuccess {

  case object Moved extends MoveSuccess

  case object Captured extends MoveSuccess

}
