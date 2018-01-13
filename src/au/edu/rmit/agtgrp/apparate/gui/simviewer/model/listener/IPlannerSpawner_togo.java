package au.edu.rmit.agtgrp.apparate.gui.simviewer.model.listener;

import au.edu.rmit.agtgrp.apparate.jpathplan.pathplanners.PathPlanner;

/**
 * The abstraction for a factory to produce new instances of PathPlanners
 * 
 * @author Andy Heng Xie
 *
 */
public interface IPlannerSpawner_togo {
	
	/**
	 * create a new instance of the path planner
	 * @return
	 */
	public PathPlanner createPathPlannerInstance();
}
