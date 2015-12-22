name := "ip-manager"

organization := "de.alexholly.util"

version := "0.0.2-SNAPSHOT"

scalaVersion := "2.11.7"

assemblyJarName in assembly := "ip-manager" + ".jar"

scalacOptions += "-language:postfixOps"

libraryDependencies += "org.scala-lang.modules" %% "scala-xml" % "1.0.1"

assemblyMergeStrategy in assembly := {
  case PathList("javax", "servlet", xs @ _*)         => MergeStrategy.first
  case PathList(ps @ _*) if ps.last endsWith ".html" => MergeStrategy.first
  case "application.conf"                            => MergeStrategy.concat
  case "unwanted.txt"                                => MergeStrategy.discard
  case x =>
    val oldStrategy = (assemblyMergeStrategy in assembly).value
    oldStrategy(x)
}
