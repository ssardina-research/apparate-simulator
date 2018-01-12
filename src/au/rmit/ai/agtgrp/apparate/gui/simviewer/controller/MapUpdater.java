package au.rmit.ai.agtgrp.apparate.gui.simviewer.controller;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

import au.rmit.ai.agtgrp.apparate.gui.interfaces.PlanningAgent;
import au.rmit.ai.agtgrp.apparate.gui.simviewer.model.listener.IGridEnvironUpdateListener;
import au.rmit.ai.agtgrp.apparate.gui.simviewer.model.listener.ITimerListener;
import au.rmit.ai.agtgrp.apparate.gui.simviewer.model.timer.AEnvironTimer;
import au.rmit.ai.agtgrp.apparate.gui.simviewer.view.MapInstance;
import au.rmit.ai.agtgrp.apparate.gui.simviewer.model.GridCell;
import au.rmit.ai.agtgrp.apparate.gui.simviewer.model.GridCoord;
import au.rmit.ai.agtgrp.apparate.gui.simviewer.model.GridEnviron;
import au.rmit.ai.agtgrp.apparate.gui.simviewer.view.MapCoord;
import au.rmit.ai.agtgrp.apparate.jpathplan.entites.Plan;

import au.rmit.ai.agtgrp.apparate.gui.interfaces.*;
import au.rmit.ai.agtgrp.apparate.gui.simviewer.model.GridCellType;
import au.rmit.ai.agtgrp.apparate.gui.simviewer.model.GridView;
import au.rmit.ai.agtgrp.apparate.gui.simviewer.model.timer.GridEnvironResult;

/**
 * Listens to any events occurred within grid environment and would trigger a redraw of the map
 * 
 * @author Andy Xie
 *
 */
public class MapUpdater implements IGridEnvironUpdateListener, ITimerListener {

	private static final Color MURKY_GREEN = new Color(51, 51, 0); // The colour of swamps
	
	/* *******************
	 * Variable
	 *********************/
	
	private final GridEnviron environ; // the environment this is listening to
	private final MapInstance map; // the map which this will apply the redraw to
	private int lastdrawnstep; // check which step was last drawn
	private final Object drawlock = new Object(); // prevent this from updating the map by two thread at the same time to stop graphic corruption
	
	

	/* *******************
	 * Constructor
	 *********************/
	
	/**
	 * 
	 * @param environ the grid environment this listener is listening to 
	 * @param map the map this will update the environment to
	 */
	public MapUpdater(GridEnviron environ, MapInstance map) {
		Color[][] terrianmap = new Color[environ.getWidth()][environ.getHeight()];
		boolean[][] blockedmap = new boolean[environ.getWidth()][environ.getHeight()];
		this.environ = environ;
		this.map = map;
		
		for (int i = 0; i < environ.getWidth(); i++) for (int j = 0; j < environ.getHeight(); j++) {
			GridCell curcell = environ.getCellAt(i, j);
			terrianmap[i][j] = getCellTypeColour(curcell.getCellType());
			blockedmap[i][j] = curcell.isBlocked();
		}
		map.updateMapColor(terrianmap);
		map.updateMapTraversability(blockedmap);
		if (environ.getStartPosition() != null) map.setStartNode(environ.getStartPosition().getX(), environ.getStartPosition().getY());
		if (environ.getGoalPosition() != null) map.setGoalNode(environ.getGoalPosition().getX(), environ.getGoalPosition().getY());
		if(environ.getPlannerSpawner().showInfo()){updatePathPlanState();}
	}
	
	/* *******************
	 * Events
	 *********************/
	
	/**
	 * redraw the start position when start position changes
	 */
	@Override
	public void startMoved(GridEnviron environ, GridCoord oldpos, GridCoord newpos) {
		map.setStartNode(newpos.getX(), newpos.getY());
	}

	/**
	 * redraw the goal position when goal position changes
	 */
	@Override
	public void goalMoved(GridEnviron environ, GridCoord oldpos, GridCoord newpos) {
		map.setGoalNode(newpos.getX(), newpos.getY());
	}

	@Override
	public void environmentBeforeExecution(GridEnviron environ) {
		// TODO Auto-generated method stub

	}

