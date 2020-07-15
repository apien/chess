package com.github.apien.chess

import com.github.apien.chess.console_ui.ui._

package object console_ui {
  implicit val pieceShow = new PieceShow
  implicit val boardShow = new BoardShow
  implicit val rowShow = new RowShow
  implicit val colShow = new ColumnShow
  implicit val coordinateShow = new CoordinateShow
  implicit val moveShow = new MoveShow
  implicit val colorShow = new PieceColorShow
}
