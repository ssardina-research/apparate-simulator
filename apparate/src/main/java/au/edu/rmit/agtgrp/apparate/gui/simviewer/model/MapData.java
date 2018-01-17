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

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Scanner;

/**
 * Hows data of the map from file as indicated by the formate
 * Also holds function to parse the given file format
 * 
 * @author Andy Xie
 *
 */
public class MapData {

	protected int width, height; // width and height of the map
	protected GridCellType[][] celldata; // the array of cell data
	protected static float GROUND_COST=1;
	protected static float TREE_COST=Float.POSITIVE_INFINITY;
	protected static float WATER_COST=20;
	protected static float SWAMP_COST=10;
	protected static Boolean GROUND_TRAVERSABLE = true;
	protected static Boolean TREE_TRAVERSABLE = true;
	protected static Boolean WATER_TRAVERSABLE = true;
	protected static Boolean SWAMP_TRAVERSABLE = true;
	
	/* *******************
	 * Constructor
	 *********************/
	
	/**
	 * Creates a new empty map of the given dimension
	 * @param width
	 * @param height
	 */
	private MapData(int width, int height) {
		this.width = width;
		this.height = height;
		celldata = new GridCellType[width][height];
	}
	
	/* *******************
	 * Static
	 *********************/
	
	public static MapData readExternalData(String file) {
		try {
			return readExternalData(new FileInputStream(new File(file)));
		} catch (FileNotFoundException e) {
			return null;
		}
	}
	
	/**
	 * reads the file and parse it into a mapdata object
	 * expect it to be in the specified format
	 * @param path the path of the file
	 * @return the new data object
	 */
	public static MapData readExternalData(InputStream stream) {
        //Scanner sc = new Scanner(Pathfinder.class.getResourceAsStream(path));
		Scanner sc = null;
		sc = new Scanner(stream);
		
		// size of map
		int height = 0;
		int width = 0;
		
		// Let's read the first key name 
        String key = sc.next().toLowerCase();
        
        // Keep reading keys until we git "map"
        while (!key.equals("map")) {        
        	String keyValue = sc.next().toLowerCase();
        
        	if (key.equals("height"))
        		height = Integer.parseInt(keyValue);
        	else if (key.equals("width"))
        		width = Integer.parseInt(keyValue);
        	else if (key.equals("ground")){
        		if(keyValue.equals("+inf")){
        			GROUND_COST =  Float.POSITIVE_INFINITY;
        			GROUND_TRAVERSABLE = false;
        		} else
        			GROUND_COST = Float.parseFloat(keyValue);
        	} else if(key.equals("tree")){
        		if(keyValue.equals("+inf")){
        			TREE_COST =  Float.POSITIVE_INFINITY;
        			TREE_TRAVERSABLE = false;
        		} else
        			TREE_COST = Float.parseFloat(keyValue);
        	} else if(key.equals("swamp")){
        		if(keyValue.equals("+inf")){
        			SWAMP_COST =  Float.POSITIVE_INFINITY;
        			SWAMP_TRAVERSABLE = false;
        		}else
        			SWAMP_COST = Float.parseFloat(keyValue);
        	} else if(key.equals("water")){
        		if(keyValue.equals("+inf")){
        			WATER_COST =  Float.POSITIVE_INFINITY;
        			WATER_TRAVERSABLE = false;
        		} else
        			WATER_COST = Float.parseFloat(keyValue);
        	}

        	// read next key (or "map")
        	key = sc.next().toLowerCase(); 

        } // end while
        
        
        // Let's create a map with width and height
        MapData newdata = new MapData(width, height);
        
        // Initialize all cells with the default cell type
        for (int i = 0; i < width; i++) for (int j = 0; j < height; j++) 
        	newdata.celldata[i][j] = GridCellType.getDefault();
        
        // Now read the actual map from file
        for (int j = 0; j < height && sc.hasNext(); j++) {
            char[] fila = sc.next().toCharArray();
            for (int i = 0; i < width && i < fila.length; i++) 
            	newdata.celldata[i][j] = charTypeMap(fila[i]);
        }
        return newdata;
		
	}
	

	/* *******************
	 * Utility
	 *********************/
	
	/**
	 * converts the character to the supposed cell type
	 * @param chartype the character read from the file
	 * @return the supposed cell type, returns default if the character is not recognised
	 */
	private static GridCellType charTypeMap(char chartype) {
		switch (chartype) {
		case '.':
		case 'g':
		case 'G':
			return GridCellType.GROUND;
		case 't':
		case 'T':
			return GridCellType.TREE;
		case 's':
		case 'S':
			return GridCellType.SWAMP;
		case 'w':
		case 'W':
			return GridCellType.WATER;
		case '@':
		case 'o':
		case 'O':
			return GridCellType.OUTOFBOUND;
		default:
			return GridCellType.getDefault();
		}
	}
	
}
