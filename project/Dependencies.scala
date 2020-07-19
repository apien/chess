import sbt._

object Dependencies {
  lazy val cats = "org.typelevel" %% "cats-core" % VersionOf.cats
  lazy val scalaTest = "org.scalatest" %% "scalatest" % VersionOf.scalaTest
  lazy val monix = "io.monix" %% "monix" % VersionOf.monix
  lazy val newType = "io.estatico" %% "newtype" % VersionOf.newType

  lazy val refined = "eu.timepit" %% "refined" % VersionOf.refinedVersion
  lazy val refinedCats = "eu.timepit" %% "refined-cats" % VersionOf.refinedVersion
  lazy val refinedDecline = "com.monovore" %% "decline-refined" % VersionOf.refinedVersion
}
