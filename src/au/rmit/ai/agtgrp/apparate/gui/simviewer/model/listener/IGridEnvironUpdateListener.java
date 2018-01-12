package au.rmit.ai.agtgrp.apparate.gui.simviewer.model.listener;

import au.rmit.ai.agtgrp.apparate.gui.interfaces.PlanningAgent;
import au.rmit.ai.agtgrp.apparate.gui.simviewer.model.GridCell;
import au.rmit.ai.agtgrp.apparate.gui.simviewer.model.GridCoord;
import au.rmit.ai.agtgrp.apparate.gui.simviewer.model.GridEnviron;
import au.rmit.ai.agtgrp.apparate.gui.simviewer.model.GridView;

/**
 * This holds all the event which can be fired by the Grid Environment
 * 
 * @author Andy Xie
 *
 */
public interface IGridEnvironUpdateListener {
	
	/**
	 * Fired when the start position have been changed
	 * Fired after movement has been made
	 * @param oldpos the original start position
	 * @param newpos the new start position
	 */
	public void startMoved(GridEnviron environ, GridCoord oldpos, GridCoord newpos);
	
	/**
	 * Fired when the goal position have been moved
	 * Fired after movement has been made
	 * @param oldpos the original goal position 
	 * @param newpos the new goal position
	 */
	public void goalMoved(GridEnviron environ, GridCoord oldpos, GridCoord newpos);
	
	
	/**
	 * Fired when one of the grid cell has been altered
	 * Fires after alteration has been made
	 * @param newcell the updated cell
	 */
	public void cellUpdated(GridEnviron environ, GridCell newcell);
	
	
	/**
	 * Fires after the call to run the path planner but before actually running it
	 */
	public void environmentBeforeExecution(GridEnviron environ);
	
	/**
	 * Fires after the run of the path planner
	 */
	public void environmentAfterExecution(GridEnviron environ);
	
	
	/**
	 * Fires when the start position is on the goal position
	 */
	public void arrivedAtGoal(GridEnviron environ);
	
	/**
	 * fired when the spawner have been changed
	 * @param environ
	 * @param oldspawner
	 * @param newspawner
	 */
	public void spawnerChanged(GridEnviron environ, PlanningAgent oldspawner, PlanningAgent newspawner);
	
	/**
	 * fired when grid view type has been changed
	 * @param environ
	 * @param oldspawner
	 * @param newspawner
	 */
	public void gridviewChanged(GridEnviron environ, GridView oldspawner, GridView newspawner);
}
