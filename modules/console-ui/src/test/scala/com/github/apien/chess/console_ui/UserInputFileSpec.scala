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

}
