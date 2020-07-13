package com.github.apien.domain

import cats.syntax.either._
import cats.syntax.option._
import com.github.apien.test.ChessSpec
import com.github.chess.apien.domain.model.PieceColor.{Black, White}
import com.github.chess.apien.domain.model.PieceType.{King, Pawn, Rook}
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

  it should "not allow to move a king when it put him in check" in {
    val board = new Board(
      Map(
        Coordinate(7, 0) -> Piece(King(), Black),
        Coordinate(0, 7) -> Piece(King(), White),
        Coordinate(6, 7) -> Piece(Rook(), White)
      )
    )
    val engine = ChessEngine.create(board)

    engine.applyMove(Move(Coordinate(7, 0), Coordinate(6, 0)), PieceColor.Black) shouldBe MoveError.KingInCheck.asLeft
    engine.state shouldBe board
  }

  it should "not allow to even other a piece  when it put own king in check" in {
    val board = new Board(
      Map(
        Coordinate(7, 0) -> Piece(King(), Black),
        Coordinate(7, 1) -> Piece(Rook(), Black),
        Coordinate(0, 7) -> Piece(King(), White),
        Coordinate(7, 7) -> Piece(Rook(), White)
      )
    )
    val engine = ChessEngine.create(board)

    engine.applyMove(Move(Coordinate(7, 1), Coordinate(6, 1)), PieceColor.Black) shouldBe MoveError.KingInCheck.asLeft
    engine.state shouldBe board
  }

  it should "allow to move another piece to avoid the in check" in {
    val board = new Board(
      Map(
        Coordinate(7, 0) -> Piece(King(), Black),
        Coordinate(7, 1) -> Piece(Rook(), Black),
        Coordinate(0, 7) -> Piece(King(), White),
        Coordinate(7, 7) -> Piece(Rook(), White)
      )
    )
    val engine = ChessEngine.create(board)

    engine.applyMove(Move(Coordinate(7, 1), Coordinate(7, 7)), PieceColor.Black) shouldBe MoveSuccess.Captured.asRight
    engine.state.squares shouldBe Map(
      Coordinate(7, 0) -> Piece(King(), Black),
      Coordinate(0, 7) -> Piece(King(), White),
      Coordinate(7, 7) -> Piece(Rook(), Black)
    )
  }
}
