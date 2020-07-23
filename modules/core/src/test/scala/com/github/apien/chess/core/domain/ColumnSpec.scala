package com.github.apien.chess.core.domain

import com.github.apien.chess.core.domain.model.Column
import com.github.apien.chess.core.test.ChessSpec
class ColumnSpec extends ChessSpec {

  "Column.toTheEnd" should "do not include given beginning value" in {
    Column.toTheEnd(0) shouldBe List(Column.at1, Column.at2, Column.at3, Column.at4, Column.at5, Column.at6, Column.at7)
    Column.toTheEnd(0) shouldBe List(Column.at1, Column.at2, Column.at3, Column.at4, Column.at5, Column.at6, Column.at7)
  }

  it should "return Nil for boundary value" in {
    Column.toTheEnd(7) shouldBe Nil
  }

  "Column.fromBeginningTo" should "do not include the given last value" in {
    Column.fromBeginningTo(7) shouldBe List(Column.at0, Column.at1, Column.at2, Column.at3, Column.at4, Column.at5, Column.at6)
  }

  it should "return Nil for boundary value" in {
    Column.fromBeginningTo(0) shouldBe Nil
  }
}
