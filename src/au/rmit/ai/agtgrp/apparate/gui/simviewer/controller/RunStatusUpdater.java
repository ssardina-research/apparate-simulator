package au.rmit.ai.agtgrp.apparate.gui.simviewer.controller;

import au.rmit.ai.agtgrp.apparate.gui.simviewer.model.listener.ITimerListener;
import au.rmit.ai.agtgrp.apparate.gui.simviewer.model.timer.AEnvironTimer;
import au.rmit.ai.agtgrp.apparate.gui.simviewer.model.timer.GridEnvironResult;
import au.rmit.ai.agtgrp.apparate.gui.simviewer.model.timer.PulserTimer;
import au.rmit.ai.agtgrp.apparate.gui.simviewer.view.MapInstance;

/**
 * Updates the running status on the status bar
 * 
 * @author Andy Heng Xie
 *
 */
public class RunStatusUpdater implements ITimerListener {

	private MapInstance map = null; // the map to update to
	private int curstep = 0; // the current step to show
	private int maxSteps = 0; // maximum number of steps that can be taken
	private int maxTime = 0; // maximum time taken (in milliseconds)
	private long timeSoFar = 0; // time taken so far 
	private double costSoFar = 0; // time taken so far 
	private String currunstate = "Stopped"; // the running status to show
	private int pulses = -1;
	

	/* *******************
	 * Constructor
	 *********************/
	
	/**
	 * 
	 * @param map which map to update to
	 */
	public RunStatusUpdater(MapInstance map) {
		this.map = map;
		this.updateStatus();
	}
	
	public RunStatusUpdater(MapInstance map, int maxSteps) {
		this.maxSteps = maxSteps;
		this.maxTime = (int)Launcher.getRunEnviron().getTimer().getMaxTime();
		this.map = map;
		this.updateStatus();
	}

	/* *******************
	 * Events
	 *********************/
	
	@Override
	public void ExecutionFinished(GridEnvironResult result) {
		// only updates the step if the current step is less than the one given
		if (result.Step > curstep) {
			// Udpate step no and the time that has passed so far
			curstep = result.Step;			
			timeSoFar = result.TimeSoFar;
			costSoFar = result.CostSoFar;
			
			this.updateStatus(); // Update the status bar
		}
		
		AEnvironTimer timer = Launcher.getRunEnviron().getTimer();
		if (timer instanceof PulserTimer) {
			int curpulse = ((PulserTimer) timer).getCurrentPulse(); 
			if (curpulse > this.pulses) this.pulses = curpulse;
		} else this.pulses = -1;	
		
		
	}

	@Override
	public void RunStart(AEnvironTimer timer) {
		currunstate = "Running";
		this.updateStatus();

	}

	@Override
	public void RunFinished(AEnvironTimer timer) {
		currunstate = "Stopped";		
		this.updateStatus();

	}

	@Override
	public void RunPaused(AEnvironTimer timer) {
		// one can only progress from "Running" state to "Pausing" state and not from "Stopped"
		if (currunstate.equals("Running")) currunstate = "Pausing";		
		this.updateStatus();

	}

	@Override
	public void RunFailed(AEnvironTimer timer, Exception e) {
		currunstate = "Stopped with Exception: '" + e.getMessage() + "'";
		this.updateStatus();
		System.out.println(currunstate); // Print exception to console too!
		
	}

	/* *******************
	 * Utility
	 *********************/
	
	/**
	 * update the displayed status bar
	 */
	private void updateStatus() {
		maxTime = (int)Launcher.getRunEnviron().getTimer().getMaxTime();
		map.setRunState(currunstate, curstep, timeSoFar/1000000L, costSoFar, pulses, maxSteps, maxTime);
	}


}
