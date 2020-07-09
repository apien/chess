package com.github.chess.apien.domain.model

import com.github.chess.apien.domain.model.PieceColor.{Black, White}

case class Board(squares: Map[Coordinate, Piece]) {

  /**
   *
   * @param column
   * @param begin Row exclusive.
   * @param end   Row inclusive.
   * @return
   */
  def select(column: Column, begin: Row, end: Row): List[(Coordinate, Piece)] =
    squares
      .filter { case (coordinate, _) => coordinate.column == column }
      .filter { case (coordinate, _) => coordinate.row > begin && coordinate.row <= end }
      .toList
}

object Board {
  lazy val initial: Map[Coordinate, Piece] = {
    initializeSecondRow(Row(0), Black) ++
      initializeFrontRow(Row(1), Black) ++
      initializeFrontRow(Row(6), White) ++
      initializeSecondRow(Row(7), White)
  }

  private def initializeFrontRow(row: Row, color: PieceColor): Map[Coordinate, Piece] =
    Column.entireRow
      .map(column => Coordinate(column, row) -> Piece(PieceType.Pawn(), color))
      .toMap

  private def initializeSecondRow(row: Row, color: PieceColor): Map[Coordinate, Piece] = {
    val pieceFactory = Piece(_, color)
    val coordinateFactory = Coordinate(_, row)
    Map(
      coordinateFactory(Column(0)) -> pieceFactory(PieceType.Rook),
      coordinateFactory(Column(1)) -> pieceFactory(PieceType.Knight),
      coordinateFactory(Column(2)) -> pieceFactory(PieceType.Bishop),
      coordinateFactory(Column(3)) -> pieceFactory(PieceType.Queen),
      coordinateFactory(Column(4)) -> pieceFactory(PieceType.King),
      coordinateFactory(Column(5)) -> pieceFactory(PieceType.Bishop),
      coordinateFactory(Column(6)) -> pieceFactory(PieceType.Knight),
      coordinateFactory(Column(7)) -> pieceFactory(PieceType.Rook)
    )
  }
}
