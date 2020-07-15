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
class ScalaUserInput private (inputFile: UserInput) {

  /**
    * Try to read a next position.
    *
    * If there would be the exception then return None.
    * If source does not contain any more moves then return None.
    * If loaded data has invalid format then also return None.
    *
    * @return None when smth happen or
    */
  def nextMove(): Option[Move] = {
    for {
      rawInput <- Try(Option(inputFile.nextMove())).toOption.flatten.filter(_.size == 4)
      //TODO log information about exception
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
