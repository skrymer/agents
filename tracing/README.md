# Trace-agent
Java agent for tracing - duhhhh

## To build 
mvn clean package

## To use in your app
TODO package agent without main class

# Development

## To run 
java -jar -javaagent:tracer-1.0-SNAPSHOT-jar-with-dependencies.jar -DpackageToBeTraced="com/skrymer/test/somepackage" tracer-1.0-SNAPSHOT-jar-with-dependencies.jar  

## To debug
java -jar -Xdebug -Xrunjdwp:server=y,transport=dt_socket,address=4000,suspend=y -javaagent:tracer-1.0-SNAPSHOT-jar-with-dependencies.jar -DpackageToBeTraced="com/skrymer/test/somepackage" tracer-1.0-SNAPSHOT-jar-with-dependencies.jar

## Using fish
mvn clean package; and cd target; and java -jar -javaagent:tracer-1.0-SNAPSHOT-jar-with-dependencies.jar -DpackageToBeTraced="com.skrymer.test.somepackage" tracer-1.0-SNAPSHOT-jar-with-dependencies.jar 
