package au.edu.rmit.agtgrp.apparate.gui.simviewer.view;

import java.awt.Color;

/**
 * A single drawable object on the map
 * 
 * @author Andy Xie
 *
 */
public class MapEntity {
	public final int x, y, width, height; // the position and the dimension of the entity
	public final Color color; // the colour of the entity
	

	/* *******************
	 * Constructor
	 *********************/
	
	// A dummy 1x1 map drawable entity
	public MapEntity(int x, int y, Color color) {
		this(x, y, 1, 1, color);
	}
	
	public MapEntity (int x, int y, int width,  int height, Color color) {
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		this.color = color;
	}
}
