name := "GIS Shapes Reverse Geocoder"

version := "0.0.1"

scalaVersion := "2.9.0-1"

// dependencies
// geo
{
    val geotoolsVersion = "2.7.2"
    libraryDependencies += "org.geotools" % "gt-main" % geotoolsVersion
    libraryDependencies += "org.geotools" % "gt-shapefile" % geotoolsVersion
}

// test
libraryDependencies += "com.novocode" % "junit-interface" % "0.7" % "test->default"

libraryDependencies += "junit" % "junit" % "4.8.1" % "test"

libraryDependencies += "org.scala-tools.testing" %% "scalacheck" % "1.9" % "test"

// plugins
seq(sbtassembly.Plugin.assemblySettings: _*)

// resolvers
resolvers += "Nexus Public Repo" at "http://nexus.places.devbln.europe.nokia.com/nexus/content/groups/public/"

resolvers += "Nexus Public Snapshots Repo" at "http://nexus.places.devbln.europe.nokia.com/nexus/content/groups/public-snapshots/"

// other
ivyLoggingLevel := UpdateLogging.Full
