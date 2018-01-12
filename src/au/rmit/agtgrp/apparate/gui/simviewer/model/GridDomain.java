/**
 * RACT-PAL (RACT Path-Planning Algorithms Library) - A Library of Path Planning
 * Algorithms
 * Copyright (C) 2010 Abhijeet Anand, RACT - RMIT Agent Contest Team, School of
 * Computer Science and Information Technology,
 * RMIT University, Melbourne VIC 3000.
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 * You should have received a copy of the GNU General Public License
 * along with this program. If not, see <http://www.gnu.org/licenses/>.
 */
package au.rmit.agtgrp.apparate.gui.simviewer.model;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import au.rmit.agtgrp.apparate.gui.simviewer.model.listener.ICostCalc;
import au.rmit.ai.agtgrp.apparate.jpathplan.entites.Edge;
import au.rmit.ai.agtgrp.apparate.jpathplan.entites.State;
import au.rmit.ai.agtgrp.apparate.jpathplan.entites.SearchDomain;

import au.rmit.agtgrp.apparate.gui.simviewer.model.listener.ICellEval;
import au.rmit.agtgrp.apparate.gui.simviewer.model.listener.IHeuristicCalc;
/**
 * @author Abhijeet Anand (<a href="mailto:abhijeet.anand@rmit.edu.au">abhijeet
 *         [dot] anand [at] rmit [dot] edu [dot] au</a>)
 */
public class GridDomain extends SearchDomain {
    
    public static int       WIDTH   = 50;   // 8;//18;
    public static int       HEIGHT  = 50;   // 6;//15;
    
    
    
    protected int width, height; // the grid's width and height
    protected float mincost = Float.POSITIVE_INFINITY; // the min cost of the grid
    
    protected GridCell[][] basemap, oldmap; // the current shown map and the original before map change
    
    protected ICostCalc costcalc; // the calculated cost between two coordinate
    protected IHeuristicCalc hcalc; // the calculated heuristic between two coordinate
    protected ICellEval celleval; // the list of function to interpret the grid
    protected Set<GridCoord> changes; // the list of all coordinate which had changed
    protected GridEnviron parent; // the parent environment

    
    /*
     * =======================================================================*
     * ----------------------------- INNER CLASS -----------------------------*
     * =======================================================================*
     */
    
    private class GridEdge extends Edge {
        private final float oldCost, newCost; // the original cost and the current cost of this edge
        private final GridCell start, end; // the from coordinate and the to coordinate
        
    	/* *******************
    	 * Constructor
    	 *********************/
        
        public GridEdge(GridCell start, GridCell end, float oldCost, float newCost) {
            this.oldCost = oldCost;
            this.newCost = newCost;
            this.start = start;
            this.end = end;
        }
        
    	/* *******************
    	 * Override
    	 *********************/
        @Override
        public float cost() {
            return newCost;
        }
        
        /*
         * (non-Javadoc)
         * 
         * @see Edge#getEnd()
         */
        
        public GridCell getEnd() {
            return end;
        }
        
        /*
         * (non-Javadoc)
         * 
         * @see Edge#getStart()
         */
        @Override
        public GridCell getStart() {
            return start;
        }
        
        /*
         * (non-Javadoc)
         * 
         * @see Edge#oldCost()
         */
        @Override
        public float oldCost() {
            return oldCost;
        }
        
        /*
         * (non-Javadoc)
         * @see java.lang.Object#hashCode()
         */
        public int hashCode() {
        	int newhash = start.getCoord().getX() % 128;
        	newhash = newhash * 128 + start.getCoord().getY() % 128;
        	newhash = newhash * 128 + end.getCoord().getX() % 128;
        	newhash = newhash * 128 + end.getCoord().getY() % 128;
        	return newhash;
        }
        
        /*
         * (non-Javadoc)
         * @see java.lang.Object#equals(java.lang.Object)
         */
        public boolean equals(Object obj) {
        	if (super.equals(obj)) return true;
        	GridEdge casted = (GridEdge) obj;
        	if (!casted.start.equals(start)) return false;
        	if (!casted.end.equals(end)) return false;
        	return true;
        }
        
    }
    
    /*
     * =======================================================================*
     * ----------------------------- CONSTRUCTORS ----------------------------*
     * =======================================================================*
     */
    
    /**
     * Initiate the grid with the given width and height and also set the cells to the given type
     * @param width
     * @param height
     * @param basetype the cell's default type on construction
     */
    protected GridDomain(int width, int height, GridCellType basetype) {
    	changes = new HashSet<GridCoord>();
    	this.width = width;
    	this.height = height;
    	if (this.width <= 0) this.width = GridDomain.WIDTH;
    	if (this.height <= 0) this.height = GridDomain.HEIGHT;
    	
    	basemap = new GridCell[this.width][this.height];
        oldmap = new GridCell[this.width][this.height];
        
        if (basetype == null) basetype = GridCellType.getDefault();
        
        for (int i = 0; i < this.width; i++) for (int j = 0; j < this.height; j++) {
        	basemap[i][j] = new GridCell(i, j, this, basetype);
        	changes.add(basemap[i][j].getCoord());
        }
        
        // set the minimum cost of the grid
        mincost = calculateMinCostGrid();

    }
    
