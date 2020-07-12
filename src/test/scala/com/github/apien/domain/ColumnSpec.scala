package com.github.apien.domain

import com.github.apien.test.ChessSpec
import com.github.chess.apien.domain.model.Column

class ColumnSpec extends ChessSpec {

 "Column.toTheEnd" should "do not include given beginning value" in {
  Column.toTheEnd(0) shouldBe List(Column(1), Column(2), Column(3), Column(4), Column(5), Column(6), Column(7))
  Column.toTheEnd(0) shouldBe List(Column(1), Column(2), Column(3), Column(4), Column(5), Column(6), Column(7))
 }

 it should "return Nil for boundary value" in {
  Column.toTheEnd(7) shouldBe Nil
 }

 "Column.fromBeginningTo" should "do not include the given last value" in {
  Column.fromBeginningTo(7) shouldBe List(Column(0), Column(1), Column(2), Column(3), Column(4), Column(5), Column(6))
 }

 it should "return Nil for boundary value" in {
  Column.fromBeginningTo(0) shouldBe Nil
 }
}
