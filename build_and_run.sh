#!/usr/bin/env bash


if [ -z "$1" ] || [ -z "$2" ]; then
    echo "We need the app name as first parameter. As second, your host port."
    exit 1
fi

if [ "$#" -ne 2 ]; then
    echo "Illegal number of parameters"
    exit 1
fi

HERE="$( cd "$( dirname "${BASH_SOURCE[0]}" )" >/dev/null 2>&1 && pwd )"
pushd .
cd $HERE
mvn package && APP_NAME=$1 HTTP_HOST_PORT=$2 docker-compose up --build
popd
