package com.github.apien.domain

import cats.syntax.either._
import com.github.apien.test.ChessSpec
import com.github.chess.apien.domain.MoveSuccess.Moved
import com.github.chess.apien.domain.model.PieceColor.{Black, White}
import com.github.chess.apien.domain.model.PieceType.Pawn
import com.github.chess.apien.domain.model._
import com.github.chess.apien.domain.{MoveError, MoveSuccess, MovementValidator}

class PawnMovementValidatorSpec extends ChessSpec {

  private val validator = MovementValidator.pawnValidator
  private val board = Board(Board.initial)

  "PawnMovementValidator" should "do not allow to move backward for black pawn" in {
    validator.validate(board)(Piece(Pawn(), Black), Coordinate(1, 1), Coordinate(1, 0)) shouldBe MoveError.IllegalMove.asLeft
  }

  it should "do not allow to move backward for white pawn" in {
    validator.validate(board)(Piece(Pawn(), White), Coordinate(1, 6), Coordinate(1, 7)) shouldBe MoveError.IllegalMove.asLeft
  }

  it should "do not allow to move horizontally" in {
    //TODO implement it
    ignore
  }

  it should "do not allow to move when there is other pawn on the road" in {
    val myBoard = Board(
      Map(
        Coordinate(0, 1) -> Piece(Pawn(), PieceColor.Black),
        Coordinate(0, 2) -> Piece(PieceType.Bishop(), PieceColor.White)
      )
    )

    validator.validate(myBoard)(
      Piece(Pawn(), PieceColor.Black),
      Coordinate(0, 1),
      Coordinate(0, 3)
    ) shouldBe MoveError.IllegalMove.asLeft
  }

  it should "do not allow to move when there is other pawn on destination field" in {
    val myBoard = Board(
      Map(
        Coordinate(0, 1) -> Piece(Pawn(), PieceColor.Black),
        Coordinate(0, 2) -> Piece(PieceType.Bishop(), PieceColor.White)
      )
    )

    validator.validate(myBoard)(
      Piece(Pawn(), PieceColor.Black),
      Coordinate(0, 1),
      Coordinate(0, 2)
    ) shouldBe MoveError.IllegalMove.asLeft

  }

  it should "do not allow to move more than two fields forward" in {
    validator.validate(board)(Piece(Pawn(), White), Coordinate(1, 6), Coordinate(1, 3)) shouldBe MoveError.IllegalMove.asLeft
  }

  it should "do not allow move by diagonal when there is not other piece" in {
    val myBoard = Board(
      Map(
        Coordinate(4, 1) -> Piece(Pawn(), PieceColor.Black)
      )
    )

    validator.validate(myBoard)(
      Piece(Pawn(), PieceColor.Black),
      Coordinate(4, 1),
      Coordinate(3, 2)
    ) shouldBe MoveError.IllegalMove.asLeft
  }




  it should "allow to move one fields forward as first move for black spawn" in {
    validator.validate(board)(Piece(Pawn(), Black), Coordinate(5, 1), Coordinate(5, 2)) shouldBe Moved.asRight
  }

  it should "allow to move two fields forward as first move for white spawn" in {
    validator.validate(board)(Piece(Pawn(), White), Coordinate(5, 6), Coordinate(5, 5)) shouldBe Moved.asRight
  }

  it should "allow to move also two fields forward as first move for black spawn" in {
    validator.validate(board)(Piece(Pawn(), Black), Coordinate(5, 1), Coordinate(5, 3)) shouldBe Moved.asRight
  }

  it should "allow to move also two fields forward as first move for white spawn" in {
    validator.validate(board)(Piece(Pawn(), White), Coordinate(5, 6), Coordinate(5, 4)) shouldBe Moved.asRight
  }

  it should "return Captured object when the pawn captured other piece (right diagonal)" in {
    val myBoard = Board(
      Map(
        Coordinate(4, 1) -> Piece(Pawn(), PieceColor.Black),
        Coordinate(3, 2) -> Piece(PieceType.Bishop(), PieceColor.White)
      )
    )

    validator.validate(myBoard)(
      Piece(Pawn(), PieceColor.Black),
      Coordinate(4, 1),
      Coordinate(3, 2)
    ) shouldBe MoveSuccess.Captured.asRight
  }

  it should "return Captured object when the pawn captured other piece (left diagonal)" in {
    val myBoard = Board(
      Map(
        Coordinate(4, 1) -> Piece(Pawn(), PieceColor.Black),
        Coordinate(5, 2) -> Piece(PieceType.Bishop(), PieceColor.White)
      )
    )

    validator.validate(myBoard)(
      Piece(Pawn(), PieceColor.Black),
      Coordinate(4, 1),
      Coordinate(5, 2)
    ) shouldBe MoveSuccess.Captured.asRight
  }
}
