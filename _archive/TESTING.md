# Integration Testing (OUTDATED info here - needs to be updated!!!)
This means in practice that the "client <-> server communication" is tested +
the commands/queries sent by the client are executed by the server correctly and
the expected results are returned.


## Executing JS tests on `Node.js` (and not in the browser).

In the `imJS` project:

Before starting `sbt`, execute:

`export NODE_PATH=./node/node_modules:$NODE_PATH`

Then start `sbt` and run the tests from there :

```
> project layer_V_JS_client
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
