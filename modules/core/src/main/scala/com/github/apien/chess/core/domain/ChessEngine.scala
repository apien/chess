package com.github.apien.chess.core.domain

import cats.syntax.either._
import com.github.apien.chess.core.domain.ChessEngine.PlayerMove
import com.github.apien.chess.core.domain.MoveError.{EmptyField, NotYourTurn}
import com.github.apien.chess.core.domain.determinant.MoveDeterminant
import com.github.apien.chess.core.domain.determinant.MoveDeterminant.MoveType
import com.github.apien.chess.core.domain.model.PieceColor.{Black, White}
import com.github.apien.chess.core.domain.model._

class ChessEngine private (private var board: Board, private var moves: List[PlayerMove]) {
  //TODO get rid of the var - maybe return State monad?

  /**
    * @return Current board state.
    */
  def state: Board = board

  /**
    * Check if white king is threaded by check.
    * @return Move which follow to checkmate.
    */
  def whiteKingInCheck: Option[Move] = CheckVerifier.check(board, PieceColor.White)

  /**
    * Check if black king is threaded by check.
    * @return Move which follow to checkmate.
    */
  def blackKingInCheck: Option[Move] = CheckVerifier.check(board, PieceColor.Black)

  def applyMove(move: Move): Either[MoveError, MoveSuccess] =
    for {
      piece <- checkIfPineExists(move.source)
      _ <- validateUserTurn(piece)
      moveResult <- validateMove(piece, move)
      (boardAfterMove, movesAfterMove) = determineNewBoard(move, piece)
      _ <- validateInCheck(boardAfterMove, piece.color)
      result <- moveResult match {
        case MoveType.Capture(_) => MoveSuccess.Captured.asRight
        case MoveType.Vacant     => MoveSuccess.Moved.asRight
      }
    } yield {
      board = boardAfterMove
      moves = movesAfterMove
      result
    }

  def whichPlayerTurn: PieceColor = {
    moves.lastOption
      .map(_.color) match {
      case None        => White
      case Some(White) => Black
      case Some(Black) => White
    }
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

  private def validateUserTurn(piece: Piece): Either[MoveError, Piece] =
    Either.cond(
      piece.color == whichPlayerTurn,
      piece,
      NotYourTurn
    )

  private def validateMove(piece: Piece, move: Move): Either[MoveError, MoveType] = {
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
