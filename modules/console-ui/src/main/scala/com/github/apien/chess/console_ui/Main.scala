package com.github.apien.chess.console_ui

import cats.effect.ExitCode
import monix.bio.{BIOApp, Task, UIO}

object Main extends BIOApp {
  override def run(args: List[String]): UIO[ExitCode] =
    (for {
      chessApplication <- Task(ChessApplication.consoleApplication)
      _ <- chessApplication.run()
    } yield ExitCode.Success).hideErrors
}
