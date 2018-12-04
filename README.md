
# Pre-requeisites

## Installing JS dependencies

In `node` directory run `yarn`
and then `./node_modules/.bin/webpack`. This will create
`node/generated.js/index-bundle.js` which contains all dependencies "bundled up"
into a single JS file.

# "Production"

## Compiling and running the "production" server

Before starting the "production" server, in

```
app.server.RESTService.mocks.TestServerFactory
```

 set the parameter to the function
   `IndexDotHtmlTestTemplate.txt` to  `false`, so that it will read :


```
override lazy val rootPageHtml = IndexDotHtmlTestTemplate.txt(false)
```


This means that a `html` "main" page will be generated that uses the production (non-test)
`js` files (which are output of the compiler, compiling the production code and not
the test code).

Then, run `sbt` and execute :

```
> layer_W_JVM_akka_http_server/test:runMain app.server.RESTService.mocks.runnableApps.TestServer_App_LabelOne_ClientTesting "localhost"
```

or at the command prompt, run :
```
./runTestServerInSbt.sh
```

then open the browser at `http://localhost:8043/`

## Compiling the "production" client

Start a second `sbt`:
  - "Second" here means: "other than the one in which the test server is running."
  -  it should be started in the project root, where this `README.md` file is located

  Execute the following commands in the REPL:

```bash
> project layer_Y_JS_client

> compile

> fastOptJS
```

This will create the following files in the `layer_Y_JS_client/target/scala-2.11` directory:

```
layer_y_js_client-fastopt.js        <<<=== the main appp
layer_y_js_client-fastopt.js.map    <<<=== source map
layer_y_js_client-jsdeps.js         <<<=== dependencies (empty in this case)
layer_y_js_client-launcher.js       <<<=== app launcher (called by the 'html page')
```

# (Integration) Testing

## Compiling and running the test server

In contrary to the "production" case, here, set the parameter to `IndexDotHtmlTestTemplate.txt` to `false`
in `app.server.RESTService.mocks.TestServerFactory`.

Then start `sbt` and type:

```
> layer_W_JVM_akka_http_server/test:runMain app.server.RESTService.mocks.runnableApps.TestServer_App_LabelOne_ClientTesting "localhost"
```

or at the command prompt, run :
```
./runTestServerInSbt.sh
```

then open the browser at `http://localhost:8043/`

## Compiling the test client

Start a second `sbt`:
  - "Second" here means: "other than the one in which the test server is running."
  -  it should be started in the project root, where this `README.md` file is located

  Execute the following commands in the REPL:

```
> project layer_Y_JS_client

> compile

> fastOptJS
```


## Executing JS tests on `Node.js` (and not in the browser).

In the `imJS` project:

Before starting `sbt`, execute:

`export NODE_PATH=./node/node_modules:$NODE_PATH`

Then start `sbt` and run the tests from there :

```
> project layer_Y_JS_client
> test
```

^^^ this might not be 100% correct/enough, maybe there is something else is also needed.

Note: right now no tests seem to be found ... this needs to be investigated.
The output is :

```
> test
[info] Run completed in 4 milliseconds.
[info] Total number of tests run: 0
[info] Suites: completed 0, aborted 0
[info] Tests: succeeded 0, failed 0, canceled 0, ignored 0, pending 0
[info] No tests were executed.
[success] Total time: 9 s, completed Dec 4, 2018 11:16:57 PM
```
even though tests should have been found and executed. This needs to be looked into. ==>> TODO
Somehow it does not find the tests... even though it used to ...


# Other tips

- traffic can be analyzed with :  `sudo tcpflow -e http -i lo0 -C -g port 8043`
