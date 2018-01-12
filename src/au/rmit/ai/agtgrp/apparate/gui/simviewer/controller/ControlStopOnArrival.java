package au.rmit.ai.agtgrp.apparate.gui.simviewer.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.AbstractButton;

/**
 * Switch whether to stop when agent arrived at destination
 * 
 * @author Andy Heng Xie
 *
 */
public class ControlStopOnArrival implements ActionListener {

	/* *******************
	 * Constructor
	 *********************/
	
	public ControlStopOnArrival() { }
	

	/* *******************
	 * Events
	 *********************/
	
	@Override
	public void actionPerformed(ActionEvent arg0) {
		try {
			Launcher.getRunEnviron().getStopOnArrival().setDoStop(((AbstractButton) arg0.getSource()).isSelected());
		} catch (Exception e) { }
	}

}
