package com.github.apien.chess.core.domain.model

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

  /**
    * Select all in range (begin,7>.
    * @param begin
    * @return
    */
  def toTheEnd(begin: Column): List[Column] = (begin.value + 1 to max).map(Column.apply).toList

  /**
    * Select all in range <0,end).
    * @param end
    * @return
    */
  def fromBeginningTo(end: Column): List[Column] = (min until end.value).map(Column.apply).toList

  def isBelongToRange(column: Column, other: Column, maxShift: Int): Boolean =
    (column.value - other.value).abs <= maxShift
}
