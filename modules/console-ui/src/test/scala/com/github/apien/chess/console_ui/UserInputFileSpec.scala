package com.github.apien.chess.console_ui

import com.github.apien.chess.console_ui.test.ResourceSpec
import org.scalatest.flatspec
import org.scalatest.matchers.should

class UserInputFileSpec extends flatspec.AnyFlatSpec with should.Matchers with ResourceSpec {

  "UserInputFile" should "properly map bess coordinates to board coordinates " in {
    val input = createInput("/sample.txt")

    input.nextMove() shouldBe Array(4, 6, 4, 4)
  }

  it should "return null where there is no more move" in {
    createInput("/empty.txt").nextMove() shouldBe null
  }

  it should "return array even when move is not correct algebraic but contain 4 letters" in {
    createInput("/invalid_syntax.txt").nextMove() shouldBe Array(0, -42, 2, -44)
  }

  it should "throw StringIndexOutOfBoundsException when move does not contain 4 letters" in {
    an[StringIndexOutOfBoundsException] should be thrownBy(createInput("/not_defined.txt").nextMove())
  }

}
