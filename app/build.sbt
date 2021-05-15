import Dependencies._

ThisBuild / scalaVersion := "2.13.5"
ThisBuild / version := "0.1.0-SNAPSHOT"
ThisBuild / organization := "com.example"
ThisBuild / organizationName := "example"

lazy val AkkaVersion     = "2.6.14"
lazy val AkkaHttpVersion = "10.2.4"

lazy val root = (project in file("."))
  .settings(
    name := "extremely-simple-ad-api-with-akka-http",
    libraryDependencies ++= Seq(
      "com.typesafe.akka" %% "akka-actor-typed"     % AkkaVersion,
      "com.typesafe.akka" %% "akka-stream"          % AkkaVersion,
      "com.typesafe.akka" %% "akka-http"            % AkkaHttpVersion,
      "com.typesafe.akka" %% "akka-http-spray-json" % AkkaHttpVersion,
      "com.google.inject"  % "guice"                % "5.0.1",
    )
  )

// Wartremover settings
Compile / wartremoverWarnings ++= Warts.allBut(
  Wart.DefaultArguments,
  Wart.ToString,
  Wart.ImplicitParameter,
)
