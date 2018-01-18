/*
 * APPARATE - Path Planner Framework and Simulator in Java
 *
 * Copyright (C) 2010-2018 Andy Xie, Abhijeet Anand and Sebastian Sardina
 * School of CS and IT, RMIT University, Melbourne VIC 3000.
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program. If not, see <http://www.gnu.org/licenses/>.
 */

package au.edu.rmit.agtgrp.apparate.gui.simviewer.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import au.edu.rmit.agtgrp.apparate.gui.simviewer.view.AppContentPane;

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
