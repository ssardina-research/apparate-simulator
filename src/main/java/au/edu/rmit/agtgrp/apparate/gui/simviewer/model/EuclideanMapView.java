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
 * All of them will evaluated the grid domain to a Euclidean grid map
 * Thus diagonal travel is valid
 * 
 * @author Andy Xie
 *
 */
public class EuclideanMapView implements ICellEval, ICostCalc, IHeuristicCalc {

	/* *******************
	 * Events
	 *********************/
	
	/**
	 * get the heuristic between the two cell
	 */
	@Override
	public float getHCost(GridCell sNode, GridCell tNode, GridDomain nodemap) {
		return (sNode.getCoord().getEuclideanDistance(tNode.getCoord()) * nodemap.getMinCost());
	}

	/**
	 * get the actual cost between the two node.
	 * Only valid if the two node is consider a valid edge
	 */
	@Override
	public float getCost(GridCell sNode, GridCell tNode, GridDomain nodemap) {
		if (!this.isValidEdge(sNode, tNode, nodemap)) return Float.POSITIVE_INFINITY;
		return tNode.getCellCost();
	}

	/**
	 * checks if the cell is blocked if travel by foot
	 */
	@Override
	public boolean isBlocked(GridCell cell) {
		return !cell.getCellType().traversable;
	}

	/**
	 * list edges of all 8 surrounding cell 
	 */
	@Override
	public ArrayList<GridCell> getViableSuccessors(GridCell sNode,
			GridDomain nodemap) {
		GridCoord nodeloc = sNode.getCoord();
		GridCell curnode;
		ArrayList<GridCell> newlist = new ArrayList<GridCell>();
		curnode = nodemap.getCell(nodeloc.getX() - 1, nodeloc.getY() - 1);
		if (curnode != null) newlist.add(curnode);
		curnode = nodemap.getCell(nodeloc.getX() - 1, nodeloc.getY());
		if (curnode != null) newlist.add(curnode);
		curnode = nodemap.getCell(nodeloc.getX() - 1, nodeloc.getY() + 1);
		if (curnode != null) newlist.add(curnode);
		curnode = nodemap.getCell(nodeloc.getX(), nodeloc.getY() - 1);
		if (curnode != null) newlist.add(curnode);
		curnode = nodemap.getCell(nodeloc.getX(), nodeloc.getY() + 1);
		if (curnode != null) newlist.add(curnode);
		curnode = nodemap.getCell(nodeloc.getX() + 1, nodeloc.getY() - 1);
		if (curnode != null) newlist.add(curnode);
		curnode = nodemap.getCell(nodeloc.getX() + 1, nodeloc.getY());
		if (curnode != null) newlist.add(curnode);
		curnode = nodemap.getCell(nodeloc.getX() + 1, nodeloc.getY() + 1);
		if (curnode != null) newlist.add(curnode);
		return newlist;
	}

	
	public int getMaxSuccessors() {
		return 8;
	}
	
	public GridCell getNextSuccessor(int _currentIndex, GridCell parent, GridDomain dom) {
		int  _actions[][] = { {0,-1}, {1,1}, {1,-1}, {-1,-1}, {-1,1}, {1,0}, {0,1}, {-1,0} };
		
		return dom.getCell(parent.getCoord().getX() + _actions[_currentIndex][0], parent.getCoord().getY() + _actions[_currentIndex][1]);
		
}	
		
	
	/**
	 * list all 8 surround edges which points to the given cell
	 */
	@Override
	public ArrayList<GridCell> getViablePredecessors(GridCell tNode,
			GridDomain nodemap) {
		return getViableSuccessors(tNode, nodemap);
	}

	/* *******************
	 * Utility
	 *********************/
	
	/**
	 * checks that the two cells are adjacent either by edge or corner
	 */
	@Override
	public boolean isValidEdge(GridCell sNode, GridCell tNode, GridDomain nodemap) {
		if (!(sNode.getCellType().traversable && tNode.getCellType().traversable)) return false;
		GridCoord sCoord = sNode.getCoord(), tCoord = tNode.getCoord();
		int xdiff = Math.abs(sCoord.getX() - tCoord.getX()), ydiff =  Math.abs(sCoord.getY() - tCoord.getY());
		return !(xdiff == 0 && ydiff == 0 || xdiff > 1 || ydiff > 1);
	}

}
