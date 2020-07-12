package com.github.chess.apien.domain

import com.github.chess.apien.domain.model.{Column, Coordinate, Row}

object Diagonal {

  def topLeft(source: Coordinate): List[Coordinate] = {
    for {
      i <- 1 to 7
      col = source.column.value - i
      row = source.row.value - i
      if col >= 0
      if row >= 0
    } yield Coordinate(Column(col), Row(row))
  }.toList

  def downRight(source:Coordinate):List[Coordinate] = {
    for {
      i <- 1 to 7
      col = source.column.value + i
      row = source.row.value + i
      if col <= 7
      if row <= 7
    } yield Coordinate(Column(col), Row(row))
  }.toList

  def topRight(source: Coordinate): List[Coordinate] = {
    for {
      i <- 1 to 7
      col = source.column.value + i
      row = source.row.value - i
      if col <= 7
      if row >= 0
    } yield Coordinate(Column(col), Row(row))
  }.toList

  def downLeft(source:Coordinate):List[Coordinate] = {
    for {
      i <- 1 to 7
      col = source.column.value - i
      row = source.row.value + i
      if col >= 0
      if row <= 7
    } yield Coordinate(Column(col), Row(row))
  }.toList


}
