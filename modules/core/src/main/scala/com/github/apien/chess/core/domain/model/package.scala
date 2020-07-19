package com.github.apien.chess.core.domain

import eu.timepit.refined._
import eu.timepit.refined.numeric.NonNegative
import io.estatico.newtype.macros.newtype

package object model {

  /**
   * It represents of the row on a chessboard.
   *
   * Valid number belongs to the range <0,7>.
   *
   * @param value Row value.
   */
  @newtype case class Row private(value: Int) {

    def >(other: Row): Boolean = value > other.value

    def <=(other: Row): Boolean = value <= other.value

    def <(other: Row): Boolean = value < other.value
  }

  object Row {
    private val minRow = 0
    private val maxRow = 7

    lazy val all: List[Row] = (minRow to maxRow).map(Row.apply).toList

    /**
     * Select all in a range (begin,7>.
     *
     * @param begin Begin value in the range (exclusive)
     * @return Determined values.
     */
    def toTheEnd(begin: Row): List[Row] = all.filter(_ > begin)

    /**
     * Select all in a range <0,end).
     *
     * @param end End value in the range (exclusive).
     * @return Determined values.
     */
    def fromBeginningTo(end: Row): List[Row] = all.filter(_ < end)

    def isBelongToRange(column: Row, other: Row, maxShift: Int): Boolean =
      (column.value - other.value).abs <= maxShift
  }

  /**
   * It represents of the column on a chessboard.
   *
   * Valid number belongs to the range <0,7>.
   *
   * @param value Column value.
   */
  @newtype case class Column private(value: Int) {

    def >(other: Column): Boolean = value > other.value

    def <=(other: Column): Boolean = value <= other.value

    def <(other: Column): Boolean = value < other.value
  }

  object Column {
    private val min = Column(0)
    private val max = Column(7)

    def create(value: Int): Either[String, Column] = {
      refineV[NonNegative](value).map(v => Column(v.value))
    }

    lazy val entireRow: List[Column] = (min.value to max.value).map(Column.apply).toList

    /**
     * Select all in a range (begin,7>.
     *
     * @param begin Begin value of the range (exclusive).
     * @return Determined values.
     */
    def toTheEnd(begin: Column): List[Column] = entireRow.filter(_ > begin)

    /**
     * Select all in a range <0,end).
     *
     * @param end End value of the range (exclusive).
     * @return Determined values.
     */
    def fromBeginningTo(end: Column): List[Column] = entireRow.filter(_ < end)

    def isBelongToRange(column: Column, other: Column, maxShift: Int): Boolean =
      (column.value - other.value).abs <= maxShift
  }

}
