# Discount API

## Table of contents
* [General info](#general-info)
* [Requirements](#requirements)
* [Setup](#setup)
* [Usage](#usage)
* [Project Status](#project-status)
* [Sources](#sources)

## General info
The Discount API provides a set of endpoints for sharing and managing discounts.
This project is built using Spring Boot and includes OpenAPI documentation. 
It is designed to be connected to an external PostgreSQL database.

## Requirements
The following software and tools are required to build and run this project:
* Java Development Kit: A JDK with version 17 or higher is recommended.
* Apache Maven: The project includes a Maven wrapper for ease of use. However, you may also use your own installation of Maven.
* Lombok Plugin: This plugin is required for the project. Please ensure that it is installed and configured correctly in your development environment.
* PostgreSQL Database: A PostgreSQL database is required for production use. For development purposes, an H2 database can be used.

## Setup
1. Copy the configuration file to the project directory located at ``src/main/resources``.
2. Verify the correctness of the ``application.yaml`` configuration file. This file should include the following properties:
 * server.*
 * image.*
 * spring.*
 * springdoc.*
3. Run the appropriate commands in the terminal open in the project root directory.

#### Copy "application.yml" to the project directory "src/main/resources":
```
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

### Launching the Spring Boot Application

##### Launch the Spring Boot application:
```sh
mvn spring-boot:run
```

If the application is executed correctly, it is possible to use OpenAPI to test the endpoints.

### Sonar Cube
Sonar Cube is an open-source platform designed for continuous inspection of code quality.

#### Initialize the Sonar Cube container using docker-compose:
```sh
docker-compose -f src/main/resources/docker/sonar/sonar.yml up -d local-sonar
```

#### Initialize code quality analysis using Maven:
In Powershell terminal:
```sh
mvn clean verify sonar:sonar "-Dsonar.host.url=http://localhost:9001" "-Pcoverage"
```
In other terminals:
```sh
mvn clean verify sonar:sonar -Dsonar.host.url=http://localhost:9001 -Pcoverage
```

## Project Status
This project is currently under development.
Work is being done towards a stable release, which will be available in the near future.
At present, the focus is on implementing and refining category functionalities.

## Sources
##### OpenAPI Specification 
https://swagger.io/specification/

##### Guide to building Spring Boot application 
https://spring.io/guides/gs/spring-boot/

