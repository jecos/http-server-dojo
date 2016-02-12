name := "http-server-dojo"

version := "1.0"

scalaVersion := "2.11.7"

libraryDependencies ++= Seq(
  "org.scalatest" %% "scalatest" % "2.2.5" % Test,
  "org.apache.httpcomponents" % "httpclient" % "4.3.1" % Test
)