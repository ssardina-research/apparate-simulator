package au.rmit.ai.agtgrp.apparate.gui.simviewer.model.timer;

import java.util.ArrayList;
import java.util.List;

import au.rmit.ai.agtgrp.apparate.gui.simviewer.model.GridCell;
import au.rmit.ai.agtgrp.apparate.gui.simviewer.model.GridCoord;
import au.rmit.ai.agtgrp.apparate.gui.simviewer.model.GridEnviron;
import au.rmit.ai.agtgrp.apparate.jpathplan.entites.ComputedPlan;
import au.rmit.ai.agtgrp.apparate.jpathplan.entites.Plan;

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
