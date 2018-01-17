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
	 *  Main method of a planninga agent that yields the next cell to move to.
     *
	 * @param grid	    The current grid
	 * @param sState    The current location of the agent
	 * @param gState    The goal location
	 * @param stepLeft  How many steps are left in the simulation
	 * @param stepTime  How much time is allowed per step
	 * @param timeLeft  How much time is left in the simulatoin
	 * @return          The cell to move to
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
	 * Returns whether the agent wants to show information on expanded/unexpanded nodes
     *
	 * @return True if the agent is showing the information on expanded nodes
	 */
	public Boolean showInfo();

    /**
     * Return the current stored plan
     *
     * @return A precomputed path
     */
	public ComputedPlan getPath();
}
