name := "fetcher-service"
version := "1.0-SNAPSHOT"
 
scalaVersion := "2.11.8"
 
libraryDependencies ++= Seq(
  "com.typesafe.akka" %% "akka-http-experimental" % "2.4.11",
  "org.specs2" % "specs2-core_2.11" % "3.8.6-20161107094644-7ddfac2"
)