#!/usr/bin/env bash

mvn package && docker-compose up --build
