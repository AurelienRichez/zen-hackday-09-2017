// Use the Play sbt plugin for Play projects
addSbtPlugin("com.typesafe.play" % "sbt-plugin" % "2.6.1")

// To keep an homogeneous code style
addSbtPlugin("com.lucidchart" % "sbt-scalafmt" % "1.10")

// Add build infos into code
addSbtPlugin("com.eed3si9n" % "sbt-buildinfo" % "0.6.1")

// plugin to manage release cycle
addSbtPlugin("com.github.gseitz" % "sbt-release" % "1.0.4")
