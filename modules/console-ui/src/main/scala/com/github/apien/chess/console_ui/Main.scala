package com.github.apien.chess.console_ui

import cats.effect.ExitCode
import monix.eval.{Task, TaskApp}

object Main extends TaskApp {
  override def run(args: List[String]): Task[ExitCode] =
    for {
      chessApplication <- Task(ChessApplication.consoleApplication)
      _ <- chessApplication.run()
    } yield ExitCode.Success
}
