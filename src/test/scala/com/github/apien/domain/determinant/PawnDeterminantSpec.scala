package com.github.apien.domain.determinant

import com.github.apien.test.ChessSpec
import com.github.chess.apien.domain.determinant.MoveDeterminant.MoveType
import com.github.chess.apien.domain.determinant.MoveDeterminant.MoveType.{Capture, Vacant}
import com.github.chess.apien.domain.determinant.PawnDeterminant
import com.github.chess.apien.domain.model.PieceColor.{Black, White}
import com.github.chess.apien.domain.model.PieceType.Pawn
import com.github.chess.apien.domain.model.{Board, Coordinate, Piece}

class PawnDeterminantSpec extends ChessSpec {

  private val moveDetermination = new PawnDeterminant()

  "PawnMovementValidation" should "allow to move 2 boxes forward as first move for a white pawn" in {
    implicit val baord = new Board(
      Map(
        Coordinate(5, 6) -> Piece(Pawn(), White)
      )
    )

    moveDetermination.validate(Coordinate(5, 6), White) shouldBe Set(
      Coordinate(5, 5) -> Vacant,
      Coordinate(5, 4) -> Vacant
    )
  }

  it should "allow to move one box forward when it is not fist move (white pawn)" in {
    implicit val baord = new Board(
      Map(
        Coordinate(5, 5) -> Piece(Pawn(), White)
      )
    )

    moveDetermination.validate(Coordinate(5, 5), White) shouldBe Set(Coordinate(5, 4) -> Vacant)
  }

  it should "allow to move 2 boxes forward as first move for a black pawn" in {
    implicit val baord = new Board(
      Map(
        Coordinate(4, 1) -> Piece(Pawn(), Black)
      )
    )
    moveDetermination.validate(Coordinate(4, 1), Black) shouldBe Set(
      Coordinate(4, 2) -> Vacant,
      Coordinate(4, 3) -> Vacant
    )
  }

  it should "allow to move one box forward when it is not fist move (black pawn)" in {
    implicit val baord = new Board(
      Map(
        Coordinate(4, 2) -> Piece(Pawn(), Black)
      )
    )
    moveDetermination.validate(Coordinate(4, 2), Black) shouldBe Set(
      Coordinate(4, 3) -> Vacant
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
      Coordinate(4, 2) -> MoveType.Vacant,
      Coordinate(4, 3) -> MoveType.Vacant,
      Coordinate(3, 2) -> MoveType.Capture(Pawn()),
      Coordinate(5, 2) -> MoveType.Capture(Pawn())
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
      Coordinate(5, 5) -> Vacant,
      Coordinate(5, 4) -> Vacant,
      Coordinate(4, 5) -> Capture(Pawn()),
      Coordinate(6, 5) -> Capture(Pawn())
    )
  }
}
