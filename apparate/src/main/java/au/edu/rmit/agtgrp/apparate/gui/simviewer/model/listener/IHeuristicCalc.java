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
