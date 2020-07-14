package com.github.apien.chess.core.domain.model

sealed trait PieceColor

object PieceColor {

  final case object White extends PieceColor

  final case object Black extends PieceColor

}
