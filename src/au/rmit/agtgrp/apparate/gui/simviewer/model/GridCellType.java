package au.rmit.agtgrp.apparate.gui.simviewer.model;

/**
 * the list of cell type used 
 * @author Andy Xie
 *
 */
public enum GridCellType {
	/**
	 * default type, a simulated null
	 */
	VOID(false, false, false, Float.POSITIVE_INFINITY, "Void"),
	/**
	 * normal ground, travel by everything except sailing
	 */
	GROUND(MapData.GROUND_TRAVERSABLE, false, true, MapData.GROUND_COST, "Ground"),
	/**
	 * our of bound region, cannot be traversed by foot, sail or air
	 */
	OUTOFBOUND(false, false, false, Float.POSITIVE_INFINITY, "Out of Bound"),
	/**
	 * a tree, only can be flown over
	 */
	TREE(MapData.TREE_TRAVERSABLE, false, true, MapData.TREE_COST, "Tree"),
	/**
	 * swamp, can be traversed by all mode
	 */
	SWAMP(MapData.SWAMP_TRAVERSABLE, true, true, MapData.SWAMP_COST, "Swamp"),
	/**
	 * water, cannot be traversed by foot
	 */
	WATER(MapData.WATER_TRAVERSABLE, true, true, MapData.WATER_COST, "Water");
	
	
	public final boolean traversable, sailable, flyable; // is it traversable by foot, sail or air?
	public final String name; // name used to describe this type
	public final float cost; // cost of being at this cell
	
	/* *******************
	 * Constructor
	 *********************/
	
	GridCellType(boolean traversable, boolean sailable, boolean flyable, float cost, String name) {
		this.traversable = traversable;
		this.sailable = sailable;
		this.flyable = flyable;
		this.cost = cost;
		this.name = name;
	}
	
	/* *******************
	 * Override
	 *********************/
	
	public final String toString() {
		return "[" + name + "]";
	}
	
	/* *******************
	 * Events
	 *********************/
	
	public static final GridCellType getDefault() {
		return VOID;
	}


}
