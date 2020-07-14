package com.github.chess.apien.ui

import cats.Show
import com.github.chess.apien.domain.model.{Piece, PieceColor, PieceType}

class PieceShow extends Show[Piece] {
  override def show(piece: Piece): String = {

    val mark = piece.kind match {
      case PieceType.Pawn() => "P"
      case PieceType.Knight() => "N"
      case PieceType.Bishop() => "B"
      case PieceType.Rook() => "R"
      case PieceType.Queen() => "Q"
      case PieceType.King() => "K"
    }

    val toggle: String => String = (value: String) =>
      piece.color match {
        case PieceColor.White => value.toUpperCase
        case PieceColor.Black => value.toLowerCase
      }

    toggle(mark)
  }
}
