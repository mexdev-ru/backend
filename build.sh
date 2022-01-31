#!/bin/bash
docker run -it --rm --name my-maven-project -v "$(pwd)":/usr/src/mymaven -w /usr/src/mymaven maven:3.8-openjdk-11 mvn clean install -D skipTests