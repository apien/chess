package com.github.chess.apien.domain

import cats.syntax.either._
import com.github.chess.apien.domain.MoveError.IllegalMove
import com.github.chess.apien.domain.MoveSuccess.{Captured, Moved}
import com.github.chess.apien.domain.model.PieceColor.{Black, White}
import com.github.chess.apien.domain.model.PieceType.Pawn
import com.github.chess.apien.domain.model._

class PawnMovementValidator extends MovementValidator[Pawn] {

  override def validate(board: Board)(piece: Piece, source: Coordinate, destination: Coordinate): Either[MoveError, MoveSuccess] = {
    //TODO refactor this ugly sequence of if statements!
    lazy val attemptToMove = source.column == destination.column
    if (isMovingForward(piece.color, source, destination) && checkIfNoOtherPiecesForward(board)(source, destination)) {
      val dx = (destination.row.value - source.row.value).abs
      if (attemptToMove) {
        dx match {
          case 1                                     => Moved.asRight
          case 2 if isFirstMove(piece.color, source) => Moved.asRight
          case _                                     => MoveError.IllegalMove.asLeft
        }
      } else {
        val columnShift = (destination.column.value - source.column.value).abs
        if (columnShift == 1 && dx == 1)
          board.squares
            .get(destination)
            .fold[Either[MoveError, MoveSuccess]](IllegalMove.asLeft)(_ => Captured.asRight)
        else MoveError.IllegalMove.asLeft
      }
    } else MoveError.IllegalMove.asLeft
  }

  private def checkIfNoOtherPiecesForward(board: Board)(source: Coordinate, destination: Coordinate): Boolean =
    board
      .select(source.column, source.row, destination.row)
      .isEmpty

  private def isMovingForward(color: PieceColor, source: Coordinate, destination: Coordinate): Boolean = {
    val dx = destination.row.value - source.row.value
    color match {
      case Black if dx > 0 => true
      case White if dx < 0 => true
      case _               => false
    }
  }

  private def isFirstMove(color: PieceColor, source: Coordinate): Boolean =
    color match {
      case Black if source.row == Row(1) => true
      case White if source.row == Row(6) => true
      case _                             => false
    }
}
