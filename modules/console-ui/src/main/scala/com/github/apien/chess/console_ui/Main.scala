package com.github.apien.chess.console_ui

import cats.effect.ExitCode
import com.github.apien.chess.console_ui.utils.ScalaUserInput
import com.github.apien.chess.core.domain.model.Move
import com.github.apien.chess.core.domain.{ChessEngine, MoveError, MoveSuccess}
import com.whitehatgaming.UserInputFile
import monix.eval.{Task, TaskApp}

import scala.Console.out
import scala.io.StdIn.readLine
import scala.util.{Failure, Success}

//TODO: display when king is int check, display which player is turn
object Main extends TaskApp {
  override def run(args: List[String]): Task[ExitCode] =
    for {
      _ <- Task(out.println("Please give a path with sequence of moves!"))
      filePath <- Task(readLine())

      input <- Task(ScalaUserInput.initialize(() => new UserInputFile(filePath)))
      gameEngine <- Task(ChessEngine.initial)
      in <- Task {
        input match {
          case Failure(exception) =>
            out.println(s"Please solve the following problem: ${exception.getMessage}")
            None
          case Success(value) =>
            Some(value)
        }
      }
      _ <- in.fold(Task(()))(inValue => applicationLoop(gameEngine, inValue))
    } yield ExitCode.Success

  def applicationLoop(engine: ChessEngine, input: ScalaUserInput): Task[Unit] =
    Task(input.nextMove())
      .flatMap {
        case None => Task(out.println("#There is no more moves in the file!"))
        case Some(move) =>
          val moveResult = engine.applyMove(move)
          moveResult match {
            case Left(value) => handleFailedMove(value, move)
            case Right(successStatus) =>
              for {
                _ <- handleSuccessMove(successStatus, engine, move)
                _ <- applicationLoop(engine, input)
              } yield ()
          }
      }

  private def handleSuccessMove(moveSuccess: MoveSuccess, engine: ChessEngine, move: Move): Task[Unit] =
    Task {
      val statusMessage = moveSuccess match {
        case MoveSuccess.Moved =>
          s"# Moved a piece successfully! ${moveShow.show(move)}."
        case MoveSuccess.Captured =>
          s"# Captured an enemy piece! Well done! ${moveShow.show(move)}"
      }

      out.println(statusMessage)
      out.println(boardShow.show(engine.board))
    }

  private def handleFailedMove(moveError: MoveError, move: Move): Task[Unit] =
    Task {
      moveError match {
        case MoveError.IllegalMove =>
          out.println(
            s"""
               |Yikes! Selected pine can not go ${moveShow.show(move)}}
               |It is is illegal for this kind of a pine.
               |""".stripMargin
          )
        case MoveError.EmptyField => out.println(s"Yikes! Selected square does not contain a piece ${moveShow.show(move)}!")
        case MoveError.NotYourTurn =>
          out.println(s"Yikes! It is not your turn! Please select a pine of other color! ${moveShow.show(move)} ")
        case MoveError.KingInCheck =>
          out.println(s"Yikes! You can not make this move because it put your king in check! ${moveShow.show(move)}")
      }
    }

}
