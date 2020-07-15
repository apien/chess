# chess
Simple implementation of chess game.

It does not support the following rules:
- Castling
- Piece promotion
- En passant

## Modules
The project contains two modules. It allows to reuse core logic i.e: expose application as REST API with 
persistent storage.

### core
Module ``core`` contains whole domain logic chess i.e: validation of rules, logic of each piece.

### console-ui
Module `console-ui` provides simple console application which load moves from a file. 
Application asks user for a file path with move sequence. Application accepts algebraic notation without a space between 
source and demanded square ie: `e2e4`.  Directory `bin/data` contains example files.

When a file contains invalid move (a piece can move this way) then it just try to fetch another move.

> When file contain move not valid from syntax perspective then it stops the application! Application treats it as no more moves.

## Getting started

* Scala SBT [here](https://www.scala-sbt.org/)
* Java 1.8

### Run application

Directory `/bin` contains the bash script `run.sh` to run a `console-ui` application then you need to provide a path
to the file with moves. 


TODO: 
- User property tests to test moves of the pines
- Use Refined Type along with scala-newtype which allows for safer representation of domain objects like: Columns, Row
- Get rid of vars in ChessEngine - I can accomplish it by State monad :) 
- Write missing tests i.e: for main application loop ChessApplication
- Write it in Scala 3/Dotty :) - The project in current state does not require too much external libraries, so it is good place to check it out!