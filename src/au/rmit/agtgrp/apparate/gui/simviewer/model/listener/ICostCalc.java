package au.rmit.agtgrp.apparate.gui.simviewer.model.listener;

import au.rmit.agtgrp.apparate.gui.simviewer.model.GridCell;
import au.rmit.agtgrp.apparate.gui.simviewer.model.GridDomain;

/**
 * Abstract the cost calculation of the grid domain
 * 
 * @author Andy Xie
 *
 */
public interface ICostCalc {
	
	/**
	 * get the cost on a valid edge
	 * @param sNode
	 * @param tNode
	 * @param nodemap
	 * @return
	 */
	public float getCost(GridCell sNode, GridCell tNode, GridDomain nodemap);

}
