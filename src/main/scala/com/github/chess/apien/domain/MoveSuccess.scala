package com.github.chess.apien.domain

sealed trait MoveSuccess

object MoveSuccess {

  final case object Moved extends MoveSuccess

  final case object Captured extends MoveSuccess

}
