package com.github.apien.chess.core.domain.determinant

import cats.syntax.option._
import com.github.apien.chess.core.domain.determinant.MoveDeterminant.MoveType
import com.github.apien.chess.core.domain.model
import com.github.apien.chess.core.domain.model.PieceColor.{Black, White}
import com.github.apien.chess.core.domain.model.PieceType.Pawn
import com.github.apien.chess.core.domain.model.{Board, Coordinate, Piece, PieceType}
import com.github.apien.chess.core.test.ChessSpec

class MoveDeterminantSpec extends ChessSpec {

  "Determine available moves horizontally" should "" in {
    implicit val board = Board(
      Map(
        Coordinate(1, 4) -> Piece(Pawn(), White),
        model.Coordinate(7, 4) -> Piece(Pawn(), Black)
      )
    )
    MoveDeterminant.horizontally(model.Coordinate(3, 4), Black) shouldBe
      List(
        model.Coordinate(2, 4) -> MoveType.Vacant,
        model.Coordinate(1, 4) -> MoveType.Capture(PieceType.Pawn()),
        model.Coordinate(4, 4) -> MoveType.Vacant,
        model.Coordinate(5, 4) -> MoveType.Vacant,
        model.Coordinate(6, 4) -> MoveType.Vacant
      )
  }

  it should "apply limit to the move step" in {
    implicit val board = Board(
      Map(
        model.Coordinate(1, 4) -> Piece(Pawn(), White),
        model.Coordinate(7, 4) -> Piece(Pawn(), Black)
      )
    )
    MoveDeterminant.horizontally(model.Coordinate(3, 4), Black, 1.some) shouldBe
      List(
        model.Coordinate(2, 4) -> MoveType.Vacant,
        model.Coordinate(4, 4) -> MoveType.Vacant
      )
  }

  "Determine available moves vertically" should "" in {
    implicit val board = Board(
      Map(
        model.Coordinate(3, 7) -> Piece(Pawn(), White),
        model.Coordinate(3, 1) -> Piece(Pawn(), Black)
      )
    )

    MoveDeterminant.vertically(model.Coordinate(3, 4), White) shouldBe
      List(
        model.Coordinate(3, 3) -> MoveType.Vacant,
        model.Coordinate(3, 2) -> MoveType.Vacant,
        model.Coordinate(3, 1) -> MoveType.Capture(PieceType.Pawn()),
        model.Coordinate(3, 5) -> MoveType.Vacant,
        model.Coordinate(3, 6) -> MoveType.Vacant
      )

  }

  it should "restrict move limit" in {
    implicit val board = Board(
      Map(
        model.Coordinate(3, 7) -> Piece(Pawn(), White),
        model.Coordinate(3, 1) -> Piece(Pawn(), Black)
      )
    )

    MoveDeterminant.vertically(model.Coordinate(3, 4), White, 1.some) shouldBe
      List(
        model.Coordinate(3, 3) -> MoveType.Vacant,
        model.Coordinate(3, 5) -> MoveType.Vacant
      )
  }

  "Determine" should "should restrict boundaries of the board" in {
    implicit val board = Board(
      Map(
        model.Coordinate(3, 4) -> Piece(Pawn(), White)
      )
    )

    MoveDeterminant.diagonally(model.Coordinate(3, 4), White) shouldBe
      List(
        model.Coordinate(2, 3) -> MoveType.Vacant,
        model.Coordinate(1, 2) -> MoveType.Vacant,
        model.Coordinate(0, 1) -> MoveType.Vacant,
        model.Coordinate(4, 5) -> MoveType.Vacant,
        model.Coordinate(5, 6) -> MoveType.Vacant,
        model.Coordinate(6, 7) -> MoveType.Vacant,
        model.Coordinate(4, 3) -> MoveType.Vacant,
        model.Coordinate(5, 2) -> MoveType.Vacant,
        model.Coordinate(6, 1) -> MoveType.Vacant,
        model.Coordinate(7, 0) -> MoveType.Vacant,
        model.Coordinate(2, 5) -> MoveType.Vacant,
        model.Coordinate(1, 6) -> MoveType.Vacant,
        model.Coordinate(0, 7) -> MoveType.Vacant
      )
  }

  it should "restrict opponents pieces on the walk" in {
    implicit val board = Board(
      Map(
        model.Coordinate(3, 4) -> Piece(Pawn(), White),
        model.Coordinate(1, 2) -> Piece(Pawn(), Black),
        model.Coordinate(4, 3) -> Piece(Pawn(), Black),
        model.Coordinate(0, 7) -> Piece(Pawn(), Black),
        model.Coordinate(5, 6) -> Piece(Pawn(), Black)
      )
    )

    MoveDeterminant.diagonally(model.Coordinate(3, 4), White) shouldBe
      List(
        model.Coordinate(2, 3) -> MoveType.Vacant,
        model.Coordinate(1, 2) -> MoveType.Capture(Pawn()),
        model.Coordinate(4, 5) -> MoveType.Vacant,
        model.Coordinate(5, 6) -> MoveType.Capture(Pawn()),
        model.Coordinate(4, 3) -> MoveType.Capture(Pawn()),
        model.Coordinate(2, 5) -> MoveType.Vacant,
        model.Coordinate(1, 6) -> MoveType.Vacant,
        model.Coordinate(0, 7) -> MoveType.Capture(Pawn())
      )
  }

  it should "restrict the given limit" in {
    implicit val board = Board(
      Map(
        model.Coordinate(3, 4) -> Piece(Pawn(), White)
      )
    )

    MoveDeterminant.diagonally(model.Coordinate(3, 4), White, 1.some) shouldBe
      List(
        model.Coordinate(2, 3) -> MoveType.Vacant,
        model.Coordinate(4, 5) -> MoveType.Vacant,
        model.Coordinate(4, 3) -> MoveType.Vacant,
        model.Coordinate(2, 5) -> MoveType.Vacant
      )
  }

  it should "restrict own pieces on the path" in {
    //TODO implement it!
    ignore
  }
}
