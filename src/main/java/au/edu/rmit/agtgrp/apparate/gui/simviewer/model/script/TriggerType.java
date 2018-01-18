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

package au.edu.rmit.agtgrp.apparate.gui.simviewer.model.script;
/**
 * A list of all trigger type
 * 
 * @author Andy Heng Xie
 *
 */
public enum TriggerType {
	TRIG_START_ENTER, // when the start position enters the monitoring area
	TRIG_START_LEAVE, // when the start position leaves the monitoring area
	TRIG_START_ON, // when the start position is in the monitoring area
	TRIG_START_OFF, // when the start position is not in the monitoring area
	TRIG_GOAL_ENTER, // when the goal position enters the monitoring area
	TRIG_GOAL_LEAVE, // when the goal position leaves the monitoring area
	TRIG_GOAL_ON, // when the goal position is in the monitoring area
	TRIG_GOAL_OFF; // when the goal position is not in the monitoring area
}
