package com.github.chess.apien.domain.model
import cats.syntax.option._

/**
 * It represents coordinate on a chessboard.
 *
 * @param column Column on the board.
 * @param row    Row on the column.
 */
case class Coordinate(column: Column, row: Row)

object Coordinate {

  def attempt(column: Int, row: Int): Option[Coordinate] = {
    if (column >= 0 && column <= 7 && row >= 0 && row <= 7)
      Coordinate(Column(column), Row(row)).some
    else
      None
  }
}
