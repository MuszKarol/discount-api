# Discount API

## Table of contents
* [General info](#general-info)
* [Requirements](#requirements)
* [Setup](#setup)
* [Usage](#usage)
* [Project Status](#project-status)
* [Sources](#sources)

## General info
Discount API provides endpoints for sharing and managing discounts. This project consists of:
* Spring Boot application
* OpenAPI documentation
* database connection (external or built in RAM memory database)

## Requirements
* JDK (17 or higher is recommended)
* Maven (project includes Maven wrapper)
* Lombok plugin
* Postgresql database (H2 for development)

## Setup
Before set up the Spring Boot application, copy the configuration file to the project directory "src/main/resources".
Then check the correctness of the "application.yaml" configuration, which should include the following properties:
* server.*
* image.*
* spring.*
* springdoc.*

Then run the following commands in the terminal open in the project root directory:

#### Copy "application.yml" to the project directory "src/main/resources":
```sh
cp <source-path>/application.yml <target-path>/application.yml
```

#### Clean "/target" folder in project (optional):
```sh
mvn clean
```
 
#### Compile the code and package as a JAR file:
```sh
mvn package
```

## Usage

#### Launch Spring Boot application:
```sh
mvn spring-boot:run
```

If the application runs correctly, it is possible to use OpenAPI to test the endpoints.

### Sonar Cube
Sonar Cube is an open-source platform for continuous inspection of code quality.

#### Build the Sonar Cube container using docker-compose command
```sh
docker-compose -f src/main/resources/docker/sonar.yml up -d local-sonar
```

#### Start code quality analysis using maven command
Powershell terminal
```sh
mvn clean verify sonar:sonar "-Dsonar.host.url=http://localhost:9001" "-Pcoverage"
```
other terminals
```sh
mvn clean verify sonar:sonar -Dsonar.host.url=http://localhost:9001 -Pcoverage
```

## Project Status
Project is: in progress

## Sources
##### OpenAPI Specification 
https://swagger.io/specification/

##### Guide to building Spring Boot application 
https://spring.io/guides/gs/spring-boot/

