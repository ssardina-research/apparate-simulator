package au.edu.rmit.agtgrp.apparate.gui.simviewer.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import au.edu.rmit.agtgrp.apparate.gui.interfaces.PlanningAgent;


public class SetPlannerFromExternal implements ActionListener {

	public SetPlannerFromExternal() { }

	public SetPlannerFromExternal(Integer agentId) {
		PlanningAgent externalspawner = Launcher.getExternalSpawner();
		if (externalspawner != null) Launcher.getRunEnviron().getGridEnviron().setPlannerSpawner(externalspawner);
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		PlanningAgent externalspawner = Launcher.getExternalSpawner();
		if (externalspawner != null) Launcher.getRunEnviron().getGridEnviron().setPlannerSpawner(externalspawner);
	}

}
