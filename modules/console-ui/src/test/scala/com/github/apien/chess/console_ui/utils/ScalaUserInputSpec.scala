package com.github.apien.chess.console_ui.utils

import java.util.UUID

import cats.syntax.option._
import com.github.apien.chess.console_ui.test.ResourceSpec
import com.github.apien.chess.core.domain.model.{Coordinate, Move}
import com.github.apien.chess.core.test.ChessSpec
import org.scalatest.flatspec
import org.scalatest.matchers.should

class ScalaUserInputSpec extends flatspec.AnyFlatSpec with should.Matchers with ResourceSpec with ChessSpec {

  "ScalaUserInput" should "properly map bess coordinates to board coordinates " in {
    val input = ScalaUserInput.initialize(() => createInput("/sample.txt")).get

    input.nextMove() shouldBe Move(Coordinate(4, 6), Coordinate(4, 4)).some
  }

  it should "return null where there is no more move" in {
    ScalaUserInput.initialize(() => createInput("/empty.txt")).get.nextMove() shouldBe None
  }

  it should "return Failure when no such file" in {
    ScalaUserInput.initialize(() => createInput(s"/${UUID.randomUUID().toString}")).isFailure shouldBe true
  }

  it should "return None when exception has been thrown during fetch a move " in {
    //TODO implement it!
    ignore
  }

  it should "return None when there is not valid move" in {
    ScalaUserInput.initialize(() => createInput("/invalid_syntax.txt")).get.nextMove() shouldBe None
  }

  it should "return None when there is not valid sa" in {
    ScalaUserInput.initialize(() => createInput("/not_defined.txt")).get.nextMove() shouldBe None
  }

}
