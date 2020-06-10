# java-agent-samples
Java agent samples

## To build 
mvn clean package

## To run 
java -jar -javaagent:my-agent-1.0-SNAPSHOT.jar my-agent-1.0-SNAPSHOT.jar  

## To debug
java -jar -Xdebug -Xrunjdwp:server=y,transport=dt_socket,address=4000,suspend=y -javaagent:my-agent-1.0-SNAPSHOT.jar my-agent-1.0-SNAPSHOT.jar

## Using fish
mvn clean package; and cd target; and java -jar -javaagent:my-agent-1.0-SNAPSHOT.jar my-agent-1.0-SNAPSHOT.jar; and cd ../

