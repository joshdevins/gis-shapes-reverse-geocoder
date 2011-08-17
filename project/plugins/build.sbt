
// assembly -- https://github.com/eed3si9n/sbt-assembly
libraryDependencies <+= (sbtVersion) { sv => "com.eed3si9n" %% "sbt-assembly" % ("sbt" + sv + "_0.6") }
