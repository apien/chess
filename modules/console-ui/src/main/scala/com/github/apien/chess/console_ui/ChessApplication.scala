package com.github.apien.chess.console_ui

import com.github.apien.chess.console_ui.utils.ScalaUserInput
import com.github.apien.chess.core.domain.model.PieceColor.{Black, White}
import com.github.apien.chess.core.domain.model.{Move, PieceColor}
import com.github.apien.chess.core.domain.{ChessEngine, MoveError, MoveSuccess}
import com.whitehatgaming.UserInputFile
import monix.eval.Task

import scala.util.{Failure, Success}

//TODO: display when king is int check, display which player is turn
/**
  * Our flow of the application;
  * - take path to a file with sequence of the moves
  * - apply move to the chessboard
  * - display a chessboard after each successfully made move
  * - display info when given is invalid (a pike can not make given move, other player turn etc.). When this such situation
  * - display a message when player's king is in check
  * happen then omit the invalid move and load next one.
  * Application ends when there is no more moves.
  *
  * @param movesFilePath Provide patch to the file with moves sequence.
  * @param displayOutput Allows to provide feedback for the user.
  */
class ChessApplication(movesFilePath: () => String, displayOutput: String => Unit) {

  def run(): Task[Unit] =
    for {
      _ <- Task(displayOutput("Please give a path with sequence of moves!"))
      filePath <- Task(movesFilePath())
      input <- Task(ScalaUserInput.initialize(() => new UserInputFile(filePath)))
      gameEngine <- Task(ChessEngine.initial)
      moveInputOp <- Task {
        input match {
          case Failure(exception) =>
            displayOutput(s"Please solve the following problem: ${exception.getMessage}")
            None
          case Success(value) =>
            Some(value)
        }
      }
      _ <- Task {
        displayOutput("# So game begin! It is how looks like initial board!")
        displayOutput(boardShow.show(gameEngine.state))
      }
      _ <- moveInputOp.fold(Task(()))(moveInputValue => applicationLoop(gameEngine, moveInputValue))
    } yield ()

  private def applicationLoop(engine: ChessEngine, input: ScalaUserInput): Task[Unit] =
    Task(input.nextMove())
      .flatMap {
        case None => Task(displayOutput("#There is no more moves in the file!"))
        case Some(move) =>
          for {
            moveResult <- Task(engine.applyMove(move))
            _ <- moveResult.fold(handleFailedMove(_, move), handleSuccessMove(_, engine, move))
            _ <- applicationLoop(engine, input)
          } yield ()

      }

  private def handleSuccessMove(moveSuccess: MoveSuccess, engine: ChessEngine, move: Move): Task[Unit] =
    Task {
      val statusMessage = moveSuccess match {
        case MoveSuccess.Moved =>
          s"# Moved a piece successfully! ${moveShow.show(move)}."
        case MoveSuccess.Captured =>
          s"# Captured an enemy piece! Well done! ${moveShow.show(move)}"
      }

      displayOutput(statusMessage)
      val playerTurn = engine.whichPlayerTurn
      displayOutput(s"# Turn: ${colorShow.show(playerTurn)}")
      checkAndDisplayCheckOfThePlayer(playerTurn, engine)
      displayOutput(boardShow.show(engine.state))
    }

  private def checkAndDisplayCheckOfThePlayer(playerTurn: PieceColor, engine: ChessEngine): Unit = {
    lazy val inCheckMsg = s"# Your king IN CHECK!"

    val msg = playerTurn match {
      case White =>
        engine.whiteKingInCheck.map(_ => inCheckMsg)
      case Black =>
        engine.blackKingInCheck.map(_ => inCheckMsg)
    }
    msg.foreach(displayOutput)
  }

  private def handleFailedMove(moveError: MoveError, move: Move): Task[Unit] =
    Task {
      val msg = moveError match {
        case MoveError.IllegalMove =>
          s"""
             |Yikes! Selected pine can not go ${moveShow.show(move)}}
             |It is is illegal for this kind of a pine.
             |""".stripMargin
        case MoveError.EmptyField =>
          s"Yikes! Selected square does not contain a piece ${moveShow.show(move)}!"
        case MoveError.NotYourTurn =>
          s"Yikes! It is not your turn! Please select a pine of other color! ${moveShow.show(move)} "
        case MoveError.KingInCheck =>
          s"Yikes! You can not make this move because it put your king in check! ${moveShow.show(move)}"
      }
      displayOutput(msg)
    }

}

object ChessApplication {

  import scala.io.StdIn.readLine

  def consoleApplication: ChessApplication =
    new ChessApplication(
      () => readLine(),
      (value: String) => Console.out.println(value)
    )
}
