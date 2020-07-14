import sbt._

object Dependencies {
  lazy val cats = "org.typelevel" %% "cats-core" % VersionOf.cats
  lazy val scalaTest = "org.scalatest" %% "scalatest" % VersionOf.scalaTest
  lazy val monix = "io.monix" %% "monix" % VersionOf.monix
}
