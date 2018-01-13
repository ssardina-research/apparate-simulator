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
