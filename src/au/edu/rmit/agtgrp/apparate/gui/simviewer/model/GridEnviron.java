package au.edu.rmit.agtgrp.apparate.gui.simviewer.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import au.edu.rmit.agtgrp.apparate.gui.simviewer.controller.Launcher;
import au.edu.rmit.agtgrp.apparate.gui.interfaces.PlanningAgent;
import au.edu.rmit.agtgrp.apparate.gui.simviewer.model.listener.IGridEnvironUpdateListener;
import au.edu.rmit.agtgrp.apparate.jpathplan.entites.ComputedPlan;
import au.edu.rmit.agtgrp.apparate.jpathplan.entites.Plan;

import au.edu.rmit.agtgrp.apparate.gui.interfaces.PlanningAgent;

import java.lang.management.ManagementFactory;
import java.lang.management.ThreadMXBean;

/**
 * Encapsulate all data relavent to the execution of an agent on a grid map
 * Contains the map, the planner, start position and goal position and all data collected from the planner
 * Also fires event on certain condition with the IGridEnvironUpdateListener
 * 
 * This serves as the main entry point for the model
 * @author Andy Xie
 *
 */
public class GridEnviron {
	// Used to meassure time of each step
	final ThreadMXBean threadMX = ManagementFactory.getThreadMXBean();
	final private double 	MS_TO_NS_CONV_FACT = 1E6;
	
	private final Object steplock = new Object();
	private final Object writelock = new Object();
	private GridDomain grid; // the map
	private PlanningAgent plannerspawner; // which agent to spawn
	private GridView gridview; // how the grid is perceived, manhatten or euclidean

	private GridCoord startpos, goalpos; // the start and goal position
	private GridCoord laststartpos, lastgoalpos; // the start and goal position before the current step
	
	private int stepsPerformed = 0; // number of steps performed

	private Map<Integer, Long> runtime = new HashMap<Integer, Long>(); // the amount of time in nanosecond of each run
	private long runtimeSoFar = 0; // total time taken so far

	private Map<Integer, Double> runcost = new HashMap<Integer, Double>(); // the cost of movement of agent. only consider internal movement and not external
	private double runcostSoFar = 0; // total cost so far

	
	private Map<Integer, GridCoord> runlocation = new HashMap<Integer, GridCoord>(); // all the location it internally moved t

	private List<IGridEnvironUpdateListener> updateevent = new ArrayList<IGridEnvironUpdateListener>(); // all the events
	private List<GridCoord> starttravelledpath = new ArrayList<GridCoord>(); // list of coordinate the start position have traversed
	private List<GridCoord> goaltravelledpath = new ArrayList<GridCoord>(); // list of coordinate the goal position have traversed
	//private Path lastpath = null; // the path generated after a run
	private GridCell nextMove;
	private boolean dogarbagecollection = false; // should it perform garbage collection to get optimal time

	
	// C Timer called via JNI Interface.
	//HRTimer timer = new HRTimer();
	
	/* *******************
	 * Constructor
	 *********************/
	
	/**
	 * Creates an environment with an empty map
	 * Default uses the manhatten astar
	 * 
	 * @param width the width of the grid
	 * @param height the height of the grid
	 */
	public GridEnviron(int width, int height) {
		grid = new GridDomain(width, height, GridCellType.getDefault());
		grid.setParent(this);
		stepsPerformed = 0;
	}
	
	/**
	 * Creates an environment with a preloaded map
	 * Default uses the manhatten astar
	 * 
	 * @param newmap the map of data
	 */
	public GridEnviron(MapData newmap) {
		grid = new GridDomain(newmap);
		grid.setParent(this);
		stepsPerformed = 0;
	}

	/* *******************
	 * Accessor
	 *********************/
	
	/**
	 * get how the grid is perceived
	 * @return
	 */
	public GridView getGridView() {
		return this.gridview;
	}
	
	/**
	 * gets a list of coordinate that the agent has traversed
	 * @return
	 */
	public List<GridCoord> getStartPath() {
		return Collections.unmodifiableList(starttravelledpath);
	}
	
	/**
	 * gets a list of coordinate that the goal has traversed
	 * @return
	 */
	public List<GridCoord> getGoalPath() {
		return Collections.unmodifiableList(goaltravelledpath);
	}

	/**
	 * returns the grid cell at the given location
	 * @param x
	 * @param y
	 * @return null if position given is out of bound
	 */
	public GridCell getCellAt(int x, int y) {
		return grid.getCell(x, y);
	}
	
