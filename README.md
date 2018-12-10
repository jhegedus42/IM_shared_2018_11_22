# What is this ?

A humble attempt at writing a 'full-stack' end-to-end 'Scala-only' Single-page-app "Hello-World web-framework" 
with the following properties: 
- Server :
  - Event Sourcing
  - Persistence
    - achieved by journaling all CRUD events
    
- Client 
    - React
    - Single page application
    - URL router
    - State is NOT stored using
      - FLUX
      - DIODE 
      - REDUX
    - State is stored in a Cache 
        - simple and transparent
        - same (relational) data representation as on server
          - relational in the sense : 
             - both normalized and denormalized representation of the domain model is possible
             - references : "primary keys", "foreign keys"
             - joins, etc ...
          - data is contained in `case classes`
          
        - can contain both normalized ("Entities") and denormalized data (views)

- Domain model
    - Normalized data representation (relational model)
    - Views that can be denormalized 
     
- Communication
  - type-safe RPC calls (poor man's [servant](https://haskell-servant.github.io/) for Scala) 
    - implemented using type classes
    - type-safe RPC call interface is available to client and server
  - the same data structures (types) can be used in the source code for both 
    - the client
    - the server
  - this is possible because : part of the source code is compiled into both
    - `JVM bytecode`
    - `Javascript` 
  - Entities, Views, RPC calls are transmitted as `case classes` encoded into `JSON`
  


# Pre-requeisites

## Installing JS dependencies

In `node` directory run `yarn`
and then `./node_modules/.bin/webpack`. This will create
`node/generated.js/index-bundle.js` which contains all dependencies "bundled up"
into a single JS file.

# Starting the Test Server

First, ake sure that in

```
app.server.RESTService.mocks.TestServerFactory
```

 the parameter to the function
   `IndexDotHtmlTestTemplate.txt` is set to  `false`, so that it reads :


```
override lazy val rootPageHtml = IndexDotHtmlTestTemplate.txt(false)
```



then at the command prompt, run :
```
utils/runTestServerInSbt.sh
```
This compiles and builds everything.

If you want to clear all caches then execute the following command before the above command:
```
./clean.sh
```

then open the browser at `http://localhost:8043/`



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

# Generating html for README.md

- Install `pandoc`
- Execute `utils/markdown/generateHtmlForREADME.sh` in the base directory (where `README.md` is)
