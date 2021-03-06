# Image scaler

A cloud ready service designed to upload images and scale them based on a configuration.

## Key features
* Dynamic configuration for scaling pictures.
* Session and messaging management supported by Redis.
* Status and health http endpoints for each process.
* Asynchronous scaling of images on a separate backend process.
* All data external to containers.
* MongoDB using GridFs as first storage layer.
* Api doc with swagger.

## Required to run
* Java 1.8+ (Open JRE/JDK)
* Maven
* Docker compose

## How to run
This project has a [docker-compose.yml](docker-compose.yml) file.
There is a nice compile and run script in the project root that
you can use to run unit tests and bring up all the stack easily:

```bash
# First, param the name for your app. Second param the exposed port on your machine. 
./build_and_run.sh "Your desired service name" 8080
```
Go to http://your-host:port

## How to run unit tests
```bash
mvn test
```

## How to run integration tests
```bash
mvn integration-test
```

## Architecture
A layered architecture with some domain driven design traits. It covers many 
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