package com.github.apien.chess.core.domain

import cats.syntax.option._
import com.github.apien.chess.core.test.ChessSpec

class DiagonalSpec extends ChessSpec {

  "Diagonal.topLeft" should "determine diagonal cords" in {
    Diagonal.topLeft(model.Coordinate(3, 4)) shouldBe List(
      model.Coordinate(2, 3),
      model.Coordinate(1, 2),
      model.Coordinate(0, 1)
    )
  }

  it should "return Nil" in {
    Diagonal.topLeft(model.Coordinate(0, 0)) shouldBe Nil
  }

  it should "take care about the given limit" in {
    Diagonal.topLeft(model.Coordinate(3, 4), 1.some) shouldBe List(model.Coordinate(2, 3))
    Diagonal.topLeft(model.Coordinate(3, 4), 2.some) shouldBe List(model.Coordinate(2, 3), model.Coordinate(1, 2))
  }

  "Diagonal.downRight" should "determine diagonal cords" in {
    Diagonal.downRight(model.Coordinate(3, 4)) shouldBe List(
      model.Coordinate(4, 5),
      model.Coordinate(5, 6),
      model.Coordinate(6, 7)
    )
  }

  it should "return Nil" in {
    Diagonal.downRight(model.Coordinate(7, 7)) shouldBe Nil
  }

  it should "take care about the given limit" in {
    Diagonal.downRight(model.Coordinate(3, 4), 1.some) shouldBe List(model.Coordinate(4, 5))
    Diagonal.downRight(model.Coordinate(3, 4), 2.some) shouldBe List(model.Coordinate(4, 5), model.Coordinate(5, 6))
  }

  "Diagonal.topRight" should "determine diagonal cords" in {
    Diagonal.topRight(model.Coordinate(3, 4)) shouldBe List(
      model.Coordinate(4, 3),
      model.Coordinate(5, 2),
      model.Coordinate(6, 1),
      model.Coordinate(7, 0)
    )
  }

  it should "take care about the given limit" in {
    Diagonal.topRight(model.Coordinate(3, 4), 1.some) shouldBe List(model.Coordinate(4, 3))
    Diagonal.topRight(model.Coordinate(3, 4), 2.some) shouldBe List(model.Coordinate(4, 3), model.Coordinate(5, 2))
  }

  it should "return Nil" in {
    Diagonal.downRight(model.Coordinate(7, 0)) shouldBe Nil
  }

  "Diagonal.downLeft" should "determine diagonal cords" in {
    Diagonal.downLeft(model.Coordinate(3, 4)) shouldBe List(
      model.Coordinate(2, 5),
      model.Coordinate(1, 6),
      model.Coordinate(0, 7)
    )
  }

  it should "take care about the given limit" in {
    Diagonal.downLeft(model.Coordinate(3, 4), 1.some) shouldBe List(model.Coordinate(2, 5))
    Diagonal.downLeft(model.Coordinate(3, 4), 2.some) shouldBe List(model.Coordinate(2, 5), model.Coordinate(1, 6))
  }

  it should "return Nil" in {
    Diagonal.downLeft(model.Coordinate(0, 7)) shouldBe Nil
  }

}
