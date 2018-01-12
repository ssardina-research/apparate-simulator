package au.rmit.agtgrp.apparate.gui.simviewer.controller;

import au.rmit.agtgrp.apparate.gui.simviewer.model.listener.ITimerListener;
import au.rmit.agtgrp.apparate.gui.simviewer.model.timer.AEnvironTimer;
import au.rmit.agtgrp.apparate.gui.simviewer.model.timer.GridEnvironResult;

/**
 * Update the loop checkbox when the environment stops running
 * 
 * @author Andy Heng Xie
 *
 */
public class RunStateDisplayUpdater implements ITimerListener {

	/* *******************
	 * Constructor
	 *********************/
	
	public RunStateDisplayUpdater() { }
	

	/* *******************
	 * Override
	 *********************/
	
	@Override
	public void ExecutionFinished(GridEnvironResult result) {
		// TODO Auto-generated method stub

	}

	@Override
	public void RunStart(AEnvironTimer timer) {

	}

	@Override
	public void RunFinished(AEnvironTimer timer) {
		// TODO Auto-generated method stub

	}

	@Override
	public void RunPaused(AEnvironTimer timer) {
		Launcher.getAppPane().chk_loop.setSelected(false);
	}
	@Override
	public void RunFailed(AEnvironTimer timer, Exception e) {
		Launcher.getAppPane().chk_loop.setSelected(false);
	}


}
