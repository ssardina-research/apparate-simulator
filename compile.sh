#!/bin/bash

rm -rf bin/*
javac -d bin/ -cp src/ \
	`find src/main/java/ -name *.java | xargs`


