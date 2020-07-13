package com.github.apien.domain

import cats.syntax.option._
import com.github.apien.test.ChessSpec
import com.github.chess.apien.domain.CheckVerifier
import com.github.chess.apien.domain.model.PieceColor.{Black, White}
import com.github.chess.apien.domain.model.PieceType.{King, Pawn}
import com.github.chess.apien.domain.model.{Board, Coordinate, Move, Piece}

class CheckVerifierSpec extends ChessSpec {

  "CheckVerifier" should "return None when there is no in check" in {
    CheckVerifier.check(Board.initial, White) shouldBe None
  }

  it should "return move which threat a king" in {
    val board = new Board(
      Map(
        Coordinate(3, 5) -> Piece(Pawn(), White),
        Coordinate(2, 4) -> Piece(King(), Black),
        Coordinate(7, 7) -> Piece(King(), White)
      )
    )

    CheckVerifier.check(board, Black) shouldBe Move(Coordinate(3, 5), Coordinate(2, 4)).some
    CheckVerifier.check(board, White) shouldBe None
  }

}
