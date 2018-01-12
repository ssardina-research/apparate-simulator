package au.rmit.ai.agtgrp.apparate.gui.simviewer.model;

import au.rmit.ai.agtgrp.apparate.gui.simviewer.model.script.ScriptEvents;
import au.rmit.ai.agtgrp.apparate.gui.simviewer.model.script.StopOnArrival;
import au.rmit.ai.agtgrp.apparate.gui.simviewer.model.timer.AEnvironTimer;

/**
 * Holds all the necessary components for a running environment
 * 
 * @author Andy Heng Xie
 *
 */
public class RunEnviron {
	private GridEnviron grid; // the grid environment
	private ScriptEvents script; // the script events
	private AEnvironTimer timer; // the timer for the grid environment
	private StopOnArrival soa; // the stop on arrival event
	

	/* *******************
	 * Constructor
	 *********************/
	
	public RunEnviron(GridEnviron grid, ScriptEvents script, AEnvironTimer timer, StopOnArrival soa) {
		this.grid = grid;
		this.script = script;
		this.timer = timer;
		this.soa = soa;
	}
	
	public RunEnviron() {
		this(null, null, null, null);
	}
	

	/* *******************
	 * Accessors
	 *********************/
	
	public GridEnviron getGridEnviron() {
		return grid;
	}
	
	public ScriptEvents getScriptEvents() {
		return script;
	}
	
	public AEnvironTimer getTimer() {
		return timer;
	}
	
	public StopOnArrival getStopOnArrival() {
		return soa;
	}


	/* *******************
	 * Mutators
	 *********************/
	
	public void setGridEnviron(GridEnviron grid) {
		this.grid = grid;
	}
	
	public void setScriptEvents(ScriptEvents script) {
		this.script = script;
	}
	
	public void setTimer(AEnvironTimer timer) {
		this.timer = timer;
	}
	
	public void setStopOnArrival(StopOnArrival soa) {
		this.soa = soa;
	}

	public long getTimeLeft() {
		return timer.getTimeLeft();	
	}

	public void setMaxTime(int maxtime) {
		
		timer.resetTimeMax(maxtime);
		
	}
}
