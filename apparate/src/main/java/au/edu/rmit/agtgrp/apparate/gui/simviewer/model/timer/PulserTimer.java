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
 * This allows the environment to run for a certain amount of time before polling it for a result
 * If no result available, it will re-poll again after a length of time
 * 
 * @author Andy Heng Xie
 *
 */
public class PulserTimer extends AEnvironTimer {
	private final int BUFFERTIME = 1000; // the amount of time extra when it tries to determine if it should kill the environment thread
	final private double MS_TO_NS_CONV_FACT = 1E6;

	private int pulsePeriodms = 100; // the re-polling period in milliseconds
	private int maxNoSteps = 0; // the maximum number of polling 
	private int maxTimems = 0; // the maximum amount of time to execute (ms)
	private int maxStepTimems = 0; // the maximum amount of time given at each step (ms)

	private volatile int noStepsPassed = 0; // the number of run performed; 
	private volatile long timenanoPassed = 0; // the amount of time passed; 
	

	

	/* *******************
	 * Constructor
	 *********************/
	
	/**
	 * Constructor
	 *
	 * @param environ the environment to execute to
	 * @param pulseperiod the polling period in millisecond, minimum is 40 millisecond
	 * @param maxexecution the maximum number of polling, set -1 to be infinite
	 * @param maxstepmillitime the maximum amount of time in millisecond to execute each step, set -1 to be infinite
	 * @param maxmillitime the maximum amount of time in millisecond to execute, set -1 to be infinite
	 */
	public PulserTimer(GridEnviron environ, int pulseperiod, int maxexecution, int maxstepmillitime, int maxmillitime) {
		super(environ, true);
		this.pulsePeriodms = java.lang.Math.max(pulseperiod, 1);
		this.maxNoSteps = java.lang.Math.max(maxexecution, 0);
		this.maxStepTimems = java.lang.Math.max(maxstepmillitime, 0);
		this.maxTimems = java.lang.Math.max(maxmillitime, 0);
	}

	

	/***
	 * Reset max time to maxtime (consider what has been used so far and add newtime to that)
     *
	 * @param newMaxTime the new max time to use
	 */
	public void resetTimeMax(int newMaxTime){
		//this.maxTimems = java.lang.Math.max(newMaxTime, 0);
		this.maxTimems = (int) (this.timenanoPassed / MS_TO_NS_CONV_FACT) + newMaxTime;
	}

	public long getMaxTime(){return maxTimems;}
	/* *******************
	 * Accessors
	 *********************/
	
	public int getPulsePeriod() {
		return this.pulsePeriodms;
	}
	
	public int getMaxPulse() {
		return this.maxNoSteps;
	}
	
	/**
     * Get max time
	 * 
	 * @return the max time given in milliseconds
	 */
	public int getMaxExecutionTime() {
		return this.maxTimems;
	}
	
	public int getCurrentPulse() {
		return this.noStepsPassed;
	}

	/**
     * Get current time
	 * 
	 * @return current time given in milliseconds
	 */
	public int getCurrentExecutionTime() {
		return (int) (this.timenanoPassed / 1000000);
	}
	
	/* *******************
	 * Override
	 *********************/
	
	@Override
	public void StartExecution() throws Exception {
		// Run steps (false means it is not just one step)
		RunSteps(false);
	}
	

	@Override
	protected void StepExecution() throws Exception {
		// Run steps (true means just one step)
		RunSteps(true);
	}
	
	/* *******************
	 * Utility
	 *********************/
	
	/**
	 * Check if current state is valid to keep on running
	 * @return
	 */
	private boolean isValidRunCondition() {
		if (this.doabort) return false;
		if (this.maxNoSteps > 0 && noStepsPassed >= this.maxNoSteps) return false;
		if (this.maxTimems > 0 && timenanoPassed > maxTimems * 1000000L) return false;
		return true;
	}
	

	
	private void RunSteps(boolean runOnlyOnce) throws Exception {
		long timeLeftnano;
		int millitime, nanotime;
		boolean hasTimeLimit = this.maxTimems > 0 ? true : false;
		boolean hasStepTimeLimit = this.maxStepTimems > 0 ? true : false;
		//GridEnviron currentEnvironment = this.getEnvironment(); // this is the current agent environment that is being used

		// Do all the event changes that come BEFORE the step is computed and taken
		this.getEnvironment().CallenvironmentBeforeExecution(this.getEnvironment());
		
		try {
			// Define the thread we will use to compute the next step with the current environment as input
			EnvironRunner stepworker = new EnvironRunner(this.getEnvironment());
			Thread worker;
			
			// get the initial number of steps performed and time taken so far in the environment
			this.noStepsPassed = this.getEnvironment().getStep();
			this.timenanoPassed = this.getEnvironment().getTimeSoFar();

		
			do {						
				// Calculate how much time is left (add a bit of buffer for extra java book-keeping)
				timeLeftnano = java.lang.Math.max( (maxTimems * 1000000L - timenanoPassed), 0);
				if (timeLeftnano > 0) timeLeftnano += BUFFERTIME * 1000000L;
				

				
				// Continue only if this is a valid run (enough time and steps remaining and not paused)
				if (!isValidRunCondition()) return;
				
				// Perform the next agent step in a separate thread
				worker = new Thread(stepworker);
				worker.start();
		

				if (hasTimeLimit) // that is, maxtime > 0
					// Wait on worker for millitime+nanotime before dying
					worker.join((timeLeftnano / 1000000L), (int) (timeLeftnano % 1000000L));
				else
					// No time limit, wait as much as needed until thread finishes
					worker.join();
				
				if (worker.isAlive()) {	// We have waited enough but the thread is still working, kill it!
					worker.interrupt();	// INTERRUPT THREAD!! TIME IS OVER!
					throw new Exception("Forced to kill - Total time limit exceeded!");
				} else {	// The step finished within the limits

					// Pause for the pulse-period (substract time passed on doing the step!)
					long pulseleft = pulsePeriodms * 1000000L - (this.getEnvironment().getStepTime() % (pulsePeriodms * 1000000L));
					Thread.sleep((int) (pulseleft / 1000000), (int) (pulseleft % 1000000));
					
					// If stepworker threw exception, then throw it here
					if (stepworker.getRunException() != null) throw stepworker.getRunException();
					
					// Do all the updates corresponding to the finished step
					this.FinishedExecutionEvent();

					// Do all the event changes that come AFTER the step is computed and taken
					this.getEnvironment().CallenvironmentBeforeExecution(this.getEnvironment());
				}

				// Update  number of steps performed and time taken so far in the environment
				noStepsPassed = this.getEnvironment().getStep();
				timenanoPassed = this.getEnvironment().getTimeSoFar();
				
			
			} while (!runOnlyOnce);
		} finally {
			this.doabort = false;
			
		}
	}
	
	/**
	 * 
	 * @return 	time left allowed in milliseconds
	 */
	public long getTimeLeft() {
		return this.maxTimems - getCurrentExecutionTime();
		
	}

}
