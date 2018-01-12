package au.rmit.ai.agtgrp.apparate.gui.simviewer.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import au.rmit.ai.agtgrp.apparate.gui.simviewer.view.AppContentPane;

/**
 * Reload the map and script when triggered
 * 
 * @author Andy Heng Xie
 *
 */
public class ControlReset implements ActionListener {

	
	private AppContentPane frame; // the frame it will reset the map
	

	/* *******************
	 * Constructor
	 *********************/
	
	/**
	 * 
	 * @param curframe the frame it will try to open the map to
	 */
	public ControlReset(AppContentPane curframe) {
		frame = curframe;
	}

	/* *******************
	 * Events
	 *********************/
	
	@Override
	public void actionPerformed(ActionEvent arg0) {
		LaunchOptions options = (LaunchOptions) Launcher.getLastLauchOptions();
		Launcher.getRunEnviron().getTimer().setForceAbort(true);
		Launcher.getRunEnviron().getTimer().Pause();
		Launcher.setRunEnviron(Launcher.initRunEnviron(options));
		frame.setMap(Launcher.initGUI(Launcher.getRunEnviron()));
	}

}
