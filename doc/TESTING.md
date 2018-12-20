
# Testing 

## Unit testing the server

Execute the following script:

```
 ./runUnitTestsForServer.sh
```

### Compiling all (server side) tests

```
sbt
> test:compile
``` 

### Running the unit tests 
We run unit tests for two different types of code:
1) for testing pure JVM bytecode which is compiled for running the the server 
2) for JVM and JS code which is compiled from the same Scala source, which corresponds to :
    - the logic which is used by both the server and by the client, this is a so called "shared-code" 
         that contains mainly:
       - data structures
         - that represent the "domain logic" of the application, 
         because they are used both by the server and by the client to represent and
         manipulate domain knowledge data
       - code for "type-safe" client-server communication
          - for example : what kind of 'commands' can be
         be sent to the server by the client and what kind of answer can the client expect in return :
            - this has been implemented as type "safely" as possible
            - this code is used to create type class instances on the server side, which contain
              the logic that calculates the answer for the client's requests


First let's clean up the project and execute the package manager In `bash`:
```bash
./clean.sh
sbt
```
then in the `sbt` shell (I insert extra empty lines below for better readability):

```bash
> clean

> projects # this shows what kind of modules need to be tested 
             (the ones which whave JVM in their name)
             
[info] In file:/Users/joco/dev/IM/im-2018jan/
[info]   * im-2018jan
[info]     layer_V_JS_client
[info]     layer_W_JVM_akka_http_server
[info]     layer_X_JVM_stateAccess
[info]     layer_Y_JVM_persistence
[info]     layer_Z_JVM_and_JS_sharedJS
[info]     layer_Z_JVM_and_JS_sharedJVM

> layer_W_JVM_akka_http_server/test

... output of tests running ...

[info] Run completed in 3 seconds, 621 milliseconds.
[info] Total number of tests run: 29
[info] Suites: completed 8, aborted 0
[info] Tests: succeeded 29, failed 0, canceled 0, ignored 0, pending 0

> layer_X_JVM_stateAccess/test
[info] Run completed in 19 milliseconds.
[info] Total number of tests run: 0
[info] Suites: completed 0, aborted 0
[info] Tests: succeeded 0, failed 0, canceled 0, ignored 0, pending 0
[info] No tests were executed.
[success] Total time: 1 s, completed Dec 9, 2018 6:47:43 AM

> layer_Y_JVM_persistence/test

... output of tests running ...

[info] Run completed in 1 second, 747 milliseconds.
[info] Total number of tests run: 4
[info] Suites: completed 3, aborted 0
[info] Tests: succeeded 4, failed 0, canceled 0, ignored 0, pending 0
[info] All tests passed.
[success] Total time: 3 s, completed Dec 9, 2018 6:53:33 AM

> layer_Z_JVM_and_JS_sharedJVM/test

... output of tests running ...

[info] Run completed in 693 milliseconds.
[info] Total number of tests run: 6
[info] Suites: completed 3, aborted 0
[info] Tests: succeeded 6, failed 0, canceled 0, ignored 0, pending 0
[info] All tests passed.
[info] Passed: Total 6, Failed 0, Errors 0, Passed 6
[success] Total time: 2 s, completed Dec 9, 2018 6:57:20 AM


# we can also test the same code but compiled to javascript by :

> layer_Z_JVM_and_JS_sharedJS/test 

# in this case - first - the `.js` file `layer_z_js_shared-test-fastopt.js` 
# - needed for testing - will be built :

[info] Updating {file:/Users/joco/dev/IM/im-2018jan/}layer_Z_JVM_and_JS_sharedJS...
[info] Resolving org.eclipse.jetty#jetty-continuation;8.1.16.v20140903 ...
[info] Done updating.
[info] Compiling 34 Scala sources to /Users/joco/dev/IM/im-2018jan/layer_Z_JVM_and_JS_shared/.js/target/scala-2.12/classes...
[warn] there were two feature warnings; re-run with -feature for details
[warn] one warning found
[info] Compiling 5 Scala sources to /Users/joco/dev/IM/im-2018jan/layer_Z_JVM_and_JS_shared/.js/target/scala-2.12/test-classes...
[info] Fast optimizing /Users/joco/dev/IM/im-2018jan/layer_Z_JVM_and_JS_shared/.js/target/scala-2.12/layer_z_js_shared-test-fastopt.js

... output of tests running ...

[info] Run completed in 9 seconds, 594 milliseconds.
[info] Total number of tests run: 6
[info] Suites: completed 3, aborted 0
[info] Tests: succeeded 6, failed 0, canceled 0, ignored 0, pending 0
[info] All tests passed.
[success] Total time: 16 s, completed Dec 9, 2018 7:05:26 AM
```

