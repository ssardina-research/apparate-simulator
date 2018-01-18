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
 * Predetermined action which can be performed from a script
 * 
 * @author Andy Heng Xie
 *
 */
public enum ActionType {
	ACT_PUTSTART(false), // move the start/source position
	ACT_PUTGOAL(false), // move the goal position
	ACT_PUSHSTART(false), // move the start/source position
	ACT_PUSHGOAL(false), // move the goal position
	ACT_FILLOUTOFBOUND(true), // fill given area with 'out of bound'
	ACT_FILLGROUND(true), // fill given area with 'ground'
	ACT_FILLSWAMP(true), // fill given area with 'swamp'
	ACT_FILLWATER(true), // fill given area with 'water'
	ACT_FILLTREE(true), // fill given area with 'tree'
	ACT_SETTIME(false); // set the max time
	
	
	private final boolean isselect; // is it an area action?
	

	/* *******************
	 * Constructor
	 *********************/
	
	private ActionType(boolean isselectaction) {
		this.isselect = isselectaction;
	}
	

	/* *******************
	 * Accessor
	 *********************/
	
	public boolean isSelectAction() {
		return this.isselect;
	}
}
