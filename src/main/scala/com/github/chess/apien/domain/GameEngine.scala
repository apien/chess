package com.github.chess.apien.domain

import com.github.chess.apien.domain.MoveError.{EmptyField, NotYourTurn}
import com.github.chess.apien.domain.model._

class GameEngine {
  private val chessBoard = Board.initial
  private val board = Board(chessBoard)

  def applyMove(move: Move, color: PieceColor): Either[MoveError, MoveSuccess] = {
    for {
      piece <- checkIfPineExists(move.source)
      _ <- validateColor(color, piece)
      result <- validateMove(board, piece, move)
    } yield result
  }

  private def checkIfPineExists(coordinate: Coordinate): Either[MoveError, Piece] =
    chessBoard
      .get(coordinate)
      .toRight(EmptyField)

  private def validateColor(color: PieceColor, piece: Piece): Either[MoveError, Piece] = {
    Either.cond(
      color == piece.color,
      piece,
      NotYourTurn
    )
  }

  def validateMove[T <: PieceType](board: Board, piece: Piece, move: Move)(
    implicit validator: MovementValidator[T]): Either[MoveError, MoveSuccess] = {
    validator.validate(board)(piece, move.source, move.destination)
  }

}

object GameEngine {}
