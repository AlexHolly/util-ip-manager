name := "ip-manager"

organization := "de.alexholly.util"

version := "0.0.1-SNAPSHOT"

scalaVersion := "2.11.7"

assemblyJarName in assembly := "ip-manager" + ".jar"

scalacOptions += "-language:postfixOps"

libraryDependencies += "org.scala-lang.modules" %% "scala-xml" % "1.0.1"
