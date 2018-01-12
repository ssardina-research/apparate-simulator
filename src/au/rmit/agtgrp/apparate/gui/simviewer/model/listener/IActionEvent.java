package au.rmit.agtgrp.apparate.gui.simviewer.model.listener;

import au.rmit.agtgrp.apparate.gui.simviewer.model.GridEnviron;
import au.rmit.agtgrp.apparate.gui.simviewer.model.script.ActionType;

/**
 * Abstraction of a script event action
 * 
 * @author Andy Heng Xie
 *
 */
public interface IActionEvent {

	/**
	 * Execute the event on the given environment
	 * @param environ
	 */
	public void RunAction(GridEnviron environ);
	
	/**
	 * the actiontype of this action
	 * @return
	 */
	public ActionType getAction();
}
