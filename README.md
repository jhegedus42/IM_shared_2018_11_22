
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



#Unit testing the server

Execute the following script:

```
 ./runUnitTestsForServer.sh
```
