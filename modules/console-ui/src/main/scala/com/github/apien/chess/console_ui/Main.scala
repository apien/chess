package com.github.apien.chess.console_ui

import cats.effect.ExitCode
import com.github.apien.chess.core.domain.ChessEngine
import com.github.apien.chess.core.domain.model.{Column, Coordinate, Move, Row}
import monix.eval.{Task, TaskApp}

object Main extends TaskApp {

  override def run(args: List[String]): Task[ExitCode] = {
    for {
      _ <- applicationLoop(ChessEngine.initial)
    } yield ExitCode.Success

  }

  def applicationLoop(engine: ChessEngine): Task[Unit] =
    for {
      boardDescription <- Task(boardShow.show(engine.state))
      _ <- Task(Console.out.println(boardDescription))
      _ <- Task(Console.out.println(s"What is your move?"))
      move <- Task {
        val raw = Console.in.readLine()
        val cords = raw.toList.map(c => c.toString.toInt).take(4)
        Move(
          Coordinate(Column(cords(0)), Row(cords(1))),
          Coordinate(Column(cords(2)), Row(cords(3)))
        )
      }
      moveREsutl <- Task(engine.applyMove(move))
      resultStr = moveREsutl match {
        case Right(_) => "Well done!"
        case Left(_) => "Smth went wrong!"
      }
      _ <- Task(Console.out.println(s"##Move result: $resultStr"))
      _ <- applicationLoop(engine)
    } yield ()

  sealed trait Command

  final case object StopGame extends Command

  final case class MakeMove(move: Move) extends Command

}
