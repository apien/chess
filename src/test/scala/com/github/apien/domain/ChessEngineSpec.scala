package com.github.apien.domain

import cats.syntax.either._
import cats.syntax.option._
import com.github.apien.test.ChessSpec
import com.github.chess.apien.domain.model.PieceColor.{Black, White}
import com.github.chess.apien.domain.model.PieceType.Pawn
import com.github.chess.apien.domain.model._
import com.github.chess.apien.domain.{ChessEngine, MoveError, MoveSuccess}

class ChessEngineSpec extends ChessSpec {

  private val gameEngine = ChessEngine.initial
  "GameEngine.makeMove" should "return EmptyField error when user try to move from empty field" in {
    gameEngine.applyMove(Move(Coordinate(1, 4), Coordinate(1, 3)), White) shouldBe MoveError.EmptyField.asLeft
  }

  it should "return NotYourTurn error when source field contains a piece but other color" in {
    gameEngine.applyMove(Move(Coordinate(0, 6), Coordinate(0, 4)), Black) shouldBe MoveError.NotYourTurn.asLeft
  }

  it should "move the piece to the vacant square" in {
    val source = Coordinate(3, 6)
    val destination = Coordinate(3, 5)
    gameEngine.applyMove(Move(source, destination), White) shouldBe MoveSuccess.Moved.asRight

    gameEngine.state.squares should have size 32
    gameEngine.state.squares.get(source) shouldBe None
    gameEngine.state.squares.get(destination) shouldBe Piece(Pawn(), White).some
  }

  it should "capture the enemy piece" in {
    val board = new Board(
      Map(
        Coordinate(3, 6) -> Piece(Pawn(), White),
        Coordinate(4, 5) -> Piece(Pawn(), Black)
      )
    )
    val engine = ChessEngine.create(board)
    val source = Coordinate(3, 6)
    val destination = Coordinate(4, 5)

    engine.applyMove(Move(source, destination), PieceColor.White) shouldBe MoveSuccess.Captured.asRight
    engine.state.squares should have size 1
    engine.state.squares.get(source) shouldBe None
    engine.state.squares.get(destination) shouldBe Piece(Pawn(), White).some
  }

}
