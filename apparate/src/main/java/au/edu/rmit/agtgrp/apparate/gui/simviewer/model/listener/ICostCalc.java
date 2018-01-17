package au.edu.rmit.agtgrp.apparate.gui.simviewer.model.listener;

import au.edu.rmit.agtgrp.apparate.gui.simviewer.model.GridCell;
import au.edu.rmit.agtgrp.apparate.gui.simviewer.model.GridDomain;

/**
 * Abstract the cost calculation of the grid domain
 * 
 * @author Andy Xie
 *
 */
public interface ICostCalc {
	
	/**
	 * Get the cost on a valid edge
	 *
	 * @param sNode		the source node
	 * @param tNode		the destination node
	 * @param nodemap	the map to use
	 * @return			cost of edge between sNode and tNode in nodemap map
	 */
	public float getCost(GridCell sNode, GridCell tNode, GridDomain nodemap);

}
