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

import au.edu.rmit.agtgrp.apparate.jpathplan.entites.State;


/**
 * @author Abhijeet Anand (<a href="mailto:abhijeet.anand@rmit.edu.au">abhijeet
 *         [dot] anand [at] rmit [dot] edu [dot] au</a>)
 */
public class GridCell extends State {
	/**
	 * The coordinate of this cell
	 */
	private final GridCoord coord;
	/**
	 * The parent map of this cell
	 */
	private final GridDomain parent;
	/**
	 * The type of cell this is
	 */
	private GridCellType celltype;

	/**
	 * The type of cell this is
	 */
	private float cellCost;
	/*
	 * =======================================================================*
	 * ----------------------------- INNER CLASS -----------------------------*
	 * =======================================================================*
	 */

	/*
	 * =======================================================================*
	 * ----------------------------- CONSTRUCTORS ----------------------------*
	 * =======================================================================*
	 */
	
	/**
	 * Create grid cell using default Grid Cell Type
	 * @param x the x coordinate of the cell
	 * @param y the y coordinate of the cell
	 * @param parent the grid domain holding this cell
	 */
	protected GridCell(int x, int y, GridDomain parent) {
		coord = new GridCoord(x, y);
		this.parent = parent;
		celltype = GridCellType.getDefault();
		cellCost = celltype.cost;
	}

	/**
	 * Create grid cell with the given cell type
	 * @param x the x coordinate of the cell
	 * @param y the y coordinate of the cell
	 * @param parent the grid domain holding this cell
	 * @param defaultterrain the cell type of this cell, use default if null given
	 */
	protected GridCell(int x, int y, GridDomain parent,
			GridCellType defaultterrain) {
		coord = new GridCoord(x, y);
		this.parent = parent;
		celltype = defaultterrain;
		if (celltype == null) celltype = GridCellType.getDefault();
		cellCost = celltype.cost;
	}

	/*
	 * =======================================================================*
	 * ---------------------------- STATIC METHODS ---------------------------*
	 * =======================================================================*
	 */

	/*
	 * =======================================================================*
	 * ---------------------------- PUBLIC METHODS ---------------------------*
	 * =======================================================================*
	 */

	/*
	 * =======================================================================*
	 * --------------------------- ACCESSOR METHODS --------------------------*
	 * =======================================================================*
	 */

	public GridCoord getCoord() {
		return this.coord;
	}

	public GridCellType getCellType() {
		return this.celltype;
	}

	protected GridDomain getParent() {
		return this.parent;
	}

	/*
	 * =======================================================================*
	 * --------------------------- MUTATOR METHODS ---------------------------*
	 * =======================================================================*
	 */

	/**
	 * set the cell type of this cell
	 * @param celltype the cell type to override
	 */
	public void setCellType(GridCellType celltype) {
		if (celltype != null) {
			if (this.celltype != celltype) {
				this.parent.cellToUpdate(this);
				this.celltype = celltype;
				this.parent.cellUpdated(this);
				cellCost = this.celltype.cost;
			}
		}
	}

	/*
	 * =======================================================================*
	 * --------------------- OVERRIDDEN INTERFACE METHODS --------------------*
	 * =======================================================================*
	 */
	/*
	 * (non-Javadoc)
	 * 
	 * @see au.rmit.ract.planning.pathplanning.entity.Node#isBlocked()
	 */
	@Override
	public boolean isBlocked() {
		return this.parent.isBlocked(this);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see au.rmit.ract.planning.pathplanning.entity.Node#parent()
	 */
	@Override
	public State parent() {
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (obj == null) return false;
		if (!(obj instanceof GridCell))
			return false;
		GridCell casted = (GridCell) obj;
		if (!casted.coord.equals(coord))
			return false;
		if (!casted.celltype.equals(celltype))
			return false;
		if (!casted.parent.equals(parent))
			return false;
		return true;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return "{" + celltype.name + " " + coord + "}";
	}

	public int hashCode() {
		return coord.hashCode();
	}

	public float getCellCost() {
		return cellCost;
	}

	protected GridCell clone() {
		return new GridCell(coord.getX(), coord.getY(), parent, celltype);
	}

	/*
	 * =======================================================================*
	 * --------------------------- UTILITY METHODS ---------------------------*
	 * =======================================================================*
	 */

}
