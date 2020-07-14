package com.github.apien.chess.core.domain

sealed trait MoveSuccess

object MoveSuccess {

  final case object Moved extends MoveSuccess

  final case object Captured extends MoveSuccess

}
