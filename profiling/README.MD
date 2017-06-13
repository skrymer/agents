# Java agent example
Some more text

## To build 
mvn clean package

## To run 
java -jar -javaagent:profiler-agent-1.0-SNAPSHOT.jar -DpackageToBeProfiled="com/skrymer/test/tobeinstrumented" java.agent.example-1.0-SNAPSHOT-jar-with-dependencies.jar  

## To debug
java -jar -Xdebug -Xrunjdwp:server=y,transport=dt_socket,address=4000,suspend=y -javaagent:java.agent.example-1.0-SNAPSHOT-jar-with-dependencies.jar -DpackageToBeProfiled="com/skrymer/javaagent/example/tobeinstrumented" java.agent.example-1.0-SNAPSHOT-jar-with-dependencies.jar

## Using Fish
mvn clean package; and cd target; and java -jar -javaagent:profiler-agent-1.0-SNAPSHOT.jar -DpackageToBeProfiled="com/skrymer/test/tobeinstrumented" profiler-agent-1.0-SNAPSHOT.jar; and cd ../
