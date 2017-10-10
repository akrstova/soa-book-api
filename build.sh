#!/bin/bash

cd api-gateway
mvn package docker:build
cd ..

cd author
mvn package docker:build
cd ..

cd book
mvn package docker:build
cd ..

cd eureka
mvn package docker:build
cd ..

cd genre
mvn package docker:build
cd ..

cd random-search
mvn package docker:build
cd ..

cd rating
mvn package docker:build
cd ..

cd user
mvn package docker:build
cd ..

cd user-preferences
mvn package docker:build
cd ..

cd zuul
mvn package docker:build
cd ..
