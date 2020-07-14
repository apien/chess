package com.github.apien.chess.core.domain.model

/**
 * Type pieces on chessboard,
 */
sealed trait PieceType

object PieceType {

  final case class Pawn() extends PieceType

  final case class Knight() extends PieceType

  final case class Bishop() extends PieceType

  final case class Rook() extends PieceType

  final case class Queen() extends PieceType

  final case class King() extends PieceType

}
