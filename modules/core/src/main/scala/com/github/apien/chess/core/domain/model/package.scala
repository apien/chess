package com.github.apien.chess.core.domain

import eu.timepit.refined._
import eu.timepit.refined.api.Refined
import eu.timepit.refined.auto._
import eu.timepit.refined.boolean.And
import eu.timepit.refined.numeric.{GreaterEqual, LessEqual}
import io.estatico.newtype.macros.newtype

package object model {

  type ChessBoardCoordinateCondition = GreaterEqual[W.`0`.T] And LessEqual[W.`7`.T]
  type ChessBoardCoordinate = Int Refined ChessBoardCoordinateCondition

  /**
    * It represents of the row on a chessboard.
    *
    * Valid number belongs to the range <0,7>.
    *
    * @param value Row value.
    */
  @newtype case class Row private (value: ChessBoardCoordinate) {

    def >(other: Row): Boolean = value > other.value

    def <=(other: Row): Boolean = value <= other.value

    def <(other: Row): Boolean = value < other.value
  }

  object Row {
    val at0: Row = Row(0)
    val at1: Row = Row(1)
    val at2: Row = Row(2)
    val at3: Row = Row(3)
    val at4: Row = Row(4)
    val at5: Row = Row(5)
    val at6: Row = Row(6)
    val at7: Row = Row(7)

    lazy val all: List[Row] = List(at0, at1, at2, at3, at4, at5, at6, at7)

    def parse(value: Int): Either[String, Row] = refineV[ChessBoardCoordinateCondition](value).map(Row(_))

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
  @newtype case class Column(value: ChessBoardCoordinate) {

    def >(other: Column): Boolean = value > other.value

    def <=(other: Column): Boolean = value <= other.value

    def <(other: Column): Boolean = value < other.value
  }

  object Column {

    val at0: Column = Column(0)
    val at1: Column = Column(1)
    val at2: Column = Column(2)
    val at3: Column = Column(3)
    val at4: Column = Column(4)
    val at5: Column = Column(5)
    val at6: Column = Column(6)
    val at7: Column = Column(7)

    lazy val all: List[Column] = List(at0, at1, at2, at3, at4, at5, at6, at7)

    def parse(value: Int): Either[String, Column] = refineV[ChessBoardCoordinateCondition](value).map(Column(_))

    /**
      * Select all in a range (begin,7>.
      *
      * @param begin Begin value of the range (exclusive).
      * @return Determined values.
      */
    def toTheEnd(begin: Column): List[Column] = all.filter(_ > begin)

    /**
      * Select all in a range <0,end).
      *
      * @param end End value of the range (exclusive).
      * @return Determined values.
      */
    def fromBeginningTo(end: Column): List[Column] = all.filter(_ < end)

    def isBelongToRange(column: Column, other: Column, maxShift: Int): Boolean =
      (column.value - other.value).abs <= maxShift
  }
}
