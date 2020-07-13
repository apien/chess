package com.github.apien.domain

import cats.syntax.option._
import com.github.apien.test.ChessSpec
import com.github.chess.apien.domain.Diagonal
import com.github.chess.apien.domain.model.Coordinate
class DiagonalSpec extends ChessSpec {

  "Diagonal.topLeft" should "determine diagonal cords" in {
    Diagonal.topLeft(Coordinate(3, 4)) shouldBe List(
      Coordinate(2, 3),
      Coordinate(1, 2),
      Coordinate(0, 1)
    )
  }

  it should "return Nil" in {
    Diagonal.topLeft(Coordinate(0, 0)) shouldBe Nil
  }

  it should "take care about the given limit" in {
    Diagonal.topLeft(Coordinate(3, 4), 1.some) shouldBe List(Coordinate(2, 3))
    Diagonal.topLeft(Coordinate(3, 4), 2.some) shouldBe List(Coordinate(2, 3), Coordinate(1, 2))
  }

  "Diagonal.downRight" should "determine diagonal cords" in {
    Diagonal.downRight(Coordinate(3, 4)) shouldBe List(
      Coordinate(4, 5),
      Coordinate(5, 6),
      Coordinate(6, 7)
    )
  }

  it should "return Nil" in {
    Diagonal.downRight(Coordinate(7, 7)) shouldBe Nil
  }

  it should "take care about the given limit" in {
    Diagonal.downRight(Coordinate(3, 4), 1.some) shouldBe List(Coordinate(4, 5))
    Diagonal.downRight(Coordinate(3, 4), 2.some) shouldBe List(Coordinate(4, 5), Coordinate(5, 6))
  }

  "Diagonal.topRight" should "determine diagonal cords" in {
    Diagonal.topRight(Coordinate(3, 4)) shouldBe List(
      Coordinate(4, 3),
      Coordinate(5, 2),
      Coordinate(6, 1),
      Coordinate(7, 0)
    )
  }

  it should "take care about the given limit" in {
    Diagonal.topRight(Coordinate(3, 4), 1.some) shouldBe List(Coordinate(4, 3))
    Diagonal.topRight(Coordinate(3, 4), 2.some) shouldBe List(Coordinate(4, 3), Coordinate(5, 2))
  }

  it should "return Nil" in {
    Diagonal.downRight(Coordinate(7, 0)) shouldBe Nil
  }

  "Diagonal.downLeft" should "determine diagonal cords" in {
    Diagonal.downLeft(Coordinate(3, 4)) shouldBe List(
      Coordinate(2, 5),
      Coordinate(1, 6),
      Coordinate(0, 7)
    )
  }

  it should "take care about the given limit" in {
    Diagonal.downLeft(Coordinate(3, 4), 1.some) shouldBe List(Coordinate(2, 5))
    Diagonal.downLeft(Coordinate(3, 4), 2.some) shouldBe List(Coordinate(2, 5), Coordinate(1, 6))
  }

  it should "return Nil" in {
    Diagonal.downLeft(Coordinate(0, 7)) shouldBe Nil
  }

}
