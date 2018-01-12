#!/bin/bash

javac -d bin/ -cp src/ \
	`find src/au/rmit/ai/agtgrp/apparate/agents/ -name *.java | xargs`


