package au.rmit.ai.agtgrp.apparate.gui.simviewer.model.script;

/**
 * Predetermined action which can be performed from a script
 * 
 * @author Andy Heng Xie
 *
 */
public enum ActionType {
	ACT_PUTSTART(false), // move the start/source position
	ACT_PUTGOAL(false), // move the goal position
	ACT_PUSHSTART(false), // move the start/source position
	ACT_PUSHGOAL(false), // move the goal position
	ACT_FILLOUTOFBOUND(true), // fill given area with 'out of bound'
	ACT_FILLGROUND(true), // fill given area with 'ground'
	ACT_FILLSWAMP(true), // fill given area with 'swamp'
	ACT_FILLWATER(true), // fill given area with 'water'
	ACT_FILLTREE(true), // fill given area with 'tree'
	ACT_SETTIME(false); // set the max time
	
	
	private final boolean isselect; // is it an area action?
	

	/* *******************
	 * Constructor
	 *********************/
	
	private ActionType(boolean isselectaction) {
		this.isselect = isselectaction;
	}
	

	/* *******************
	 * Accessor
	 *********************/
	
	public boolean isSelectAction() {
		return this.isselect;
	}
}
