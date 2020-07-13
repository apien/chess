package com.github.apien.domain.determinant

import com.github.apien.test.ChessSpec
import com.github.chess.apien.domain.determinant.KingDeterminant
import com.github.chess.apien.domain.determinant.MoveDeterminant.MoveType.{Capture, Vacant}
import com.github.chess.apien.domain.model.PieceColor.{Black, White}
import com.github.chess.apien.domain.model.PieceType.{King, Pawn}
import com.github.chess.apien.domain.model.{Board, Coordinate, Piece}

class KingDeterminantSpec extends ChessSpec {

  private val determination = new KingDeterminant
  "KingMovementDetermination" should "allows to move horizontally" in {
    implicit val baord = new Board(
      Map(
        Coordinate(3, 4) -> Piece(King(), White)
      )
    )
    determination.validate(Coordinate(3, 4), White) shouldBe Set(
      Coordinate(3, 3) -> Vacant,
      Coordinate(4, 3) -> Vacant,
      Coordinate(4, 4) -> Vacant,
      Coordinate(4, 5) -> Vacant,
      Coordinate(3, 5) -> Vacant,
      Coordinate(2, 5) -> Vacant,
      Coordinate(2, 4) -> Vacant,
      Coordinate(2, 3) -> Vacant
    )
  }

  it should "return information about available piece to capture" in {
    implicit val baord = new Board(
      Map(
        Coordinate(3, 4) -> Piece(King(), White),
        Coordinate(4, 3) -> Piece(Pawn(), Black),
        Coordinate(3, 5) -> Piece(Pawn(), Black)
      )
    )
    determination.validate(Coordinate(3, 4), White) shouldBe Set(
      Coordinate(3, 3) -> Vacant,
      Coordinate(4, 3) -> Capture(Pawn()),
      Coordinate(4, 4) -> Vacant,
      Coordinate(4, 5) -> Vacant,
      Coordinate(3, 5) -> Capture(Pawn()),
      Coordinate(2, 5) -> Vacant,
      Coordinate(2, 4) -> Vacant,
      Coordinate(2, 3) -> Vacant
    )
  }

  it should "not allow to move on field occupied by onw piece" in {
    implicit val baord = new Board(
      Map(
        Coordinate(3, 4) -> Piece(King(), White),
        Coordinate(2, 3) -> Piece(Pawn(), White),
        Coordinate(4, 5) -> Piece(Pawn(), White)
      )
    )
    determination.validate(Coordinate(3, 4), White) shouldBe Set(
      Coordinate(3, 3) -> Vacant,
      Coordinate(4, 3) -> Vacant,
      Coordinate(4, 4) -> Vacant,
      Coordinate(3, 5) -> Vacant,
      Coordinate(2, 5) -> Vacant,
      Coordinate(2, 4) -> Vacant
    )
  }

}
