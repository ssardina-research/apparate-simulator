package au.rmit.ai.agtgrp.apparate.gui.simviewer.model.timer;

import au.rmit.ai.agtgrp.apparate.gui.simviewer.model.GridEnviron;

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