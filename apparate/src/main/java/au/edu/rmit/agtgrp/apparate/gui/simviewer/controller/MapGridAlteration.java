/*
 * APPARATE - Path Planner Framework and Simulator in Java
 *
 * Copyright (C) 2010-2018 Andy Xie, Abhijeet Anand and Sebastian Sardina
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

package au.edu.rmit.agtgrp.apparate.gui.simviewer.controller;

import java.awt.Point;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.geom.Dimension2D;
import java.util.HashMap;
import java.util.Map;

import au.edu.rmit.agtgrp.apparate.gui.simviewer.model.GridEnviron;
import au.edu.rmit.agtgrp.apparate.gui.simviewer.model.script.ActionType;
import au.edu.rmit.agtgrp.apparate.gui.simviewer.model.script.EntityActionEvent;
import au.edu.rmit.agtgrp.apparate.gui.simviewer.view.MapInstance;
import au.edu.rmit.agtgrp.apparate.gui.simviewer.model.listener.IActionEvent;
import au.edu.rmit.agtgrp.apparate.gui.simviewer.model.script.TerrainActionEvent;
import au.edu.rmit.agtgrp.apparate.gui.simviewer.view.MapCoord;

/**
 * Listens to the map and apply instructed changes to the grid environment
 * 
 * @author Andy Xie
 *
 */
public class MapGridAlteration implements MouseListener, KeyListener, MouseMotionListener {

	/* *******************
	 * Variable
	 *********************/
	
	private final GridEnviron environ; // The environment which this is applying the changes to
	private final MapInstance map; // the map this listener is listening to
	
	private MapCoord lastknownpos, lastknownmousepos, lastposition, lastmouseposition; // the initial click position
	private MouseActionType lastaction; // the action which was selection on first click
	private char lastkey; // the character which was pressed
	

	/* *******************
	 * Enum
	 *********************/
	
	/**
	 * Types of action which can be performed with the mouse
	 * @author Andy Heng Xie
	 *
	 */
	private enum MouseActionType {
		SETGOAL('d', false, "Set Destination", ActionType.ACT_PUTGOAL), // set the destination where it is clicked
		SETSTART('s', false, "Set Source", ActionType.ACT_PUTSTART), // set the agent position where it is clicked
		FILLOOB('q', true, "Fill Out of Bound", ActionType.ACT_FILLOUTOFBOUND), // fill the selected area with out of bound terrain
		FILLWATER('w', true, "Fill Water", ActionType.ACT_FILLWATER), // fill the selected area with water terrain
		FILLGROUND('e', true, "Fill Ground", ActionType.ACT_FILLGROUND), // fill the selected area with ground terrain
		FILLSWAMP('r', true, "Fill Swamp", ActionType.ACT_FILLSWAMP), // fill the selected area with swamp
		FILLTREE('t', true, "Fill Tree", ActionType.ACT_FILLTREE), // fill the selected area with tree
		ZOOMIN('z', false, "Zooming in", null), // zoom in at the location clicked
		ZOOMOUT('x', false, "Zooming out", null), // zoom out at the location clicked
		VIEWSELECT('v', true, "Draw View", null), // pan around the map by dragging it
		VIEWDRAG('c', true, "Select View", null); // select the area to zoom into
		
		private static final Map<Character, MouseActionType> triggermap = new HashMap<Character, MouseActionType>(); // which mouse action have which keyboard key trigger
		private static final Map<ActionType, MouseActionType> actionmap = new HashMap<ActionType, MouseActionType>(); // which mouse action have which action type
		
		static {
			// creats a map for all action type and keyboard key type
			for (MouseActionType mouseaction: MouseActionType.values()) {
				triggermap.put(mouseaction.TriggerKey, mouseaction);
				if (mouseaction.Action != null) actionmap.put(mouseaction.Action, mouseaction);
			}
		}
		
		public final boolean isSelection; // does this action involve selecting an area
		public final char TriggerKey; // which key will trigger this action
		public final String Description; // the description of the action to show on the status
		public final ActionType Action; // if applicable, which action to perform onto the map
		
