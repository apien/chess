package com.github.apien

import com.github.apien.test.ResourceSpec
import com.whitehatgaming.{UserInput, UserInputFile}
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

  private def createInput(resourceFileName: String): UserInput = {
    new UserInputFile(getPathToResource(resourceFileName))
  }
}
