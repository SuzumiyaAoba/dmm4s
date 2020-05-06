name := "dmm4s"

version := "0.1"

scalaVersion := "2.13.1"

scalacOptions ++= Seq(
  "-encoding", "utf8",
  "-deprecation",
  "-language:higherKinds"
)

val httpzVersion = "0.6.0"

libraryDependencies ++= Seq(
  "com.github.xuwei-k" %% "httpz" % httpzVersion,
  "com.github.xuwei-k" %% "httpz-scalaj" % httpzVersion % "test",
  "joda-time" % "joda-time" % "2.10.6",
  "org.specs2" %% "specs2-core" % "4.8.3" % "test"
)