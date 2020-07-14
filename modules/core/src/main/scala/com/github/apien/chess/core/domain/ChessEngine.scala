package com.github.apien.chess.core.domain

import cats.syntax.either._
import com.github.apien.chess.core.domain.ChessEngine.PlayerMove
import com.github.apien.chess.core.domain.MoveError.{EmptyField, NotYourTurn}
import com.github.apien.chess.core.domain.determinant.MoveDeterminant
import com.github.apien.chess.core.domain.determinant.MoveDeterminant.MoveType
import com.github.apien.chess.core.domain.model.PieceColor.White
import com.github.apien.chess.core.domain.model._

class ChessEngine private(var board: Board, var moves: List[PlayerMove]) {
  //TODO get rid of the var - maybe return State monad?

  /**
   * @return Current board state.
   */
  def state: Board = board

  def applyMove(move: Move): Either[MoveError, MoveSuccess] =
    for {
      piece <- checkIfPineExists(move.source)
      _ <- validateUserTurn(piece)
      moveResult <- validateMove(piece, move)
      (boardAfterMove, movesAfterMove) = determineNewBoard(move, piece)
      _ <- validateInCheck(boardAfterMove, piece.color)
      result <- moveResult match {
        case MoveType.Capture(_) => MoveSuccess.Captured.asRight
        case MoveType.Vacant => MoveSuccess.Moved.asRight
      }
    } yield {
      board = boardAfterMove
      moves = movesAfterMove
      result
    }

  private def determineNewBoard(move: Move, piece: Piece): (Board, List[PlayerMove]) = {
    val updatedSquares = board.squares.updated(move.destination, piece).removed(move.source)
    val updatedMoves = moves :+ PlayerMove(piece.color, move)
    (new Board(updatedSquares), updatedMoves)
  }

  private def validateInCheck(newBoard: Board, color: PieceColor): Either[MoveError, Unit] = {
    CheckVerifier
      .check(newBoard, color)
      .fold[Either[MoveError, Unit]](().asRight)(_ => MoveError.KingInCheck.asLeft)
  }

  private def checkIfPineExists(coordinate: Coordinate): Either[MoveError, Piece] =
    board.squares
      .get(coordinate)
      .toRight(EmptyField)

  private def validateUserTurn(piece: Piece): Either[MoveError, Piece] = {
    val isPlayerTurn = moves.lastOption match {
      case None if piece.color == White => true
      case Some(PlayerMove(lastMoveColor, _)) if lastMoveColor != piece.color => true
      case _ => false
    }

    Either.cond(
      isPlayerTurn,
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

  def initial: ChessEngine = new ChessEngine(Board.initial, Nil)

  def create(board: Board): ChessEngine = new ChessEngine(board, Nil)

  case class PlayerMove(color: PieceColor, move: Move)

}