	/**
	 * get the current agent running
	 * @return
	 */
	public PlanningAgent getPlannerSpawner() {
		return plannerspawner;
	}
	
	/**
	 * gets the start position
	 * @return
	 */
	public GridCoord getStartPosition() {
		return startpos;
	}
	
	/**
	 * gets the goal's positiong
	 * @return
	 */
	public GridCoord getGoalPosition() {
		return goalpos;
	}

	/**
	 * gets a random position that is not blocked
	 * @return
	 */
	public GridCoord getRandomNonBlockedPosition() {
		int x,y;
		do {
			x = new Random().nextInt(getWidth()-1)+1;
			y = new Random().nextInt(getHeight()-1)+1;
		} while (getCellAt(x, y).isBlocked());
		
		
		return new GridCoord(x, y);
	}
	
	
	/**
	 * get the position of the start position in the previous step
	 * return null if no step has been run yet
	 * @return
	 */
	public GridCoord getLastStartPosition() {
		return laststartpos;
	}
	
	/**
	 * get the position of the goal position in the previous step
	 * return null if no step has been run yet
	 * @return
	 */
	public GridCoord getLastGoalPosition() {
		return lastgoalpos;
	}
	
	/**
	 * get the planned path of last run
	 * @return null if no run has be given
	 */
	public Plan getPath() {
		//return this.lastpath;
		if(nextMove==null){return null;}
	    ComputedPlan	lastPath = new ComputedPlan();
	    
		lastPath.appendStep(nextMove);
		return lastPath;
	}
	
	/**
	 * gets a list of nodes expended/open after the last run
	 * @return 
	 */
	public ArrayList<GridCell> getOpenedNodes() {
		if (this.plannerspawner == null) return null;
		return new ArrayList<GridCell>(this.plannerspawner.expandedNodes());
	}
	
	/**
	 * gets a list of all nodes closed/unexpended in the last run
	 * @return
	 */
	public ArrayList<GridCell> getClosedNodes() {
		if (this.plannerspawner == null) return null;
		return new ArrayList<GridCell>(this.plannerspawner.unexpandedNodes());
	}
	
	/**
	 * get width of the map
	 * @return
	 */
	public int getWidth() {
		return grid.getWidth();
	}
	
	/**
	 * get height of the map
	 * @return
	 */
	public int getHeight() {
		return grid.getHeight();
	}
	
	/**
	 * gets number of runs this environment has performed
	 * @return
	 */
	public int getStep() {
		return stepsPerformed;
	}
	
	/**
	 * returns the run time of the previous step in nano seconds
	 * @return
	 */
	public long getStepTime() {
		return runtime.get(stepsPerformed);
	}

	/**
	 * returns the time used so far
	 * @return
	 */
	public long getTimeSoFar() {
		return runtimeSoFar;
	}
	
	/**
	 * returns the time used so far
	 * @return
	 */
	public double getCostSoFar() {
		return runcostSoFar;
	}
	
	/**
	 * returns the run time of the step in nano seconds
	 * @return
	 */
	public long getStepTime(int step) {
		return runtime.get(step);
	}
	
	/**
	 * returns a list of the run time of each step
	 * @return
	 */
	public Map<Integer, Long> getRunTime() {
		return new HashMap<Integer, Long>(runtime);
	}
	
	/**
	 * returns a list of all step's movement cost
	 * @return
	 */
	public Map<Integer, Double> getRunCost() {
		return new HashMap<Integer, Double>(runcost);
	}
	
	/**
	 * returns a list of all coordinate which itself moved to
	 */
	public Map<Integer, GridCoord> getRunMove() {
		return new HashMap<Integer, GridCoord>(runlocation);
	}
	
	/**
	 * returns a list of all movement of the start point
	 * @return
	 */
	public List<GridCoord> getAllStartMove() {
		return new ArrayList<GridCoord>(starttravelledpath);
	}

	/**
	 * returns a list of all movemment of the goal point
	 * @return
	 */
	public List<GridCoord> getAllGoalMove() {
		return new ArrayList<GridCoord>(goaltravelledpath);
	}
	
	/**
	 * Check if it would garbage collect before each run
	 * @return
	 */
	public boolean doGarbageCollection() {
		return this.dogarbagecollection;
	}
	
	/* *******************
	 * Mutator
	 *********************/

	public void setDoGarbageCollection(boolean enabled) {
		this.dogarbagecollection = enabled;
	}
	
