/**
 * A Library of Path Planning Algorithms
 *
 * Copyright (C) 2010 Andy Xie, Abhijeet Anand and Sebastian Sardina
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
package au.edu.rmit.agtgrp.apparate.gui.simviewer.model;

/**
 * the coordinate on the grid
 * 
 * @author Andy Xie
 *
 */
public class GridCoord {

	private final int x, y; // x and y coordinate
	
	/* *******************
	 * Constructor
	 *********************/
	
	/**
	 * 
	 * @param x the x coordinate
	 * @param y the y coordinate
	 */
	public GridCoord(int x, int y) {
		this.x = x;
		this.y = y;
	}
	
	/* *******************
	 * Accessors
	 *********************/
	
	/**
	 * @return the x coordinate
	 */
	public int getX() {
		return x;
	}
	
	/**
	 * @return the y coordinate
	 */
	public int getY() {
		return y;
	}
	
	/* *******************
	 * Utility
	 *********************/
	
	/**
	 * get the manhatten distance between this point and the given coordinate
	 * @param other the coordinate to find the distance to
	 * @return the manhatten distance between these coordinates
	 */
    public float getManhattenDistance(GridCoord other) {
    	return (Math.abs(this.x - other.x) + Math.abs(this.y - other.y));
    }
    
    /**
	 * get the euclidean distance between this point and the given coordinate
     * @param other the coordinate to find the distance to
     * @return the euclidean distance between these coordinates
     */
    public float getEuclideanDistance(GridCoord other) {
    	return (float) Math.sqrt(Math.pow(this.x - other.x, 2) + Math.pow(this.y - other.y, 2));
    }
	
	/* *******************
	 * Override
	 *********************/
    
	public boolean equals(Object obj) {
		if (super.equals(obj)) return true;
		if (!(obj instanceof GridCoord)) return false;
		GridCoord casted = (GridCoord) obj;
		return casted.x == this.x && casted.y == this.y;
	}
	
	public GridCoord clone() {
		return new GridCoord(this.x, this.y);
	}
	
	public String toString() {
		return "<" + this.x + ", " + this.y + ">";
	}
	
	public int hashCode() {
		return x % 32768 + ((y % 32768) * 32768);
	}
}
