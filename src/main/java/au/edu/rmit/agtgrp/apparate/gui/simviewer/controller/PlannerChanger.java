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

import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

import au.edu.rmit.agtgrp.apparate.gui.simviewer.model.GridEnviron;


/**
 * This manages the changing of planner spawner
 * 
 * @author Andy Heng Xie
 *
 */
public class PlannerChanger implements ItemListener {

	private GridEnviron environ; // the environment to change to
	
	/* *******************
	 * Constructor
	 *********************/
	
	public PlannerChanger(GridEnviron environ) {
		this.environ = environ;
	}
	

	/* *******************
	 * Events
	 *********************/
	
	@Override
	public void itemStateChanged(ItemEvent arg0) {
		//PlannerSelector selector = PlannerSelector.getSelectorByDescription(arg0.getItem().toString());
		/*
		if (selector == null) selector = PlannerSelector.BASEASTAR;
		environ.setPlannerSpawner(selector.Spawner);
		*/
	}

}
