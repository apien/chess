package com.github.apien.chess.core.domain.model

/**
  * It represents coordinate on a chessboard.
  *
  * @param column Column on the board.
  * @param row    Row on the column.
  */
case class Coordinate(column: Column, row: Row)

object Coordinate {

  def attempt(column: Int, row: Int): Option[Coordinate] = {
    for {
      c <- Column.create(column).toOption
      r <- Row.parse(row).toOption
    } yield Coordinate(c, r)
  }
}
