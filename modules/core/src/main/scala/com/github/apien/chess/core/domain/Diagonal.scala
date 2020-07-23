package com.github.apien.chess.core.domain

import com.github.apien.chess.core.domain.model.{Column, Coordinate, Row}

import scala.annotation.tailrec
object Diagonal {

  def topLeft(source: Coordinate, limit: Option[Int] = None): List[Coordinate] =
    loop(
      source,
      Nil,
      applyStepLimit(limit, source),
      _.value.value - 1,
      _.value.value - 1
    )

  def downRight(source: Coordinate, limit: Option[Int] = None): List[Coordinate] =
    loop(
      source,
      Nil,
      applyStepLimit(limit, source),
      _.value.value + 1,
      _.value.value + 1
    )

  def topRight(source: Coordinate, limit: Option[Int] = None): List[Coordinate] =
    loop(
      source,
      Nil,
      applyStepLimit(limit, source),
      _.value.value + 1,
      _.value.value - 1
    )

  def downLeft(source: Coordinate, limit: Option[Int] = None): List[Coordinate] =
    loop(
      source,
      Nil,
      applyStepLimit(limit, source),
      _.value.value - 1,
      _.value.value + 1
    )

  @tailrec
  private def loop(
      source: Coordinate,
      acc: List[Coordinate],
      limit: (Coordinate) => Boolean,
      colShift: Column => Int,
      rowShift: Row => Int
  ): List[Coordinate] =
    Coordinate.attempt(colShift(source.column), rowShift(source.row)) match {
      case None                                  => acc
      case Some(coordinate) if limit(coordinate) => loop(coordinate, acc :+ coordinate, limit, colShift, rowShift)
      case Some(_)                               => acc
    }

  private def applyStepLimit(limitOp: Option[Int], begining: Coordinate)(other: Coordinate): Boolean =
    limitOp.fold(true)(limitValue => civDistance(begining, other) <= limitValue)

  private def civDistance(c1: Coordinate, c2: Coordinate): Int =
    List(
      (c1.column.value.value - c2.column.value.value).abs,
      (c1.row.value.value - c2.row.value.value).abs
    ).max

}
