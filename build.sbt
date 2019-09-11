name := "scala-programming"

version := "0.1"

organization := "com.piyushpatel2005.scala"

scalaVersion := "2.11.7"

resolvers ++= Seq("snapshots" at "http://scala-tools.org/repo-snapshots",
  "releases" at "http://scala-tools.org/repo-releases")

libraryDependencies ++= Seq(
  "org.scalatest" %% "scalatest" % "3.0.7" % "test" withSources() withJavadoc(),
  "joda-time" % "joda-time" % "1.6.2" withSources() withJavadoc(),
  "junit" % "junit" % "4.10" withSources() withJavadoc(),
  "org.testng" % "testng" % "6.1.1" % "test" withSources() withJavadoc(),
  "org.specs2" %% "specs2" % "2.3.11" withSources() withJavadoc(),
  "org.easymock" % "easymock" % "3.1" % "test" withSources() withJavadoc(),
  "org.mockito" % "mockito-core" % "1.9.0" % "test" withSources() withJavadoc(),
  "org.scalacheck" %% "scalacheck" % "1.13.5" % "test" withSources() withJavadoc()
)