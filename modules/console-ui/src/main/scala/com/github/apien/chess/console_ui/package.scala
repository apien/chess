package com.github.apien.chess

import com.github.apien.chess.console_ui.ui.{BoardShow, PieceShow}

package object console_ui {
  implicit val pieceShow = new PieceShow
  implicit val boardShow = new BoardShow
}
