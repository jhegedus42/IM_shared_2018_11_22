
# Pre-requeisites

## Installing JS dependencies

In `node` directory run `yarn`
and then `./node_modules/.bin/webpack`. This will create
`node/generated.js/index-bundle.js` which contains all dependencies "bundled up"
into a single JS file.

# "Production"

Before starting the "production" server, in

```
app.server.RESTService.mocks.TestServerFactory
```

 set the parameter to the function
   `IndexDotHtmlTestTemplate.txt` to  `false`, so that it will read :


```
override lazy val rootPageHtml = IndexDotHtmlTestTemplate.txt(false)
```



then at the command prompt, run :
```
./runTestServerInSbt.sh
```
This compiles and builds everything.

If you want to clear all caches then execute the following command before the above command:
```
./clean.sh
```

then open the browser at `http://localhost:8043/`

This will create the following files in the `layer_V_JS_client/target/scala-2.11` directory:


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
> project layer_V_JS_client

> compile

> fastOptJS
```

