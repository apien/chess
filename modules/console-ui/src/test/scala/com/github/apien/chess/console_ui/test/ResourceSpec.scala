package com.github.apien.chess.console_ui.test

trait ResourceSpec {

  /**
   * @param fileName File name.
   * @return Return path to the file from the test resources.
   */
  def getPathToResource(fileName: String): String = getClass.getResource(fileName).getPath
}
