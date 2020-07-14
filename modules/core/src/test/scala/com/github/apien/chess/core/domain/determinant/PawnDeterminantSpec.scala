package com.github.apien.chess.core.domain.determinant

import com.github.apien.chess.core.domain.determinant.MoveDeterminant.MoveType
import com.github.apien.chess.core.domain.determinant.MoveDeterminant.MoveType.{Capture, Vacant}
import com.github.apien.chess.core.domain.model
import com.github.apien.chess.core.domain.model.PieceColor.{Black, White}
import com.github.apien.chess.core.domain.model.PieceType.Pawn
import com.github.apien.chess.core.domain.model.{Board, Coordinate, Piece}
import com.github.apien.chess.core.test.ChessSpec

class PawnDeterminantSpec extends ChessSpec {

  private val moveDetermination = new PawnDeterminant()

  "PawnMovementValidation" should "allow to move 2 boxes forward as first move for a white pawn" in {
    implicit val baord = new Board(
      Map(
        Coordinate(5, 6) -> Piece(Pawn(), White)
      )
    )

    moveDetermination.validate(model.Coordinate(5, 6), White) shouldBe Set(
      model.Coordinate(5, 5) -> Vacant,
      model.Coordinate(5, 4) -> Vacant
    )
  }

  it should "allow to move one box forward when it is not fist move (white pawn)" in {
    implicit val baord = new Board(
      Map(
        model.Coordinate(5, 5) -> Piece(Pawn(), White)
      )
    )

    moveDetermination.validate(model.Coordinate(5, 5), White) shouldBe Set(model.Coordinate(5, 4) -> Vacant)
  }

  it should "allow to move 2 boxes forward as first move for a black pawn" in {
    implicit val baord = new Board(
      Map(
        model.Coordinate(4, 1) -> Piece(Pawn(), Black)
      )
    )
    moveDetermination.validate(model.Coordinate(4, 1), Black) shouldBe Set(
      model.Coordinate(4, 2) -> Vacant,
      model.Coordinate(4, 3) -> Vacant
    )
  }

  it should "allow to move one box forward when it is not fist move (black pawn)" in {
    implicit val baord = new Board(
      Map(
        model.Coordinate(4, 2) -> Piece(Pawn(), Black)
      )
    )
    moveDetermination.validate(model.Coordinate(4, 2), Black) shouldBe Set(
      model.Coordinate(4, 3) -> Vacant
    )
  }

  it should "do not allow move forward when there is another opponent pine" in {
    implicit val baord = new Board(
      Map(
        model.Coordinate(4, 1) -> Piece(Pawn(), Black),
        model.Coordinate(4, 2) -> Piece(Pawn(), White)
      )
    )
    moveDetermination.validate(model.Coordinate(4, 1), Black) shouldBe Set()
  }

  it should "do not allow move forward when there is own pine" in {
    implicit val baord = new Board(
      Map(
        model.Coordinate(4, 1) -> Piece(Pawn(), Black),
        model.Coordinate(4, 2) -> Piece(Pawn(), Black)
      )
    )
    moveDetermination.validate(model.Coordinate(4, 1), Black) shouldBe Set()
  }

  it should "allow to capture opponent piece on the diagonal (black pawn)" in {
    implicit val baord = new Board(
      Map(
        model.Coordinate(4, 1) -> Piece(Pawn(), Black),
        model.Coordinate(3, 2) -> Piece(Pawn(), White),
        model.Coordinate(5, 2) -> Piece(Pawn(), White)
      )
    )
    moveDetermination.validate(model.Coordinate(4, 1), Black) shouldBe Set(
      model.Coordinate(4, 2) -> MoveType.Vacant,
      model.Coordinate(4, 3) -> MoveType.Vacant,
      model.Coordinate(3, 2) -> MoveType.Capture(Pawn()),
      model.Coordinate(5, 2) -> MoveType.Capture(Pawn())
    )
  }

  it should "allow to capture opponent piece on the diagonal (white pawn)" in {
    implicit val baord = new Board(
      Map(
        model.Coordinate(5, 6) -> Piece(Pawn(), White),
        model.Coordinate(4, 5) -> Piece(Pawn(), Black),
        model.Coordinate(6, 5) -> Piece(Pawn(), Black)
      )
    )

    moveDetermination.validate(model.Coordinate(5, 6), White) shouldBe Set(
      model.Coordinate(5, 5) -> Vacant,
      model.Coordinate(5, 4) -> Vacant,
      model.Coordinate(4, 5) -> Capture(Pawn()),
      model.Coordinate(6, 5) -> Capture(Pawn())
    )
  }
}
