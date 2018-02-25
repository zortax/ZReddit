#!/bin/bash bash

cd ZReddit
mvn clean compile assembly:single
cd target
mv ZReddit-jar-with-dependencies.jar ZReddit.jar
