package com.github.apien.domain.determinant

import com.github.apien.test.ChessSpec
import com.github.chess.apien.domain.determinant.KnightDeterminant
import com.github.chess.apien.domain.determinant.MoveDeterminant.MoveType.{Capture, Vacant}
import com.github.chess.apien.domain.model.PieceColor.{Black, White}
import com.github.chess.apien.domain.model.PieceType.{Knight, Pawn, Rook}
import com.github.chess.apien.domain.model.{Coordinate, _}

class KnightDeterminantSpec extends ChessSpec {
  private val moveDetermination = new KnightDeterminant()

  "KnightMoveDetermination" should "determine all moves of a knight" in {
    implicit val baord = new Board(
      Map(
        Coordinate(3, 4) -> Piece(Knight(), White)
      )
    )
    moveDetermination.validate(Coordinate(3, 4), White) shouldBe Set(
      Coordinate(1, 3) -> Vacant,
      Coordinate(1, 5) -> Vacant,
      Coordinate(5, 5) -> Vacant,
      Coordinate(2, 2) -> Vacant,
      Coordinate(2, 6) -> Vacant,
      Coordinate(4, 6) -> Vacant,
      Coordinate(4, 2) -> Vacant,
      Coordinate(5, 3) -> Vacant
    )
  }

  it should "jump over other pieces" in {
    implicit val baord = new Board(
      Map(
        Coordinate(3, 4) -> Piece(Knight(), White),
        Coordinate(3, 3) -> Piece(Pawn(), Black),
        Coordinate(3, 2) -> Piece(Pawn(), Black),
        Coordinate(4, 3) -> Piece(Pawn(), Black),
        Coordinate(4, 4) -> Piece(Pawn(), Black),
        Coordinate(5, 4) -> Piece(Pawn(), Black),
        Coordinate(4, 5) -> Piece(Pawn(), Black),
        Coordinate(3, 5) -> Piece(Pawn(), Black),
        Coordinate(2, 5) -> Piece(Pawn(), Black),
        Coordinate(2, 4) -> Piece(Pawn(), Black),
        Coordinate(1, 4) -> Piece(Pawn(), Black),
        Coordinate(2, 3) -> Piece(Pawn(), Black)
      )
    )

    moveDetermination.validate(Coordinate(3, 4), White) shouldBe Set(
      Coordinate(1, 3) -> Vacant,
      Coordinate(1, 5) -> Vacant,
      Coordinate(5, 5) -> Vacant,
      Coordinate(2, 2) -> Vacant,
      Coordinate(2, 6) -> Vacant,
      Coordinate(4, 6) -> Vacant,
      Coordinate(4, 2) -> Vacant,
      Coordinate(5, 3) -> Vacant
    )
  }

  it should "return information about possible capture" in {
    {
      implicit val baord = new Board(
        Map(
          Coordinate(3, 4) -> Piece(Knight(), White),
          Coordinate(2, 6) -> Piece(Rook(), Black),
          Coordinate(4, 2) -> Piece(Pawn(), Black)
        )
      )
      moveDetermination.validate(Coordinate(3, 4), White) shouldBe Set(
        Coordinate(1, 3) -> Vacant,
        Coordinate(1, 5) -> Vacant,
        Coordinate(5, 5) -> Vacant,
        Coordinate(2, 2) -> Vacant,
        Coordinate(2, 6) -> Capture(Rook()),
        Coordinate(4, 6) -> Vacant,
        Coordinate(4, 2) -> Capture(Pawn()),
        Coordinate(5, 3) -> Vacant
      )
    }
  }

  it should "not allow to move where coordinate is occupied by piece of the same color" in {
    {
      implicit val baord = new Board(
        Map(
          Coordinate(3, 4) -> Piece(Knight(), White),
          Coordinate(2, 6) -> Piece(Rook(), White),
          Coordinate(4, 2) -> Piece(Pawn(), White)
        )
      )
      moveDetermination.validate(Coordinate(3, 4), White) shouldBe Set(
        Coordinate(1, 3) -> Vacant,
        Coordinate(1, 5) -> Vacant,
        Coordinate(5, 5) -> Vacant,
        Coordinate(2, 2) -> Vacant,
        Coordinate(4, 6) -> Vacant,
        Coordinate(5, 3) -> Vacant
      )
    }
  }

  it should "restrict chess board boundaries" in {
    implicit val baord = new Board(
      Map(
        Coordinate(3, 4) -> Piece(Knight(), White)
      )
    )
    moveDetermination.validate(Coordinate(1, 1), White) shouldBe Set(
      Coordinate(3, 0) -> Vacant,
      Coordinate(3, 2) -> Vacant,
      Coordinate(2, 3) -> Vacant,
      Coordinate(0, 3) -> Vacant
    )
  }

  //TODO write tests for another corner cases
}
