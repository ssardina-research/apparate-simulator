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

import au.edu.rmit.agtgrp.apparate.gui.simviewer.model.listener.IStepEvent;

/**
 * Indicate a event which is valid for a serial of steps
 * 
 * @author Andy Heng Xie
 *
 */
public class RangeStepEvent implements IStepEvent {
	
	private final int startstep, stepamount; // which step to start from and for how many steps
	

	/* *******************
	 * Constructor
	 *********************/
	
	/**
	 * 
	 * @param startstep the starting step
	 * @param stepamount for how many steps, 0 to indicate infinite
	 */
	public RangeStepEvent(int startstep, int stepamount) {
		this.startstep = startstep;
		if (stepamount < 0) this.stepamount = 0;
		else this.stepamount = stepamount;
	}
	

	/* *******************
	 * Accessor
	 *********************/
	
	/**
	 * check if that step is one of its valid steps
	 */
	public boolean isStep(int step){
		return step >= startstep && (step < (startstep + stepamount) || stepamount == 0);
	}
	

	/* *******************
	 * Override
	 *********************/
	
	public int hashCode() {
		return (startstep % 32768 + 32768) % 32768 + (stepamount % 32768) * 32768;
	}
	
	public boolean equals(Object obj) {
		if (super.equals(obj)) return true;
		if (obj instanceof RangeStepEvent) {
			if (((RangeStepEvent) obj).stepamount != this.stepamount) return false;
			if (((RangeStepEvent) obj).startstep != this.startstep) return false;
			return true;
		}
		return false;
	}
}
