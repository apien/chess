package com.github.apien.chess.core.domain.determinant

import com.github.apien.chess.core.domain.determinant.MoveDeterminant.MoveType.{Capture, Vacant}
import com.github.apien.chess.core.domain.model
import com.github.apien.chess.core.domain.model.PieceColor.{Black, White}
import com.github.apien.chess.core.domain.model.PieceType.{King, Pawn}
import com.github.apien.chess.core.domain.model.{Board, Coordinate, Piece}
import com.github.apien.chess.core.test.ChessSpec

class KingDeterminantSpec extends ChessSpec {

  private val determination = new KingDeterminant
  "KingMovementDetermination" should "allows to move horizontally" in {
    implicit val baord = new Board(
      Map(
        Coordinate(3, 4) -> Piece(King(), White)
      )
    )
    determination.validate(model.Coordinate(3, 4), White) shouldBe Set(
      model.Coordinate(3, 3) -> Vacant,
      model.Coordinate(4, 3) -> Vacant,
      model.Coordinate(4, 4) -> Vacant,
      model.Coordinate(4, 5) -> Vacant,
      model.Coordinate(3, 5) -> Vacant,
      model.Coordinate(2, 5) -> Vacant,
      model.Coordinate(2, 4) -> Vacant,
      model.Coordinate(2, 3) -> Vacant
    )
  }

  it should "return information about available piece to capture" in {
    implicit val baord = new Board(
      Map(
        model.Coordinate(3, 4) -> Piece(King(), White),
        model.Coordinate(4, 3) -> Piece(Pawn(), Black),
        model.Coordinate(3, 5) -> Piece(Pawn(), Black)
      )
    )
    determination.validate(model.Coordinate(3, 4), White) shouldBe Set(
      model.Coordinate(3, 3) -> Vacant,
      model.Coordinate(4, 3) -> Capture(Pawn()),
      model.Coordinate(4, 4) -> Vacant,
      model.Coordinate(4, 5) -> Vacant,
      model.Coordinate(3, 5) -> Capture(Pawn()),
      model.Coordinate(2, 5) -> Vacant,
      model.Coordinate(2, 4) -> Vacant,
      model.Coordinate(2, 3) -> Vacant
    )
  }

  it should "not allow to move on field occupied by onw piece" in {
    implicit val baord = new Board(
      Map(
        model.Coordinate(3, 4) -> Piece(King(), White),
        model.Coordinate(2, 3) -> Piece(Pawn(), White),
        model.Coordinate(4, 5) -> Piece(Pawn(), White)
      )
    )
    determination.validate(model.Coordinate(3, 4), White) shouldBe Set(
      model.Coordinate(3, 3) -> Vacant,
      model.Coordinate(4, 3) -> Vacant,
      model.Coordinate(4, 4) -> Vacant,
      model.Coordinate(3, 5) -> Vacant,
      model.Coordinate(2, 5) -> Vacant,
      model.Coordinate(2, 4) -> Vacant
    )
  }

}
