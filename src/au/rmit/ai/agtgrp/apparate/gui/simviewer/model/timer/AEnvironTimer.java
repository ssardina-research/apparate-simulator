package au.rmit.ai.agtgrp.apparate.gui.simviewer.model.timer;

import java.util.ArrayList;
import java.util.List;

import au.rmit.ai.agtgrp.apparate.gui.simviewer.model.listener.ITimerListener;
import au.rmit.ai.agtgrp.apparate.gui.simviewer.model.GridEnviron;

/**
 * The parent class for a grid environment timer Contains most of the logic for
 * the environment, event and aborting. Need to fill out how the execution is
 * runned
 * 
 * @author Andy Heng Xie
 * 
 */
public abstract class AEnvironTimer {

	private volatile boolean running = false; // check that the current timer is
												// running
	private volatile boolean looping = false; // check that the current timer is
												// looping
	private Object runninglock = new Object(); // a running write lock
	private GridEnviron environ; // the environment this is executing
	private ArrayList<ITimerListener> events = new ArrayList<ITimerListener>(); // list
																				// of
																				// events
																				// to
																				// trigger
																				// on
																				// every
																				// step
	private boolean allowforceabort = false; // allow the timer to force an
												// abortion if in event of
												// conflict

	protected volatile boolean doabort = false; // a flag to detect if the
												// current execution would be
												// terminaated

	/* *******************
	 * Constructor*******************
	 */

	public AEnvironTimer(GridEnviron environ) {
		this.environ = environ;
	}

	public AEnvironTimer(GridEnviron environ, boolean allowforceabort) {
		this.environ = environ;
		this.allowforceabort = allowforceabort;
	}

	/* *******************
	 * Accessor*******************
	 */

	public GridEnviron getEnvironment() {
		return environ;
	}

	public boolean doForceAbort() {
		return allowforceabort;
	}

	public boolean isRunning() {
		return running;
	}

	public boolean isLooping() {
		return looping;
	}

	/* *******************
	 * Event*******************
	 */

	protected void FinishedExecutionEvent() {
		if (events.size() <= 0) // No events to process, just return
			return;
		
		Thread eventworker = new Thread(new Runnable() {
			private GridEnvironResult result = new GridEnvironResult(environ);

			@Override
			public void run() {
				CallExecutionFinished(result);
			}
		});
		eventworker.start();

	}

	/* *******************
	 * Mutator*******************
	 */

	/***
	 * Set max time
	 *
	 * @param maxtime the maximum time alloed
	 */
	public abstract void resetTimeMax(int maxtime);
	
	/**
	 * Setting allow the timer to terminate the environment at any time
	 * 
	 * @param doabort	to abort or not if maxtime is reached
	 */
	public void setForceAbort(boolean doabort) {
		this.allowforceabort = doabort;
	}

	/**
	 * Add a new timer event
	 * 
	 * @param event	event to be added
	 */
	public void AddTimerEvent(ITimerListener event) {
		events.add(event);
	}

	/**
	 * Remove an existing event, nothing happens if event does not exist
	 * 
	 * @param event	event to be removed
	 */
	public void RemoveTimerEvent(ITimerListener event) {
		events.remove(event);
	}

	/**
	 * Clear all events
	 * 
	 * @return	the old list of events
	 */
	public List<ITimerListener> RemoveAllTimeEvents() {
		List<ITimerListener> oldevents = events;
		events = new ArrayList<ITimerListener>();
		return oldevents;
	}

	/**
	 * stop execution at the next step
	 */
	public void Pause() {
		this.CallRunPaused(this);
		if (running)
			this.doabort = true;
	}

	/**
	 * starts up the loop
	 */
	public void Loop() {
		if (!running) {
			synchronized (runninglock) {
				this.CallRunStart(this);
				this.looping = running = true;
				try {
					StartExecution();
					this.CallRunFinished(this);
				} catch (Exception e) {
					this.CallRunFailed(this, e);
					running = false;
				} finally {
					this.looping = running = false;
				}

			}
		}
	}

	/**
	 * perform one step only, doing while it is not already running
	 * @throws InterruptedException 
	 */
	public void Step()  {
		if (!running) {
			synchronized (runninglock) {
				this.CallRunStart(this);
				running = true;
				try {
					StepExecution();
					this.CallRunFinished(this);
				} catch (Exception e) {
					this.CallRunFailed(this, e);
				} finally {
					running = false;
				}
			}
		}
	}

	/* *******************
	 * Utility
	 * *******************
	 */

	protected void CallRunStart(AEnvironTimer timer) {
		for (ITimerListener event : events)
			try {
				event.RunStart(timer);
			} catch (Exception e) {
			}
	}

	protected void CallRunPaused(AEnvironTimer timer) {
		for (ITimerListener event : events)
			try {
				event.RunPaused(timer);
			} catch (Exception e) {
			}
	}

	protected void CallRunFinished(AEnvironTimer timer) {
		for (ITimerListener event : events)
			try {
				event.RunFinished(timer);
			} catch (Exception e) {
			}
	}

	protected void CallRunFailed(AEnvironTimer timer, Exception exception) {
		for (ITimerListener event : events)
			try {
				event.RunFailed(timer, exception);
			} catch (Exception e) {
			}
	}

	protected void CallExecutionFinished(GridEnvironResult result) {
		for (ITimerListener event : events)
			try {
				event.ExecutionFinished(result);
			} catch (Exception e) {
			}
	}

	/* *******************
	 * Abstract*******************
	 */

	/**
	 * override this for the function to loop through steps
	 */
	protected abstract void StartExecution() throws Exception;

	/**
	 * override this for the function to run a single step
	 */
	protected abstract void StepExecution() throws Exception;

	public abstract long getTimeLeft();
	public abstract long getMaxTime();
}
