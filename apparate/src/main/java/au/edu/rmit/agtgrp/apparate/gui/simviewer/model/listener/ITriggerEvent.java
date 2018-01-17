package au.edu.rmit.agtgrp.apparate.gui.simviewer.model.listener;

import au.edu.rmit.agtgrp.apparate.gui.simviewer.model.GridEnviron;
import au.edu.rmit.agtgrp.apparate.gui.simviewer.model.script.TriggerType;

/**
 * Abstraction of a trigger event to validate against the environment to check if it is valid for the given action
 * 
 * @author Andy Heng Xie
 *
 */
public interface ITriggerEvent {
	
	/**
	 * gets the trigger type
	 * @return
	 */
	public TriggerType getTriggerType();
	
	/**
	 * check if the environment status matches the trigger defined
	 * 
	 * @param environ
	 * @return
	 */
	public boolean isTriggered(GridEnviron environ);
}
