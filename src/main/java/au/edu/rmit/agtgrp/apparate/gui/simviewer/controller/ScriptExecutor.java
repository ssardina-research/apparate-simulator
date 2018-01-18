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

import au.edu.rmit.agtgrp.apparate.gui.simviewer.model.GridCell;
import au.edu.rmit.agtgrp.apparate.gui.simviewer.model.GridEnviron;
import au.edu.rmit.agtgrp.apparate.gui.simviewer.model.GridView;
import au.edu.rmit.agtgrp.apparate.gui.interfaces.PlanningAgent;
import au.edu.rmit.agtgrp.apparate.gui.simviewer.model.listener.IGridEnvironUpdateListener;
import au.edu.rmit.agtgrp.apparate.gui.simviewer.model.GridCoord;
import au.edu.rmit.agtgrp.apparate.gui.simviewer.model.RunEnviron;

/**
 * Execute the scripted events
 * 
 * @author Andy Heng Xie
 *
 */
public class ScriptExecutor implements IGridEnvironUpdateListener {

	private final RunEnviron environ; // the collection of events
	

	/* *******************
	 * Constructor
	 *********************/
	
	/**
	 * Regiter an environment to run executor
	 *
	 * @param environ the running environment
	 */
	public ScriptExecutor(RunEnviron environ) {
		this.environ = environ;
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
		if (this.environ.getScriptEvents() != null) this.environ.getScriptEvents().runStatusEvents(environ);

	}

	@Override
	public void environmentAfterExecution(GridEnviron environ) {
		if (this.environ.getScriptEvents() != null) this.environ.getScriptEvents().runChangeEvents(environ);
	}

	@Override
	public void arrivedAtGoal(GridEnviron environ) {
		// TODO Auto-generated method stub

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
