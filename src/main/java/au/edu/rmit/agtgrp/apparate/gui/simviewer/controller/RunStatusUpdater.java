/*
 * APPARATE - Path Planner Framework and Simulator in Java
 *
 * Copyright (C) 2010-2018 Andy Xie, Abhijeet Anand and Sebastian Sardina
 * School of CS and IT, RMIT University, Melbourne VIC 3000.
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program. If not, see <http://www.gnu.org/licenses/>.
 */

package au.edu.rmit.agtgrp.apparate.gui.simviewer.controller;

import au.edu.rmit.agtgrp.apparate.gui.simviewer.model.listener.ITimerListener;
import au.edu.rmit.agtgrp.apparate.gui.simviewer.model.timer.GridEnvironResult;
import au.edu.rmit.agtgrp.apparate.gui.simviewer.model.timer.PulserTimer;
import au.edu.rmit.agtgrp.apparate.gui.simviewer.view.MapInstance;
import au.edu.rmit.agtgrp.apparate.gui.simviewer.model.timer.AEnvironTimer;

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
