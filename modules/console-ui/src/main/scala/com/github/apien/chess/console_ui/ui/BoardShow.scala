package com.github.apien.chess.console_ui.ui

import cats.Show
import com.github.apien.chess.core.domain.model._

class BoardShow(implicit pieceShow: Show[Piece]) extends Show[Board] {
  private val rowDelimiter = "  --------------------------------- \n"
  private val columnRow = "    0   1   2   3   4   5   6   7"

  override def show(board: Board): String = {
    def piece(col: Column, row: Row): String =
      board.squares
        .get(Coordinate(col, row))
        .fold(" ")(piece => pieceShow.show(piece))

    val sb = new StringBuilder()
    for (row <- Row.all) {
      row match {
        case Row(0) => sb ++= s"$columnRow\n"
        case _ => sb ++= "\n"
      }
      sb ++= rowDelimiter
      sb ++= s"${row.value} "
      for (col <- Column.entireRow) {
        sb ++= "| " + s"${piece(col, row)}" + " "
      }
      sb ++= "|"
    }

    sb ++= "\n"
    sb ++= rowDelimiter
    sb.toString()
  }
}
