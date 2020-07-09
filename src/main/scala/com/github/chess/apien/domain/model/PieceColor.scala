package com.github.chess.apien.domain.model

sealed trait PieceColor

object PieceColor {

  case object White extends PieceColor

  case object Black extends PieceColor

}
