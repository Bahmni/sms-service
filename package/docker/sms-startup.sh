#!/bin/bash
./home/generate_token.sh &
#DEBUG_OPTS="-agentlib:jdwp=transport=dt_socket,server=y,suspend=n,address=*:8081"
#java ${DEBUG_OPTS} -jar app.jar
java -jar app.jar