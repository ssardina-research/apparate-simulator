package au.edu.rmit.agtgrp.apparate.gui.simviewer.model.listener;

import au.edu.rmit.agtgrp.apparate.gui.simviewer.model.GridEnviron;
import au.edu.rmit.agtgrp.apparate.gui.simviewer.model.script.ActionType;

/**
 * Abstraction of a script event action
 * 
 * @author Andy Heng Xie
 *
 */
public interface IActionEvent {

	/**
	 * Execute the event on the given environment
	 *
	 * @param environ the environment where to run the action
	 */
	public void RunAction(GridEnviron environ);
	
	/**
	 * The actiontype of this action
	 *
	 * @return the type of this action
	 */
	public ActionType getAction();
}
