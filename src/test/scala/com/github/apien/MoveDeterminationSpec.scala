package com.github.apien

import cats.syntax.option._
import com.github.apien.test.ChessSpec
import com.github.chess.apien.domain.MoveDetermination
import com.github.chess.apien.domain.MoveDetermination.MoveType
import com.github.chess.apien.domain.model.PieceColor.{Black, White}
import com.github.chess.apien.domain.model.PieceType.Pawn
import com.github.chess.apien.domain.model.{Board, Coordinate, Piece, PieceType}
class MoveDeterminationSpec extends ChessSpec {

  "Determine available moves horizontally" should "" in {
    implicit val board = Board(
      Map(
        Coordinate(1, 4) -> Piece(Pawn(), White),
        Coordinate(7, 4) -> Piece(Pawn(), Black)
      )
    )
    MoveDetermination.horizontally(Coordinate(3, 4), Black) shouldBe
      List(
        Coordinate(2, 4) -> MoveType.Moved,
        Coordinate(1, 4) -> MoveType.Captured(PieceType.Pawn()),
        Coordinate(4, 4) -> MoveType.Moved,
        Coordinate(5, 4) -> MoveType.Moved,
        Coordinate(6, 4) -> MoveType.Moved
      )
  }

  it should "apply limit to the move step" in {
    implicit val board = Board(
      Map(
        Coordinate(1, 4) -> Piece(Pawn(), White),
        Coordinate(7, 4) -> Piece(Pawn(), Black)
      )
    )
    MoveDetermination.horizontally(Coordinate(3, 4), Black, 1.some) shouldBe
      List(
        Coordinate(2, 4) -> MoveType.Moved,
        Coordinate(4, 4) -> MoveType.Moved
      )
  }

  "Determine available moves vertically" should "" in {
    implicit val board = Board(
      Map(
        Coordinate(3, 7) -> Piece(Pawn(), White),
        Coordinate(3, 1) -> Piece(Pawn(), Black)
      )
    )

    MoveDetermination.vertically(Coordinate(3, 4), White) shouldBe
      List(
        Coordinate(3, 3) -> MoveType.Moved,
        Coordinate(3, 2) -> MoveType.Moved,
        Coordinate(3, 1) -> MoveType.Captured(PieceType.Pawn()),
        Coordinate(3, 5) -> MoveType.Moved,
        Coordinate(3, 6) -> MoveType.Moved
      )

  }

  it should "restrict move limit" in {
    implicit val board = Board(
      Map(
        Coordinate(3, 7) -> Piece(Pawn(), White),
        Coordinate(3, 1) -> Piece(Pawn(), Black)
      )
    )

    MoveDetermination.vertically(Coordinate(3, 4), White, 1.some) shouldBe
      List(
        Coordinate(3, 3) -> MoveType.Moved,
        Coordinate(3, 5) -> MoveType.Moved
      )
  }

  "Determine" should "should restrict boundaries of the board" in {
    implicit val board = Board(
      Map(
        Coordinate(3, 4) -> Piece(Pawn(), White)
      )
    )

    MoveDetermination.diagonally(Coordinate(3, 4), White) shouldBe
      List(
        Coordinate(2, 3) -> MoveType.Moved,
        Coordinate(1, 2) -> MoveType.Moved,
        Coordinate(0, 1) -> MoveType.Moved,
        Coordinate(4, 5) -> MoveType.Moved,
        Coordinate(5, 6) -> MoveType.Moved,
        Coordinate(6, 7) -> MoveType.Moved,
        Coordinate(4, 3) -> MoveType.Moved,
        Coordinate(5, 2) -> MoveType.Moved,
        Coordinate(6, 1) -> MoveType.Moved,
        Coordinate(7, 0) -> MoveType.Moved,
        Coordinate(2, 5) -> MoveType.Moved,
        Coordinate(1, 6) -> MoveType.Moved,
        Coordinate(0, 7) -> MoveType.Moved
      )
  }

  it should "restrict opponents pieces on the walk" in {
    implicit val board = Board(
      Map(
        Coordinate(3, 4) -> Piece(Pawn(), White),
        Coordinate(1, 2) -> Piece(Pawn(), Black),
        Coordinate(4, 3) -> Piece(Pawn(), Black),
        Coordinate(0, 7) -> Piece(Pawn(), Black),
        Coordinate(5, 6) -> Piece(Pawn(), Black)
      )
    )

    MoveDetermination.diagonally(Coordinate(3, 4), White) shouldBe
      List(
        Coordinate(2, 3) -> MoveType.Moved,
        Coordinate(1, 2) -> MoveType.Captured(Pawn()),
        Coordinate(4, 5) -> MoveType.Moved,
        Coordinate(5, 6) -> MoveType.Captured(Pawn()),
        Coordinate(4, 3) -> MoveType.Captured(Pawn()),
        Coordinate(2, 5) -> MoveType.Moved,
        Coordinate(1, 6) -> MoveType.Moved,
        Coordinate(0, 7) -> MoveType.Captured(Pawn())
      )
  }

  it should "restrict own pieces on the path" in {
    //TODO implement it!
    ignore
  }
}
