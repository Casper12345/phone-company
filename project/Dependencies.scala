import sbt._

object Dependencies {
  lazy val scalaTest = "org.scalatest" %% "scalatest" % "3.0.3" % Test
  lazy val guice = "net.codingwell" %% "scala-guice" % "4.2.1"
  lazy val java8Compat = "org.scala-lang.modules" % "scala-java8-compat_2.11" % "0.8.0-RC3"
  lazy val logger = "ch.qos.logback" % "logback-classic" % "1.2.3"

}
