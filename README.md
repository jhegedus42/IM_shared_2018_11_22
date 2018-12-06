
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



# Testing

## Unit testing the server

Execute the following script:

```
 ./runUnitTestsForServer.sh
```

## Compiling all (server side) tests

```
sbt
> test:compile
``` 

## Integration testing (client <-> server)

Execute the code below for compiling and building client side (integration) test code:

```
sbt
> project layer_V_JS_client
> test:clean
> test:compile
> test:fastOptJS
```

QUESTION : how to execute these integrations tests ??? //TODO
