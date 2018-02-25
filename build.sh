#!/bin/bash bash
cd ZReddit
mvn clean compile assembly:single
mv ZReddit-jar-with-dependencies.jar ZReddit.jar
