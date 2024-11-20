# Java Grep App

## Introduction
This Grep application allows users to search regular expression  patterns in files within a specified directory and its subdirectories. The search results are written in a seperate file. Users will have options to pass the arguments in command line interface including text pattern, the direcory and the result file.
The project is a well-organized Java application that leverages advanced Java features and industry-standard tools throughout its development, testing, and deployment phases. Built using IntelliJ IDEA, the project is managed and built with Maven, handling all dependencies effectively. File operations are managed with Java.io, while the Stream API and Lambda functions are utilized to simplify and enhance data processing. The application is packaged as a JAR file through Maven, with dependencies like SLF4J for logging. For deployment, a Docker image was created via a Dockerfile, distributed on Docker Hub, and executed within a Docker container.

## Quick Start
Pull the Docker image from Docker Hub and run a container using that image. The necessary volumes are specified during the container launch to allow the application to deploy and run within the Docker environment.

```
#Pull image from Docker
docker pull umarani100/grep

#**-v \pwd`/data:/data**: Maps the data directory from current host directory (using pwd to get the current directory) to /data inside the container. This makes files in data accessible to the container.

docker run -v `pwd`/data:/data -v `pwd`/log:log umarani100/grep .*Romeo.*Juliet.* /data   /log/grep.out
```

## Implementation
This project's implementation involved using Java, Maven, and Docker to build, package, and deploy a Java application capable of reading and writing to files.


## Performance Issue

When handling large file systems and large files in Java, it is essential to set apporopriate Java Heap size, if heap memory not set properly, it may lead to OutOfMemoryException during run time. To address memory issue apporopriate directory should be given as arguments. 

## dockerfile
```
FROM openjdk:8-alpine
COPY target/grep*.jar /usr/local/app/grep/lib/grep.jar
ENTRYPOINT ["java", "-jar", "/usr/local/app/grep/lib/grep.jar"]
```
## Deployment

The application is available in Docker hub:
[Docker Hub](https://hub.docker.com/repository/docker/umarani100/grep/general)

## Improvements
1. Implementation of multi threading and parallel streams instead of stream for better performance.
2. Provide Statistics including number of files, lines and volume processed to get results for improved user engagement.

