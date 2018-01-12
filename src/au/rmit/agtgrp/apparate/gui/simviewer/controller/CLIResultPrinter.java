package au.rmit.agtgrp.apparate.gui.simviewer.controller;

import java.util.Map;

import au.rmit.agtgrp.apparate.gui.simviewer.model.GridEnviron;
import au.rmit.agtgrp.apparate.gui.simviewer.model.listener.ITimerListener;
import au.rmit.agtgrp.apparate.gui.simviewer.model.timer.AEnvironTimer;
import au.rmit.agtgrp.apparate.gui.simviewer.model.timer.GridEnvironResult;

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
