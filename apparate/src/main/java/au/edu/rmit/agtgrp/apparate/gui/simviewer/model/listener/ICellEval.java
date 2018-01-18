/**
 * A Library of Path Planning Algorithms
 *
 * Copyright (C) 2010 Andy Xie, Abhijeet Anand and Sebastian Sardina
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

import java.util.ArrayList;

import au.edu.rmit.agtgrp.apparate.gui.simviewer.model.GridCell;
import au.edu.rmit.agtgrp.apparate.gui.simviewer.model.GridDomain;

/**
 * Hows all the function to abstract the interpretation of the map
 * 
 * @author Andy Xie
 *
 */
public interface ICellEval {

	/**
	 * Check if a cell is blocked
	 *
	 * @param cell cell to check
	 * @return true if cell is a block one
	 */
	public boolean isBlocked(GridCell cell);
	
	/**
	 * Returns a list of all possible edge pointing from this cell
     *
	 * @param sNode     the cell to check
	 * @param nodemap   the map to use
	 * @return          list of successor cells fron sNode in nodemap
	 */
	public ArrayList<GridCell> getViableSuccessors(GridCell sNode, GridDomain nodemap);
	
	/**
	 * returns a list of all possible edge point to this cell
	 * @param tNode     the cell to check
	 * @param nodemap   the map to use
	 * @return          list of predecessors cells fronm tNode in nodemap
	 */
	public ArrayList<GridCell> getViablePredecessors(GridCell tNode, GridDomain nodemap);
	
	/**
	 * Checks if the two cell given are a valid edge
     *
	 * @param sNode     one cell
	 * @param tNode     another cell
	 * @param nodemap   the map to use
	 * @return          true if ther is a link in nodemap between sNode and tNode
	 */
	public boolean isValidEdge(GridCell sNode, GridCell tNode, GridDomain nodemap);

	/**
	 * Get the next successor after a current index in a given problem
     *
	 * @param _currentIndex the current index
	 * @param _parent       the parent cell
	 * @param _problem      the problem domain
	 * @return              the next successor
	 */
	public GridCell getNextSuccessor(int _currentIndex, GridCell _parent, GridDomain _problem);

	/**
	 * @return the maximum number of successors
	 */
	public int getMaxSuccessors();	
	
	
}
