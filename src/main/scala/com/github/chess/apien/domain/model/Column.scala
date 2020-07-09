package com.github.chess.apien.domain.model

/**
 * It represents of the column on a chessboard.
 *
 * Valid number belongs to the range <0,7>.
 *
 * @param value Column value.
 */
case class Column(value: Int) extends AnyVal

object Column {
  private val min = 0
  private val max = 7

  lazy val entireRow: List[Column] = (min to max).map(Column.apply).toList
}
