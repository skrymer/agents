# Commons agent
Inserts toString, Equals and HashCode functions using apache commons

## To build 
mvn clean package

## To run 
java -jar -javaagent:commons-agent-1.0-SNAPSHOT.jar commons-agent-1.0-SNAPSHOT.jar  

## To debug
java -jar -Xdebug -Xrunjdwp:server=y,transport=dt_socket,address=4000,suspend=y -javaagent:common-agent-1.0-SNAPSHOT.jar commons-agent-1.0-SNAPSHOT.jar

## Using fish
mvn clean package; and cd target; and java -jar -javaagent:commons-agent-1.0-SNAPSHOT.jar commons-agent-1.0-SNAPSHOT.jar; and cd ../

