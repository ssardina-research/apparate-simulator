#!/bin/bash

echo "Running path planner on configuration file" "$1"
echo ================================================
cat "$1"
echo `java -version`
echo ================================================

java -Djava.library.path=lib/ -cp bin/:lib/MyCoolAgent.jar \
	au.edu.rmit.agtgrp.apparate.gui.simviewer.controller.Launcher "$1"
