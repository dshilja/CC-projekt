#!/usr/bin/env bash

set -o errexit
set -o errtrace
set -o nounset
set -o pipefail

pkill -9 -f spring-onlinestore || echo "Failed to kill any apps"

docker-compose kill || echo "No docker containers are running"

echo "Running infra"
docker-compose up -d grafana-server prometheus-server tracing-server

echo "Running apps"
mkdir -p target
nohup java -jar spring-onlinestore-config-server/target/*.jar --server.port=8888 --spring.profiles.active=chaos-monkey > target/config-server.log 2>&1 &
echo "Waiting for config server to start"
sleep 20
nohup java -jar spring-onlinestore-discovery-server/target/*.jar --server.port=8761 --spring.profiles.active=chaos-monkey > target/discovery-server.log 2>&1 &
echo "Waiting for discovery server to start"
sleep 20
nohup java -jar spring-onlinestore-customers-service/target/*.jar --server.port=8081 --spring.profiles.active=chaos-monkey > target/customers-service.log 2>&1 &
nohup java -jar spring-onlinestore-api-gateway/target/*.jar --server.port=8080 --spring.profiles.active=chaos-monkey > target/gateway-service.log 2>&1 &
echo "Waiting for apps to start"
sleep 60
