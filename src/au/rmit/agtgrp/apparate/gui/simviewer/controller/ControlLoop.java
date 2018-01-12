package au.rmit.agtgrp.apparate.gui.simviewer.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.AbstractButton;

import au.rmit.agtgrp.apparate.gui.simviewer.model.timer.ThreadedTimer;

/**
 * Enables to start, pause the environment execution
 * 
 * @author Andy Heng Xie
 *
 */
public class ControlLoop implements ActionListener {


	/* *******************
	 * Constructor
	 *********************/
	
	public ControlLoop() { }
	

	/* *******************
	 * Events
	 *********************/

	/**
	 * Pause the run when uncheck, resume when checked
	 */
	@Override
	public void actionPerformed(ActionEvent arg0) {
		try {
			// Start loop when checked, Attempt to pause when unchecked
			if (((AbstractButton) arg0.getSource()).isSelected()) (new ThreadedTimer(Launcher.getRunEnviron().getTimer())).Loop();
			else (new ThreadedTimer(Launcher.getRunEnviron().getTimer())).Pause();
		} catch (Exception e) { }
	}
	

}
