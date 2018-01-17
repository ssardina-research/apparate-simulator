package au.edu.rmit.agtgrp.apparate.gui.simviewer.model.script;
/**
 * A list of all trigger type
 * 
 * @author Andy Heng Xie
 *
 */
public enum TriggerType {
	TRIG_START_ENTER, // when the start position enters the monitoring area
	TRIG_START_LEAVE, // when the start position leaves the monitoring area
	TRIG_START_ON, // when the start position is in the monitoring area
	TRIG_START_OFF, // when the start position is not in the monitoring area
	TRIG_GOAL_ENTER, // when the goal position enters the monitoring area
	TRIG_GOAL_LEAVE, // when the goal position leaves the monitoring area
	TRIG_GOAL_ON, // when the goal position is in the monitoring area
	TRIG_GOAL_OFF; // when the goal position is not in the monitoring area
}
