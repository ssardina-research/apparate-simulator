package au.edu.rmit.agtgrp.apparate.gui.simviewer.model.listener;

/**
 * the abstraction of a step event check which will validate if the current step is valid for the action
 * 
 * @author Andy Heng Xie
 *
 */
public interface IStepEvent {
	
	/**
	 * Checks the given step matches this event
	 *
	 * @param step the step number
	 * @return true if event is this step
	 */
	public boolean isStep(int step);
}
