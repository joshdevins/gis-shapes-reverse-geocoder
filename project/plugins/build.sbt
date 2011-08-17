
// eclipse -- https://github.com/typesafehub/sbteclipse
resolvers += {
  val typesafeRepoUrl = new java.net.URL("http://repo.typesafe.com/typesafe/releases")
  val pattern = Patterns(false, "[organisation]/[module]/[sbtversion]/[revision]/[type]s/[module](-[classifier])-[revision].[ext]")
  Resolver.url("Typesafe Repository", typesafeRepoUrl)(pattern)
}

libraryDependencies <+= (sbtVersion) { v => "com.typesafe.sbteclipse" %% "sbteclipse" % "1.3-RC3" extra("sbtversion" -> v) }

// assembly -- https://github.com/eed3si9n/sbt-assembly
libraryDependencies <+= (sbtVersion) { v => "com.eed3si9n" %% "sbt-assembly" % ("sbt" + v + "_0.6") }

// akka -- https://github.com/jboner/akka/tree/master/akka-sbt-plugin
libraryDependencies ++= Seq(
  "se.scalablesolutions.akka" % "akka-sbt-plugin" % "1.1.3"
)

// web -- https://github.com/siasia/xsbt-web-plugin
libraryDependencies <+= (sbtVersion) { v => "com.github.siasia" %% "xsbt-web-plugin" % ("0.1.0-" + v) }
