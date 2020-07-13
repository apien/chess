package com.github.chess.apien.domain

import cats.syntax.option._
import com.github.chess.apien.domain.MoveDetermination.MoveType.Captured
import com.github.chess.apien.domain.MoveDetermination.{AvailableMove, MoveType, getMoveTrack}
import com.github.chess.apien.domain.model.PieceColor.{Black, White}
import com.github.chess.apien.domain.model.PieceType.Pawn
import com.github.chess.apien.domain.model.{Board, Coordinate, PieceColor, Row}

class PawnMovementDetermination extends MoveDetermination[Pawn] {
  override def validate(source: Coordinate, color: PieceColor)(implicit board: Board): Set[AvailableMove] = {
    val verticalMoveLimit = if (isFirstMove(color, source)) 2 else 1
    val moves = MoveDetermination
      .vertically(source, color, verticalMoveLimit.some)
      .filter { case (cord, _) => isMovingForward(color, source, cord) }
      .filter {
        case (_, MoveType.Captured(_)) => false //TODO A Pawn piece as the only piece can not capture other pieces in typical move
        case _ => true
      }

    val captures = color match {
      case Black =>
        val cords =
          getMoveTrack(Diagonal.downLeft(source, 1.some), color) ++
            getMoveTrack(Diagonal.downRight(source, 1.some), color)
        cords.filter {
          case (_, Captured(_)) => true
          case _ => false
        }
      case White =>
        val cords = getMoveTrack(Diagonal.topLeft(source, 1.some), color) ++
          getMoveTrack(Diagonal.topRight(source, 1.some), color)
        cords
          .filter {
            case (_, Captured(_)) => true
            case _ => false
          }
    }
    moves.toSet ++ captures.toSet
  }

  private def isFirstMove(color: PieceColor, source: Coordinate): Boolean =
    color match {
      case Black if source.row == Row(1) => true
      case White if source.row == Row(6) => true
      case _ => false
    }

  private def isMovingForward(color: PieceColor, source: Coordinate, destination: Coordinate): Boolean = {
    val dx = destination.row.value - source.row.value
    color match {
      case Black if dx > 0 => true
      case White if dx < 0 => true
      case _ => false
    }
  }

}
