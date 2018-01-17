#!/bin/bash

rm -rf bin/*
javac -d bin/ -cp src/ \
	`find src/au/edu/rmit/agtgrp/apparate/agents/ -name *.java | xargs`


