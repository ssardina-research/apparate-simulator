/**
 * A Library of Path Planning Algorithms
 *
 * Copyright (C) 2010 Abhijeet Anand and Sebastian Sardina, School of CS and IT, RMIT University, Melbourne VIC 3000.
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
package au.edu.rmit.agtgrp.apparate.gui.simviewer.model;

import au.edu.rmit.agtgrp.apparate.gui.simviewer.model.script.ScriptEvents;
import au.edu.rmit.agtgrp.apparate.gui.simviewer.model.script.StopOnArrival;
import au.edu.rmit.agtgrp.apparate.gui.simviewer.model.timer.AEnvironTimer;

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
