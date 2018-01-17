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
