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

import au.edu.rmit.agtgrp.apparate.gui.simviewer.model.GridEnviron;
import au.edu.rmit.agtgrp.apparate.gui.simviewer.model.listener.ITriggerEvent;
import au.edu.rmit.agtgrp.apparate.gui.simviewer.model.GridCoord;

/**
 * The trigger event which validates if the recent change to the environment matches the one indicated
 * 
 * @author Andy Heng Xie
 *
 */
public class TriggerEvent implements ITriggerEvent {

	private final TriggerType trigger; // the trigger type
	private final int goalx, goaly; // whats the starting position
	private final int sizex, sizey; // if is selection type of action, whats the area size
	

	/* *******************
	 * Constructor
	 *********************/
	
	/**
	 * the trigger type and the watched area
	 * @param trigger the trigger type
	 * @param goalx the start x
	 * @param goaly the start y
	 * @param sizex the width
	 * @param sizey the height
	 */
	public TriggerEvent(TriggerType trigger, int goalx, int goaly, int sizex, int sizey) {
		this.trigger = trigger;
		this.goalx = goalx;
		this.goaly = goaly;
		
		if (sizex < 0) this.sizex = 0;
		else this.sizex = sizex;
		if (sizey < 0) this.sizey = 0;
		else this.sizey = sizey;
	}
	

	/* *******************
	 * Events
	 *********************/
	
	public boolean isTriggered(GridEnviron environ) {
		GridCoord lastpos = null, curpos = null;
		switch (trigger) {
		case TRIG_START_ENTER:
		case TRIG_START_ON:
		case TRIG_START_OFF:
		case TRIG_START_LEAVE:
			lastpos = environ.getLastStartPosition();
			curpos = environ.getStartPosition();
			break;
		case TRIG_GOAL_ENTER:
		case TRIG_GOAL_ON:
		case TRIG_GOAL_OFF:
		case TRIG_GOAL_LEAVE:
			lastpos = environ.getLastGoalPosition();
			curpos = environ.getGoalPosition();
			break;
		}

		switch (trigger) {
		case TRIG_START_ENTER:
		case TRIG_GOAL_ENTER:
			if (lastpos == null) return false;
			return (!hasPoint(lastpos.getX(), lastpos.getY()) && hasPoint(curpos.getX(), curpos.getY()));
		case TRIG_START_LEAVE:
		case TRIG_GOAL_LEAVE:
			if (lastpos == null) return false;
			return (hasPoint(lastpos.getX(), lastpos.getY()) && !hasPoint(curpos.getX(), curpos.getY()));
		case TRIG_START_ON:
		case TRIG_GOAL_ON:
			return hasPoint(curpos.getX(), curpos.getY());
		case TRIG_START_OFF:
		case TRIG_GOAL_OFF:
			return !hasPoint(curpos.getX(), curpos.getY());
		}
		return false;
		
	}
	

	/* *******************
	 * Accessors
	 *********************/
	
	public TriggerType getTriggerType() {
		return trigger;
	}
	

	/* *******************
	 * Utility
	 *********************/
	
	public boolean hasPoint(int pointx, int pointy) {
		return pointx >= goalx && pointx < goalx + sizex && pointy >= goaly && pointy < goaly + sizey;
	}
	
	public boolean overlapRect(int pointx, int pointy, int rectwidth, int rectheight) {
		int width = rectwidth, height = rectheight;
		if (width < 0) width = 0;
		if (height < 0) height = 0;
		return pointx + width > goalx && pointx < goalx + sizex && pointy + height > goaly && pointy < goaly + sizey;
	}

	public boolean containRect(int pointx, int pointy, int rectwidth, int rectheight) {
		int width = rectwidth, height = rectheight;
		if (width < 0) width = 0;
		if (height < 0) height = 0;
		return pointx >= goalx && pointx + width <= goalx + sizex && pointy >= goaly && pointy + height <= goaly + sizey;
	}
	

	/* *******************
	 * Override
	 *********************/
	
	public int hashCode() {
		int triggerhash = 0;
		switch (trigger) {
		case TRIG_START_ENTER:
		case TRIG_START_ON:
		case TRIG_START_OFF:
		case TRIG_START_LEAVE:
			triggerhash = 1073741824;
			break;
		}
		return goalx % 256 + (goalx % 256) * 256 + (sizex % 128) * 32768 + (sizey % 128) * 4194304 + triggerhash;
	}
	
	public boolean equals(Object obj) {
		if (super.equals(obj)) return true;
		if (obj instanceof TriggerEvent) {
			if (((TriggerEvent) obj).trigger != this.trigger) return false;
			if (((TriggerEvent) obj).goalx != this.goalx) return false;
			if (((TriggerEvent) obj).goaly != this.goaly) return false;
			if (((TriggerEvent) obj).sizex != this.sizex) return false;
			if (((TriggerEvent) obj).sizey != this.sizey) return false;
			return true;
		}
		return false;
	}
	
}
