package au.rmit.agtgrp.apparate.gui.simviewer.model.script;

import java.io.File;
import java.io.InputStream;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Scanner;

import au.rmit.agtgrp.apparate.gui.simviewer.model.GridEnviron;
import au.rmit.agtgrp.apparate.gui.simviewer.model.listener.IActionEvent;
import au.rmit.agtgrp.apparate.gui.simviewer.model.listener.IStepEvent;
import au.rmit.agtgrp.apparate.gui.simviewer.model.listener.ITriggerEvent;

/**
 * Controls all event for the grid environment and how it would be executed
 * Also the loading and saving the "*.mapscript" files
 * 
 * @author Andy Heng Xie
 *
 */
public class ScriptEvents {
	
	private static final int MAX_INPUT_LOOKAHEAD = 5; // the maximum number of variable can be accepted for each command
	

	/* *******************
	 * Static Enum
	 *********************/
	
	/**
	 * the type of command
	 * A single command need at least one action and at least one of either step or trigger type
	 * 
	 * @author Andy Heng Xie
	 *
	 */
	private static enum CommandGroup {
		GROUP_STEP,
		GROUP_TRIGGER,
		GROUP_ACTION, 
		TIME_ACTION;
	}
	
	/**
	 * The format of the command input it accepts
	 * 
	 * @author Andy Heng Xie
	 *
	 */
	private static enum CommandInputType {
		INPUT_RANGE,
		INPUT_REPEAT,
		INPUT_POINT,
		INPUT_OFFSET,
		INPUT_BOUND;
	}
	
	/**
	 * List of available commands for scripting
	 * 
	 * @author Andy Heng Xie
	 *
	 */
	private static enum Command {
		
		STEP("STEP", CommandGroup.GROUP_STEP, CommandInputType.INPUT_RANGE, null, null), // a step command to indicate the range of step active
		REPEAT("REPEAT", CommandGroup.GROUP_STEP, CommandInputType.INPUT_REPEAT, null, null), // a repeat step command to indicate which period of steps are active
		FILLOUTOFBOUND("FILLOUTOFBOUND", CommandGroup.GROUP_ACTION, CommandInputType.INPUT_BOUND, ActionType.ACT_FILLOUTOFBOUND, null), // action to fill area with out of bound
		FILLGROUND("FILLGROUND", CommandGroup.GROUP_ACTION, CommandInputType.INPUT_BOUND, ActionType.ACT_FILLGROUND, null), // action to fill area with ground
		FILLSWAMP("FILLSWAMP", CommandGroup.GROUP_ACTION, CommandInputType.INPUT_BOUND, ActionType.ACT_FILLSWAMP, null), // action to fill area with swamp
		FILLWATER("FILLWATER", CommandGroup.GROUP_ACTION, CommandInputType.INPUT_BOUND, ActionType.ACT_FILLWATER, null), // action to fill area with water
		FILLTREE("FILLTREE", CommandGroup.GROUP_ACTION, CommandInputType.INPUT_BOUND, ActionType.ACT_FILLTREE, null), // action to fill area with tree
		SETMAXTIME("SETMAXTIME", CommandGroup.TIME_ACTION, CommandInputType.INPUT_RANGE, ActionType.ACT_SETTIME, null), // action to put the agent at a location
		PUTSTART("PUTSTART", CommandGroup.GROUP_ACTION, CommandInputType.INPUT_POINT, ActionType.ACT_PUTSTART, null), // action to put the agent at a location
		PUTGOAL("PUTGOAL", CommandGroup.GROUP_ACTION, CommandInputType.INPUT_POINT, ActionType.ACT_PUTGOAL, null), // action to put the destination at a location
		PUSHSTART("PUSHSTART", CommandGroup.GROUP_ACTION, CommandInputType.INPUT_OFFSET, ActionType.ACT_PUSHSTART, null), // action to offset the agent
		PUSHGOAL("PUSHGOAL", CommandGroup.GROUP_ACTION, CommandInputType.INPUT_OFFSET, ActionType.ACT_PUSHGOAL, null), // action to offset the destination
		STARTENTER("STARTENTER", CommandGroup.GROUP_TRIGGER, CommandInputType.INPUT_BOUND, null, TriggerType.TRIG_START_ENTER), // triggers when agent enters the area
		STARTON("STARTON", CommandGroup.GROUP_TRIGGER, CommandInputType.INPUT_BOUND, null, TriggerType.TRIG_START_ON), // triggers when agent is in the area
		STARTOFF("STARTOFF", CommandGroup.GROUP_TRIGGER, CommandInputType.INPUT_BOUND, null, TriggerType.TRIG_START_OFF), // triggers when agent is not on the area
		STARTLEAVE("STARTLEAVE", CommandGroup.GROUP_TRIGGER, CommandInputType.INPUT_BOUND, null, TriggerType.TRIG_START_LEAVE), // triggers when agent leaves the area
		GOALENTER("GOALENTER", CommandGroup.GROUP_TRIGGER, CommandInputType.INPUT_BOUND, null, TriggerType.TRIG_GOAL_ENTER), // triggers when destination is enters the area
		GOALON("GOALON", CommandGroup.GROUP_TRIGGER, CommandInputType.INPUT_BOUND, null, TriggerType.TRIG_GOAL_ON), // triggers when the destination is in the area
		GOALOFF("GOALOFF", CommandGroup.GROUP_TRIGGER, CommandInputType.INPUT_BOUND, null, TriggerType.TRIG_GOAL_OFF), // triggers when the destination is not in the area
		GOALLEAVE("GOALLEAVE", CommandGroup.GROUP_TRIGGER, CommandInputType.INPUT_BOUND, null, TriggerType.TRIG_GOAL_LEAVE); // triggers when the destination leaves the area
		
		
		public final String CommandString; // the command name
		public final CommandGroup CommandType; // which command group
		public final CommandInputType InputType; // which input format
		public final ActionType Action; // if it is an action command, this is the related action
		public final TriggerType Trigger; // if it is a trigger command, this is the related trigger
		
		
		private Command(String cmdstr, CommandGroup cmdtype, CommandInputType inputtype, ActionType action, TriggerType trigger) {
			this.CommandString = cmdstr;
			this.CommandType = cmdtype;
			this.InputType = inputtype;
			this.Action = action;
			this.Trigger = trigger;
		}
		

