package com.github.apien.chess.core.domain.determinant

import com.github.apien.chess.core.domain.determinant.MoveDeterminant.MoveType.{Capture, Vacant}
import com.github.apien.chess.core.domain.model
import com.github.apien.chess.core.domain.model.PieceColor.{Black, White}
import com.github.apien.chess.core.domain.model.PieceType.{Knight, Pawn, Rook}
import com.github.apien.chess.core.domain.model.{Board, Coordinate, Piece}
import com.github.apien.chess.core.test.ChessSpec

class KnightDeterminantSpec extends ChessSpec {
  private val moveDetermination = new KnightDeterminant()

  "KnightMoveDetermination" should "determine all moves of a knight" in {
    implicit val baord = new Board(
      Map(
        Coordinate(3, 4) -> Piece(Knight(), White)
      )
    )
    moveDetermination.validate(model.Coordinate(3, 4), White) shouldBe Set(
      model.Coordinate(1, 3) -> Vacant,
      model.Coordinate(1, 5) -> Vacant,
      model.Coordinate(5, 5) -> Vacant,
      model.Coordinate(2, 2) -> Vacant,
      model.Coordinate(2, 6) -> Vacant,
      model.Coordinate(4, 6) -> Vacant,
      model.Coordinate(4, 2) -> Vacant,
      model.Coordinate(5, 3) -> Vacant
    )
  }

  it should "jump over other pieces" in {
    implicit val baord = new Board(
      Map(
        model.Coordinate(3, 4) -> Piece(Knight(), White),
        model.Coordinate(3, 3) -> Piece(Pawn(), Black),
        model.Coordinate(3, 2) -> Piece(Pawn(), Black),
        model.Coordinate(4, 3) -> Piece(Pawn(), Black),
        model.Coordinate(4, 4) -> Piece(Pawn(), Black),
        model.Coordinate(5, 4) -> Piece(Pawn(), Black),
        model.Coordinate(4, 5) -> Piece(Pawn(), Black),
        model.Coordinate(3, 5) -> Piece(Pawn(), Black),
        model.Coordinate(2, 5) -> Piece(Pawn(), Black),
        model.Coordinate(2, 4) -> Piece(Pawn(), Black),
        model.Coordinate(1, 4) -> Piece(Pawn(), Black),
        model.Coordinate(2, 3) -> Piece(Pawn(), Black)
      )
    )

    moveDetermination.validate(model.Coordinate(3, 4), White) shouldBe Set(
      model.Coordinate(1, 3) -> Vacant,
      model.Coordinate(1, 5) -> Vacant,
      model.Coordinate(5, 5) -> Vacant,
      model.Coordinate(2, 2) -> Vacant,
      model.Coordinate(2, 6) -> Vacant,
      model.Coordinate(4, 6) -> Vacant,
      model.Coordinate(4, 2) -> Vacant,
      model.Coordinate(5, 3) -> Vacant
    )
  }

  it should "return information about possible capture" in {
    {
      implicit val baord = new Board(
        Map(
          model.Coordinate(3, 4) -> Piece(Knight(), White),
          model.Coordinate(2, 6) -> Piece(Rook(), Black),
          model.Coordinate(4, 2) -> Piece(Pawn(), Black)
        )
      )
      moveDetermination.validate(model.Coordinate(3, 4), White) shouldBe Set(
        model.Coordinate(1, 3) -> Vacant,
        model.Coordinate(1, 5) -> Vacant,
        model.Coordinate(5, 5) -> Vacant,
        model.Coordinate(2, 2) -> Vacant,
        model.Coordinate(2, 6) -> Capture(Rook()),
        model.Coordinate(4, 6) -> Vacant,
        model.Coordinate(4, 2) -> Capture(Pawn()),
        model.Coordinate(5, 3) -> Vacant
      )
    }
  }

  it should "not allow to move where coordinate is occupied by piece of the same color" in {
    {
      implicit val baord = new Board(
        Map(
          model.Coordinate(3, 4) -> Piece(Knight(), White),
          model.Coordinate(2, 6) -> Piece(Rook(), White),
          model.Coordinate(4, 2) -> Piece(Pawn(), White)
        )
      )
      moveDetermination.validate(model.Coordinate(3, 4), White) shouldBe Set(
        model.Coordinate(1, 3) -> Vacant,
        model.Coordinate(1, 5) -> Vacant,
        model.Coordinate(5, 5) -> Vacant,
        model.Coordinate(2, 2) -> Vacant,
        model.Coordinate(4, 6) -> Vacant,
        model.Coordinate(5, 3) -> Vacant
      )
    }
  }

  it should "restrict chess board boundaries" in {
    implicit val baord = new Board(
      Map(
        model.Coordinate(3, 4) -> Piece(Knight(), White)
      )
    )
    moveDetermination.validate(model.Coordinate(1, 1), White) shouldBe Set(
      model.Coordinate(3, 0) -> Vacant,
      model.Coordinate(3, 2) -> Vacant,
      model.Coordinate(2, 3) -> Vacant,
      model.Coordinate(0, 3) -> Vacant
    )
  }

  //TODO write tests for another corner cases
}
