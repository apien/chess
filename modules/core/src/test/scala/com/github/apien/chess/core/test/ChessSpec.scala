package com.github.apien.chess.core.test

import com.github.apien.chess.core.domain.model.{Column, Row}
import org.scalatest.flatspec
import org.scalatest.matchers.should

trait ChessSpec extends flatspec.AnyFlatSpec with should.Matchers {

  implicit def intToRow(value: Int): Row = Row(value)

  implicit def intToColumn(value: Int): Column = Column(value)
}
