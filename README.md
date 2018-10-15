
for runnning the test server in the `imJVM` project

`test:runMain app.server.RESTService.mocks.runnableApps.TestServer_App_LabelOne_ClientTesting`

for executing JS test on node in the `imJS` project:

`export NODE_PATH=./node_modules:$NODE_PATH`

in node directory run `yarn`
and then `./node_modules/.bin/webpack`


traffic can be analyzed with : 
sudo tcpflow -e http -i lo0 -C -g port 8043