		private static HashMap<String, Command> commandmap = new HashMap<String, Command>(); // the mapping of command name to the command
		
		static {
			for (Command cmd: Command.values()) commandmap.put(cmd.CommandString, cmd);
		}
		
		/**
		 * get the command based on the command name used
		 * 
		 * @param cmdstr the command name
		 * @return
		 */
		public static Command getCommandByCommandString(String cmdstr) {
			return commandmap.get(cmdstr);
		}
	}
	

	/* *******************
	 * Static
	 *********************/
	
	/**
	 * Loads the events from a file
	 * @param file
	 * @return
	 */
	public static ScriptEvents readExternalData(InputStream stream) {
		String readline;
		try {
			ScriptEvents events = new ScriptEvents();
			Scanner reader = new Scanner(stream);
			while (reader.hasNextLine()) {
				readline = reader.nextLine();
				if (readline.trim().startsWith("#")) continue;
				try {
					String[] readtoken;
					int numread, curtoken = 0;
					int[] numbers = new int[MAX_INPUT_LOOKAHEAD];
					
					HashSet<IStepEvent> steplist = new HashSet<IStepEvent>();
					HashSet<ITriggerEvent> triggerlist = new HashSet<ITriggerEvent>();
					HashSet<IActionEvent> actionlist = new HashSet<IActionEvent>();
					
					
					readtoken = readline.split("\\s");
					
					while (curtoken < readtoken.length) {
						String curcommandtoken = readtoken[curtoken++].toUpperCase();
						Command curcommand = Command.getCommandByCommandString(curcommandtoken);
						int sizex = 1, sizey = 1;
						
						if (curcommand == null) throw new Exception("Invalid command type");
						for (numread = 0; numread < MAX_INPUT_LOOKAHEAD; numread++) {
							try {
								numbers[numread] = Integer.parseInt(readtoken[curtoken + numread]);
							} catch (Exception e) {
								break;
							}
						}

						switch (curcommand.CommandType) {
						case GROUP_STEP:
							if (curcommand == Command.STEP) {
								if (numread != 1 && numread != 2) throw new Exception("Invalid arguments amount");
								int steplen = 1;
								if (numread > 1) steplen = numbers[1];
								steplist.add(new RangeStepEvent(numbers[0], steplen));
								curtoken += numread;
							} else if (curcommand == Command.REPEAT) {
								if (numread < 1 && numread > 5) throw new Exception("Invalid arguments amount");
								int[] input = new int[]{1, 1, 0, 0, 0};
								for (int i = 0; i < numread; i++) input[i] = numbers[i];
								steplist.add(new RepeatRangeStepEvent(input[0], input[1], input[2], input[3], input[4]));
								curtoken += numread;
							}
							break;
						case GROUP_TRIGGER:
							if (numread != 2 && numread != 4) throw new Exception("Invalid arguments amount");
							if (numread == 4) {
								sizex = numbers[2];
								sizey = numbers[3];
							}
							triggerlist.add(new TriggerEvent(curcommand.Trigger, numbers[0], numbers[1], sizex, sizey));
							curtoken += numread;
							break;
						case GROUP_ACTION:
							if (numread != 2 && numread != 4) throw new Exception("Invalid arguments amount");
							if (!curcommand.Action.isSelectAction() && numread == 4) throw new Exception("Invalid arguments amount");
							if (numread == 4) {
								sizex = numbers[2];
								sizey = numbers[3];
							}
							if (curcommand.Action.isSelectAction()) {
								actionlist.add(new TerrainActionEvent(curcommand.Action, numbers[0], numbers[1], sizex, sizey));
							} else {
								actionlist.add(new EntityActionEvent(curcommand.Action, numbers[0], numbers[1]));
							}
							curtoken += numread;
							break;
						case TIME_ACTION:
							if (numread != 1) throw new Exception("Invalid arguments amount");
							//if (!curcommand.Action.isSelectAction() && numread == 4) throw new Exception("Invalid arguments amount");
			
							actionlist.add(new EntityActionEvent(curcommand.Action, numbers[0],0));
							
							curtoken += numread;
							break;
						}
					}
					
					if (actionlist.size() <= 0) throw new Exception("No action was specified");
					if (triggerlist.size() + steplist.size() <= 0) throw new Exception("No step or trigger has been specified");
					
					for (IActionEvent action: actionlist) {
						for (IStepEvent step: steplist) events.addEvent(action, step);
						for (ITriggerEvent trigger: triggerlist) events.addEvent(action, trigger);
					}
					
				} catch (Exception e) { }
			}
			return events;
		} catch (Exception e) {
			return null;
		}
	}
	
