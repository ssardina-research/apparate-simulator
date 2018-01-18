/*
 * APPARATE - Path Planner Framework and Simulator in Java
 *
 * Copyright (C) 2010-2018 Andy Xie, Abhijeet Anand and Sebastian Sardina
 * School of CS and IT, RMIT University, Melbourne VIC 3000.
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

package au.edu.rmit.agtgrp.apparate.gui.simviewer.controller;

import java.util.Map;

import au.edu.rmit.agtgrp.apparate.gui.simviewer.model.GridEnviron;
import au.edu.rmit.agtgrp.apparate.gui.simviewer.model.listener.ITimerListener;
import au.edu.rmit.agtgrp.apparate.gui.simviewer.model.timer.AEnvironTimer;
import au.edu.rmit.agtgrp.apparate.gui.simviewer.model.timer.GridEnvironResult;

/**
 * This will print the result of the run after an execution
 * Will be used for the CLI
 * 
 * @author Andy Heng Xie
 *
 */
public class CLIResultPrinter implements ITimerListener {


	/* *******************
	 * Constructor
	 *********************/
	
	public CLIResultPrinter() { }
	

	/* *******************
	 * Event
	 *********************/
	
	@Override
	public void ExecutionFinished(GridEnvironResult result) {
		// TODO Auto-generated method stub

	}

	@Override
	public void RunStart(AEnvironTimer timer) {
		// TODO Auto-generated method stub

	}

	@Override
	public void RunFinished(AEnvironTimer timer) {
		// On a successful run, printout the statistics
		
		// check if the timer is a threaded timer. If so, attempt to use the inner timer
		GridEnviron currentEnvironment = timer.getEnvironment();
		
		// Calculate total time and total cost going through each step that was saved
		Map<Integer, Long> runtime = currentEnvironment.getRunTime(); // get table of runtime
		Map<Integer, Double> runcost = currentEnvironment.getRunCost(); // get table of cost
		long totaltime = 0;
		double totalcost = 0;
		for (Double stepcost: runcost.values()) totalcost += stepcost;
		for (Long steptime: runtime.values()) totaltime += steptime;
		// OBS: totaltime = currentEnvironment.getTimeSoFar()

		if (currentEnvironment.getStartPosition().equals(currentEnvironment.getGoalPosition())) 
			reportMessage("Path completed successfully.\n");
		else 
			reportMessage("Path completed unsuccessfully.\n");
		
		reportMessage("Total steps taken: " + currentEnvironment.getStep() + "\n");
		reportMessage("Total cost of travel: " + totalcost + "\n");
		reportMessage("Total time taken: " + totaltime + " nsec - " + totaltime/1000000L + " ms - " + totaltime/1000000000L + " sec \n");

		
	}

	private void reportMessage(String message) {
		System.err.print(message);
	}
	
	@Override
	public void RunPaused(AEnvironTimer timer) {
	}


	@Override
	public void RunFailed(AEnvironTimer timer, Exception e) {
		// On occasion that the planner crashed or ran too long, the stack track would be printeed out
		reportMessage("Path Planner was unable to navigate to the destination\n");
		e.printStackTrace(System.out);
		
		// TODO: This exit needs to be fixed to only when in non-quiet mode
		//Thread.sleep(5000);
		System.exit(0);
	}

}
