package com.github.apien.ui

import com.github.apien.test.ChessSpec
import com.github.chess.apien.domain.model.PieceColor.{Black, White}
import com.github.chess.apien.domain.model.PieceType._
import com.github.chess.apien.domain.model.{Piece, PieceType}
import com.github.chess.apien.ui.PieceShow

class PieceShowSpec extends ChessSpec {

  private def white(pieceType: PieceType): Piece = Piece(pieceType, White)

  private def black(pieceType: PieceType): Piece = Piece(pieceType, Black)

  private val pieceShow = new PieceShow
  "PieceShow" should "describe a white pawn as 'P'" in {
    pieceShow.show(white(Pawn())) shouldBe "P"
  }

  it should "describe a white knight as 'N" in {
    pieceShow.show(white(Knight())) shouldBe "N"
  }

  it should "describe a white bishop as 'B" in {
    pieceShow.show(white(Bishop())) shouldBe "B"
  }

  it should "describe a white rook as 'R" in {
    pieceShow.show(white(Rook())) shouldBe "R"
  }

  it should "describe a white queen as 'Q" in {
    pieceShow.show(white(Queen())) shouldBe "Q"
  }

  it should "describe a white king as 'K" in {
    pieceShow.show(white(King())) shouldBe "K"
  }

  it should "describe a black pawn as 'P'" in {
    pieceShow.show(black(Pawn())) shouldBe "p"
  }

  it should "describe a black knight as 'n" in {
    pieceShow.show(black(Knight())) shouldBe "n"
  }

  it should "describe a black bishop as 'b" in {
    pieceShow.show(black(Bishop())) shouldBe "b"
  }

  it should "describe a black rook as 'r" in {
    pieceShow.show(black(Rook())) shouldBe "r"
  }

  it should "describe a black queen as 'q" in {
    pieceShow.show(black(Queen())) shouldBe "q"
  }

  it should "describe a black king as 'k" in {
    pieceShow.show(black(King())) shouldBe "k"
  }


}
