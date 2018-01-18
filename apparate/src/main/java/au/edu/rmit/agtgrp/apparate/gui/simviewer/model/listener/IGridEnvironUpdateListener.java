/**
 * A Library of Path Planning Algorithms
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
package au.edu.rmit.agtgrp.apparate.gui.simviewer.model.listener;

import au.edu.rmit.agtgrp.apparate.gui.simviewer.model.GridCell;
import au.edu.rmit.agtgrp.apparate.gui.simviewer.model.GridEnviron;
import au.edu.rmit.agtgrp.apparate.gui.simviewer.model.GridView;
import au.edu.rmit.agtgrp.apparate.gui.interfaces.PlanningAgent;
import au.edu.rmit.agtgrp.apparate.gui.simviewer.model.GridCoord;

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
	 *
     * @param environ the running grid environment
	 * @param oldpos the original start position
	 * @param newpos the new start position
	 */
	public void startMoved(GridEnviron environ, GridCoord oldpos, GridCoord newpos);
	
	/**
	 * Fired when the goal position have been moved
	 * Fired after movement has been made
     *
     * @param environ the running grid environment
	 * @param oldpos the original goal position 
	 * @param newpos the new goal position
	 */
	public void goalMoved(GridEnviron environ, GridCoord oldpos, GridCoord newpos);
	
	
	/**
	 * Fired when one of the grid cell has been altered
	 * Fires after alteration has been made
     *
     * @param environ the running grid environment
	 * @param newcell the updated cell
	 */
	public void cellUpdated(GridEnviron environ, GridCell newcell);
	
	
	/**
	 * Fires after the call to run the path planner but before actually running it
     *
     * @param environ the running grid environment
	 */
	public void environmentBeforeExecution(GridEnviron environ);
	
	/**
	 * Fires after the run of the path planner
     *
     * @param environ the running grid environment
	 */
	public void environmentAfterExecution(GridEnviron environ);
	
	
	/**
	 * Fires when the start position is on the goal position
     *
     * @param environ the running grid environment
	 */
	public void arrivedAtGoal(GridEnviron environ);
	
	/**
	 * Fired when the spawner have been changed
     *
     * @param environ the running grid environment
	 * @param oldspawner old planning spawner
	 * @param newspawner new planning spawner
	 */
	public void spawnerChanged(GridEnviron environ, PlanningAgent oldspawner, PlanningAgent newspawner);
	
	/**
	 * Fired when grid view type has been changed
     *
     * @param environ the running grid environment
	 * @param oldspawner old grid view spawner
	 * @param newspawner new grid view spawner
	 */
	public void gridviewChanged(GridEnviron environ, GridView oldspawner, GridView newspawner);
}
