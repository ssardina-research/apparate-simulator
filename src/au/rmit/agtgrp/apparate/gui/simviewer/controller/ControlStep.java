package au.rmit.agtgrp.apparate.gui.simviewer.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import au.rmit.agtgrp.apparate.gui.simviewer.model.timer.ThreadedTimer;


/**
 * Enables to step the environment execution
 * 
 * @author Andy Heng Xie
 *
 */

public class ControlStep implements ActionListener {


	/* *******************
	 * Constructor
	 *********************/
	
	public ControlStep() {
	}
	

	/* *******************
	 * Events
	 *********************/

	/**
	 * Pause the run when uncheck, resume when checked
	 */

	@Override
	public void actionPerformed(ActionEvent arg0) {
		try {
			(new ThreadedTimer(Launcher.getRunEnviron().getTimer())).Step();
		} catch (Exception e) { }
	}
}
	