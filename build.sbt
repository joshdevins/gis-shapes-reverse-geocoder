name := "GIS Shapes Reverse Geocoder"

organization := "net.joshdevins.gis"

version := "0.0.1"

scalaVersion := "2.9.0-1"

ivyLoggingLevel := UpdateLogging.Full

// dependencies
// geo
{
    val geotoolsVersion = "2.7.2"
    libraryDependencies += "org.geotools" % "gt-main" % geotoolsVersion
    libraryDependencies += "org.geotools" % "gt-shapefile" % geotoolsVersion
}

// Akka/Spray
libraryDependencies ++= Seq(
    "se.scalablesolutions.akka" % "akka" % "1.1.3",
    "cc.spray" %% "spray-http" % "0.7.0" % "compile" withSources(),
    "cc.spray" %% "spray-server" % "0.7.0" % "compile" withSources(),
    "cc.spray.json" %% "spray-json" % "1.0.0" % "compile" withSources()
)

// logging
libraryDependencies ++= Seq(
  "se.scalablesolutions.akka" % "akka-slf4j" % "1.1.3",
  "ch.qos.logback" % "logback-classic" % "0.9.29" % "runtime"
)

// test
libraryDependencies ++= Seq(
    "com.novocode" % "junit-interface" % "0.7" % "test->default",
    "junit" % "junit" % "4.8.1" % "test",
    "org.scala-tools.testing" %% "scalacheck" % "1.9" % "test",
    "org.eclipse.jetty" % "jetty-server" % "8.0.0.M3" % "jetty",
    "org.eclipse.jetty" % "jetty-webapp" % "8.0.0.M3" % "jetty"
)

// plugins
seq(sbtassembly.Plugin.assemblySettings: _*)    // assembly

seq(webSettings :_*)    // web settings

// resolvers
resolvers ++= Seq(
    "Nexus Public Repo" at "http://nexus.places.devbln.europe.nokia.com/nexus/content/groups/public/",
    "Nexus Public Snapshots Repo" at "http://nexus.places.devbln.europe.nokia.com/nexus/content/groups/public-snapshots/",
    "Akka Repository" at "http://akka.io/repository"
)