	private HashMap<IStepEvent, HashSet<IActionEvent>> stepaction = new HashMap<IStepEvent, HashSet<IActionEvent>>(); // the actions for a step event
	private HashMap<ITriggerEvent, HashSet<IActionEvent>> triggeraction = new HashMap<ITriggerEvent, HashSet<IActionEvent>>(); // the actions for a trigger event
	private HashMap<ITriggerEvent, HashSet<IActionEvent>> statustriggeraction = new HashMap<ITriggerEvent, HashSet<IActionEvent>>(); // the actions for a status check type trigger event
	private HashMap<ITriggerEvent, HashSet<IActionEvent>> changetriggeraction = new HashMap<ITriggerEvent, HashSet<IActionEvent>>(); // the actions for a change type trigger event
	
	private HashMap<IActionEvent, HashSet<IStepEvent>> actionsteps = new HashMap<IActionEvent, HashSet<IStepEvent>>(); // the step event of an action
	private HashMap<IActionEvent, HashSet<ITriggerEvent>> actiontriggers = new HashMap<IActionEvent, HashSet<ITriggerEvent>>(); // the trigger event of an action
	
	private HashSet<IActionEvent> actionnostep =  new  HashSet<IActionEvent>(); // list of action with no step event
	private HashSet<IActionEvent> actionnotrigger =  new  HashSet<IActionEvent>(); // list of action with no trigger event
	

	/* *******************
	 * Mutators
	 *********************/
	
	/**
	 * Add an action with a step event
	 * @param event
	 * @param step
	 */
	public void addEvent(IActionEvent event, IStepEvent step) {
		if (event == null || step == null) return;
		HashSet<IStepEvent> steplist = actionsteps.get(event);
		HashSet<ITriggerEvent> triggerlist = actiontriggers.get(event);
		HashSet<IActionEvent> actionlist = stepaction.get(step);
		
		if (actionlist == null) {
			actionlist = new HashSet<IActionEvent>();
			stepaction.put(step, actionlist);
		}
		actionlist.add(event);
		
		if (steplist == null) {
			steplist = new HashSet<IStepEvent>();
			actionsteps.put(event, steplist);
		}
		steplist.add(step);
		
		actionnostep.remove(event);
		if (triggerlist == null) actionnotrigger.add(event);
	}

