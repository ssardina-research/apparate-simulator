#  APPARATE Path Planner Simulator and Visualization System

This is a GUI simulator for running path-planning systems over [Moving-AI+](http://movingai.com/benchmarks/) maps. 

It contains three packages:

1. **jpathplan**: set of generic classes to define path planners.
2. **gui**: all the classes to implement the simulation and visualization.
3. **agents**: agents that can do actions in the map, by outputing one action per simulation cycle. Agents can use path-planners.

Two related projects (and repos) are:

1. [Map Creator](https://bitbucket.org/ssardina-research/apparate-mapcreator): To be able to create text map files easier (e.g., from an image file done in GIMP).
2. [Extra Agents and Path Planners](https://bitbucket.org/ssardina-research/apparate-agents-extras): Additional, more complex, agents, many of them using powerful path-planners. 
	* **NOTE:** This repo is private and requires premission.

For bugs, issues, and extensions [please open an issue](https://bitbucket.org/ssardina-research/apparate-simulator/issues?status=new&status=open).


**Have fun developing your agents!!!**


----------
[TOC]


## Prerequisites


* Java Runtime Environment (JRE) and Java Compiler (javac) v1.6+ (Sun version recommended)
* `libHRTimer.so` to have access to C access to the clock.
* A configuration file (see below)
* Maps in the [Moving-AI+](http://movingai.com/benchmarks/) format + extension with cost (see below).
* Dynamic map scripts (if needed).


## Compiling and Running APPARATE 

We show here how to compile the framework from CLI (Command Line Interface). 

To compile the simulator and GUI system (package `ppplanning`) and place all `.class` files under `bin/`, run:

```
javac -d bin/ -cp src/ \    
    src/au/rmit/ai/agtgrp/apparate/gui/simviewer/controller/Launcher.java
```

Then compile all agents available in package `agents`:

```
javac -d bin/ -cp src/  \
    `find src/au/rmit/ai/agtgrp/apparate/agents/ -name *.java | xargs`
```


To **run** the system from CLI:

```
java -cp bin/: \
    au.rmit.ai.agtgrp.apparate.gui.simviewer.controller.Launcher <config-file>
```

where:

* `au.rmit.ai.agtgrp.apparate.gui.simviewer.controller.Launcher` is the main class that starts the application.
* `<config-file>` contains all the options for the simulation (an example `config.properties` is included). See below for details.

You can also use the script `./run.sh <conf-file>`

	
## Configuration File

This is the file configuring how the application should run. It is mostly self-explanatory:


```
map=maps/blastedlands.map           % the map file, else it would use a blank map
grid=euclidean                      % either manhatten or euclidean
totaltime=10000                     % total time allowed in milliseconds (0 =  no limit)
pulsertime=200                      % pause time between pulses (only if not quiet)
mintimestep=1                       % step of less than 1ms are not counted towards time
steps=200                           % how many total steps allowed (0 = no limit)
steptime=400                        % time allowed in each step (default: no limit)
startpos=200,110                    % start position (missing or =random --> random pick)
destpos=300,270                     % goal position (missing or =random --> random pick)
quiet=false                         % no GUI, print out stats (arrived?, #steps, #distance, #time)
killonlimit=true                    % kill the agent if limit time exceeded?
managegc=falsE                      % Performs garbage collection before each step
#script=maps/testscript.mapscript   % the file with the scripted events

# Location of the agent JAR or binary
agentname=MyGoodAgent
agentloc=./

### Which agents to make available?
agentclass=au.rmit.agtgrp.apparate.agents.MyRandomAgent

```
		
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

## Map Extensions

### Extension to Handle Cost

The system uses maps in the form of the [Moving-AI gridworld domains](http://movingai.com/benchmarks/)

The format of those files can be found here: 

http://movingai.com/benchmarks/formats.html

The standard maps do not include cost of cells and it relies on different type of navigations (by foot, sailing, flying, etc).  In contrast, **APPARATE** is based soley on cost. So, to accommodate that, one can modify the first part of the map to include costs for each cell type.

The cost specification has to come before `map` keyword and respect the following order:

```
ground <cost>
tree <cost>
swamp <cost>
water <cost>
```

where `<cost>` is a `float` or `+inf` (for infinite cost). Infinite cost cells are then assumed to be non-traversable. For example:

```
type octile
height 512
width 512
ground 1
tree +inf
swamp 10
water 20
map
```

Observe the order is important: ground, tree, swamp, water just before the `map` keyword. If an entity is skipped, a default cost is applied.


### Map Scripting for Dynamic Changes

A map scripting file can be given to specify changes in the domain dynamically at run-time. This is useful to test and see how different incremental path planners deal with changes in the environment.

* File name needs to end with `.mapscript` extension.	
* File contains commands, one per line.
* Each command is of the form **`Triggering+ Action`**
    * Thus, a Command is executed only if at least one trigger is valid during the step.
    * Use '#' at the start of the command to mark it as a comment.
* Triggering could be of two types:
    * **Step Triggering** include:
        * `STEP <START> [<LENGTH>:1]`: which steps to execute the action, LENGTH of 0 indicate it is valid for infinite.
        * `REPEAT <INACTIVELEN> [<ACTIVELEN>:1] [<OFFSET>:0] [<START>:0] [<LENGTH>:0]`: repeating steps to execute the action, `LENGTH` of 0 indicate it is valid for infinite. The repeat begins by waiting over the `INACTIVELEN` before executing the number of steps for `ACTIVELEN`.
    * **Conditon Triggering** include:
        * `STARTENTER <X> <Y> [<WIDTH>:1 <HEIGHT>:1]`: valid when the start enters the area specified
        * `STARTLEAVE <X> <Y> [<WIDTH>:1 <HEIGHT>:1]`: valid when the start leaves the area specified
        * `STARTON <X> <Y> [<WIDTH>:1 <HEIGHT>:1]`: valid when the start is within the area specified
        * `STARTOFF <X> <Y> [<WIDTH>:1 <HEIGHT>:1]`: valid when the start is not within the area specified
        * `GOALENTER <X> <Y> [<WIDTH>:1 <HEIGHT>:1]`: valid when the goal enters the area specified
        * `GOALLEAVE <X> <Y> [<WIDTH>:1 <HEIGHT>:1]`: valid when the goal leaves the area specified
        * `GOALON <X> <Y> [<WIDTH>:1 <HEIGHT>:1]`: valid when the goal is within the area specified
        * `GOALOFF <X> <Y> [<WIDTH>:1 <HEIGHT>:1]`: valid when the goal is not within the area specified
* The possible actions (to be done when the triggering applies) are:
    * `FILLOUTOFBOUND <X> <Y> [<WIDTH>:1 <HEIGHT>:1]`: fill the given area with "out of bound" terrain
    * `SETMAXTIME <N>`: set max limit time to `N` milliseconds
    * `FILLWATER <X> <Y> [<WIDTH>:1 <HEIGHT>:1]`: fill the given area with "water" terrain
    * `FILLGROUND <X> <Y> [<WIDTH>:1 <HEIGHT>:1]`: fill the given area with "ground" terrain
    * `FILLSWAMP <X> <Y> [<WIDTH>:1 <HEIGHT>:1]` : fill the given area with "swamp" terrain
    * `FILLTREE <X> <Y> [<WIDTH>:1 <HEIGHT>:1]`: fill the given area with "tree" terrain
    * `PUTSTART <X> <Y>`: place the start at the new given location
    * `PUTGOAL <X> <Y>`: place the goal at the new given location
    * `PUSHSTART <OFFSETX> <OFFSETY>`: move the start by the given offset amount
    * `PUSHGOAL <OFFSETX> <OFFSETY>`: move the goal by the given offset amount

Some examples:

* On the seventh step, fill from <10, 10> to <100, 100> with trees: `STEP 7 FILLTREE 10 10 91 91`
* When the start is within 5 step of the goal at <10, 10>, move the goal to <100, 100>: `STARTENTER 5 5 11 11 PUTGOAL 100 100`
* After the 100 step, move the goal to the right at the rate of 1 move per 7 steps: `REPEAT 6 1 -6 100 0 PUSHGOAL 1 0`
* Every 4 moves, move the start up one step and move the goal left one step: `REPEAT 3 1 PUSHSTART 0 1 PUSHGOAL -1 0`
* When the start is within 4 step of the goal at <10, 10> within 100 steps, move the goal to <20, 20>, else move it to <30, 30>: 

    ```
    STEP 1 100 STARTENTER 6 6 9 9 PUTGOAL 20 20
    STEP 100 0 STARTENTER 6 6 9 9 PUTGOAL 30 30 
    ```
		
## Bugs

1. GUI occassionally locks up if the planner work is too intense (e.g., the map is modified while the agent is moving)
2. It is recommended to stop the execution (F5), do all changes to the map, and then resume execution (F5)
	

## Changelog

- January 2017 - release v4.0
    - Full refactoring and refreshing.
- November 2012 - release v3.5
	- Improvements on dynamic events, more efficient handling.
- October 2012 - release v3.1
	- Field & button to change the time left in the GUI
	- Fixed heuristics to account for the minimum cost rather than just 1
	- Added getMinCost() in GridDomain class to get min cell cost
	- New option mintimestep (integer): step below that ms time is not meassured
	- Clean-up way that command line options are accesses in various parts
- September 2012 - release v2.8
	- Improved reading costs in map files
- August 2012 - release v2.7 
	- Added random start/goal
	- Can now read costs of cells (trees, water, terrain) from map files
	- Fixed bug with getNextStep() in ComputedPlan
- August 2012 - release v2.6 (SS)
	- Add more info in the status bar (cost, etc)
	- Fixed calculation of time remaining
	- Improved way of calculating how much time agent took in each cycle
	- Cleaned up call to path planners
	- Cleaned up and deleted many classes
- August 2012 - release v2.5 (Nitin Yadav & SS)
- January 2012 - release v2 (Andy Xie & SS)


## Contributors

* Sebastian Sardina (contact - ssardina@gmail.com)
* Andy Heng Xie
* Nitin Yadav


## License

This project is using the GPLv3 for open source licensing for information and the license visit GNU website (https://www.gnu.org/licenses/gpl-3.0.en.html).

This program is free software: you can redistribute it and/or modify it under the terms of the GNU General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU General Public License for more details.

You should have received a copy of the GNU General Public License along with this program.  If not, see <http://www.gnu.org/licenses/>.
