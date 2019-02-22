#!/bin/bash
while ! (nc -z config-server 8888 && nc -z eureka-server 8761); do sleep 3; echo 'Waiting for config-server and eureka-server to start-up...'; done
java -jar -Dspring.profiles.active=stage -Dspring.cloud.config.uri=http://config-server:8888 order-service.jar