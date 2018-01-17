/**
 * A Library of Path Planning Algorithms
 *
 * Copyright (C) 2010 Abhijeet Anand and Sebastian Sardina, School of CS and IT, RMIT University, Melbourne VIC 3000.
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
package au.edu.rmit.agtgrp.apparate.gui.simviewer.model;

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
