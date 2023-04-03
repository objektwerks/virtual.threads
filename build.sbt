name := "virtual.threads"
organization := "objektwerks"
version := "0.1-SNAPSHOT"
scalaVersion := "3.2.2"
libraryDependencies ++= {
  Seq(
    "com.softwaremill.ox" %% "core" % "0.0.5",
    "org.scalatest" %% "scalatest" % "3.2.15" % Test
  )
}