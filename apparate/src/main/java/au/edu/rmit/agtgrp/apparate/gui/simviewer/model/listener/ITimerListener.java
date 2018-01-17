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
