#!/bin/bash -e

cd $(dirname $0)

IMAGE=${IMAGE:-dockerstorm}

docker run -p 5000:5000 -p 6379:6379 -td \
-v "$(pwd)"/src:/src \
--name ${IMAGE} \
${IMAGE} bash

# docker exec -ti dockerstorm bash
