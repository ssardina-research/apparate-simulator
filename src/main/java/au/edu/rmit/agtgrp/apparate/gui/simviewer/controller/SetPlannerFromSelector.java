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