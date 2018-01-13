package au.edu.rmit.agtgrp.apparate.gui.simviewer.model.script;

import au.edu.rmit.agtgrp.apparate.gui.simviewer.model.GridCellType;
import au.edu.rmit.agtgrp.apparate.gui.simviewer.model.GridEnviron;
import au.edu.rmit.agtgrp.apparate.gui.simviewer.model.listener.IActionEvent;

/**
 * Actions which affect the terrain of the map
 * 
 * @author Andy Heng Xie
 *
 */
public class TerrainActionEvent implements IActionEvent {

	private final ActionType action; // what action is it
	private final int goalx, goaly; // whats the starting position
	private final int sizex, sizey; // if is selection type of action, whats the area size
	

	/* *******************
	 * Constructor
	 *********************/
	
	/**
	 * The action ot perform and the affected area
	 * @param action this action type
	 * @param goalx the start x position
	 * @param goaly the start y position
	 * @param sizex the width
	 * @param sizey the height
	 */
	public TerrainActionEvent(ActionType action, int goalx, int goaly, int sizex, int sizey) {
		this.action = action;
		this.goalx = goalx;
		this.goaly = goaly;
		this.sizex = sizex;
		this.sizey = sizey;
	}
	

	/* *******************
	 * Events
	 *********************/
	
	@Override
	public void RunAction(GridEnviron environ) {
		switch (action) {
		case ACT_FILLOUTOFBOUND:
			paintTerrain(environ, GridCellType.OUTOFBOUND, goalx, goaly, sizex, sizey);
			break;
		case ACT_FILLGROUND:
			paintTerrain(environ, GridCellType.GROUND, goalx, goaly, sizex, sizey);
			break;
		case ACT_FILLSWAMP:
			paintTerrain(environ, GridCellType.SWAMP, goalx, goaly, sizex, sizey);
			break;
		case ACT_FILLWATER:
			paintTerrain(environ, GridCellType.WATER, goalx, goaly, sizex, sizey);
			break;
		case ACT_FILLTREE:
			paintTerrain(environ, GridCellType.TREE, goalx, goaly, sizex, sizey);
			break;
		}
		
	}
	@Override
	public ActionType getAction() {
		return this.action;
	}
	

	/* *******************
	 * Utility
	 *********************/
	
	/**
	 * fill the given area with the suggested terrain
	 * @param environ
	 * @param type terrain type
	 * @param startx
	 * @param starty
	 * @param sizex
	 * @param sizey
	 */
	private static void paintTerrain(GridEnviron environ, GridCellType type, int startx, int starty, int sizex, int sizey) {
		int curstartx = startx, curstarty = starty, cursizex = sizex, cursizey = sizey;
		if (curstartx < 0) curstartx = 0;
		if (curstarty < 0) curstarty = 0;
		if (cursizex > environ.getWidth() - curstartx) cursizex = environ.getWidth() - curstartx;
		if (cursizey > environ.getHeight() - curstarty) cursizey = environ.getHeight() - curstarty;
		
		for (int i = 0; i < cursizex; i++) for (int j = 0; j < cursizey; j++) environ.getCellAt(curstartx + i, curstarty + j).setCellType(type);
	}
	
	

	/* *******************
	 * Override
	 *********************/
	
	public int hashCode() {
		int actionhash = 0;
		switch (action) {
		case ACT_FILLTREE:
		case ACT_FILLGROUND:
			actionhash = 1;
			break;
		default:
			actionhash = 0;
		}
		return goalx % 256 + (goalx % 256) * 256 + (sizex % 128) * 32768 + (sizey % 128) * 4194304 + actionhash * 1073741824;
	}

}
