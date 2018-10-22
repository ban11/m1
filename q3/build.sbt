organization := "com.github.ban11"

name := "q3"

scalaVersion := "2.12.6"

scalacOptions ++= Seq("-deprecation", "-feature", "-unchecked", "-Xlint")

libraryDependencies += "org.biojava" % "biojava-core" % "5.1.1"

resolvers += "BioJava repository" at "http://oss.sonatype.org/content/repositories/snapshots"
