package com.github.apien.chess.console_ui.ui

import cats.Show
import com.github.apien.chess.core.domain.model.{Column, Coordinate, Row}

class CoordinateShow(implicit colShow: Show[Column], rowShow: Show[Row]) extends Show[Coordinate] {

  override def show(cord: Coordinate): String =
    s"(${colShow.show(cord.column)},${rowShow.show(cord.row)})"

}
