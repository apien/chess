package com.github.apien.domain

import cats.syntax.either._
import com.github.apien.test.ChessSpec
import com.github.chess.apien.domain.model.PieceColor.{Black, White}
import com.github.chess.apien.domain.model.{Coordinate, Move}
import com.github.chess.apien.domain.{ChessEngine, MoveError}

class ChessEngineSpec extends ChessSpec {

  private val gameEngine = new ChessEngine()
  "GameEngine.makeMove" should "return EmptyField error when user try to move from empty field" in {
    gameEngine.applyMove(Move(Coordinate(1, 4), Coordinate(1, 3)), White) shouldBe MoveError.EmptyField.asLeft
  }

  it should "return NotYourTurn error when source field contains a piece but other color" in {
    gameEngine.applyMove(Move(Coordinate(0, 6), Coordinate(0, 4)), Black) shouldBe MoveError.NotYourTurn.asLeft
  }

}
