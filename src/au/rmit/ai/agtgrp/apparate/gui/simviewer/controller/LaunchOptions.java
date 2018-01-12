package au.rmit.ai.agtgrp.apparate.gui.simviewer.controller;

import java.io.File;
import java.net.URL;
import java.util.HashMap;

import au.rmit.ai.agtgrp.apparate.gui.interfaces.PlanningAgent;
import au.rmit.ai.agtgrp.apparate.gui.simviewer.model.GridView;


/**
 * Holds all the setting to inital a map
 * 
 * @author Andy Heng Xie
 *
 */
public class LaunchOptions implements Cloneable {

	/* *******************
	 * Enum
	 *********************/
	/**
	 * Types of timers
	 * 
	 * @author Andy Heng Xie
	 *
	 */
	public enum TimerType {
		TIMER_PULSER, // poll the grid environment at a periodical time for a move
		TIMER_CONTINUOUS; // run each step of the environment one after another
	}
	
	public boolean hasdogc = false; // -gc
	public boolean dogc = false;
	
	public boolean hasview = false; // -v <txt>
	public GridView view = null;
	
	public boolean hastimelimit = false; // -t <int>
	public int timelimit = -1;

	public boolean hasmintimestep = false; // -t <int>
	public int mintimestep = 0;

	public boolean hassteptimelimit = false; // -t <int>
	public int steptimelimit = -1;

	public boolean hassteplimit = false; // -s <int>
	public int steplimit = -1;
	
	public boolean hasstart = false; // -f <int> <int>
	public int startx = 0, starty = 0;
	
	public boolean hasgoal = false; // -g <int> <int>
	public int goalx = 0, goaly = 0;
	
	public URL mapfile = null, scriptfile = null; // -o <txt>, -e <txt>
	
	public boolean hasquiet = false; // -q
	public boolean quiet = false;
	
	//public IPlannerSpawner planner = null; // -p <txt>
	public PlanningAgent planner = null; // -p <txt> // modified interface binding
	public HashMap<Integer, PlanningAgent> agents = new HashMap<Integer, PlanningAgent>(); // store all the agents
	
	public boolean haskillonlimit = false; // -k
	public boolean killonlimit = false;
	
	public boolean hastimertype = false; // -w <txt>
	public TimerType timertype = null;
	
	public boolean hasintermission = false; // -i <int>
	public int intermission = 0;
	
	public boolean hasexternalspawner = false; // -j <txt> <txt>
	public File jarfile = null;
	public String classpath = null;
	public String agentName=null;
	
	/* *******************
	 * Constructor
	 *********************/
	
	public LaunchOptions() { }
	
	/* *******************
	 * Accessors
	 *********************/
	
	public boolean hasStartPosition() {
		return false;
	}

	public boolean hasGoalPosition() {
		return false;
	}

	public boolean hasMap() {
		return false;
	}

	public boolean hasScript() {
		return false;
	}
	
	public boolean hasQuiet() {
		return false;
	}
	
	public boolean hasKillOnLimit() {
		return false;
	}

	public boolean hasSpawner() {
		return false;
	}

	public boolean hasExternalSpawner() {
		return false;
	}
	
	public boolean hasGridView() {
		return false;
	}
	
	public boolean hasTimer() {
		return false;
	}

	public boolean hasTimeLimit() {
		return false;
	}
	
	public boolean hasStepLimit() {
		return false;
	}

	public boolean hasIntermission() {
		return false;
	}
	
	/* *******************
	 * Mutators
	 *********************/

	public void setCurrentAgent(int id){
		planner = agents.get(id);
	}
	
	public boolean setStartPosition(int x, int y) {
		return false;
	}

	public boolean setGoalPosition(int x, int y) {
		return false;
	}

	public boolean setMap(String file) {
		return false;
	}

	public boolean setScript(String file) {
		return false;
	}
	
	public boolean setQuiet(boolean quiet) {
		return false;
	}
	
	public boolean setKillOnLimit(boolean dokill) {
		return false;
	}

	public boolean setSpawner(String spawner) {
		return false;
	}

	public boolean setExternalSpawner(String file, String classpath) {
		return false;
	}
	
	public boolean setGridView(String view) {
		return false;
	}
	
	public boolean setTimer(String view) {
		return false;
	}

	public boolean setTimeLimit(int timelimit) {
		return false;
	}
	
	public boolean setStepLimit(int steplimit) {
		return false;
	}

	public boolean setIntermission(int interlength) {
		return false;
	}
	
	/* *******************
	 * Override
	 *********************/
	
	public Object clone() {
		LaunchOptions dup = new LaunchOptions();
		dup.hasview = this.hasview;
		dup.view = this.view;
		dup.hastimelimit = this.hastimelimit;
		dup.timelimit = this.timelimit;
		dup.hasmintimestep = this.hasmintimestep;
		dup.mintimestep = this.mintimestep;
		dup.hassteplimit = this.hassteplimit;
		dup.steplimit = this.steplimit;
		dup.hasstart = this.hasstart;
		dup.startx = this.startx;
		dup.starty = this.starty;
		dup.hasgoal = this.hasgoal;
		dup.goalx = this.goalx;
		dup.goaly = this.goaly;
		dup.mapfile = this.mapfile;
		dup.scriptfile = this.scriptfile;
		dup.hasquiet = this.hasquiet;
		dup.quiet = this.quiet;
		dup.planner = this.planner;
		dup.haskillonlimit = this.haskillonlimit;
		dup.killonlimit = this.killonlimit;
		dup.hastimertype = this.hastimertype;
		dup.timertype = this.timertype;
		dup.hasintermission = this.hasintermission;
		dup.intermission = this.intermission;
		return dup;
	}

}
