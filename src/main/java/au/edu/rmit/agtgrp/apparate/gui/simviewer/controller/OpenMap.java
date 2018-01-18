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
import java.io.File;

import javax.swing.JFileChooser;
import javax.swing.filechooser.FileFilter;

import au.edu.rmit.agtgrp.apparate.gui.simviewer.view.AppContentPane;

/**
 * Attempt to open a new map when triggered
 * 
 * @author Andy Heng Xie
 *
 */
public class OpenMap implements ActionListener {

	
	private AppContentPane frame; // the frame it will open the new map to
	

	/* *******************
	 * Constructor
	 *********************/
	
	/**
	 * 
	 * @param curframe the frame it will try to open the map to
	 */
	public OpenMap(AppContentPane curframe) {
		frame = curframe;
	}
	

	/* *******************
	 * Events
	 *********************/
	
	@Override
	public void actionPerformed(ActionEvent e) {
		// select a file from directory
		JFileChooser chooser = new JFileChooser();
		chooser.setFileFilter(new FileFilter() {
			@Override
			public boolean accept(File arg0) {
				if (arg0.isDirectory()) return true;
				return arg0.getName().toLowerCase().endsWith(".map");
			}

			@Override
			public String getDescription() {
				return "Maps";
			}
		});
		
		if (chooser.showOpenDialog(null) != JFileChooser.APPROVE_OPTION) return;
		
		// loading it using the original launch options as the default
		String fileloc = chooser.getSelectedFile().getAbsolutePath();
		LaunchOptions options = (LaunchOptions) Launcher.getInitLauchOptions().clone();
		try {
			options.mapfile = (new File(fileloc)).toURI().toURL();
			options.scriptfile = null;
			Launcher.getRunEnviron().getTimer().setForceAbort(true);
			Launcher.getRunEnviron().getTimer().Pause();
			Launcher.setRunEnviron(Launcher.initRunEnviron(options));
			Launcher.setLastLaunchOption(options);
			frame.setMap(Launcher.initGUI(Launcher.getRunEnviron()));
		} catch (Exception except) { }
	}

}
