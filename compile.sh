#!/bin/bash

javac -d bin/ -cp src/ \
	`find src/au/edu/rmit/agtgrp/apparate/agents/ -name *.java | xargs`


