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

import au.edu.rmit.agtgrp.apparate.gui.simviewer.controller.Launcher;
import au.edu.rmit.agtgrp.apparate.gui.simviewer.model.GridEnviron;
import au.edu.rmit.agtgrp.apparate.gui.simviewer.model.GridCell;
import au.edu.rmit.agtgrp.apparate.gui.simviewer.model.GridCoord;
import au.edu.rmit.agtgrp.apparate.gui.simviewer.model.listener.IActionEvent;

/**
 * Actions which affect the start or goal position
 * 
 * @author Andy Heng Xie
 *
 */
public class EntityActionEvent implements IActionEvent {

	private final ActionType action; // what action is it
	private final int goalx, goaly; // whats the starting position
	

	/* *******************
	 * Constructor
	 *********************/
	
	public EntityActionEvent(ActionType action, int goalx, int goaly) {
		this.action = action;
		this.goalx = goalx;
		this.goaly = goaly;
	}


	/* *******************
	 * Events
	 *********************/
	
	@Override
	public void RunAction(GridEnviron environ) {
		GridCoord curpos;
		switch (action) {
		case ACT_PUTSTART:
			if (validPlacement(environ, goalx, goaly))
				environ.setStartPosition(new GridCoord(goalx, goaly));
			break;
		case ACT_PUTGOAL:
			if (validPlacement(environ, goalx, goaly))
				environ.setGoalPosition(new GridCoord(goalx, goaly));
			break;
		case ACT_PUSHSTART:
			curpos = environ.getStartPosition();
			if (validPlacement(environ, curpos.getX() + goalx, curpos.getY() + goaly))
				environ.setStartPosition(new GridCoord(curpos.getX() + goalx, curpos.getY() + goaly));
			break;
		case ACT_PUSHGOAL:
			curpos = environ.getGoalPosition();
			if (validPlacement(environ, curpos.getX() + goalx, curpos.getY() + goaly))
				environ.setGoalPosition(new GridCoord(curpos.getX() + goalx, curpos.getY() + goaly));
			break;	
		case ACT_SETTIME:
			Launcher.getRunEnviron().setMaxTime(goalx);
			break;
		}
		
	}

	@Override
	public ActionType getAction() {
		// TODO Auto-generated method stub
		return null;
	}


	/* *******************
	 * Utility
	 *********************/

	/**
	 * Check if the given coordinate is a valid position for placement of start/goal
	 * @param environ
	 * @param gridx
	 * @param gridy
	 * @return
	 */
	private static boolean validPlacement(GridEnviron environ, int gridx, int gridy) {
		GridCell curcell = environ.getCellAt(gridx, gridy);
		if (curcell == null) return false;
		return !curcell.isBlocked();
	}
	

	/* *******************
	 * Override
	 *********************/
	
	public int hashCode() {
		int actionhash = 0;
		switch (action) {
		case ACT_PUTSTART:
			actionhash = 0;
			break;
		case ACT_PUTGOAL:
			actionhash = 1;
			break;
		case ACT_PUSHSTART:
			actionhash = 2;
			break;
		case ACT_PUSHGOAL:
			actionhash = 3;
			break;
		case ACT_SETTIME:
			actionhash = 4;
			break;	
		}
		return goalx % 16384 + (goalx % 16384) * 16384 + actionhash * 268435456;
	}
}
