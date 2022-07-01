import Dependencies._

ThisBuild / scalaVersion     := "2.13.8"
ThisBuild / version          := "0.1.0-SNAPSHOT"
ThisBuild / organization     := "com.meetup"
ThisBuild / organizationName := "swcraftersmurcia"

lazy val root = (project in file("."))
  .settings(
    name := "dni-kata",
    libraryDependencies ++= Seq(
      munit, munitScalacheck
    )
  )
