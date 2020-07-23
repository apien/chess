package com.github.apien.chess.core.domain.model

import com.github.apien.chess.core.domain.model.PieceColor.{Black, White}

case class Board(squares: Map[Coordinate, Piece]) {

  /**
    * @param column Demanded column.
    * @param begin  Row exclusive.
    * @param end    Row inclusive.
    * @return Pieces from selected range.
    */
  def select(column: Column, begin: Row, end: Row): List[(Coordinate, Piece)] =
    squares
      .filter { case (coordinate, _) => coordinate.column == column }
      .filter { case (coordinate, _) => coordinate.row > begin && coordinate.row <= end }
      .toList
}

object Board {
  lazy val initialSquares: Map[Coordinate, Piece] = {
    initializeSecondRow(Row.at0, Black) ++
      initializeFrontRow(Row.at1, Black) ++
      initializeFrontRow(Row.at6, White) ++
      initializeSecondRow(Row.at7, White)
  }

  val initial: Board = Board(initialSquares)

  private def initializeFrontRow(row: Row, color: PieceColor): Map[Coordinate, Piece] =
    Column.entireRow
      .map(column => Coordinate(column, row) -> Piece(PieceType.Pawn(), color))
      .toMap

  private def initializeSecondRow(row: Row, color: PieceColor): Map[Coordinate, Piece] = {
    val pieceFactory = Piece(_, color)
    val coordinateFactory = Coordinate(_, row)
    Map(
      coordinateFactory(Column.at0) -> pieceFactory(PieceType.Rook()),
      coordinateFactory(Column.at1) -> pieceFactory(PieceType.Knight()),
      coordinateFactory(Column.at2) -> pieceFactory(PieceType.Bishop()),
      coordinateFactory(Column.at3) -> pieceFactory(PieceType.Queen()),
      coordinateFactory(Column.at4) -> pieceFactory(PieceType.King()),
      coordinateFactory(Column.at5) -> pieceFactory(PieceType.Bishop()),
      coordinateFactory(Column.at6) -> pieceFactory(PieceType.Knight()),
      coordinateFactory(Column.at7) -> pieceFactory(PieceType.Rook())
    )
  }
}