	/**
	 * add an action with a trigger event
	 * @param event
	 * @param trigger
	 */
	public void addEvent(IActionEvent event, ITriggerEvent trigger) {
		if (event == null || trigger == null) return;
		HashSet<IStepEvent> steplist = actionsteps.get(event);
		HashSet<ITriggerEvent> triggerlist = actiontriggers.get(event);
		HashSet<IActionEvent> actionlist = triggeraction.get(trigger);
		
		if (actionlist == null) {
			actionlist = new HashSet<IActionEvent>();
			triggeraction.put(trigger, actionlist);
			switch (trigger.getTriggerType()) {
				case TRIG_START_ENTER:
				case TRIG_START_LEAVE:
				case TRIG_GOAL_ENTER:
				case TRIG_GOAL_LEAVE:
					changetriggeraction.put(trigger, actionlist);
					break;
				case TRIG_START_ON:
				case TRIG_START_OFF:
				case TRIG_GOAL_ON:
				case TRIG_GOAL_OFF:
					statustriggeraction.put(trigger, actionlist);
					break;
			}
		}
		actionlist.add(event);
		
		if (triggerlist == null) {
			triggerlist = new HashSet<ITriggerEvent>();
			actiontriggers.put(event, triggerlist);
		}
		triggerlist.add(trigger);
		
		actionnotrigger.remove(event);
		if (steplist == null) actionnostep.add(event);
	}
	
	/**
	 * remove an event and all its steps/triggers
	 * @param event
	 */
	public void removeEvent(IActionEvent event) {
		if (event == null) return;
		HashSet<IStepEvent> steplist = actionsteps.remove(event);
		HashSet<ITriggerEvent> triggerlist = actiontriggers.remove(event);
		actionnostep.remove(event);
		actionnotrigger.remove(event);
		for (IStepEvent step: steplist) stepaction.remove(step);
		for (ITriggerEvent trigger: triggerlist) {
			triggeraction.remove(trigger);
			changetriggeraction.remove(trigger);
			statustriggeraction.remove(trigger);
		}
	}
	
	/**
	 * remove the action association with the trigger
	 * @param step
	 */
	public void removeEvent(RangeStepEvent step) {
		HashSet<IActionEvent> stepactionlist = stepaction.remove(step);
		
		for (IActionEvent event: stepactionlist) {
			HashSet<IStepEvent> steplist = actionsteps.get(event);
			steplist.remove(step);
			if (steplist.size() <= 0) {
				actionsteps.remove(event);
				if (actionnotrigger.contains(event)) actionnotrigger.remove(event);
				else actionnostep.add(event);
			}
		}
	}

	/**
	 * remove the action association with the trigger
	 * @param trigger
	 */
	public void removeEvent(TriggerEvent trigger) {
		HashSet<IActionEvent> triggeractionlist = triggeraction.remove(trigger);
		changetriggeraction.remove(trigger);
		statustriggeraction.remove(trigger);
		
		for (IActionEvent event: triggeractionlist) {
			HashSet<ITriggerEvent> triggerlist = actiontriggers.get(event);
			triggerlist.remove(trigger);
			if (triggerlist.size() <= 0) {
				actiontriggers.remove(event);
				if (actionnostep.contains(event)) actionnostep.remove(event);
				else actionnotrigger.add(event);
			}
		}
	}
	
	/**
	 * Run only event which validates with the status of the environment. 
	 * Thus all step events and those with association with status type trigger events
	 * @param environ
	 */
	public void runStatusEvents(GridEnviron environ) {
		HashSet<IActionEvent> validstepactions = new HashSet<IActionEvent>(actionnostep);
		HashSet<IActionEvent> validtriggeractions = new HashSet<IActionEvent>(actionnotrigger);
		
		for (IStepEvent step: stepaction.keySet()) 
			if (step.isStep(environ.getStep())) {
				validstepactions.addAll(stepaction.get(step));
			}
		for (ITriggerEvent trigger: statustriggeraction.keySet()) 
			if (trigger.isTriggered(environ))	
				validtriggeractions.addAll(statustriggeraction.get(trigger));
				
		validstepactions.retainAll(validtriggeractions);
		
		for (IActionEvent event: validstepactions) event.RunAction(environ);
	}
	
	/**
	 * Run only events which monitor changes in the environment. 
	 * Thus all change type trigger events
	 * @param environ
	 */
	public void runChangeEvents(GridEnviron environ) {
		HashSet<IActionEvent> validstepactions = new HashSet<IActionEvent>(actionnostep);
		HashSet<IActionEvent> validtriggeractions = new HashSet<IActionEvent>();
		
		for (IStepEvent step: stepaction.keySet()) 
			if (step.isStep(environ.getStep())) validstepactions.addAll(stepaction.get(step));
		for (ITriggerEvent trigger: changetriggeraction.keySet()) 
			if (trigger.isTriggered(environ)) validtriggeractions.addAll(triggeraction.get(trigger));
				
		validstepactions.retainAll(validtriggeractions);
		
		for (IActionEvent event: validstepactions) event.RunAction(environ);
	}
	
	/**
	 * Saves the script to a file
	 * @param file
	 */
	public void saveData(File file) {
		// TODO: saving
	}
	
}
