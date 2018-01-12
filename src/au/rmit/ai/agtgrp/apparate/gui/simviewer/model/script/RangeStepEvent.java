package au.rmit.ai.agtgrp.apparate.gui.simviewer.model.script;

import au.rmit.ai.agtgrp.apparate.gui.simviewer.model.listener.IStepEvent;

/**
 * Indicate a event which is valid for a serial of steps
 * 
 * @author Andy Heng Xie
 *
 */
public class RangeStepEvent implements IStepEvent {
	
	private final int startstep, stepamount; // which step to start from and for how many steps
	

	/* *******************
	 * Constructor
	 *********************/
	
	/**
	 * 
	 * @param startstep the starting step
	 * @param stepamount for how many steps, 0 to indicate infinite
	 */
	public RangeStepEvent(int startstep, int stepamount) {
		this.startstep = startstep;
		if (stepamount < 0) this.stepamount = 0;
		else this.stepamount = stepamount;
	}
	

	/* *******************
	 * Accessor
	 *********************/
	
	/**
	 * check if that step is one of its valid steps
	 */
	public boolean isStep(int step){
		return step >= startstep && (step < (startstep + stepamount) || stepamount == 0);
	}
	

	/* *******************
	 * Override
	 *********************/
	
	public int hashCode() {
		return (startstep % 32768 + 32768) % 32768 + (stepamount % 32768) * 32768;
	}
	
	public boolean equals(Object obj) {
		if (super.equals(obj)) return true;
		if (obj instanceof RangeStepEvent) {
			if (((RangeStepEvent) obj).stepamount != this.stepamount) return false;
			if (((RangeStepEvent) obj).startstep != this.startstep) return false;
			return true;
		}
		return false;
	}
}
