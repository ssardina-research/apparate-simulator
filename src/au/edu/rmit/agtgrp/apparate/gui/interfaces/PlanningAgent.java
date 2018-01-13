package au.edu.rmit.agtgrp.apparate.gui.interfaces;

import java.util.ArrayList;

import au.edu.rmit.agtgrp.apparate.gui.simviewer.model.GridCell;
import au.edu.rmit.agtgrp.apparate.gui.simviewer.model.GridDomain;
import au.edu.rmit.agtgrp.apparate.jpathplan.entites.ComputedPlan;


/**
 * Interface to a planning agent
 * 
 * @author nitin
 * 
 */
public interface PlanningAgent {
	/**
	 *  
	 * @param grid
	 * @param sNode
	 * @param tNode
	 * @param stepLeft
	 * @param stepTime
	 * @param timeLeft
	 * @return
	 */ 
	public GridCell getNextMove(GridDomain grid, GridCell sState, GridCell gState, int stepLeft,
                                long stepTime, long timeLeft);
 
	/**
	 * Search algorithms would expand certain nodes while searching for a path.
	 * While some algorithms would explore the whole search space, others would
	 * only expand a limited set of nodes. This method should return the
	 * expanded nodes in the current search iteration.
	 * 
	 * @return an ArrayList of Node which were expanded during the current
	 *         search iteration.
	 */
	public ArrayList<GridCell> expandedNodes();

	/**
	 * Search algorithms would expand certain nodes while searching for a path.
	 * While some algorithms would explore the whole search space, others would
	 * only expand a limited set of nodes. However, not all the nodes in the
	 * search space are expanded though they are prospective frontiers in
	 * search. This method should return those unexpanded nodes in the current
	 * search iteration.
	 * 
	 * @return an ArrayList of Node which were not expanded during the current
	 *         search iteration.
	 */
	public ArrayList<GridCell> unexpandedNodes();
	
	/**
	 * If the agent wants to show expanded/unexpanded nodes
	 * @return
	 */
	public Boolean showInfo();
	public ComputedPlan getPath();
}
