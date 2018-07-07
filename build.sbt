import Dependencies._

lazy val phoneCompany = (project in file(".")).settings(
  Seq(
    name := "disco-test-phone-company",
    version := "1.0",
    scalaVersion := "2.12.3"
  ), libraryDependencies ++= scalaTest :: guice :: java8Compat :: logger :: Nil
)
