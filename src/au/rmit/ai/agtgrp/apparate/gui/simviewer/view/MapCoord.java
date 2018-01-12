package au.rmit.ai.agtgrp.apparate.gui.simviewer.view;

/**
 * A coordinate on the map
 * 
 * @author Andy Xie
 *
 */
public class MapCoord {
	public final int x, y; // x, y position
	

	/* *******************
	 * Constructor
	 *********************/
	
	public MapCoord(int x, int y) {
		this.x = x;
		this.y = y;
	}

	/* *******************
	 * Accessor
	 *********************/
	
	public int getX() {
		return this.x;
	}
	
	public int getY() {
		return this.y;
	}
	
	public String toString() {
		return "<" + this.x + ", " + this.y + ">";
	}
}
