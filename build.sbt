lazy val finagleVersion = "18.3.0"
lazy val finchVersion = "0.17.0"
lazy val genericVersion = "0.9.0"
lazy val circeVersion = "0.9.1"

lazy val lib = Seq(
//  "com.twitter" %% "finagle-http" % finagleVersion,
  "org.typelevel" %% "cats-core" % "1.0.1",
  "com.github.finagle" %% "finch-core" % finchVersion,
  "com.github.finagle" %% "finch-circe" % finchVersion,
  "io.circe" %% "circe-generic" % genericVersion,
  "io.circe" %% "circe-parser" % circeVersion
)

lazy val root = (project in file("."))
  .settings(
    name := "FinagleSSP",
    version := "0.1",
    scalaVersion := "2.12.4",
    libraryDependencies ++= lib
  )