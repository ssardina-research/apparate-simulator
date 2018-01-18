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

import java.util.List;

import au.edu.rmit.agtgrp.apparate.gui.simviewer.model.GridEnviron;
import au.edu.rmit.agtgrp.apparate.gui.simviewer.model.listener.ITimerListener;

/**
 * Wraps around a environment timer to have it run on another thread
 * @author Sykout
 *
 */
public class ThreadedTimer extends AEnvironTimer {
	

	/* *******************
	 * Private class
	 *********************/
	
	/**
	 * Move the step execution to another thread to prevent interface locking
	 * 
	 * @author Andy Heng Xie
	 *
	 */
	private class RunStep implements Runnable {
		private final AEnvironTimer curtimer = timer;
		@Override
		public void run() {
			curtimer.Step();
		}
	}
	
	/**
	 * Move the pause execution to another thread to prevent interface locking
	 * 
	 * @author Andy Heng Xie
	 *
	 */	
	private class RunPause implements Runnable {
		private final AEnvironTimer curtimer = timer;
		@Override
		public void run() {
			curtimer.Pause();
		}
	}
	
	/**
	 * Move the loop execution to another thread to prevent interface locking
	 * 
	 * @author Andy Heng Xie
	 *
	 */	
	private class RunLoop implements Runnable {
		private final AEnvironTimer curtimer = timer;
		@Override
		public void run() {
			curtimer.Loop();
		}
	}
	
	
	private final AEnvironTimer timer;
	
	public ThreadedTimer(AEnvironTimer timer) {
		super(null);
		this.timer = timer;
	}
	
	public AEnvironTimer getTimer() {
		return timer;
	}

	public void setForceAbort(boolean doabort) {
		timer.setForceAbort(doabort);
	}
	
	public GridEnviron getEnvironment() {
		return timer.getEnvironment();
	}
	
	public boolean doForceAbort() {
		return timer.doForceAbort();
	}
	
	public boolean isRunning() {
		return timer.isRunning();
	}
	
	public boolean isLooping() {
		return timer.isLooping();
	}
	
	public void Loop() {
		(new Thread(new RunLoop())).start();
	}
	
	public void Step() {
		(new Thread(new RunStep())).start();
	}
	
	public void Pause() {
		(new Thread(new RunPause())).start();
	}

	public void AddTimerEvent(ITimerListener event) {
		timer.AddTimerEvent(event);
	}
	
	public void RemoveTimerEvent(ITimerListener event) {
		timer.RemoveTimerEvent(event);
	}
	
	public List<ITimerListener> RemoveAllTimeEvents() {
		return timer.RemoveAllTimeEvents();
	}
	
	
	@Override
	protected void StartExecution() {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void StepExecution() {
		// TODO Auto-generated method stub
		
	}
	public long getTimeLeft(){return 0;}
	
	public void resetTimeMax(int maxtime){
		
	}
	public long getMaxTime(){return 0;}
}
