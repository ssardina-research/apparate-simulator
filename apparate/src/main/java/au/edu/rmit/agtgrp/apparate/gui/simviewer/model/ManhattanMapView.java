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
package au.edu.rmit.agtgrp.apparate.gui.simviewer.model;

import java.util.ArrayList;

import au.edu.rmit.agtgrp.apparate.gui.simviewer.model.listener.ICellEval;
import au.edu.rmit.agtgrp.apparate.gui.simviewer.model.listener.ICostCalc;
import au.edu.rmit.agtgrp.apparate.gui.simviewer.model.listener.IHeuristicCalc;

/**
 * A set of functions used by the environment and the grid domain
 * All of them will evaluated the grid domain to a Manhatten grid map
 * Thus only left, right, up, down is valid
 * 
 * @author Andy Xie
 *
 */
public class ManhattanMapView implements ICellEval, ICostCalc, IHeuristicCalc {


	/* *******************
	 * Event
	 *********************/
	
	@Override
	public float getHCost(GridCell sNode, GridCell tNode, GridDomain nodemap) {
		return (sNode.getCoord().getManhattenDistance(tNode.getCoord())*nodemap.getMinCost());
	}

	@Override
	public float getCost(GridCell sNode, GridCell tNode, GridDomain nodemap) {
		if (!this.isValidEdge(sNode, tNode, nodemap)) return Float.POSITIVE_INFINITY;
		return  tNode.getCellCost();
	}

	@Override
	public boolean isBlocked(GridCell cell) {
		return !cell.getCellType().traversable;
	}

	/**
	 * Lists only the edge left, right, above, below of the given cell
	 */
	@Override
	public ArrayList<GridCell> getViableSuccessors(GridCell sNode,
			GridDomain nodemap) {
		GridCoord nodeloc = sNode.getCoord();
		GridCell curnode;
		ArrayList<GridCell> newlist = new ArrayList<GridCell>();
		curnode = nodemap.getCell(nodeloc.getX() - 1, nodeloc.getY());
		if (curnode != null) newlist.add(curnode);
		curnode = nodemap.getCell(nodeloc.getX(), nodeloc.getY() - 1);
		if (curnode != null) newlist.add(curnode);
		curnode = nodemap.getCell(nodeloc.getX(), nodeloc.getY() + 1);
		if (curnode != null) newlist.add(curnode);
		curnode = nodemap.getCell(nodeloc.getX() + 1, nodeloc.getY());
		if (curnode != null) newlist.add(curnode);
		return newlist;
	}
	
	@Override
	public int getMaxSuccessors() {
		return 4;
	}
	
	@Override
	public GridCell getNextSuccessor(int _currentIndex, GridCell parent, GridDomain dom) {
		int  _actions[][] = { {0,-1}, {1,0}, {0,1}, {-1, 0} };
		
		return dom.getCell(parent.getCoord().getX() + _actions[_currentIndex][0], parent.getCoord().getY() + _actions[_currentIndex][1]);
		
}	
		
		

	
	/**
	 * List only the edge left, right, above, below the given cell
	 */
	@Override
	public ArrayList<GridCell> getViablePredecessors(GridCell tNode,
			GridDomain nodemap) {
		return getViableSuccessors(tNode, nodemap);
	}

	/**
	 * Checks of the edge given is a valid edge
	 */
	@Override
	public boolean isValidEdge(GridCell sNode, GridCell tNode, GridDomain nodemap) {
		if (!(sNode.getCellType().traversable && tNode.getCellType().traversable)) return false;
		GridCoord sCoord = sNode.getCoord(), tCoord = tNode.getCoord();
		int xdiff = Math.abs(sCoord.getX() - tCoord.getX()), ydiff =  Math.abs(sCoord.getY() - tCoord.getY());
		return (xdiff + ydiff == 1);
	}

}
