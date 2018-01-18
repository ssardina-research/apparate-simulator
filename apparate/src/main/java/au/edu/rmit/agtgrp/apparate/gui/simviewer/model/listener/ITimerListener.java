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

import au.edu.rmit.agtgrp.apparate.gui.simviewer.model.timer.AEnvironTimer;
import au.edu.rmit.agtgrp.apparate.gui.simviewer.model.timer.GridEnvironResult;

/**
 * The interface for the event of the grid environment timer
 * This is used in place of the GridEnviron event to reduce overhead on a run, to get more accurate timing
 * 
 * @author Andy Heng Xie
 *
 */
public interface ITimerListener {
	/**
	 * Execute when a run of the planner finished
	 *
	 * @param result all the relevant information of the environment right after planner execution
	 * 				this is separated to allow this be to run on a concurrent thread from the planner execution
	 */
	public void ExecutionFinished(GridEnvironResult result);
	
	/**
	 * Triggered when execution begins
     *
     * @param timer the timer to use
	 */
	public void RunStart(AEnvironTimer timer);
	
	/**
	 * Triggers when the whole execution finishes
     *
     * @param timer the timer to use
	 */
	public void RunFinished(AEnvironTimer timer);
	
	/**
	 * Triggers when attempting to pause execution
     *
     * @param timer the timer to use
	 */
	public void RunPaused(AEnvironTimer timer);
	
	/**
	 * Trigger when executing environment threw an exception or take to long (as specified)
	 * Run in place of RunFinished
     *
	 * @param timer the timer to use
	 * @param e the exception thrown
	 */
	public void RunFailed(AEnvironTimer timer, Exception e);
	
}
