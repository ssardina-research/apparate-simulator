package au.rmit.agtgrp.apparate.gui.simviewer.controller;

import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

import au.rmit.agtgrp.apparate.gui.simviewer.model.GridEnviron;


/**
 * This manages the changing of planner spawner
 * 
 * @author Andy Heng Xie
 *
 */
public class PlannerChanger implements ItemListener {

	private GridEnviron environ; // the environment to change to
	
	/* *******************
	 * Constructor
	 *********************/
	
	public PlannerChanger(GridEnviron environ) {
		this.environ = environ;
	}
	

	/* *******************
	 * Events
	 *********************/
	
	@Override
	public void itemStateChanged(ItemEvent arg0) {
		//PlannerSelector selector = PlannerSelector.getSelectorByDescription(arg0.getItem().toString());
		/*
		if (selector == null) selector = PlannerSelector.BASEASTAR;
		environ.setPlannerSpawner(selector.Spawner);
		*/
	}

}
