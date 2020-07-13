package com.github.chess.apien.domain

import cats.syntax.either._
import com.github.chess.apien.domain.MoveError.{EmptyField, NotYourTurn}
import com.github.chess.apien.domain.determinant.MoveDeterminant
import com.github.chess.apien.domain.determinant.MoveDeterminant.MoveType
import com.github.chess.apien.domain.model._

class ChessEngine private(var board: Board) {
  //TODO get rid of the var - maybe return State monad?
  //  private implicit var board: Board = Board(Board.initial)

  /**
   * @return Current board state.
   */
  def state: Board = board

  def applyMove(move: Move, color: PieceColor): Either[MoveError, MoveSuccess] =
    for {
      piece <- checkIfPineExists(move.source)
      _ <- validateColor(color, piece)
      moveResult <- validateMove(piece, move)
      boardAfterMove = determineNewBoard(move, piece)
      result <- moveResult match {
        case MoveType.Capture(_) => MoveSuccess.Captured.asRight
        case MoveType.Vacant => MoveSuccess.Moved.asRight
      }
    } yield {
      board = boardAfterMove
      result

    }

  private def determineNewBoard(move: Move, piece: Piece): Board = {
    val updatedSquares = board.squares.updated(move.destination, piece).removed(move.source)
    new Board(updatedSquares)
  }

  private def checkIfPineExists(coordinate: Coordinate): Either[MoveError, Piece] =
    board.squares
      .get(coordinate)
      .toRight(EmptyField)

  private def validateColor(color: PieceColor, piece: Piece): Either[MoveError, Piece] = {
    Either.cond(
      color == piece.color,
      piece,
      NotYourTurn
    )
  }

  def validateMove(piece: Piece, move: Move): Either[MoveError, MoveType] = {
    MoveDeterminant
      .getMoves(piece.kind, move.source, piece.color, board)
      .find { case (coordinate, _) => coordinate == move.destination }
      .fold[Either[MoveError, MoveType]](MoveError.IllegalMove.asLeft) { case (_, moveType) => moveType.asRight }
  }
}

object ChessEngine {

  def initial: ChessEngine = new ChessEngine(Board.initial)

  def create(board: Board): ChessEngine = new ChessEngine(board)
}
