package com.github.apien.chess.console_ui.utils

import com.github.apien.chess.core.domain.model.{Coordinate, Move}
import com.whitehatgaming.UserInput

import scala.util.Try

/**
 * It is a wrapper for the [[UserInput]] more scala way interface :)
 *
 * It allows to operate on our domain object [[Move]] along with [[Option]] instead of a nasty null.
 *
 * @param inputFile Input File which it wraps.
 */
class ScalaUserInput private(inputFile: UserInput) {

  /**
   * Try to read next position.
   *
   * @return None then the
   */
  def nextMove(): Option[Move] = {
    for {
      rawInput <- Option(inputFile.nextMove()).filter(_.size == 4)
      source <- Coordinate.attempt(rawInput(0), rawInput(1))
      destination <- Coordinate.attempt(rawInput(2), rawInput(3))
    } yield Move(source, destination)

  }
}

object ScalaUserInput {

  def initialize(getUserInput: () => UserInput): Try[ScalaUserInput] = {
    Try(getUserInput())
      .map(input => new ScalaUserInput(input))
  }
}
