#!/bin/bash

echo "Running path planner on configuration file" "$1"
echo ================================================
cat "$1"
echo `java -version`
echo ================================================

java -Djava.library.path=lib/ -cp bin/:lib/JPathPlan.jar:lib/MyCoolAgent.jar au.rmit.agtgrp.apparate.gui.simviewer.controller.Launcher "$1"
