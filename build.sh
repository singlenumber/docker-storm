#!/bin/bash

set -e

pushd $(dirname $0)

containerName=${containerName:-dockerstorm}

docker build -t $containerName .
