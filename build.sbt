name := "virtual.threads"
organization := "objektwerks"
version := "0.1-SNAPSHOT"
scalaVersion := "3.3.1"
libraryDependencies ++= {
  Seq(
    "com.softwaremill.ox" %% "core" % "0.0.11", // 2023.9.4 - 0.0.12 breaks OxTest! Only works with JDK 21 EA and Scala nightly build!
    "org.scalatest" %% "scalatest" % "3.2.16" % Test
  )
}
scalacOptions ++= Seq(
  "-Wunused:all"
)
