import ReleaseTransformations._

organization := "io.zengularity"
name := """new-project"""

val timestamp = new java.text.SimpleDateFormat("yyyyMMdd-HHmm").format(new java.util.Date)

val commonSettings = Seq(
  scalaVersion := "2.12.2",
  scalacOptions := Seq(
    "-encoding",
    "UTF-8",
    "-target:jvm-1.8",
    "-Ywarn-adapted-args",
    "-Ywarn-inaccessible",
    "-Ywarn-nullary-override",
    "-Ywarn-infer-any",
    "-Ywarn-dead-code",
    "-Ywarn-unused",
    "-Ywarn-unused-import",
    "-Ywarn-value-discard",
    "-unchecked",
    "-deprecation",
    "-feature",
    "-g:vars",
    "-Xlint:_",
    "-opt:_"
  ),
  // name dist with timestamp
  packageName in Universal := s"${name.value}-${version.value}-$timestamp",
  // skip scaladoc when running dist
  publishArtifact in (Compile, packageDoc) := false,
  publishArtifact in packageDoc := false,
  sources in (Compile, doc) := Seq.empty
)

lazy val root = (project in file("."))
  .enablePlugins(PlayScala, BuildInfoPlugin, DockerPlugin)
  .settings(commonSettings: _*)
  .settings(
    libraryDependencies ++= Seq(
      ws,
      // persistence
      "com.typesafe.play" %% "anorm" % Versions.anorm,
      "com.github.marcospereira" %% "play-hocon-i18n" % Versions.hoconi18n,
      // enums utils
      "com.beachape" %% "enumeratum" % Versions.enumeratum,
      "com.beachape" %% "enumeratum-play" % Versions.enumeratum,
      // metrics
      "io.kamon" %% "kamon-core" % Versions.kamon,
      "io.kamon" %% "kamon-influxdb" % Versions.kamon,
      "io.kamon" %% "kamon-system-metrics" % Versions.kamon,
      // Test dependencies
      "org.scalatestplus.play" %% "scalatestplus-play" % Versions.scalatestPlay % Test
    ),
    buildInfoKeys := Seq[BuildInfoKey](
      name,
      version,
      scalaVersion
    ),
    buildInfoPackage := "sbt",
    buildInfoOptions += BuildInfoOption.BuildTime,
    // docker config
    maintainer in Docker := "team@zengularity.com",
    dockerBaseImage := "anapsix/alpine-java:8_server-jre_unlimited",
    dockerExposedPorts := Seq(9000),
    // dont include local.conf in dist
    mappings in Universal := {
      val origMappings = (mappings in Universal).value
      origMappings.filterNot { case (_, file) => file.endsWith("local.conf") }
    },
    // release process
    releaseProcess := Seq[ReleaseStep](
      runClean,
      checkSnapshotDependencies,
      inquireVersions,
      runTest,
      setReleaseVersion,
      commitReleaseVersion,
      tagRelease,
      setNextVersion,
      commitNextVersion,
      pushChanges
    )
  )

scalafmtVersion := "1.2.0"
scalafmtOnCompile in ThisBuild := true
