package com.github.apien.chess.core.domain.model

import com.github.apien.chess.core.domain.model
import com.github.apien.chess.core.domain.model.PieceType._
import com.github.apien.chess.core.test.ChessSpec

class BoardSpec extends ChessSpec {

  "Board initial state of the board" should "contains 32 pieces at the beginning" in {
    Board.initialSquares should have size 32
  }

  it should "set up properly a initial status of the chessboard" in {
    Board.initialSquares shouldBe Map[Coordinate, Piece](
      //Second row of black pieces,
      Coordinate(0, 0) -> blackPiece(Rook()),
      model.Coordinate(1, 0) -> blackPiece(Knight()),
      model.Coordinate(2, 0) -> blackPiece(Bishop()),
      model.Coordinate(3, 0) -> blackPiece(Queen()),
      model.Coordinate(4, 0) -> blackPiece(King()),
      model.Coordinate(5, 0) -> blackPiece(Bishop()),
      model.Coordinate(6, 0) -> blackPiece(Knight()),
      model.Coordinate(7, 0) -> blackPiece(Rook()),
      //Front row of black pieces
      model.Coordinate(0, 1) -> blackPiece(Pawn()),
      model.Coordinate(1, 1) -> blackPiece(Pawn()),
      model.Coordinate(2, 1) -> blackPiece(Pawn()),
      model.Coordinate(3, 1) -> blackPiece(Pawn()),
      model.Coordinate(4, 1) -> blackPiece(Pawn()),
      model.Coordinate(5, 1) -> blackPiece(Pawn()),
      model.Coordinate(6, 1) -> blackPiece(Pawn()),
      model.Coordinate(7, 1) -> blackPiece(Pawn()),
      //Front row of white pieces
      model.Coordinate(0, 6) -> whitePiece(Pawn()),
      model.Coordinate(1, 6) -> whitePiece(Pawn()),
      model.Coordinate(2, 6) -> whitePiece(Pawn()),
      model.Coordinate(3, 6) -> whitePiece(Pawn()),
      model.Coordinate(4, 6) -> whitePiece(Pawn()),
      model.Coordinate(5, 6) -> whitePiece(Pawn()),
      model.Coordinate(6, 6) -> whitePiece(Pawn()),
      model.Coordinate(7, 6) -> whitePiece(Pawn()),
      //Second row of white pieces
      model.Coordinate(0, 7) -> whitePiece(Rook()),
      model.Coordinate(1, 7) -> whitePiece(Knight()),
      model.Coordinate(2, 7) -> whitePiece(Bishop()),
      model.Coordinate(3, 7) -> whitePiece(Queen()),
      model.Coordinate(4, 7) -> whitePiece(King()),
      model.Coordinate(5, 7) -> whitePiece(Bishop()),
      model.Coordinate(6, 7) -> whitePiece(Knight()),
      model.Coordinate(7, 7) -> whitePiece(Rook())
    )
  }

  private def whitePiece = Piece(_, PieceColor.White)

  private def blackPiece = Piece(_, PieceColor.Black)
}
