#!/bin/bash -e

cd $(dirname $0)

IMAGE=${IMAGE:-dockerstorm}

docker run -p 5000:5000 -p 6379:6379 -ti --rm \
-v "$(pwd)"/src:/src \
--name ${IMAGE} \
${IMAGE} $*
