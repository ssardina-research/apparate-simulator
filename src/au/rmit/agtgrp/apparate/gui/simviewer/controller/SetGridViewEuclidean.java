package au.rmit.agtgrp.apparate.gui.simviewer.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import au.rmit.agtgrp.apparate.gui.simviewer.model.GridView;

public class SetGridViewEuclidean implements ActionListener {
	public SetGridViewEuclidean() { }

	@Override
	public void actionPerformed(ActionEvent arg0) {
		Launcher.getRunEnviron().getGridEnviron().setGridView(GridView.EUCLIDEANGRID);
	}
	
	
}