lazy val finagleVersion = "18.3.0"
lazy val finchVersion = "0.17.0"

lazy val lib = Seq(
  "com.twitter" %% "finagle-http" % finagleVersion,
  "com.github.finagle" %% "finch-circe" % finchVersion
)

lazy val root = (project in file("."))
  .settings(
    name := "FinagleSSP",
    version := "0.1",
    scalaVersion := "2.12.4",
    libraryDependencies ++= lib
  )