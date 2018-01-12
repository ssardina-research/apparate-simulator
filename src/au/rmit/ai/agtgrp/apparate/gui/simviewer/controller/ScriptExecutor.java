package au.rmit.ai.agtgrp.apparate.gui.simviewer.controller;

import au.rmit.ai.agtgrp.apparate.gui.interfaces.PlanningAgent;
import au.rmit.ai.agtgrp.apparate.gui.simviewer.model.listener.IGridEnvironUpdateListener;
import au.rmit.ai.agtgrp.apparate.gui.simviewer.model.GridCell;
import au.rmit.ai.agtgrp.apparate.gui.simviewer.model.GridCoord;
import au.rmit.ai.agtgrp.apparate.gui.simviewer.model.GridEnviron;
import au.rmit.ai.agtgrp.apparate.gui.simviewer.model.GridView;
import au.rmit.ai.agtgrp.apparate.gui.simviewer.model.RunEnviron;

/**
 * Execute the scripted events
 * 
 * @author Andy Heng Xie
 *
 */
public class ScriptExecutor implements IGridEnvironUpdateListener {

	private final RunEnviron environ; // the collection of events
	

	/* *******************
	 * Constructor
	 *********************/
	
	/**
	 * 
	 * @param events the collection of events to run
	 */
	public ScriptExecutor(RunEnviron environ) {
		this.environ = environ;
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
		if (this.environ.getScriptEvents() != null) this.environ.getScriptEvents().runStatusEvents(environ);

	}

	@Override
	public void environmentAfterExecution(GridEnviron environ) {
		if (this.environ.getScriptEvents() != null) this.environ.getScriptEvents().runChangeEvents(environ);
	}

	@Override
	public void arrivedAtGoal(GridEnviron environ) {
		// TODO Auto-generated method stub

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
