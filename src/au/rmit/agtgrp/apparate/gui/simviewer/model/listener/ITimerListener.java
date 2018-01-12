package au.rmit.agtgrp.apparate.gui.simviewer.model.listener;

import au.rmit.agtgrp.apparate.gui.simviewer.model.timer.AEnvironTimer;
import au.rmit.agtgrp.apparate.gui.simviewer.model.timer.GridEnvironResult;

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
	 * @param result all the relevant information of the environment right after planner execution
	 * 				this is separated to allow this be to run on a concurrent thread from the planner execution
	 */
	public void ExecutionFinished(GridEnvironResult result);
	
	/**
	 * Triggered when execution begins
	 */
	public void RunStart(AEnvironTimer timer);
	
	/**
	 * Triggers when the whole execution finishes
	 */
	public void RunFinished(AEnvironTimer timer);
	
	/**
	 * Triggers when attempting to pause execution
	 */
	public void RunPaused(AEnvironTimer timer);
	
	/**
	 * Trigger when executing environment threw an exception or take to long (as specified)
	 * Run in place of RunFinished
	 * @param timer
	 * @param e the exception thrown
	 */
	public void RunFailed(AEnvironTimer timer, Exception e);
	
}