In short, you can also execute the following command in `bash`:

```bash
./runUnitTestsForServer.sh
```

## Integration testing (client <-> server)

- Execute the code below for compiling and building client side (integration) test code:

```
sbt
> project layer_V_JS_client
> test:clean
> test:compile
> test:fastOptJS
```

The last command should give something like this:
```bash
> test:fastOptJS
[info] Fast optimizing /Users/joco/dev/IM/im-2018jan/layer_V_JS_client/target/scala-2.12/layer_v_js_client-test-fastopt.js
[success] Total time: 14 s, completed Dec 10, 2018 8:27:23 AM
```

- Start a test server ( with `utils/runTestServerInSbt.sh` )
- Execute the integration tests. There are two possibilities for doing this :
    - `node` :  running the integration tests on `node`
    - `karma` :  running the integration tests in the browser
    
For running the integration tests using `node`, execute the following commands in the root directory of the 
project (where this README.md file is located), also make sure that the test server is running :

In bash:
```bash
export NODE_PATH=./node/node_modules:$NODE_PATH
sbt
``` 
In `sbt`  REPL:
```sbtshell
> project layer_V_JS_client
> clean
> test:compile
> test:fastOptJS
```

This should give something like the following :
```sbtshell
Jozsefs-MacBook-Pro:im-2018jan joco$ sbt
[info] Loading global plugins from /Users/joco/.sbt/0.13/plugins
[info] Loading project definition from /Users/joco/dev/IM/im-2018jan/project
[info] Set current project to IM root project (in build file:/Users/joco/dev/IM/im-2018jan/)
> project layer_V_JS_client
[info] Set current project to layer_V_JS_client (in build file:/Users/joco/dev/IM/im-2018jan/)
> clean
[success] Total time: 1 s, completed Dec 11, 2018 8:50:02 PM
> test:compile
[info] Updating {file:/Users/joco/dev/IM/im-2018jan/}layer_V_JS_client...
[info] Resolving org.eclipse.jetty#jetty-continuation;8.1.16.v20140903 ...
[info] Done updating.
[info] Compiling 21 Scala sources to /Users/joco/dev/IM/im-2018jan/layer_V_JS_client/target/scala-2.12/classes...
[warn] /Users/joco/dev/IM/im-2018jan/layer_V_JS_client/src/main/scala/app/client/Main.scala:11: @JSExport on objects is deprecated and will be removed in 1.0.0. Use @JSExportTopLevel instead. Note that it exports the object itself (rather than a 0-arg function returning the object), so the calling JavaScript code must be adapted.
[warn]   (you can suppress this warning in 0.6.x by passing the option `-P:scalajs:suppressExportDeprecations` to scalac)
[warn] @JSExport( "Main" )
[warn]  ^
[warn] /Users/joco/dev/IM/im-2018jan/layer_V_JS_client/src/main/scala/app/client/_jsTools/JsTools.scala:17: @JSExport on objects is deprecated and will be removed in 1.0.0. Use @JSExportTopLevel instead. Note that it exports the object itself (rather than a 0-arg function returning the object), so the calling JavaScript code must be adapted.
[warn]   (you can suppress this warning in 0.6.x by passing the option `-P:scalajs:suppressExportDeprecations` to scalac)
[warn] @JSExport
[warn]  ^
[warn] /Users/joco/dev/IM/im-2018jan/layer_V_JS_client/src/main/scala/app/client/_jsTools/JsTools.scala:45: Top-level native JS classes and objects should have an @JSGlobal or @JSImport annotation. This will be enforced in 1.0.
[warn]   If migrating from 0.6.14 or earlier, the equivalent behavior is an @JSGlobal without parameter.
[warn]   (you can suppress this warning in 0.6.x by passing the option `-P:scalajs:suppressMissingJSGlobalDeprecations` to scalac)
[warn] object vkbeautify extends js.Object {
[warn]        ^
[warn] there were 22 deprecation warnings (since 0.5.3)
[warn] there was one deprecation warning (since 0.6.20)
[warn] there was one deprecation warning (since always)
[warn] there were 24 deprecation warnings in total; re-run with -deprecation for details
[warn] 7 warnings found
[info] Compiling 9 Scala sources to /Users/joco/dev/IM/im-2018jan/layer_V_JS_client/target/scala-2.12/test-classes...
[success] Total time: 56 s, completed Dec 11, 2018 8:51:06 PM
> test:fastOptJS
[info] Fast optimizing /Users/joco/dev/IM/im-2018jan/layer_V_JS_client/target/scala-2.12/layer_v_js_client-test-fastopt.js
[success] Total time: 10 s, completed Dec 11, 2018 8:51:25 PM
>
```

Now execute the `test` command in sbt:

```sbtshell
> test
```

