export NODE_PATH=./node/node_modules:$NODE_PATH
sbt 'compile'
sbt 'test'