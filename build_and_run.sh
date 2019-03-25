#!/usr/bin/env bash


if [ -z "$1" ]
then
    echo "We need the app name as first parameter"
    exit 1
fi

if [ "$#" -ne 1 ]; then
    echo "Illegal number of parameters"
    exit 1
fi

HERE="$( cd "$( dirname "${BASH_SOURCE[0]}" )" >/dev/null 2>&1 && pwd )"
pushd .
cd $HERE
mvn package && APP_NAME=$1 docker-compose up --build
popd
