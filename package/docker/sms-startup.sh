#!/bin/bash
java -agentlib:jdwp=transport=dt_socket,server=y,suspend=n,address=*:8081 -jar app.jar &
./home/generate_token.sh &
tail -f /dev/null


##!/bin/bash
#./home/generate_token.sh &
#java -jar app.jar
