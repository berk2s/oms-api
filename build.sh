#!/bin/bash -x
echo "Project is being running"
docker-compose rm -f
docker rmi berk2s/oms-api
docker rmi berk2s/oms-api -f
docker build -t berk2s/oms-api .
docker compose up
read -p "Press any key for terminate process"
docker compose down
docker compose rm -f
docker rmi berk2s/oms-api
docker rmi berk2s/oms-api -f
echo "Images and containers deleted"