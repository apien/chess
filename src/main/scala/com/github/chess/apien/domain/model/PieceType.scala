package com.github.chess.apien.domain.model

/**
  * Type pieces on chessboard,
  */
sealed trait PieceType

object PieceType {

  case class Pawn() extends PieceType

  case object Knight extends PieceType

  case object Bishop extends PieceType

  case class Rook() extends PieceType

  case object Queen extends PieceType

  case object King extends PieceType

}
