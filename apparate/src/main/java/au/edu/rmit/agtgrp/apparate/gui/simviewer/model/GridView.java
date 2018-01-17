/**
 * A Library of Path Planning Algorithms
 *
 * Copyright (C) 2010 Abhijeet Anand and Sebastian Sardina, School of CS and IT, RMIT University, Melbourne VIC 3000.
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

import au.edu.rmit.agtgrp.apparate.gui.simviewer.model.listener.ICostCalc;
import au.edu.rmit.agtgrp.apparate.gui.simviewer.model.listener.ICellEval;
import au.edu.rmit.agtgrp.apparate.gui.simviewer.model.listener.IHeuristicCalc;

public enum GridView {
	MANHATTENGRID(new ManhattanMapView(), new ManhattanMapView(), new ManhattanMapView()),
	EUCLIDEANGRID(new EuclideanMapView(), new EuclideanMapView(), new EuclideanMapView());
	

	private final ICostCalc costcalc; // the edge cost calculation
	private final IHeuristicCalc hcostcalc; // the heuristic cost calculaion
	private final ICellEval celleval; // the cell evaluation methods
	

	/* *******************
	 * Constructor
	 *********************/
	
	private GridView(ICostCalc costcalc, IHeuristicCalc hcostcalc, ICellEval celleval) {
		this.costcalc = costcalc;
		this.hcostcalc = hcostcalc;
		this.celleval = celleval;
		
	}
	

	/* *******************
	 * Accessor
	 *********************/
	
	public ICostCalc getCostCalculator() {
		return this.costcalc;
	}
	
	public IHeuristicCalc getHeuristicCalculator() {
		return this.hcostcalc;
	}
	
	public ICellEval getCellEvaluator() {
		return this.celleval;
	}

}
