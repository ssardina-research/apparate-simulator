/*
 * APPARATE - Path Planner Framework and Simulator in Java
 *
 * Copyright (C) 2010-2018
 * Andy Xie, Abhijeet Anand and Sebastian Sardina
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
	 * Gets the trigger type
	 *
	 * @return the type of trigger of this event
	 */
	public TriggerType getTriggerType();
	
	/**
	 * check if the environment status matches the trigger defined
	 * 
	 * @param environ the running environment
	 * @return true if environment matches this trigger
	 */
	public boolean isTriggered(GridEnviron environ);
}
