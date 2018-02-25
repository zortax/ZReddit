#!/bin/bash bash
cd ZReddit
mvn clean compile assembly:single
cd target
cp ZReddit-jar-with-dependencies.jar ZReddit.jar