		private MouseActionType(char keytrigger, boolean isSelection, String description, ActionType action) {
			this.isSelection = isSelection;
			this.TriggerKey = keytrigger;
			this.Description = description;
			this.Action = action;
		}
		
		public static MouseActionType getMouseActionType(char KeyTrigger) {
			return triggermap.get(KeyTrigger);
		}
	}
	
	
	/* *******************
	 * Constructor
	 *********************/
	
	/**
	 * @param environ the grid environment which this listener will apply changes to
	 * @param map the map instance it is listening to
	 */
	public MapGridAlteration(GridEnviron environ, MapInstance map) {
		this.environ = environ;
		this.map = map;
	}
	
	/* *******************
	 * Events
	 *********************/
	
	@Override
	public void mouseClicked(MouseEvent e) {
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// updates the status bar when you move the mouse around the map
		lastknownpos = getCurrentCoordinate(e);
		lastknownmousepos = new MapCoord(e.getX(), e.getY());
		showCurrentStatus();
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// clear the mouse status when it moves out of the map
		lastknownpos = null;
		lastknownmousepos = null;
		map.mousestate.setText("");
		
	}

	/**
	 * When the mouse is pressed, checked which action is active
	 * If action is not a selection type, apply action immediately
	 * else record the click position and action
	 */
	@Override
	public void mousePressed(MouseEvent e) {
		lastknownpos = getCurrentCoordinate(e);
		lastknownmousepos = new MapCoord(e.getX(), e.getY());
		if (lastaction != null && e.getButton() == MouseEvent.BUTTON1) {
			lastposition = lastknownpos;
			lastmouseposition = lastknownmousepos;
			if (!lastaction.isSelection) {
				if (lastaction.Action != null) {
					IActionEvent curevent = new EntityActionEvent(lastaction.Action, lastposition.getX(), lastposition.getY());
					curevent.RunAction(environ);
				} else if (lastaction == MouseActionType.ZOOMIN) {
					this.UpdateZoom(map.getPixelSize() + 1);
				} else if (lastaction == MouseActionType.ZOOMOUT) {
					this.UpdateZoom(map.getPixelSize() - 1);
				}
				lastposition = null;
				lastmouseposition = null;
				map.updateShownMap();
			}
		}
		showCurrentStatus();
	}

	/**
	 * When click release, if a action was recorded to be carried, carry the action on the selection area
	 */
	@Override
	public void mouseReleased(MouseEvent e) {
		lastknownpos = getCurrentCoordinate(e);
		lastknownmousepos = new MapCoord(e.getX(), e.getY());
		if (lastposition != null && lastaction != null) {
			MapCoord curposition = lastknownpos;
			int width = Math.abs(curposition.getX() - lastposition.getX()) + 1;
			int height =  Math.abs(curposition.getY() - lastposition.getY()) + 1;
			int left = curposition.getX(), top = curposition.getY();
			if (left > lastposition.getX()) left = lastposition.getX();
			if (top > lastposition.getY()) top = lastposition.getY();
			if (lastaction.Action != null) {
				IActionEvent curevent = new TerrainActionEvent(lastaction.Action, left, top, width, height);
				curevent.RunAction(environ);
			} else if (lastaction == MouseActionType.VIEWSELECT) {
				int pixelsize = map.getPixelSize();
				Dimension2D viewsize = map.getGridViewPortSize();
				
				int newviewx = Math.abs(lastknownpos.getX() - lastposition.getX());
				int newviewy = Math.abs(lastknownpos.getY() - lastposition.getY());
				int xscale = (int) (viewsize.getWidth() / newviewx);
				int yscale = (int) (viewsize.getHeight() / newviewy);
				int newzoom = xscale;
				if (yscale < newzoom) newzoom = yscale;
				map.setPixelSize(newzoom);
				int newpixelsize = map.getPixelSize();
				if (newpixelsize != pixelsize) map.validate();
				double newpixelratio = (((double) newpixelsize) / pixelsize);
				int viewx = (int) ((Math.abs(lastknownmousepos.getX() + lastmouseposition.getX()) * newpixelratio - viewsize.getWidth())  / 2);
				int viewy = (int) ((Math.abs(lastknownmousepos.getY() + lastmouseposition.getY()) * newpixelratio - viewsize.getHeight()) / 2);
				map.setGridViewScrollPosition(viewx, viewy);
				map.validate();
			}
			lastposition = null;
			lastmouseposition = null;
			map.clearSelection();
			map.updateShownMap();
		}
		showCurrentStatus();
	}

