# Trace-agent
Java agent for tracing - duhhhh

## To build 
mvn clean package

## To use in your app
TODO package agent without main class

# Development

## To run 
java -jar -javaagent:tracing-agent-1.0-SNAPSHOT.jar -DpackageToBeTraced="com/skrymer/test/somepackage" tracing-agent-1.0-SNAPSHOT.jar  

java -jar  tracing-agent-1.0-SNAPSHOT.jar
## To debug
java -jar -Xdebug -Xrunjdwp:server=y,transport=dt_socket,address=4000,suspend=y -javaagent:tracing-agent-1.0-SNAPSHOT.jar -DpackageToBeTraced="com/skrymer/test/somepackage" tracing-agent-1.0-SNAPSHOT.jar

## Using fish
mvn clean package; and cd target; and java -jar -javaagent:tracing-agent-1.0-SNAPSHOT.jar -DpackageToBeTraced="com.skrymer.test.somepackage" tracing-agent-1.0-SNAPSHOT.jar 
