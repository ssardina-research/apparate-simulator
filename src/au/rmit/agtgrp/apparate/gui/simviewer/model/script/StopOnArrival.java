package au.rmit.agtgrp.apparate.gui.simviewer.model.script;

import au.rmit.agtgrp.apparate.gui.simviewer.model.listener.IGridEnvironUpdateListener;
import au.rmit.agtgrp.apparate.gui.simviewer.model.timer.AEnvironTimer;
import au.rmit.agtgrp.apparate.gui.interfaces.PlanningAgent;
import au.rmit.agtgrp.apparate.gui.simviewer.model.GridCell;
import au.rmit.agtgrp.apparate.gui.simviewer.model.GridCoord;
import au.rmit.agtgrp.apparate.gui.simviewer.model.GridEnviron;
import au.rmit.agtgrp.apparate.gui.simviewer.model.GridView;

/**
 * Pauses the loop execution when it detect the start has arrived at the goal position
 * 
 * @author Andy Heng Xie
 *
 */
public class StopOnArrival implements IGridEnvironUpdateListener {

	private volatile boolean stoponarrival = true; // to pause on arrival
	private final AEnvironTimer timer; // the timer to pause
	

	/* *******************
	 * Constructor
	 *********************/
	
	/**
	 * @param enable to stop on arrival, or not
	 * @param timer the timer to pause
	 */
	public StopOnArrival(boolean enable, AEnvironTimer timer) {
		this.stoponarrival = enable;
		this.timer = timer;
	}
	
	public boolean getDoStop() {
		return stoponarrival;
	}
	
	public void setDoStop(boolean stoponarrival) {
		this.stoponarrival = stoponarrival;
	}
	
	/* *******************
	 * Events
	 *********************/
	
	@Override
	public void startMoved(GridEnviron environ, GridCoord oldpos,
			GridCoord newpos) {
		// TODO Auto-generated method stub

	}

	@Override
	public void goalMoved(GridEnviron environ, GridCoord oldpos,
			GridCoord newpos) {
		// TODO Auto-generated method stub

	}

	@Override
	public void cellUpdated(GridEnviron environ, GridCell newcell) {
		// TODO Auto-generated method stub

	}

	@Override
	public void environmentBeforeExecution(GridEnviron environ) {
		// TODO Auto-generated method stub

	}

	@Override
	public void environmentAfterExecution(GridEnviron environ) {
		// TODO Auto-generated method stub

	}

	@Override
	public void arrivedAtGoal(GridEnviron environ) {
		if (stoponarrival) timer.Pause();
	}

	@Override
	public void spawnerChanged(GridEnviron environ, PlanningAgent oldplanner,
			PlanningAgent newplanner) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void gridviewChanged(GridEnviron environ, GridView oldspawner,
			GridView newspawner) {
		// TODO Auto-generated method stub
		
	}

}
