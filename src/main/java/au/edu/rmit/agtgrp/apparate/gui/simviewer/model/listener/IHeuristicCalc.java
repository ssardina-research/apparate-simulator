/*
 * APPARATE - Path Planner Framework and Simulator in Java
 *
 * Copyright (C) 2010-2018
 * Andy Xie, Abhijeet Anand and Sebastian Sardina
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
package au.edu.rmit.agtgrp.apparate.gui.simviewer.model.listener;

import au.edu.rmit.agtgrp.apparate.gui.simviewer.model.GridCell;
import au.edu.rmit.agtgrp.apparate.gui.simviewer.model.GridDomain;

/**
 * Abstracts the heuristic calculation from the grid domain
 * 
 * @author Andy Xie
 *
 */
public interface IHeuristicCalc {

	/**
	 * Get the heuristic cost between two point
	 *
	 * @param sNode		the source node
	 * @param tNode		the destination node
	 * @param nodemap	the map to use
	 * @return 			heuristic value between sNode and tNode in nodemap. Positive infinity if invalid nodes given
	 */
	public float getHCost(GridCell sNode, GridCell tNode, GridDomain nodemap);
}
