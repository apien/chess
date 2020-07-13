package com.github.chess.apien.domain

import cats.syntax.either._
import com.github.chess.apien.domain.MoveError.{EmptyField, NotYourTurn}
import com.github.chess.apien.domain.determinant.MoveDeterminant
import com.github.chess.apien.domain.determinant.MoveDeterminant.MoveType
import com.github.chess.apien.domain.model._

class ChessEngine {
  private val chessBoard = Board.initial
  private implicit val board: Board = Board(chessBoard)

  def applyMove(move: Move, color: PieceColor): Either[MoveError, MoveSuccess] = {
    for {
      piece <- checkIfPineExists(move.source)
      _ <- validateColor(color, piece)
      result <- validateMove(piece, move)
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

  def validateMove(piece: Piece, move: Move)(implicit board: Board): Either[MoveError, MoveSuccess] = {
    MoveDeterminant
      .getMoves(piece.kind, move.source, piece.color, board)
      .find { case (coordinate, _) => coordinate == move.destination }
      .fold[Either[MoveError, MoveSuccess]](MoveError.IllegalMove.asLeft) {
        case (_, MoveType.Vacant) => MoveSuccess.Moved.asRight
        case (_, MoveType.Capture(_)) => MoveSuccess.Captured.asRight
      }
  }

}

object ChessEngine {}