    /**
     * Initiate the grid with the map data provided
     * @param map the map data
     */
    protected GridDomain(MapData map) {
    	changes = new HashSet<GridCoord>();
    	this.width = map.width;
    	this.height = map.height;
    	if (this.width <= 0) this.width = GridDomain.WIDTH;
    	if (this.height <= 0) this.height = GridDomain.HEIGHT;
    	
    	basemap = new GridCell[this.width][this.height];
        oldmap = new GridCell[this.width][this.height];
        
        
        for (int i = 0; i < map.width; i++) for (int j = 0; j < map.height; j++) {
        	GridCellType curtype = map.celldata[i][j];
        	if (curtype == null) curtype = GridCellType.getDefault();
        	basemap[i][j] = new GridCell(i, j, this, curtype);
        	changes.add(basemap[i][j].getCoord());
        }

        // set the minimum cost of the grid
        mincost = calculateMinCostGrid();
    
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
    
    /**
     * 
     * @param x
     * @param y
     * @return cell at that coordinate, else null if outside of the range
     */
    public GridCell getCell(int x, int y) {
    	if (x < 0 || y < 0) return null;
    	if (x >= basemap.length) return null;
    	if (y >= basemap[x].length) return null;
    	return basemap[x][y];
    }
    
    /**
     * 
     * @param newcoord
     * @return cell at that coordinate or null if outside of the domain's range
     */
    public GridCell getCell(GridCoord newcoord) {
    	return getCell(newcoord.getX(), newcoord.getY());
    }
    
    public int getWidth() {
    	return this.width;
    }
    
    public int getHeight() {
    	return this.height;
    }
    
    public float getMinCost() {
    	return this.mincost;
    }
    
    
    /*
     * =======================================================================*
     * --------------------------- MUTATOR METHODS ---------------------------*
     * =======================================================================*
     */
    
    public void setCostCalculator(ICostCalc costcalc) {
    	this.costcalc = costcalc;
    }
    public void setHeuristicCalculator(IHeuristicCalc hcostcalc) {
    	this.hcalc = hcostcalc;
    }
    public void setCellEvaluator(ICellEval celleval) {
    	this.celleval = celleval;
    }
    
    /**
     * collapse all the changes to the map to the current map
     * This also removed all the changes
     */
    public void applyChanges() {
    	synchronized (changes) {
        	for (GridCoord curcoord: changes) oldmap[curcoord.getX()][curcoord.getY()] = null;
        	changes.clear();
    	}
    }
    
    public void setParent(GridEnviron parent) {
    	this.parent = parent;
    }
    
    /**
     * called by the child cell before they get altered
     * this keeps a record of the original copy of the cell
     * @param updatedcell
     */
    protected void cellToUpdate(GridCell updatedcell) {
    	synchronized (changes) {
	    	if (updatedcell.getParent() != this) return;
	    	changes.add(updatedcell.getCoord());
	    	int x = updatedcell.getCoord().getX();
	    	int y = updatedcell.getCoord().getY();
	    	if (oldmap[x][y] == null) oldmap[x][y] = basemap[x][y].clone();
    	}
    }
    
    /**
     * called by the child cell after they get altered
     * @param updatedcell
     */
    protected void cellUpdated(GridCell updatedcell) {
    	if (parent != null) parent.cellAfterUpdate(updatedcell);
    }
    /*
     * =======================================================================*
     * --------------------- OVERRIDDEN INTERFACE METHODS --------------------*
     * =======================================================================*
     */
    /*
     * (non-Javadoc)
     * 
     * @see
     * SearchDomain#cost(au.edu
     * .rmit.cs.ract.planning.pathplanning.data.Node,
     * au.rmit.ract.planning.pathplanning.entity.Node)
     */
    @Override
    public float cost(State sNode, State tNode) {
    	GridCell start = (GridCell) sNode;
    	GridCell end = (GridCell) tNode;
        
    	if (start.getParent() != this || end.getParent() != this) return Float.POSITIVE_INFINITY;
    	if (celleval.isBlocked(start) || celleval.isBlocked(end)) return Float.POSITIVE_INFINITY;
    	return costcalc.getCost(start, end, this);
    }
    
    /*
     * (non-Javadoc)
     * 
     * @see
     * SearchDomain#hCost(au.rmit.ract.planning.pathplanning
     * .entity.Node, au.rmit.ract.planning.pathplanning.entity.Node)
     */
    @Override
    public float hCost(State sNode, State tNode) {
    	GridCell start = (GridCell) sNode;
    	GridCell end = (GridCell) tNode;
        
    	if (start.getParent() != this || end.getParent() != this) return Float.POSITIVE_INFINITY;
    	if (celleval.isBlocked(start) || celleval.isBlocked(end)) return Float.POSITIVE_INFINITY;
    	return hcalc.getHCost(start, end, this);
    }
    
    /*
     * (non-Javadoc)
     * 
     * @see
     * SearchDomain#getSuccessors
     * (au.rmit.ract.planning.pathplanning.entity.Node)
     */
    @Override
    public ArrayList<State> getSuccessors(State cNode) {
        return new ArrayList<State>(celleval.getViableSuccessors((GridCell) cNode, this));
    }
    
    
    /**
     * returns iterator for the successors of a given node
     * 
     * @param cNode
     * @return
     */
    public SuccessorIterator getNextSuccessor(State cNode) {
    	return new SuccessorIterator(celleval, (GridCell) cNode, this);
    }
    
    /*
     * (non-Javadoc)
     * 
     * @see
     * SearchDomain#getPredecessors
     * (au.rmit.ract.planning.pathplanning.entity.Node)
     */
    @Override
    public ArrayList<State> getPredecessors(State cNode) {
        return new ArrayList<State>(celleval.getViablePredecessors((GridCell) cNode, this));
    }
    
    /*
     * (non-Javadoc)
     * 
     * @see SearchDomain#getChangedEdges()
     */
    @Override
    public ArrayList<Edge> getChangedEdges() {
    	ArrayList<Edge> cEdges = new ArrayList<Edge>();
    	float oldcost, newcost;
    	GridCell thiscell, oldcell;
    	GridCell othercell, oldothercell;
    	
    	for (GridCoord curcoord: changes) if (hasChanged(curcoord.getX(), curcoord.getY())) {
    		thiscell = basemap[curcoord.getX()][curcoord.getY()];
    		oldcell = oldmap[curcoord.getX()][curcoord.getY()];
    		ArrayList<GridCell> successorlist = celleval.getViableSuccessors(thiscell, this);
    		ArrayList<GridCell> predecessorlist = celleval.getViablePredecessors(thiscell, this);

    		for (GridCell curcell: successorlist) {
    			if (curcell == null) continue;    			
    			othercell = basemap[curcell.getCoord().getX()][curcell.getCoord().getY()];
    			oldothercell = oldmap[curcell.getCoord().getX()][curcell.getCoord().getY()];
    			if (oldothercell == null) oldothercell = othercell;
    			oldcost = costcalc.getCost(thiscell, othercell, this);
    			newcost = costcalc.getCost(oldcell, oldothercell, this);
    			if (oldcost != newcost) cEdges.add(new GridEdge(thiscell, othercell, oldcost, newcost));
    		}
    		for (GridCell curcell: predecessorlist) {
    			if (curcell == null) continue;
    			othercell = basemap[curcell.getCoord().getX()][curcell.getCoord().getY()];
    			oldothercell = oldmap[curcell.getCoord().getX()][curcell.getCoord().getY()];
    			if (oldothercell == null) oldothercell = othercell;
    			oldcost = costcalc.getCost(othercell, thiscell, this);
    			newcost = costcalc.getCost(oldothercell, oldcell, this);
    			if (oldcost != newcost) cEdges.add(new GridEdge(othercell, thiscell, oldcost, newcost));
    		} 
    	}
        return cEdges;
    }

	@Override
	public boolean isBlocked(State cNode) {
		return celleval.isBlocked((GridCell) cNode);
	}

	@Override
	public boolean updateCost(Edge edge) {
		// TODO Auto-generated method stub
		return false;
	}
    
    /*
     * =======================================================================*
     * --------------------------- UTILITY METHODS ---------------------------*
     * =======================================================================*
     */
    
    /**
     * checks if the coordinate given is a valid coordinate within this grid
     * @param x
     * @param y
     * @return
     */
    protected boolean isValidLocation(int x, int y) {
    	return !(this.width <= x || this.height <= y || x < 0 || y < 0);
    }
    
    /**
     * checks if the coordinate has been changes since the last collapse change
     * @param x
     * @param y
     * @return
     */
    protected boolean hasChanged(int x, int y) {
    	if (oldmap[x][y] == null || basemap[x][y] == null) return false;
    	return !oldmap[x][y].equals(basemap[x][y]);
    }


	@Override
	public boolean updateCost(State sNode, State tNode, float cost) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void setBlocked(State cNode, boolean status) {
		// TODO Auto-generated method stub
		
	}
    
	protected float calculateMinCostGrid() {
		float min = Float.POSITIVE_INFINITY;
		
		for (GridCellType p : GridCellType.values()) {
			if (p.cost < min) 
				min = p.cost;
		}
		return min;
	}
}
