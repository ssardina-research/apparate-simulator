package au.rmit.agtgrp.apparate.agents;
import java.util.ArrayList;
import java.util.Random;

import au.rmit.ai.agtgrp.apparate.jpathplan.entites.ComputedPlan;
import au.rmit.agtgrp.apparate.gui.interfaces.PlanningAgent;
import au.rmit.agtgrp.apparate.gui.simviewer.model.GridCell;
import au.rmit.agtgrp.apparate.gui.simviewer.model.GridDomain;


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
