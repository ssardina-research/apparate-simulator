## Prerequisites


* Java Runtime Environment (JRE) and Java Compiler (javac) v1.6+ (Sun version recommended)
* `libHRTimer.so` to have access to C access to the clock.
* A configuration file (see below)
* Maps in the [Moving-AI+](http://movingai.com/benchmarks/) format + extension with cost (see below).
* Dynamic map scripts (if needed).


## Compiling APPARATE 

### Compile from CLI (command line)

We show here how to compile the framework from CLI (Command Line Interface). 

To compile the simulator and GUI system (package `ppplanning`) and place all `.class` files under `bin/`, run:

```
javac -d bin/ -cp src/ \    
    src/au/edu/rmit/agtgrp/apparate/gui/simviewer/controller/Launcher.java
```

Then compile all agents available in package `agents`:

```
javac -d bin/ -cp src/  \
    `find src/au/edu/rmit/agtgrp/apparate/agents/ -name *.java | xargs`
```

You can also use the script `./compile.sh` to compile into directory `bin/`.

### Compile using Maven

The [Maven Getting Started](https://maven.apache.org/guides/getting-started/index.html) can be helpful for those not familiar with Maven.

When running maven, everything will be produced under `target/`:

* You can compile using `mvn compile`; classes will be located in `target/classes`.
* Then, you can produce the JAR file for the system using `mvn jar:jar`. 
* A JAR files with sources can be produced via `mvn sources:jar`. 
* Javadoc can be produced via `mvn javadoc:jar`. The Javadoc will be left under `target/apidoc`.
* Everything can be produced under `target/` running `mvn package`.



## Running the Simulator

There are three ways to run the simulator: via CLI, via script `run.sh`, or via Maven.

To run the system **from CLI**:

```
java -cp bin/: \
    au.edu.rmit.agtgrp.apparate.gui.simviewer.controller.Launcher <config-file>
```

where:

* `au.rmit.ai.agtgrp.apparate.gui.simviewer.controller.Launcher` is the main class that starts the application.
* `<config-file>` contains all the options for the simulation. See [Configuration File](Configuration File) section for details on such file and an example.

You can also use the **running script** provided: 

        ./run.sh <conf-file>

Finally, you can run it **using Maven**: 

        mvn exec:java -Dexec.args=config-extras.properties

See this is like running 

        mvn exec:java -Dexec.mainClass="au.edu.rmit.agtgrp.apparate.gui.simviewer.controller.Launcher" -Dexec.args=config-extras.properties`
		

## GUI Interface Quick Start

While self-explanatory in most cases here is what can be done via the GUI:

#### File 

* `File->"Open Map"`:
    * Opens a new map of file type "*.map"
* `File->"Open Script"`:
    * Opens the script for the current map. Will replace the existing one if exists
		
#### Environment
		
* `Environment->"A*"|"MTD A*"|"LSS LTR A*"|"ALSS LTR A*"`: Which planner to use. These are the common planners.
* `Environment->"Others..."`: This allow you use other planners not listed above. This opens up a dialog box for you to enter `<plannertype>`.
* `Environment->"Grid as Manhatten"|"Grid as Euclidean"`: Determines if the agent is allow to move diagonal.

#### Layer

* `Layer->"Show Traversable Map"|"Show Terrain map"`: Show only the traversable area of the map or all details of the map.
* `Layer->"Show Start Position"|"Show Destination Position"`: Show the current location of the start and the destination.
* `Layer->"Show Plan Path"`: Show the path the agent has planned.
* `Layer->"Show Travelled Path"`: Show the path which the agent has traversed.
* `Layer->"Show Expanded Node"|"Show Unexpanded Node"`: Show which location has been opened and which been closed.

#### Run
	
* `Run->"Loop"`: Enable this to continuously step through the map, uncheck this to attempt to pause.
* `Run->"Step"`: Click to perform a single step on the map.
* `Run->"Stop on Arrival`: Check this to automatically stop looping once the agent has reach the destination.

#### Status Bar		

* `[Status Bar]->[Left]`: Shows the current step, and if it is running, pausing, stopped or errored.
* `[Status Bar]->[Middle Right]`: Displays the mouse status, show the coordinate of the mouse on the map. Also display the current function active and the selected area if applicable
* `[Status Bar]->[Right Slider]`: Slide right to zoom in, left to zoom out. Multiplier is shown on the left of the slider.
			

### Controlling the Map and Shortcuts
			
To control the map, use a combination of keyboard and mouse. Generally, it require you to press a key and select where to apply it on the map. There are three type of function:

1. _Click_: press a key and click where to apply it.
2. _Select_: press a key and highlight the area to apply it.
2. _Drag_: press a key and drag it around the map.
			
These are the current implemented functions:
		
* `'q' [Select]`: fill the select area with out of bound grids.
* `'w' [Select]`: fill the select area with water grids.
* `'e' [Select]`: fill the select area with ground grids.
* `'r' [Select]`: fill the select area with swamp grids.
* `'t' [Select]`: fill the select area with tree grids.
* `'s' [Click]`: place the agent at the clicked location.
* `'d' [Click]`: place the goal at the clicked location.
* `'z' [Click]`: zoom in at the given point.
* `'x' [Click]`: zoom out at the given point.
* `'c' [Drag]`: move the map around.
* `'v' [Select]`: select the area to zoom into.