#!/bin/bash

cd api-gateway
mvn package docker:build -DskipTests
cd ..

cd author
mvn package docker:build -DskipTests
cd ..

cd book
mvn package docker:build -DskipTests
cd ..

cd eureka
mvn package docker:build -DskipTests
cd ..

cd genre
mvn package docker:build -DskipTests
cd ..

cd random-search
mvn package docker:build -DskipTests
cd ..

cd rating
mvn package docker:build -DskipTests
cd ..

cd user
mvn package docker:build -DskipTests
cd ..

cd user-preferences
mvn package docker:build -DskipTests
cd ..

cd zuul
mvn package docker:build -DskipTests
cd ..
