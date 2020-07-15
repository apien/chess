package com.github.apien.chess.console_ui.ui

import cats.Show
import com.github.apien.chess.core.domain.model.Column

class ColumnShow extends Show[Column] {
  override def show(column: Column): String = column.value.toString
}
