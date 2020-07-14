package com.github.apien.chess.core.domain.determinant

import cats.syntax.option._
import com.github.apien.chess.core.domain.Diagonal
import com.github.apien.chess.core.domain.determinant.MoveDeterminant.MoveType.Capture
import com.github.apien.chess.core.domain.determinant.MoveDeterminant.{AvailableMove, MoveType, getMoveTrack}
import com.github.apien.chess.core.domain.model.PieceColor.{Black, White}
import com.github.apien.chess.core.domain.model.PieceType.Pawn
import com.github.apien.chess.core.domain.model.{Board, Coordinate, PieceColor, Row}

class PawnDeterminant extends MoveDeterminant[Pawn] {
  override def validate(source: Coordinate, color: PieceColor)(implicit board: Board): Set[AvailableMove] = {
    val verticalMoveLimit = if (isFirstMove(color, source)) 2 else 1
    val moves = MoveDeterminant
      .vertically(source, color, verticalMoveLimit.some)
      .filter { case (cord, _) => isMovingForward(color, source, cord) }
      .filter {
        case (_, MoveType.Capture(_)) => false //TODO A Pawn piece as the only piece can not capture other pieces in typical move
        case _ => true
      }

    val captures = color match {
      case Black =>
        val cords =
          getMoveTrack(Diagonal.downLeft(source, 1.some), color) ++
            getMoveTrack(Diagonal.downRight(source, 1.some), color)
        cords.filter {
          case (_, Capture(_)) => true
          case _ => false
        }
      case White =>
        val cords = getMoveTrack(Diagonal.topLeft(source, 1.some), color) ++
          getMoveTrack(Diagonal.topRight(source, 1.some), color)
        cords
          .filter {
            case (_, Capture(_)) => true
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
