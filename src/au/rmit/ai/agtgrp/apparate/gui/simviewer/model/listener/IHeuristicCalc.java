package au.rmit.ai.agtgrp.apparate.gui.simviewer.model.listener;

import au.rmit.ai.agtgrp.apparate.gui.simviewer.model.GridCell;
import au.rmit.ai.agtgrp.apparate.gui.simviewer.model.GridDomain;

/**
 * Abstracts the heuristic calculation from the grid domain
 * 
 * @author Andy Xie
 *
 */
public interface IHeuristicCalc {

	/**
	 * get the heuristic cost between two point
	 * @param sNode
	 * @param tNode
	 * @param nodemap
	 * @return return positive infinity if invalid nodes given
	 */
	public float getHCost(GridCell sNode, GridCell tNode, GridDomain nodemap);
}
