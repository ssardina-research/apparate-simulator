/*
 * APPARATE - Path Planner Framework and Simulator in Java
 *
 * Copyright (C) 2010-2018
 * Andy Xie, Abhijeet Anand and Sebastian Sardina
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

package au.edu.rmit.agtgrp.apparate.gui.simviewer.model.script;

import au.edu.rmit.agtgrp.apparate.gui.simviewer.model.GridCell;
import au.edu.rmit.agtgrp.apparate.gui.simviewer.model.GridEnviron;
import au.edu.rmit.agtgrp.apparate.gui.simviewer.model.GridView;
import au.edu.rmit.agtgrp.apparate.gui.simviewer.model.listener.IGridEnvironUpdateListener;
import au.edu.rmit.agtgrp.apparate.gui.simviewer.model.timer.AEnvironTimer;
import au.edu.rmit.agtgrp.apparate.gui.interfaces.PlanningAgent;
import au.edu.rmit.agtgrp.apparate.gui.simviewer.model.GridCoord;

/**
 * Pauses the loop execution when it detect the start has arrived at the goal position
 * 
 * @author Andy Heng Xie
 *
 */
public class StopOnArrival implements IGridEnvironUpdateListener {

	private volatile boolean stoponarrival = true; // to pause on arrival
	private final AEnvironTimer timer; // the timer to pause
	

	/* *******************
	 * Constructor
	 *********************/
	
	/**
	 * @param enable to stop on arrival, or not
	 * @param timer the timer to pause
	 */
	public StopOnArrival(boolean enable, AEnvironTimer timer) {
		this.stoponarrival = enable;
		this.timer = timer;
	}
	
	public boolean getDoStop() {
		return stoponarrival;
	}
	
	public void setDoStop(boolean stoponarrival) {
		this.stoponarrival = stoponarrival;
	}
	
	/* *******************
	 * Events
	 *********************/
	
	@Override
	public void startMoved(GridEnviron environ, GridCoord oldpos,
                           GridCoord newpos) {
		// TODO Auto-generated method stub

	}

	@Override
	public void goalMoved(GridEnviron environ, GridCoord oldpos,
			GridCoord newpos) {
		// TODO Auto-generated method stub

	}

	@Override
	public void cellUpdated(GridEnviron environ, GridCell newcell) {
		// TODO Auto-generated method stub

	}

	@Override
	public void environmentBeforeExecution(GridEnviron environ) {
		// TODO Auto-generated method stub

	}

	@Override
	public void environmentAfterExecution(GridEnviron environ) {
		// TODO Auto-generated method stub

	}

	@Override
	public void arrivedAtGoal(GridEnviron environ) {
		if (stoponarrival) timer.Pause();
	}

	@Override
	public void spawnerChanged(GridEnviron environ, PlanningAgent oldplanner,
			PlanningAgent newplanner) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void gridviewChanged(GridEnviron environ, GridView oldspawner,
			GridView newspawner) {
		// TODO Auto-generated method stub
		
	}

}
