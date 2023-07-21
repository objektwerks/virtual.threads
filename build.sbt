name := "virtual.threads"
organization := "objektwerks"
version := "0.1-SNAPSHOT"
scalaVersion := "3.3.1-RC4"
libraryDependencies ++= {
  Seq(
    "com.softwaremill.ox" %% "core" % "0.0.7",
    "org.scalatest" %% "scalatest" % "3.2.16" % Test
  )
}
scalacOptions ++= Seq(
  "-Wunused:all"
)
