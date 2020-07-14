package com.github.apien.chess.core.domain

import cats.syntax.option._
import com.github.apien.chess.core.domain.model.PieceColor.{Black, White}
import com.github.apien.chess.core.domain.model.PieceType.{King, Pawn}
import com.github.apien.chess.core.domain.model.{Board, Coordinate, Move, Piece}
import com.github.apien.chess.core.test.ChessSpec

class CheckVerifierSpec extends ChessSpec {

  "CheckVerifier" should "return None when there is no in check" in {
    CheckVerifier.check(Board.initial, White) shouldBe None
  }

  it should "return move which threat a king" in {
    val board = new Board(
      Map(
        Coordinate(3, 5) -> Piece(Pawn(), White),
        model.Coordinate(2, 4) -> Piece(King(), Black),
        model.Coordinate(7, 7) -> Piece(King(), White)
      )
    )

    CheckVerifier.check(board, Black) shouldBe Move(model.Coordinate(3, 5), model.Coordinate(2, 4)).some
    CheckVerifier.check(board, White) shouldBe None
  }

}
