#!/bin/bash

IS_GREEN=$(docker ps | grep jewelry-green)
IMAGE_TAG=$1
DOCKER_USERNAME=$2

if [ -z "$IMAGE_TAG" ]; then
  echo "ERROR: Image tag argument is missing."
  exit 1
fi

if [ -z "$IS_GREEN"  ];then # blue라면

  echo "### BLUE => GREEN ###"

  echo "1. get green image"
  docker-compose -f /home/ec2-user/docker/docker-compose.yml pull green # green으로 이미지를 내려받습니다.

  echo "2. green container up"
  docker-compose -f /home/ec2-user/docker/docker-compose.yml up -d green # green 컨테이너 실행
  while [ 1 = 1 ]; do
  echo "3. green health check..."
  sleep 3

  REQUEST=$(curl http://127.0.0.1:8082) # green으로 request
    if [ -n "$REQUEST" ]; then # 서비스 가능하면 health check 중지
            echo "health check success"
            break ;
            fi
  done;

  echo "4. reload nginx"
  sudo cp /etc/nginx/conf.d/app/service-url-green.inc /etc/nginx/conf.d/app/service-url.inc
  sudo nginx -s reload

  echo "5. blue container down"
  docker-compose -f /home/ec2-user/docker/docker-compose.yml stop blue
else
  echo "### GREEN => BLUE ###"

  echo "1. get blue image"
  docker-compose -f /home/ec2-user/docker/docker-compose.yml pull blue

  echo "2. blue container up"
  docker-compose -f /home/ec2-user/docker/docker-compose.yml up -d blue

  while [ 1 = 1 ]; do
    echo "3. blue health check..."
    sleep 3
    REQUEST=$(curl http://127.0.0.1:8081) # blue로 request

    if [ -n "$REQUEST" ]; then # 서비스 가능하면 health check 중지
      echo "health check success"
      break ;
    fi
  done;

  echo "4. reload nginx"
  sudo cp /etc/nginx/conf.d/app/service-url-blue.inc /etc/nginx/conf.d/app/service-url.inc
  sudo nginx -s reload

  echo "5. green container down"
  docker-compose -f /home/ec2-user/docker/docker-compose.yml stop green
fi