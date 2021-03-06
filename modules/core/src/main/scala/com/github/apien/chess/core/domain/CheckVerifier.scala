package com.github.apien.chess.core.domain

import com.github.apien.chess.core.domain.determinant.MoveDeterminant
import com.github.apien.chess.core.domain.determinant.MoveDeterminant.MoveType
import com.github.apien.chess.core.domain.model.PieceType.King
import com.github.apien.chess.core.domain.model.{Board, Move, PieceColor}

object CheckVerifier {

  /**
   * Check if the given king is in check state.
   *
   * @param board Current state of chess board.
   * @param color Color of the king for which find the in check move.
   * @return None if there is no possible in check in current state otherwise a move which is going to capture the king.
   */
  def check(board: Board, color: PieceColor): Option[Move] =
    board.squares
      .filter { case (_, piece) => piece.color != color }
      .toList
      .flatMap {
        case (pieceCord, piece) =>
          MoveDeterminant
            .getMoves(piece.kind, pieceCord, piece.color, board)
            .map { case (destinationCord, pieceType) => model.Move(pieceCord, destinationCord) -> pieceType }
      }
      .find { case (_, moveType) => moveType == MoveType.Capture(King()) }
      .map { case (moveToCheck, _) => moveToCheck }

}
