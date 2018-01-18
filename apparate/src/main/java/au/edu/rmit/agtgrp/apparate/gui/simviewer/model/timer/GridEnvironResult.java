/**
 * A Path Planning Simulator
 *
 * Copyright (C) 2010 Andy Xie, Abhijeet Anand and Sebastian Sardina
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
package au.edu.rmit.agtgrp.apparate.gui.simviewer.model.timer;

import java.util.ArrayList;
import java.util.List;

import au.edu.rmit.agtgrp.apparate.gui.simviewer.model.GridCell;
import au.edu.rmit.agtgrp.apparate.gui.simviewer.model.GridEnviron;
import au.edu.rmit.agtgrp.apparate.jpathplan.entites.ComputedPlan;
import au.edu.rmit.agtgrp.apparate.jpathplan.entites.Plan;
import au.edu.rmit.agtgrp.apparate.gui.simviewer.model.GridCoord;

/**
 * Saves the grid environment current state to be used later
 * 
 * @author Andy Xie
 *
 */
public class GridEnvironResult {
	public final int Step, Width, Height;
	public final long TimeSoFar;
	public final double CostSoFar;
	public final GridCoord StartPosition, GoalPosition;
	public final ArrayList<GridCell> OpenedNodes, ClosedNodes;
	public final Plan PlanPath;
	public final List<GridCoord> StartPath, GoalPath;
	
	public GridEnvironResult(GridEnviron environ) {
		Step = environ.getStep();
		TimeSoFar = environ.getTimeSoFar();
		CostSoFar = environ.getCostSoFar();
		Width = environ.getWidth();
		Height = environ.getHeight();
		/**
		 * TODO make fail safe
		 */
		if(environ.getPlannerSpawner().showInfo()){
			OpenedNodes = environ.getOpenedNodes();
			ClosedNodes = environ.getClosedNodes();
			PlanPath = environ.getPlannerSpawner().getPath();
		}else{
			OpenedNodes = new ArrayList<GridCell>();
			ClosedNodes= new ArrayList<GridCell>();
			PlanPath = new ComputedPlan();
		}
		StartPosition = environ.getStartPosition();
		GoalPosition = environ.getGoalPosition();
		
		StartPath = environ.getStartPath();
		GoalPath = environ.getGoalPath();
		
	}
}
