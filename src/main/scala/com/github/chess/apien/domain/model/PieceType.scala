package com.github.chess.apien.domain.model

/**
  * Type pieces on chessboard,
  */
sealed trait PieceType

object PieceType {

  case class Pawn() extends PieceType

  case class Knight() extends PieceType

  case class Bishop() extends PieceType

  case class Rook() extends PieceType

  case class Queen() extends PieceType

  case object King extends PieceType

}
