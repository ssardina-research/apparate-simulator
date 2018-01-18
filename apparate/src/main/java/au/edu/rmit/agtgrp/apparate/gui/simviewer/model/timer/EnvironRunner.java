/**
 * A Path Planning Simulator
 *
 * Copyright (C) 2010 Andy Xie, Abhijeet Anand and Sebastian Sardina
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
package au.edu.rmit.agtgrp.apparate.gui.simviewer.model.timer;

import au.edu.rmit.agtgrp.apparate.gui.simviewer.model.GridEnviron;

/**
 * simply enable the environment to be executed in another thread
 * 
 * @author Andy Heng Xie
 *
 */
public class EnvironRunner implements Runnable {
	private GridEnviron environ; // the environment to run
	private long steptime; // reported time for the step ran
	private Exception environexception; // reported exception

	/* *******************
	 * Constructor
	 *********************/
	
	public EnvironRunner(GridEnviron environ) {
		this.environ = environ;
	}
	
	/* *******************
	 * Accessors
	 *********************/
	
	public GridEnviron getEnvironment() {
		return environ;
	}
	
	public long getStepTime() {
		return steptime;
	}
	
	public Exception getRunException() {
		return environexception;
	}

	/* *******************
	 * Override
	 *********************/
	
	@Override
	public void run() {
		steptime = 0;
		environexception = null;
		try {
			this.environ.applyStep(true);
		} catch (Exception e) {
			environexception = e;
		} finally {
			steptime = this.environ.getStepTime();
		}
	}
	
	
	
}