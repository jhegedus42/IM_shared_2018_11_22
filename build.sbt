name := "IM root project"

import sbt.Keys._
import sbt.Project.projectToRef

// a special crossProject for configuring a JS/JVM/shared structure
lazy val layer_Z_JVM_and_JS_shared =
  (crossProject.crossType( CrossType.Pure ) in file( "layer_Z_JVM_and_JS_shared" ))
    .settings(
      addCompilerPlugin( "org.scalamacros" % "paradise" % "2.1.0" cross CrossVersion.full ),
      scalaVersion := Settings.versions.scala,
      libraryDependencies ++= Settings.sharedDependencies.value
    )

lazy val layer_Z_JVM_shared = layer_Z_JVM_and_JS_shared.jvm.settings( name := "layer_Z_JVM_shared" )

lazy val layer_Z_JS_shared = layer_Z_JVM_and_JS_shared.js.settings( name := "layer_Z_JS_shared" )

// instantiate the JS project for SBT with some additional settings
lazy val layer_Y_JS_client: Project = (project in file( "layer_Y_JS_client" ))
  .settings(
    name := "layer_Y_JS_client",
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
  .dependsOn( layer_Z_JS_shared % "compile->compile;test->test" )

// Client projects (just one in this case)
lazy val clients = Seq( layer_Y_JS_client )

lazy val layer_Y_JVM_persistence = (project in file( "layer_Y_JVM_persistence" ))
  .settings(
    name := "layer_Y_JVM_persistence",
    version := Settings.version,
    libraryDependencies ++= Settings.jvmDependencies.value,
    scalaVersion := Settings.versions.scala
  ).dependsOn( layer_Z_JVM_shared )

lazy val layer_X_JVM_stateAccess = (project in file( "layer_X_JVM_stateAccess" ))
  .settings(
    name := "layer_X_JVM_stateAccess",
    version := Settings.version,
    libraryDependencies ++= Settings.jvmDependencies.value,
    scalaVersion := Settings.versions.scala
  ).dependsOn( layer_Y_JVM_persistence % "compile->compile;test->test" )

// instantiate the JVM project for SBT with some additional settings
lazy val layer_W_JVM_akka_http_server = (project in file( "layer_W_JVM_akka_http_server" ))
  .settings(
    name := "layer_W_JVM_akka_http_server",
    version := Settings.version,
    scalaVersion := Settings.versions.scala,
    scalacOptions ++= Settings.scalacOptions,
    libraryDependencies ++= Settings.jvmDependencies.value,
    mainClass in Test := Some( "app.server.rest.testServers.TestServer_App_Basic_Data" ),
    mainClass in Compile := Some( "app.server.rest.TestHttpServerApp" ) //,
//    excludeDependencies ++= Seq(
//      ExclusionRule( "commons-logging", "commons-logging" )
//    )
  )
  .dependsOn( layer_Z_JVM_shared % "compile->compile;test->test" )
  .dependsOn( layer_X_JVM_stateAccess % "compile->compile;test->test" )

logBuffered in Test := false
//

persistLauncher in Compile := true

//persistLauncher in Test := false

cancelable in Global := true
