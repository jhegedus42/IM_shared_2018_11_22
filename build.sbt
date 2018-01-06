name := "IM root project"

import sbt.Keys._
import sbt.Project.projectToRef

// a special crossProject for configuring a JS/JVM/shared structure
lazy val shared = (crossProject.crossType( CrossType.Pure ) in file( "shared" ))
  .settings(
    addCompilerPlugin( "org.scalamacros" % "paradise" % "2.1.0" cross CrossVersion.full ),
    scalaVersion := Settings.versions.scala,
    libraryDependencies ++= Settings.sharedDependencies.value
  )

lazy val sharedJVM = shared.jvm.settings( name := "sharedJVM" )

lazy val sharedJS = shared.js.settings( name := "sharedJS" )

// instantiate the JS project for SBT with some additional settings
lazy val js: Project = (project in file( "js" ))
  .settings(
    name := "js",
    version := Settings.version,
    jsDependencies += RuntimeDOM % "test",
    scalaVersion := Settings.versions.scala,
//                                      scalacOptions ++= Settings.scalacOptions,
    libraryDependencies ++= Settings.scalajsDependencies.value,


    parallelExecution in Test := false,

    mainClass in Compile := Some( "app.client.Main" ),
    persistLauncher in Compile := true,
//    persistLauncher in Test := false,
//    persistLauncher in Test := true,
    jsEnv := new JSDOMNodeJSEnv2(),
    //    jsDependencies += RuntimeDOM,
    scalaJSOptimizerOptions ~= { _.withDisableOptimizer( true ) }
  )
  .enablePlugins( ScalaJSPlugin )
  .dependsOn( sharedJS % "compile->compile;test->test" )

// Client projects (just one in this case)
lazy val clients = Seq( js )

// instantiate the JVM project for SBT with some additional settings
lazy val jvm = (project in file( "jvm" ))
  .settings(
    name := "jvm",
    version := Settings.version,
    scalaVersion := Settings.versions.scala,
    scalacOptions ++= Settings.scalacOptions,
    libraryDependencies ++= Settings.jvmDependencies.value,
    mainClass in Test := Some( "app.server.rest.testServers.TestServer_App_Basic_Data" ),
    mainClass in Compile := Some( "app.server.rest.TestHttpServerApp" )

//                             commands += ReleaseCmd,
    // triggers scalaJSPipeline when using compile or continuous compilation
//                             compile in Compile := ((compile in Compile) dependsOn scalaJSPipeline).value,
    // connect to the client project
//                             scalaJSProjects := clients,
//                             pipelineStages in Assets := Seq(scalaJSPipeline),
//                             pipelineStages := Seq(digest, gzip),
    // compress CSS
//                             LessKeys.compress in Assets := true
  )
  .dependsOn( sharedJVM % "compile->compile;test->test" )
  .dependsOn( persistence % "compile->compile;test->test")

//scalaVersion in ThisBuild := "2.11.8"
//
//lazy val root = project
//  .in(file("."))
//  .aggregate(imJS, imJVM, persistence)
//  .settings(
//    scalaVersion := Settings.versions.scala,
//    publish := {},
//    publishLocal := {}
//  )
//
////lazy val im2=
//
//lazy val im = crossProject
//  .in(file("."))
//  .settings(
//    libraryDependencies ++= Settings.sharedDependencies.value,
//    addCompilerPlugin(
//      "org.scalamacros" % "paradise" % "2.1.0" cross CrossVersion.full),
//    scalaVersion := Settings.versions.scala,
//    name := "im",
//    version := "0.1-SNAPSHOT"
//  )
//  .jvmSettings(
//    libraryDependencies ++= Settings.jvmDependencies.value,
//    mainClass in Test := Some(
//      "app.server.rest.testServers.TestServer_App_Basic_Data"),
//    mainClass in Compile := Some("app.server.rest.TestHttpServerApp")
//  )
//  .jsSettings(
//    mainClass in Compile := Some ("app.client.Main"),
//    libraryDependencies ++= Settings.scalajsDependencies.value,
//    persistLauncher in Compile := true,
//    persistLauncher in Test := false,
//    jsEnv := new JSDOMNodeJSEnv2(),
//    jsDependencies += RuntimeDOM,
//    scalaJSOptimizerOptions ~= { _.withDisableOptimizer(true) }
//  )
//
logBuffered in Test := false
//
lazy val persistence = (project in file( "persistence" )).settings(
  name := "persistence",
  version := Settings.version,
  libraryDependencies ++= Settings.jvmDependencies.value,
  scalaVersion := Settings.versions.scala
).dependsOn(sharedJVM)

//
//
persistLauncher in Compile := true

//persistLauncher in Test := false

cancelable in Global := true
