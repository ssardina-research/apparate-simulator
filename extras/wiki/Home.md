#  APPARATE Path Planner Simulator and Visualization System

## Overview 

APPARATE is an application package to code, simulate, and visualise various path planning algorithms, such as A*, Real-time A* or incremental path planners like D* or Moving Target. 
To do so, the framework provides an interface to implement path finding algorithms and to run them, get statistics, and visualize them. The simulator uses [Moving-AI+](http://movingai.com/benchmarks/) maps.

APPARATE is a suitable tool for testing incremental algorithms; it allows dynamic changes to the environment (like adding water areas or moving the target location), via a script or the GUI interface. Also, APPARATE is able to change the path-planning algorithms on-the-fly and can handle large maps.

APPARATE can be used for teaching purposes for students to code advanced search and execution algorithms and develop their own ones. See [AOPD-2012](https://sites.google.com/site/aopd12/assignments/assignment-3-contest) for an example.

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

## Sections

1. [Compiling and Running APPARATE](Compiling and Running APPARATE): for instructions how to build and run agents in the framework.
2. [Information on Maps](Info on Maps): includes map extensions and dynamic scripts.
3. [Some Search Algorithms](Some path-planning algorithms): for some pointers to interesting search and path planning algorithms and techniques.
3. [Changelog](Changelog): on major releases.
 

## Contributors

* Sebastian Sardina (project supervisor, developer, and contact - ssardina@gmail.com).
* Abhijeet Anand (developed JPathPlan library as part of his 2011 Minor Thesis, which provides the generic framework to develop path planners).
* Andy Heng Xie (developed first GUI version as part of a summer project).

We thank [Prof. Jorge Baier](http://jabaier.sitios.ing.uc.cl/index.php?page=main) from Universidad Catolica of Chile for providing his original path-planning simulator in which we built on our system.


## License

This project is using the GPLv3 for open source licensing for information and the license visit GNU website (https://www.gnu.org/licenses/gpl-3.0.en.html).

This program is free software: you can redistribute it and/or modify it under the terms of the GNU General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU General Public License for more details.

You should have received a copy of the GNU General Public License along with this program.  If not, see <http://www.gnu.org/licenses/>.