	/**
	 * set the new spawner, clears the old planner and replace it with the new planner
	 * 
	 * @param spawner
	 */
	public void setPlannerSpawner(PlanningAgent spawner) {
		if (spawner == null) return;
		PlanningAgent oldspawner;
		synchronized (writelock) {
			oldspawner = plannerspawner;
			plannerspawner = spawner;
			//currentplanner = spawner;
			//currentplanner = spawner.createPathPlannerInstance();
		}
		this.CallspawnerChanged(this, oldspawner, spawner);
	}
	
	/**
	 * Set which grid type to be
	 * Will reset the planner
	 * @param newview
	 */
	public void setGridView(GridView newview) {
		if (newview == null) return;
		GridView oldview;
		synchronized (writelock) {
			oldview = gridview;
			gridview = newview;
			grid.setCellEvaluator(gridview.getCellEvaluator());
			grid.setCostCalculator(gridview.getCostCalculator());
			grid.setHeuristicCalculator(gridview.getHeuristicCalculator());
			//if (plannerspawner != null) currentplanner = plannerspawner.createPathPlannerInstance();
		}
		this.CallgridviewChanged(this, oldview, newview);
		
	}
	
	/**
	 * adds a grid environment listener to this environment
	 * 
	 * @param listener
	 */
	public void addGridEnvironUpdateListener(IGridEnvironUpdateListener listener) {
		synchronized (writelock) {
			updateevent.add(listener);
		}
	}
	
	/**
	 * removes an existing listener
	 * 
	 * @param listener
	 */
	public void removeGridEnvironUpdateListener(IGridEnvironUpdateListener listener) {
		synchronized (writelock) {
			updateevent.remove(listener);
		}
	}
	
	/**
	 * Alters the start position
	 * Does not change if placement is out of bound
	 * 
	 * @param newstartpos
	 */
	public void setStartPosition(GridCoord newstartpos) {
		GridCoord oldstartpos = null;
		synchronized (writelock) {
			oldstartpos = startpos;
			startpos = newstartpos;
			if (newstartpos != null) if (newstartpos.equals(oldstartpos)) starttravelledpath.add(startpos);
		}
		this.CallstartMoved(this, oldstartpos, startpos);
		if (startpos != null) if (startpos.equals(goalpos)) this.CallarrivedAtGoal(this);
	}
	
	/**
	 * Alters the goal position
	 * Does not change if placement is out of bound
	 * 
	 * @param newgoalpos
	 */
	public void setGoalPosition(GridCoord newgoalpos) {
		GridCoord oldgoalpos = null;
		synchronized (writelock) {
			oldgoalpos = goalpos;
			goalpos = newgoalpos;
			if (newgoalpos != null) if (newgoalpos.equals(oldgoalpos)) goaltravelledpath.add(startpos);
			
		}
		this.CallgoalMoved(this, oldgoalpos, goalpos);
		if (startpos != null) if (startpos.equals(goalpos)) this.CallarrivedAtGoal(this);
	}
	
	private long getElapsed() {
		// Calculate the end time of  step execution
//		return  timer.getCurrentNanotime();
		
		if (threadMX.isCurrentThreadCpuTimeSupported())
			return threadMX.getCurrentThreadCpuTime();
			//return threadMX.getCurrentThreadUserTime();
		else
			return System.nanoTime(); // well, that's the best we can do....
	}
	
	/**
	 * run the path finding
	 * @param applymove set true to move the agent one step towards the goal after the execution
	 */
	public void applyStep(boolean applymove) {
		RuntimeException hadexcepted = null;
		long startTime, endTime = 0; // used to messure the time taken on the step (both nanoseconds)
		
		
		synchronized (steplock) {
			//stepsPerformed++;	// Increment step counter
			
			// Backup start and goal positions
			laststartpos = startpos;
			lastgoalpos = goalpos;
			
			// Do all the changes BEFORE the step is computed and taken
			// NOT DONE ANY MORE HERE - DONE AT Pulser Timer level (to change time remaining before thread kill)
			//this.CallenvironmentBeforeExecution(this);

			synchronized (writelock) {
				GridCell startcell = grid.getCell(startpos), goalcell = grid.getCell(goalpos);
			
				int stepsLeft = Launcher.getInitLauchOptions().steplimit-stepsPerformed;
				int stepTimeLimit = Launcher.getInitLauchOptions().steptimelimit; 
				long timeLeft = Launcher.getRunEnviron().getTimeLeft(); 

				// Calculate the start time for step execution
				startTime = getElapsed();
				
				
				try {
					// ****************** MAIN CALL: GET THE NEXT MOVE FROM THE AGENT!! :-)) **************************
					nextMove = plannerspawner.getNextMove(grid, startcell, goalcell, stepsLeft, stepTimeLimit, timeLeft);

					// Calculate the end time of  step execution
					endTime = getElapsed();

	
					if (applymove) applyMove();	// Apply the move is required
				} catch (RuntimeException e) {
					hadexcepted = e;

					// Calculate end time after step execution
					endTime = getElapsed();

					nextMove = null; // No path returned, error
				} finally {

					stepsPerformed++;	// Increment step counter

					// Store time that took for this step <step number, time taken> and total time so far
					long stepTime = endTime - startTime;
					// If the step takes less than the min time to meassure, then do not count it
					if (stepTime <= Launcher.getInitLauchOptions().mintimestep * MS_TO_NS_CONV_FACT) {
						stepTime = 0;
					}
					runtimeSoFar += stepTime;
					runtime.put(stepsPerformed, stepTime);

					
					grid.applyChanges();
				}
			}

			// Do all the changes BEFORE the step is computed and taken
			// NOT DONE ANY MORE HERE - DONE AT Pulser Timer level (to change time remaining before thread kill)
			//this.CallenvironmentAfterExecution(this);
		}
		if (hadexcepted != null) throw hadexcepted;
	}
	
