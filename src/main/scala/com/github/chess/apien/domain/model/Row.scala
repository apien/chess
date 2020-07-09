package com.github.chess.apien.domain.model

/**
 * It represents of the row on a chessboard.
 *
 * Valid number belongs to the range <0,7>.
 *
 * @param value Row value.
 */
case class Row(value: Int) extends AnyVal {

  def >(other: Row): Boolean = value > other.value

  def <=(other: Row): Boolean = value <= other.value
}

object Row {
  private val minRow = 0
  private val maxRow = 7

  lazy val all: List[Row] = (minRow to maxRow).map(Row.apply).toList
}
