package au.edu.rmit.agtgrp.apparate.gui.simviewer.model.script;

import au.edu.rmit.agtgrp.apparate.gui.simviewer.model.listener.IStepEvent;

/**
 * Indicate an event which is valid for a repeated serial of steps
 * 
 * @author Andy Heng Xie
 *
 */
public class RepeatRangeStepEvent implements IStepEvent {

	private final int cycle, activelen, offset, activefrom, activefor; // length of the repeate cycle, the len of time activated, the offset of step, when it start to be active and for how long
	

	/* *******************
	 * Constructor
	 *********************/
	
	/**
	 * This indicates a repeating events where you would begin with a series of inactive steps follow by a series of active steps
	 * @param inactivelen how many steps not active
	 * @param activelen then now many steps active
	 * @param offset how many steps offset
	 * @param activefrom from which step it will in effect
	 * @param activefor how many steps which this will be in effect for
	 */
	public RepeatRangeStepEvent(int inactivelen, int activelen, int offset, int activefrom, int activefor) {
		if (inactivelen < 1) inactivelen = 1;
		if (activelen < 1) activelen = 1;
		if (activefor < 0) activefor = 0;
		
		this.cycle = inactivelen + activelen;
		this.activelen = activelen;
		this.offset = ((offset % (cycle)) + cycle) % cycle;
		this.activefrom = activefrom;
		this.activefor = activefor;
		
	}
	

	/* *******************
	 * Events
	 *********************/
	
	@Override
	public boolean isStep(int step) {
		if (!isWithinRange(step)) return false;
		return isWithinRepeat(step);
	}


	/* *******************
	 * Utility
	 *********************/
	
	/**
	 * is that step within the range given
	 * @param step
	 * @return
	 */
	private boolean isWithinRange(int step) {
		return step >= activefrom && (step < (activefrom + activefor) || activefor == 0);
	}
	
	/**
	 * check if this step is one of the repeated steps
	 * @param step
	 * @return
	 */
	private boolean isWithinRepeat(int step) {
		return ((((cycle - step + offset) % cycle) + cycle) % cycle) < activelen;
		
	}
	

	/* *******************
	 * Override
	 *********************/
	
	public int hashCode() {
		int hash = offset % 8 + (activelen % 8) * 8;
		hash = hash * 32 + cycle % 32;
		hash = hash * 1024 + activefrom % 1024;
		return hash * 1024 + activefor % 1024;
	}
	
	public boolean equals(Object obj) {
		if (super.equals(obj)) return true;
		if (obj instanceof RepeatRangeStepEvent) {
			if (((RepeatRangeStepEvent) obj).cycle != this.cycle) return false;
			if (((RepeatRangeStepEvent) obj).activelen != this.activelen) return false;
			if (((RepeatRangeStepEvent) obj).offset != this.offset) return false;
			if (((RepeatRangeStepEvent) obj).activefrom != this.activefrom) return false;
			if (((RepeatRangeStepEvent) obj).activefor != this.activefor) return false;
			return true;
		}
		return false;
	}

}
