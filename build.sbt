name := "virtual.threads"
organization := "objektwerks"
version := "0.1-SNAPSHOT"
scalaVersion := "3.3.1-RC7"
libraryDependencies ++= {
  Seq(
    "com.softwaremill.ox" %% "core" % "0.0.11", // 0.0.12 seriously breaks OxTest!
    "org.scalatest" %% "scalatest" % "3.2.16" % Test
  )
}
scalacOptions ++= Seq(
  "-Wunused:all"
)
