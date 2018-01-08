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
    mainClass in Compile := Some( "app.server.rest.TestHttpServerApp" )//,
//    excludeDependencies ++= Seq(
//      ExclusionRule( "commons-logging", "commons-logging" )
//    )
  )
  .dependsOn( sharedJVM % "compile->compile;test->test" )
  .dependsOn( stateAccess % "compile->compile;test->test" )

logBuffered in Test := false
//
lazy val persistence = (project in file( "persistence" ))
  .settings(
    name := "persistence",
    version := Settings.version,
    libraryDependencies ++= Settings.jvmDependencies.value,
    scalaVersion := Settings.versions.scala
  ).dependsOn( sharedJVM )

lazy val stateAccess = (project in file( "stateAccess" ))
  .settings(
    name := "stateAccess",
    version := Settings.version,
    libraryDependencies ++= Settings.jvmDependencies.value,
    scalaVersion := Settings.versions.scala
  ).dependsOn( persistence % "compile->compile;test->test")
//  .dependsOn( sharedJVM )
//
//
persistLauncher in Compile := true

//persistLauncher in Test := false

cancelable in Global := true