	/**
	 * Apply the last computed proposed step (stored in nextMove)
	 */
	public void applyMove() {
		synchronized (writelock) {
			boolean hasnewstartpos = false;
			GridCell proposedstep = null;
			GridCell curstartnode = null;
			
			if (nextMove != null) {
				proposedstep = nextMove;
				curstartnode =  grid.getCell(startpos);
				if (!grid.isBlocked(proposedstep) && grid.getSuccessors(curstartnode).contains(proposedstep)) { 
					
					// move start position to the next proposed move
					this.runlocation.put(stepsPerformed, proposedstep.getCoord());
					this.runcost.put(stepsPerformed, (double) grid.cost(curstartnode, proposedstep));
					runcostSoFar += (double) grid.cost(curstartnode, proposedstep);
					this.setStartPosition(proposedstep.getCoord());

					// Check if we have reached destination!
					if (startpos != null && startpos.equals(goalpos)) 
						this.CallarrivedAtGoal(this); // WE HAVE ARRIVED TO DESTINATION!!
				}
			}
		}
	}

	/**
	 * singal from the grid domain to indicate that one of its cell has changed
	 * @param cell
	 */
	protected void cellAfterUpdate(GridCell cell) {
		this.CallcellUpdated(this, cell);
	}
	

	/* *******************
	 * Utility
	 *********************/
	
	protected void CallstartMoved(GridEnviron environ, GridCoord oldpos, GridCoord newpos) {
		for (IGridEnvironUpdateListener listener: updateevent) try {
			listener.startMoved(environ, oldpos, newpos);
		} catch (Exception e) { }
	}

	protected void CallgoalMoved(GridEnviron environ, GridCoord oldpos, GridCoord newpos) {
		for (IGridEnvironUpdateListener listener: updateevent) try {
			listener.goalMoved(environ, oldpos, newpos);
		} catch (Exception e) { }
	}

	protected void CallcellUpdated(GridEnviron environ, GridCell newcell) {
		for (IGridEnvironUpdateListener listener: updateevent) try {
			listener.cellUpdated(environ, newcell);
		} catch (Exception e) { }
	}

	public void CallenvironmentBeforeExecution(GridEnviron environ) {
		for (IGridEnvironUpdateListener listener: updateevent) try {
			listener.environmentBeforeExecution(environ);
		} catch (Exception e) { }
	}

	public void CallenvironmentAfterExecution(GridEnviron environ) {
		for (IGridEnvironUpdateListener listener: updateevent) try {
			listener.environmentAfterExecution(environ);
		} catch (Exception e) { }
	}

	protected void CallarrivedAtGoal(GridEnviron environ) {
		for (IGridEnvironUpdateListener listener: updateevent) try {
			listener.arrivedAtGoal(environ);
		} catch (Exception e) { }
	}

	protected void CallspawnerChanged(GridEnviron environ, PlanningAgent oldspawner, PlanningAgent newspawner) {
		for (IGridEnvironUpdateListener listener: updateevent) try {
			listener.spawnerChanged(environ, oldspawner, newspawner);
		} catch (Exception e) { }
	}

	protected void CallgridviewChanged(GridEnviron environ, GridView oldview, GridView newview) {
		for (IGridEnvironUpdateListener listener: updateevent) try {
			listener.gridviewChanged(environ, oldview, newview);
		} catch (Exception e) { }
	}
	
}
