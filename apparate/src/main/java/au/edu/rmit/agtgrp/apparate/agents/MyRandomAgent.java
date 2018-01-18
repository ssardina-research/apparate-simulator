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
package au.edu.rmit.agtgrp.apparate.agents;
import java.util.ArrayList;
import java.util.Random;

import au.edu.rmit.agtgrp.apparate.gui.simviewer.model.GridCell;
import au.edu.rmit.agtgrp.apparate.gui.simviewer.model.GridDomain;
import au.edu.rmit.agtgrp.apparate.jpathplan.entites.ComputedPlan;
import au.edu.rmit.agtgrp.apparate.gui.interfaces.PlanningAgent;


public class MyRandomAgent implements PlanningAgent {

	@Override
	public GridCell getNextMove(GridDomain map, GridCell sState, GridCell gState, int stepLeft,
                                long stepTime, long timeLeft) {
	
		// Take a random step
		Random random = new Random();		
		GridCell nextCell = (GridCell) map.getPredecessors(sState).get(random.nextInt(map.getPredecessors(sState).size()));
		
		System.out.println("I'll try to move randomly to "+ nextCell.toString() + " (Time left: " + timeLeft + "ms; Steps left: " + stepLeft);

		return nextCell;
		
	}

	

	// You can if you want implement the methods below
	// Do we want to show extra info? (e.g., close and open nodes, current path)

	@Override
	public ArrayList<GridCell> expandedNodes() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ArrayList<GridCell> unexpandedNodes() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Boolean showInfo() {
		// TODO Auto-generated method stub
		return false;
	}


	public PlanningAgent createPathPlannerInstance() {
		// TODO Auto-generated method stub
		return new MyRandomAgent();
	}

	@Override
	public ComputedPlan getPath() {
		// TODO Auto-generated method stub
		return null;
	}

}
