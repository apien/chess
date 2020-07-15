package com.github.apien.chess.console_ui.ui

import cats.Show
import com.github.apien.chess.core.domain.model.Move

class MoveShow(implicit coordinateShow: CoordinateShow) extends Show[Move] {
  override def show(move: Move): String =
    s"${coordinateShow.show(move.source)} -> ${coordinateShow.show(move.destination)}"
}
