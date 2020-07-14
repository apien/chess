package com.github.apien.chess.core.domain

import cats.syntax.either._
import cats.syntax.option._
import com.github.apien.chess.core.domain.MoveSuccess.Moved
import com.github.apien.chess.core.domain.model.PieceColor.{Black, White}
import com.github.apien.chess.core.domain.model.PieceType.{King, Pawn, Rook}
import com.github.apien.chess.core.domain.model.{Board, Coordinate, Move, Piece}
import com.github.apien.chess.core.test.ChessSpec

class ChessEngineSpec extends ChessSpec {

  "GameEngine.makeMove" should "return EmptyField error when user try to move from empty field" in new BoardFixture() {
    engine.applyMove(Move(Coordinate(1, 4), model.Coordinate(1, 3))) shouldBe MoveError.EmptyField.asLeft
  }

  it should "return NotYourTurn error when a black piece attempted to begin a game" in new BoardFixture() {
    engine.applyMove(Move(model.Coordinate(2, 1), model.Coordinate(2, 2))) shouldBe MoveError.NotYourTurn.asLeft
  }

  it should "return NotYourTurn error when source field contains a piece but it is turn of the other player" in new BoardFixture() {
    engine.applyMove(Move(model.Coordinate(0, 6), model.Coordinate(0, 4))) shouldBe Moved.asRight
    engine.applyMove(Move(model.Coordinate(1, 6), model.Coordinate(1, 4))) shouldBe MoveError.NotYourTurn.asLeft
  }

  it should "move the piece to the vacant square" in new BoardFixture() {
    val source = model.Coordinate(3, 6)
    val destination = model.Coordinate(3, 5)
    engine.applyMove(Move(source, destination)) shouldBe MoveSuccess.Moved.asRight

    engine.state.squares should have size 32
    engine.state.squares.get(source) shouldBe None
    engine.state.squares.get(destination) shouldBe Piece(Pawn(), White).some
  }

  it should "capture the enemy piece" in new BoardFixture(
    new Board(
      Map(
        model.Coordinate(3, 6) -> Piece(Pawn(), White),
        model.Coordinate(4, 5) -> Piece(Pawn(), Black)
      )
    )
  ) {
    val source = model.Coordinate(3, 6)
    val destination = model.Coordinate(4, 5)

    engine.applyMove(Move(source, destination)) shouldBe MoveSuccess.Captured.asRight
    engine.state.squares should have size 1
    engine.state.squares.get(source) shouldBe None
    engine.state.squares.get(destination) shouldBe Piece(Pawn(), White).some
  }

  it should "not allow to move a king when it put him in check" in new BoardFixture(
    new Board(
      Map(
        model.Coordinate(7, 0) -> Piece(King(), White),
        model.Coordinate(0, 7) -> Piece(King(), Black),
        model.Coordinate(6, 7) -> Piece(Rook(), Black)
      )
    )
  ) {
    engine.applyMove(Move(model.Coordinate(7, 0), model.Coordinate(6, 0))) shouldBe MoveError.KingInCheck.asLeft
    engine.state shouldBe board
  }

  it should "not allow to even other a piece  when it put own king in check" in new BoardFixture(
    new Board(
      Map(
        model.Coordinate(7, 0) -> Piece(King(), White),
        model.Coordinate(7, 1) -> Piece(Rook(), White),
        model.Coordinate(0, 7) -> Piece(King(), Black),
        model.Coordinate(7, 7) -> Piece(Rook(), Black)
      )
    )
  ) {
    engine.applyMove(Move(model.Coordinate(7, 1), model.Coordinate(6, 1))) shouldBe MoveError.KingInCheck.asLeft
    engine.state shouldBe board
  }

  it should "allow to move another piece to avoid the in check" in new BoardFixture(
    new Board(
      Map(
        model.Coordinate(7, 0) -> Piece(King(), White),
        model.Coordinate(7, 1) -> Piece(Rook(), White),
        model.Coordinate(0, 7) -> Piece(King(), Black),
        model.Coordinate(7, 7) -> Piece(Rook(), Black)
      )
    )
  ) {
    engine.applyMove(Move(model.Coordinate(7, 1), model.Coordinate(7, 7))) shouldBe MoveSuccess.Captured.asRight
    engine.state.squares shouldBe Map(
      model.Coordinate(7, 0) -> Piece(King(), White),
      model.Coordinate(0, 7) -> Piece(King(), Black),
      model.Coordinate(7, 7) -> Piece(Rook(), White)
    )
  }

  abstract class BoardFixture(val board: Board = Board.initial) {
    val engine: ChessEngine = ChessEngine.create(board)

  }

}
