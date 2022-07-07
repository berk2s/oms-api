#!/bin/bash -x
echo "Project is being running"
docker-compose rm -f
docker rmi berk2s/oms-api:main
docker rmi berk2s/oms-api:main -f
docker build -t berk2s/oms-api:main . -f Dockerfile.ci
docker compose up
read -p "Press any key for terminate process"
docker compose down
docker compose rm -f
docker rmi berk2s/oms-api:main
docker rmi berk2s/oms-api:main -f
echo "Images and containers deleted"