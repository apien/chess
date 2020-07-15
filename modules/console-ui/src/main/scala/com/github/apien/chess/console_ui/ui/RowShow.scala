package com.github.apien.chess.console_ui.ui

import cats.Show
import com.github.apien.chess.core.domain.model.Row

class RowShow extends Show[Row] {
  override def show(row: Row): String = row.value.toString
}
