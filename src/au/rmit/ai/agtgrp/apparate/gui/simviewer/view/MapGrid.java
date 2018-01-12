package au.rmit.ai.agtgrp.apparate.gui.simviewer.view;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * The drawing platform for the map
 * 
 * @author Andy Xie
 *
 */
public class MapGrid extends Canvas {
	
	private static final long serialVersionUID = 1L;

	private final Lock lock = new ReentrantLock();
	private volatile boolean doredraw = false;
	
	private int pixelwidth; // how large to draw each coordinate
	private int width, height; // the width and height of the map
	private BufferedImage image; // the buffer image
	private Graphics2D graphic; // the graphic related to the image
	private MapLayer[] layers; // all the layers of the map

	/* *******************
	 * Enum
	 *********************/
	
	/**
	 * List of all type of layers within this map
	 * @author Andy Xie
	 *
	 */
	public enum LayerType {
		LAYER_TRAVERSABLEMAP(0), // map of only the area which can be traversed
		LAYER_MAP(1), // map of all entity of the map (tree, ground, water, etc)
		
		LAYER_OPENED(2), // shows all the coordinate opened/expanded during the last run
		LAYER_CLOSED(3), // shows all the coordinate closed/unexpanded during the last run
		
		LAYER_PLANPATH(4), // the path of the path given by the run
		LAYER_GOALPATH(5), // the path the goal position has moved
		LAYER_TRAVELED(6), // the path of the start position has moved
		
		LAYER_GOAL(7), // displays where the goal position
		LAYER_START(8), // displays where the start position
		
		LAYER_SELECTION(9);
		
		public final int zIndex; // the layer position this layer will be placed, lowest number will be rendered first

		/* *******************
		 * Constructor
		 *********************/
		
		private LayerType (int layernum) {
			zIndex = layernum;
		}
		

		/* *******************
		 * Static
		 *********************/
		
		/**
		 * get maximum number of layers known
		 * @return
		 */
		public static int getMaxLayer() {
			int max = -1;
			for (LayerType layer: LayerType.values()) if (layer.zIndex > max) max = layer.zIndex;
			return max + 1;
		}
	}
	

	/* *******************
	 * Constructor
	 *********************/
	/**
	 * creates a new drawn map
	 * @param width coordinate width
	 * @param height coordinate height
	 * @param pixelwidth the size of each coordinate
	 */
	public MapGrid(int width, int height) {
		this.setFocusable(true);
		this.pixelwidth = 1;
		this.width = width;
		this.height = height;
		this.image = new BufferedImage(this.width, this.height, BufferedImage.TYPE_INT_ARGB);
		this.graphic = this.image.createGraphics();
		this.graphic.setBackground(new Color(0, 0, 0, 255));
		
		
		this.layers = new MapLayer[LayerType.getMaxLayer()];
		for (LayerType type: LayerType.values()) this.layers[type.zIndex] = new MapLayer(type, this, this.width, this.height);
		this.setSize(this.width * this.pixelwidth, this.height * this.pixelwidth);
	}
	

	/* *******************
	 * Override
	 *********************/
	/**
	 * Redraws all the layers as indicated by the z-index
	 */
	public void paint(Graphics g) {
		doredraw = true;
		if (lock.tryLock()) {
			try {
				while (doredraw) {
					doredraw = false;
					graphic.clearRect(0, 0, width, height);
					for (int i = 0; i < layers.length; i++) if (layers[i] != null) synchronized(layers[i]) {
						if (layers[i].getEnabled()) graphic.drawImage(layers[i].image, 0, 0, width, height, null);
					}
					g.drawImage(image, 0, 0, width * pixelwidth, height * pixelwidth, null);
				}
			} finally {
				lock.unlock();
			}
		}
	}
	/* *******************
	 * Accessor
	 *********************/
	
	public MapLayer getLayer(LayerType layertype) {
		return this.layers[layertype.zIndex];
	}
	
	public int getGridHeight() {
		return height;
	}
	public int getGridWidth() {
		return width;
	}
	public int getPixelSize() {
		return this.pixelwidth;
	}


	/* *******************
	 * Mutator
	 *********************/
	
	/**
	 * Call by a child layer when ever they have been altered
	 * 
	 * @param layer the layer which has been altered
	 */
	protected void updatedLayer(MapLayer layer) {
	}
	
	/**
	 * Changes the pixel size of this image, must be at least 1
	 * 
	 * @param size
	 */
	public void setPixelSize(int size) {
		int newsize = size;
		if (newsize < 1) newsize = 1;
		if (newsize != this.pixelwidth) {
			this.pixelwidth = newsize;
			this.setSize(this.width * this.pixelwidth, this.height * this.pixelwidth);
			//updateShownMap();
		}
	}
	
	/* *******************
	 * Utility
	 *********************/
	
	/**
	 * force a redraw of the map
	 */
	public void updateShownMap() {
		Graphics g = getGraphics();
		if (g != null) paint(g);
	}
	
	
}
