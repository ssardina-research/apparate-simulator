package au.rmit.ai.agtgrp.apparate.gui.simviewer.model.listener;

import java.util.ArrayList;

import au.rmit.ai.agtgrp.apparate.gui.simviewer.model.GridCell;
import au.rmit.ai.agtgrp.apparate.gui.simviewer.model.GridDomain;

/**
 * Hows all the function to abstract the interpretation of the map
 * 
 * @author Andy Xie
 *
 */
public interface ICellEval {

	/**
	 * evaluate if the cell is blocked
	 * @param cell
	 * @return
	 */
	public boolean isBlocked(GridCell cell);
	
	/**
	 * returns a list of all possible edge pointing from this cell
	 * @param sNode
	 * @param nodemap
	 * @return
	 */
	public ArrayList<GridCell> getViableSuccessors(GridCell sNode, GridDomain nodemap);
	
	/**
	 * returns a list of all possible edge point to this cell
	 * @param tNode
	 * @param nodemap
	 * @return
	 */
	public ArrayList<GridCell> getViablePredecessors(GridCell tNode, GridDomain nodemap);
	
	/**
	 * checks if the two cell given are a valid edge
	 * @param sNode
	 * @param tNode
	 * @param nodemap
	 * @return
	 */
	public boolean isValidEdge(GridCell sNode, GridCell tNode, GridDomain nodemap);

	/**
	 * get the next successor after a current index in a given problem
	 * @param _currentIndex
	 * @param _parent
	 * @param _problem
	 * @return
	 */
	public GridCell getNextSuccessor(int _currentIndex, GridCell _parent, GridDomain _problem);

	/**
	 * @return
	 */
	public int getMaxSuccessors();	
	
	
}
