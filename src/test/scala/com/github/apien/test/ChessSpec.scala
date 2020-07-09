package com.github.apien.test

import com.github.chess.apien.domain.model.{Column, Row}

import scala.language.implicitConversions

trait ChessSpec {

  implicit def intToRow(value: Int): Row = Row(value)

  implicit def intToColumn(value: Int): Column = Column(value)
}
