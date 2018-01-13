package au.edu.rmit.agtgrp.apparate.gui.simviewer.view;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.List;

/**
 * A single layer of the map
 * 
 * @author Andy Xie
 *
 */
public class MapLayer {
		
	public static final Color DEFAULT_BACKGROUND = new Color(0, 0, 0, 0); // the colour to clear a image with to erase the original content
	
	public final int width, height; // height and width of the image
	public final BufferedImage image; // the image layer
	public final Graphics2D graphic; // the graphic related to the image
	private final MapGrid.LayerType type; // which type of layer is this
	private final MapGrid parent; // the parent map
	private boolean enabled; // to image the showing of this layer
	

	/* *******************
	 * Constructor
	 *********************/
	
	/**
	 * Hidden constructor so the only grid map can create this
	 * @param type
	 * @param parent
	 * @param width
	 * @param height
	 */
	protected MapLayer(MapGrid.LayerType type, MapGrid parent, int width, int height) {
		this.type = type;
		this.parent = parent;
		this.width = width;
		this.height = height;
		this.image = new BufferedImage(this.width, this.height, BufferedImage.TYPE_INT_ARGB);
		this.graphic = this.image.createGraphics();
		this.enabled = true;
	}


	/* *******************
	 * Accessor
	 *********************/
	
	public boolean getEnabled() {
		return this.enabled;
	}
	
	/* *******************
	 * Mutator
	 *********************/
	
	public void setEnabled(boolean enabled) {
		if (this.enabled != enabled) {
			this.enabled = enabled;
			parent.updatedLayer(this);
		}
	}
	
	/**
	 * Set the colour of a single coordinate
	 * @param x
	 * @param y
	 * @param colour
	 */
	public synchronized void setGrid(int x, int y, Color colour) {
		if (colour == null) return;
		if (x < 0 || y < 0) return;
		if (x >= width || y >= height) return;
		graphic.setColor(colour);
		graphic.fillRect(x, y, 1, 1);
		parent.updatedLayer(this);
	}
	
	/**
	 * draw the entity onto the layer
	 * @param entity
	 */
	public synchronized void setGrid(MapEntity entity) {
		drawEntity(entity);
		parent.updatedLayer(this);
	}
	
	/**
	 * draw a list of entity onto the layer
	 * @param entities
	 */
	public synchronized void setGrid(List<MapEntity> entities) {
		for (MapEntity entity: entities) drawEntity(entity);
		parent.updatedLayer(this);
	}
	
	/**
	 * draw the given map colour to this layer
	 * @param colours
	 */
	public synchronized void setGrid(Color[][] colours) {
		for (int i = 0; i < width && i < colours.length; i++) for (int j = 0; j < height && j < colours[i].length; j++) {
			if (colours[i][j] == null) continue;
			graphic.setColor(colours[i][j]);
			this.graphic.fillRect(i, j, 1, 1);
		}
		parent.updatedLayer(this);
	}
	
	/**
	 * Reset the grid to original clear
	 */
	public synchronized void clearGrid() {
		this.graphic.setBackground(DEFAULT_BACKGROUND);
		this.graphic.clearRect(0, 0, width, height);
		parent.updatedLayer(this);
	}
	

	/* *******************
	 * Utility
	 *********************/
	
	/**
	 * Draw a single entity onto the layer
	 * @param entity
	 */
	private void drawEntity(MapEntity entity) {
		if (!isValidEntity(entity)) return;
		graphic.setColor(entity.color);
		graphic.fillRect(entity.x, entity.y, entity.width, entity.height);
	}
	
	/**
	 * check if the entity is valid for the layer
	 * @param entity
	 * @return
	 */
	private boolean isValidEntity(MapEntity entity) {
		if (entity == null) return false;
		if (entity.color == null) return false;
		if (entity.width < 0 || entity.height <= 0) return false;
		if (entity.x >= this.width || entity.x + entity.width < 0) return false;
		if (entity.y >= this.height || entity.y + entity.height < 0) return false;
		return true;
	}
}