	/**
	 * redraw the cell if it has been changed
	 */
	@Override
	public void cellUpdated(GridEnviron environ, GridCell newcell) {
		map.updateMapColor(newcell.getCoord().getX(), newcell.getCoord().getY(), getCellTypeColour(newcell.getCellType()));
		map.updateMapTraversability(newcell.getCoord().getX(), newcell.getCoord().getY(), newcell.isBlocked());
	}

	@Override
	public void arrivedAtGoal(GridEnviron environ) {
		// TODO Auto-generated method stub
		
	}

	/**
	 * redraw the plan path, the opened node and the closed node once the path planning is complete
	 */
	@Override
	public void environmentAfterExecution(GridEnviron environ) {
	}


	/**
	 * Redraw once a single step is finished
	 */
	@Override
	public void ExecutionFinished(GridEnvironResult result) {
		synchronized (drawlock) {
			// if the reported step to draw is from a step before the one last drawn, disregard redraw
			if (result.Step < lastdrawnstep) return;
			lastdrawnstep = result.Step;
			Plan curpath = result.PlanPath;
			List<MapCoord> planpathlist = new ArrayList<MapCoord>();
			GridCoord convcoord; 
			if(environ.getPlannerSpawner().showInfo()){
				map.setOpenedNode(convertToMapCoord(result.OpenedNodes));
				map.setClosedNode(convertToMapCoord(result.ClosedNodes));
				if (curpath != null) for (int i = 0; i < curpath.getLength(); i++) {
					convcoord = ((GridCell) curpath.getStep(i)).getCoord();
					planpathlist.add(new MapCoord(convcoord.getX(), convcoord.getY()));
				}
				map.setPlanPathNode(planpathlist);
			}
			map.updateShownMap();
		}
	}

	@Override
	public void RunStart(AEnvironTimer timer) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void RunFinished(AEnvironTimer timer) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void RunPaused(AEnvironTimer timer) {
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
	
	@Override
	public void RunFailed(AEnvironTimer timer, Exception e) {
		// TODO Auto-generated method stub
		
	}

	
	/* *******************
	 * Utility
	 *********************/
	
	/**
	 * redraw the plan path, the opened node and the closed node
	 */
	private void updatePathPlanState() {
		Plan curpath = environ.getPlannerSpawner().getPath();
		List<MapCoord> planpathlist = new ArrayList<MapCoord>();
		GridCoord convcoord;
		
		/**
		 * TODO: make if fail safe
		 */
		map.setOpenedNode(convertToMapCoord(environ.getOpenedNodes()));
		map.setClosedNode(convertToMapCoord(environ.getClosedNodes()));
		
		//if(curpath!=null) 
			//	System.out.println("Length of the current plan: " + curpath.getLength());
		
		if (curpath != null) for (int i = 0; i < curpath.getLength(); i++) {
			convcoord = ((GridCell) curpath.getStep(i)).getCoord();
			planpathlist.add(new MapCoord(convcoord.getX(), convcoord.getY()));
		}
		map.setPlanPathNode(planpathlist);
	}
	
	/**
	 * convert a list of grid cell and convert it to a list of map coordinate
	 * @param arrayList list of grid cell
	 * @return a list of map coordinate converted from the grid cell
	 */
	private static List<MapCoord> convertToMapCoord(ArrayList<GridCell> arrayList) {
		List<MapCoord> newlist = new ArrayList<MapCoord>();
		GridCoord convcoord;
		for (GridCell node: arrayList) {
			if (node == null) newlist.add(null);
			else {
				try {
					convcoord = node.getCoord();
					newlist.add(new MapCoord(convcoord.getX(), convcoord.getY()));
				} catch (Exception e) {
					newlist.add(null);
				}
			}
		}
		return newlist;
	}
	
	/**
	 * Maps a Grid Cell Type to a colour for the map
	 * @param celltype the cell type to be matched
	 * @return the colour corresponded to the cell type provided
	 */
	public static Color getCellTypeColour(GridCellType celltype) {
		switch (celltype) {
		case GROUND:
			return Color.gray;
		case SWAMP:
			return MURKY_GREEN;
		case WATER:
			return Color.BLUE;
		case OUTOFBOUND:
			return Color.DARK_GRAY;
		case TREE:
			return new Color(0, 127, 0);
		default:
			return Color.BLACK;
		}
	}


}
