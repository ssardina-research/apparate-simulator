package au.rmit.agtgrp.apparate.gui.simviewer.model;

import au.rmit.agtgrp.apparate.gui.simviewer.model.listener.ICostCalc;
import au.rmit.agtgrp.apparate.gui.simviewer.model.listener.ICellEval;
import au.rmit.agtgrp.apparate.gui.simviewer.model.listener.IHeuristicCalc;

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
