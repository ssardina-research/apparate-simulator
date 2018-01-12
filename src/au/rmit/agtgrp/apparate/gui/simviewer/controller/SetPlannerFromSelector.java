package au.rmit.agtgrp.apparate.gui.simviewer.controller;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JOptionPane;


public class SetPlannerFromSelector implements ActionListener {
	private Component parent; // the parent component which is calling this;
	
	public SetPlannerFromSelector(Component parent) {
		this.parent = parent;
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		/*
		PlannerSelector selector = null;
		String inputval = JOptionPane.showInputDialog(parent, "Input the planner's commandline name: ");
		while ((selector = PlannerSelector.getSelectorByCLIValue(inputval)) == null) {
			inputval = JOptionPane.showInputDialog(parent, "Previous planner was not found, Input the planner's commandline name: ");
		}
		Launcher.getRunEnviron().getGridEnviron().setPlannerSpawner(selector.Spawner);
		*/
	}
	
}