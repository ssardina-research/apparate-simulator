## Changelog

- January 2017 - release v4.0
    - Full refactoring and refreshing of project.
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