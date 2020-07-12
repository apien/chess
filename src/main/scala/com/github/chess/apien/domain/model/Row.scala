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

  /**
    * Select all in range (begin,7>.
    * @param begin
    * @return
    */
  def toTheEnd(begin: Row): List[Row] = (begin.value + 1 to maxRow).map(Row.apply).toList

  /**
    * Select all in range <0,end).
    * @param end
    * @return
    */
  def fromBeginningTo(end: Row): List[Row] = (minRow until end.value).map(Row.apply).toList

  def isBelongToRange(column: Row, other: Row, maxShift: Int): Boolean =
    (column.value - other.value).abs <= maxShift
}
