package au.edu.rmit.agtgrp.apparate.gui.simviewer.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileFilter;

import au.edu.rmit.agtgrp.apparate.gui.simviewer.model.script.ScriptEvents;
import au.edu.rmit.agtgrp.apparate.gui.simviewer.view.AppContentPane;

/**
 * Attempt to open a new script when triggered
 * 
 * @author Andy Heng Xie
 *
 */
public class OpenScript implements ActionListener {

	
	private AppContentPane frame; // the frame it will open the new map to
	

	/* *******************
	 * Constructor
	 *********************/
	
	/**
	 * 
	 * @param curframe the frame it will try to open the scrip
	 */
	public OpenScript(AppContentPane curframe) {
		frame = curframe;
	}
	

	/* *******************
	 * Events
	 *********************/
	
	@Override
	public void actionPerformed(ActionEvent e) {
		// select map script from directory
		JFileChooser chooser = new JFileChooser();
		chooser.setFileFilter(new FileFilter() {
			@Override
			public boolean accept(File arg0) {
				if (arg0.isDirectory()) return true;
				return arg0.getName().toLowerCase().endsWith(".mapscript");
			}

			@Override
			public String getDescription() {
				return "Map Script";
			}
		});
		
		if (chooser.showOpenDialog(null) != JFileChooser.APPROVE_OPTION) return;
		
		String fileloc = chooser.getSelectedFile().getAbsolutePath();
		ScriptEvents events = null;
		LaunchOptions options = (LaunchOptions) Launcher.getInitLauchOptions().clone();
		try {
			options.scriptfile = (new File(fileloc)).toURI().toURL();
			events = ScriptEvents.readExternalData(options.scriptfile.openStream());
		} catch (Exception exception) { }
		if (events ==  null) JOptionPane.showConfirmDialog(frame, "Unable to load script. Please check syntax and try again.");
		else {
			Launcher.setLastLaunchOption(options);
			Launcher.getRunEnviron().setScriptEvents(events);
		}
	}


}
