package com.github.apien.chess.console_ui.ui

import cats.Show
import com.github.apien.chess.core.domain.model.PieceColor

class PieceColorShow extends Show[PieceColor] {
  override def show(color: PieceColor): String =
    color match {
      case PieceColor.White => "Player 1 (White pieces)"
      case PieceColor.Black => "Player 2 (Black pieces)"
    }
}
