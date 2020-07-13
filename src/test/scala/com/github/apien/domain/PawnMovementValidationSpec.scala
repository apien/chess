package com.github.apien.domain

import com.github.apien.test.ChessSpec
import com.github.chess.apien.domain.MoveDetermination.MoveType
import com.github.chess.apien.domain.MoveDetermination.MoveType.{Captured, Moved}
import com.github.chess.apien.domain.PawnMovementDetermination
import com.github.chess.apien.domain.model.PieceColor.{Black, White}
import com.github.chess.apien.domain.model.PieceType.Pawn
import com.github.chess.apien.domain.model.{Board, Coordinate, Piece}

class PawnMovementValidationSpec extends ChessSpec {

  private val moveDetermination = new PawnMovementDetermination()

  "PawnMovementValidation" should "allow to move 2 boxes forward as first move for a white pawn" in {
    implicit val baord = new Board(
      Map(
        Coordinate(5, 6) -> Piece(Pawn(), White)
      )
    )

    moveDetermination.validate(Coordinate(5, 6), White) shouldBe Set(
      Coordinate(5, 5) -> Moved,
      Coordinate(5, 4) -> Moved
    )
  }

  it should "allow to move one box forward when it is not fist move (white pawn)" in {
    implicit val baord = new Board(
      Map(
        Coordinate(5, 5) -> Piece(Pawn(), White)
      )
    )

    moveDetermination.validate(Coordinate(5, 5), White) shouldBe Set(Coordinate(5, 4) -> Moved)
  }

  it should "allow to move 2 boxes forward as first move for a black pawn" in {
    implicit val baord = new Board(
      Map(
        Coordinate(4, 1) -> Piece(Pawn(), Black)
      )
    )
    moveDetermination.validate(Coordinate(4, 1), Black) shouldBe Set(
      Coordinate(4, 2) -> Moved,
      Coordinate(4, 3) -> Moved
    )
  }

  it should "allow to move one box forward when it is not fist move (black pawn)" in {
    implicit val baord = new Board(
      Map(
        Coordinate(4, 2) -> Piece(Pawn(), Black)
      )
    )
    moveDetermination.validate(Coordinate(4, 2), Black) shouldBe Set(
      Coordinate(4, 3) -> Moved
    )
  }

  it should "do not allow move forward when there is another opponent pine" in {
    implicit val baord = new Board(
      Map(
        Coordinate(4, 1) -> Piece(Pawn(), Black),
        Coordinate(4, 2) -> Piece(Pawn(), White)
      )
    )
    moveDetermination.validate(Coordinate(4, 1), Black) shouldBe Set()
  }

  it should "do not allow move forward when there is own pine" in {
    implicit val baord = new Board(
      Map(
        Coordinate(4, 1) -> Piece(Pawn(), Black),
        Coordinate(4, 2) -> Piece(Pawn(), Black)
      )
    )
    moveDetermination.validate(Coordinate(4, 1), Black) shouldBe Set()
  }

  it should "allow to capture opponent piece on the diagonal (black pawn)" in {
    implicit val baord = new Board(
      Map(
        Coordinate(4, 1) -> Piece(Pawn(), Black),
        Coordinate(3, 2) -> Piece(Pawn(), White),
        Coordinate(5, 2) -> Piece(Pawn(), White)
      )
    )
    moveDetermination.validate(Coordinate(4, 1), Black) shouldBe Set(
      Coordinate(4, 2) -> MoveType.Moved,
      Coordinate(4, 3) -> MoveType.Moved,
      Coordinate(3, 2) -> MoveType.Captured(Pawn()),
      Coordinate(5, 2) -> MoveType.Captured(Pawn())
    )
  }

  it should "allow to capture opponent piece on the diagonal (white pawn)" in {
    implicit val baord = new Board(
      Map(
        Coordinate(5, 6) -> Piece(Pawn(), White),
        Coordinate(4, 5) -> Piece(Pawn(), Black),
        Coordinate(6, 5) -> Piece(Pawn(), Black)
      )
    )

    moveDetermination.validate(Coordinate(5, 6), White) shouldBe Set(
      Coordinate(5, 5) -> Moved,
      Coordinate(5, 4) -> Moved,
      Coordinate(4, 5) -> Captured(Pawn()),
      Coordinate(6, 5) -> Captured(Pawn())
    )
  }
}
