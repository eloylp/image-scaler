# Image scaler

A cloud ready service designed to upload pictures and scale them accordingly.

## Key features
* Dynamic configuration for scaling pictures.
* Session and messaging management supported by Redis.
* Status and health http endpoints for each process.
* Asynchronous scaling of images on a separate backend process.
* All data external to containers.
* MongoDB as first storage layer.
* Api doc with swagger.

## Required to run
* Java 1.8+
* Maven
* Docker compose

## How to run
This project has a docker compose file.
There is a nice compile and run script in the project root that
you can use to bring up all the stack easily:

```bash
# First, param the name for your app. Second param the exposed port on your machine. 
./build_and_run.sh "Your desired service name" 8080
```

## How to run tests
```bash
mvn clean test
```
## Architecture
A flavored Domain Driven Design. It covers many 
best practices and patterns to ensure the good reading and scalability of code.
It has a clever separation of all infrastructure layers like storage, messaging
analyzers and more.

## Api
An updated doc of the api can be reached through swagger at
```bash
http://your-host:port/swagger-ui.html
```

## Environment
All the vars can be found in the [docker-compose.yml](docker-compose.yml) file

## Health and status checks
All can be found at:
```bash
http://your-host:port/actuator
```