	/**
	 * Active action depend on which key was held down
	 */
	@Override
	public void keyPressed(KeyEvent e) {
		char curkey = Character.toLowerCase(e.getKeyChar());
		MouseActionType newaction = MouseActionType.getMouseActionType(curkey);
		if (newaction != null) {
			lastaction = newaction;
			lastkey = curkey;
		}
		showCurrentStatus();
	}


	/**
	 * Deactive action when key is released
	 */
	@Override
	public void keyReleased(KeyEvent e) {
//		if (lastkey == Character.toLowerCase(e.getKeyChar())) {
//			lastkey = '\0';
//			lastaction = null;
//			if (lastposition != null) {
//				lastposition = null;
//				lastmouseposition = null;
//				map.clearSelection();
//				map.updateShownMap();
//			}
//		}
//		showCurrentStatus();
	}


	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}


	/**
	 * When selecting an area to apply action, highlight the area
	 */
	@Override
	public void mouseDragged(MouseEvent e) {
		lastknownpos = getCurrentCoordinate(e);
		lastknownmousepos = new MapCoord(e.getX(), e.getY());
		if (lastposition != null && lastaction != null) {
			if (lastaction == MouseActionType.VIEWDRAG) {
				Point scrollpos = map.getGridViewScrollPosition();
				int scrollx = scrollpos.x + (lastmouseposition.getX() - lastknownmousepos.getX());
				int scrolly = scrollpos.y + (lastmouseposition.getY() - lastknownmousepos.getY());
				lastknownpos = lastposition;
				lastknownmousepos = lastmouseposition;
				map.setGridViewScrollPosition(scrollx, scrolly);
			} else {
				map.showSelection(lastposition, lastknownpos);
				map.updateShownMap();
			}
		}
		showCurrentStatus();
		
	}


	@Override
	public void mouseMoved(MouseEvent e) {
		mouseDragged(e);
		
	}
	
	/* *******************
	 * Utility
	 *********************/
	
	/**
	 * From the mouse event, get the current map coordinate
	 * 
	 * @param e
	 * @return
	 */
	private MapCoord getCurrentCoordinate(MouseEvent e) {
		int gridx = (int)(e.getPoint().getX() / map.getPixelSize());
		int gridy = (int)(e.getPoint().getY() / map.getPixelSize());
		return new MapCoord(gridx, gridy);
	}
	
	/**
	 * Update the mouse status
	 */
	private void showCurrentStatus() {
		if (lastaction != null) {
			String outval = lastaction.Description;
			if (lastaction.isSelection) {
				if (lastposition != null) {
					outval += " from " + lastposition.toString() + " to " + lastknownpos.toString();
				} else if (lastknownpos != null) {
					outval += " from " + lastknownpos.toString();
				}
			} else if (lastknownpos != null) {
				outval += " at " + lastknownpos.toString();
			}
			map.mousestate.setText(outval);
		} else if (lastknownpos != null) map.mousestate.setText(lastknownpos.toString());
		else map.mousestate.setText("");
	}
	
	/**
	 * change the zoom level
	 * 
	 * @param newzoom
	 */
	private void UpdateZoom(int newzoom) {
		int pixelsize = map.getPixelSize();
		map.setPixelSize(newzoom);
		int newpixelsize = map.getPixelSize();
		if (pixelsize != newpixelsize) {
			map.validate();
			Point scrollpos = map.getGridViewScrollPosition();
			int scrollx = lastknownmousepos.getX() - scrollpos.x;
			int scrolly = lastknownmousepos.getY() - scrollpos.y;
			int gridx = (int) (lastknownmousepos.getX() * (((double) newpixelsize) / pixelsize));
			int gridy = (int) (lastknownmousepos.getY() * (((double) newpixelsize) / pixelsize));
			map.setGridViewScrollPosition(gridx - scrollx, gridy - scrolly);
		}
		
	}



}
