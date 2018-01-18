package au.edu.rmit.agtgrp.apparate.gui.simviewer.view;

import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyEvent;
import java.util.HashMap;

import javax.swing.AbstractButton;
import javax.swing.ButtonGroup;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JRadioButtonMenuItem;
import javax.swing.JRootPane;
import javax.swing.KeyStroke;

import au.edu.rmit.agtgrp.apparate.gui.simviewer.controller.ControlStopOnArrival;
import au.edu.rmit.agtgrp.apparate.gui.simviewer.controller.OpenMap;
import au.edu.rmit.agtgrp.apparate.gui.simviewer.controller.SetGridViewEuclidean;
import au.edu.rmit.agtgrp.apparate.gui.simviewer.controller.SetPlannerFromExternal;
import au.edu.rmit.agtgrp.apparate.gui.simviewer.controller.ControlLoop;
import au.edu.rmit.agtgrp.apparate.gui.simviewer.controller.ControlReset;
import au.edu.rmit.agtgrp.apparate.gui.simviewer.controller.ControlStep;
import au.edu.rmit.agtgrp.apparate.gui.simviewer.controller.OpenScript;
import au.edu.rmit.agtgrp.apparate.gui.simviewer.controller.SetGridViewManhatten;

/**
 * The Pane that rules them all used in both the Applet and the JFrame
 * 
 * @author Andy Heng Xie
 *
 */

public class AppContentPane extends JRootPane {

	private static final long serialVersionUID = 1L;
	
	private MapInstance map; // the map in this frame
	
	private HashMap<MapGrid.LayerType, JMenuItem> layermenuitem = new HashMap<MapGrid.LayerType, JMenuItem>(); // all the layer buttons
	
	public final AbstractButton btn_openmap, btn_openscript, btn_resetenviron; // the open map and script buttons
	public final AbstractButton rdo_settomanhatten, rdo_settoeuclidean; // set view type buttons
	public final AbstractButton /*rdo_usebaseastar, rdo_useastar, rdo_usemtdastar, rdo_uselssltrastar, rdo_usealssltrastar, rdo_useothers,*/ rdo_useexternalplanner; // set planner type buttons
	public final AbstractButton chk_loop, btn_step, chk_stoponarrival; // set loop, step and stop on arrival
	
	private JMenuBar menubar = new JMenuBar();
	private JMenu menu;
	private JMenuItem menuitem;
	private ButtonGroup group;
	
	
	/**
	 * Toggles the visibility of the layers
	 *
	 */
	private class LayerToggleListener implements ItemListener {
		private MapGrid.LayerType layer; // which layer
		private AppContentPane frame; // on which frame
		
		public LayerToggleListener(AppContentPane frame, MapGrid.LayerType layer) {
			this.frame = frame;
			this.layer = layer;
		}
		
		@Override
		public void itemStateChanged(ItemEvent e) {
			frame.map.setLayerVisibility(layer, ((AbstractButton) e.getSource()).isSelected());
		}
		
	}
	

	public AppContentPane() {
		
		
		// creates the file menu which has controls to open map and script
		menu = new JMenu("File");
		menubar.add(menu);
		this.btn_openmap = menuitem = new JMenuItem("Open Map");
		menuitem.addActionListener(new OpenMap(this));
		menuitem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_O, KeyEvent.CTRL_DOWN_MASK));
		menu.add(menuitem);
		this.btn_openscript = menuitem = new JMenuItem("Open Script");
		menuitem.addActionListener(new OpenScript(this));
		menuitem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_E, KeyEvent.CTRL_DOWN_MASK));
		menu.add(menuitem);
		this.btn_resetenviron = menuitem = new JMenuItem("Reset Environment");
		menuitem.addActionListener(new ControlReset(this));
		menuitem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_R, KeyEvent.CTRL_DOWN_MASK));
		menu.add(menuitem);
		

		// creates the environment menu which has controls for which planner is running and which grid type
		menu = new JMenu("Environment");
		menubar.add(menu);
		
		
		group = new ButtonGroup();

		this.rdo_useexternalplanner = menuitem = new JRadioButtonMenuItem("External Source");
		menu.add(menuitem);
		group.add(menuitem);
		menuitem.addActionListener(new SetPlannerFromExternal());
		menuitem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_9, KeyEvent.CTRL_DOWN_MASK));
		
		
		menu.addSeparator();
		
		group = new ButtonGroup();
		this.rdo_settomanhatten = menuitem = new JRadioButtonMenuItem("Grid as Manhatten");
		menu.add(menuitem);
		group.add(menuitem);
		menuitem.addActionListener(new SetGridViewManhatten());
		menuitem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F1, 0));
		this.rdo_settoeuclidean = menuitem = new JRadioButtonMenuItem("Grid as Euclidean");
		menu.add(menuitem);
		group.add(menuitem);
		menuitem.addActionListener(new SetGridViewEuclidean());
		menuitem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F2, 0));
		
		
		// creates the layer menu which controls which layer is visible
		menu = new JMenu("Layer");
		menubar.add(menu);
		group = new ButtonGroup();
		menuitem = new JRadioButtonMenuItem("Show Traversable Map");
		menu.add(menuitem);
		group.add(menuitem);
		menuitem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_1, KeyEvent.ALT_DOWN_MASK));
		menuitem.addItemListener(new LayerToggleListener(this, MapGrid.LayerType.LAYER_TRAVERSABLEMAP));
		layermenuitem.put(MapGrid.LayerType.LAYER_TRAVERSABLEMAP, menuitem);
		menuitem = new JRadioButtonMenuItem("Show Terrain Map");
		menu.add(menuitem);
		group.add(menuitem);
		menuitem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_2, KeyEvent.ALT_DOWN_MASK));
		menuitem.addItemListener(new LayerToggleListener(this, MapGrid.LayerType.LAYER_MAP));
		layermenuitem.put(MapGrid.LayerType.LAYER_MAP, menuitem);
		menu.addSeparator();
		
		menuitem = new JCheckBoxMenuItem("Show Start Position");
		menuitem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_3, KeyEvent.ALT_DOWN_MASK));
		menuitem.addItemListener(new LayerToggleListener(this, MapGrid.LayerType.LAYER_START));
		layermenuitem.put(MapGrid.LayerType.LAYER_START, menuitem);
		menu.add(menuitem);
		menuitem = new JCheckBoxMenuItem("Show Destination Position");
		menuitem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_4, KeyEvent.ALT_DOWN_MASK));
		menuitem.addItemListener(new LayerToggleListener(this, MapGrid.LayerType.LAYER_GOAL));
		layermenuitem.put(MapGrid.LayerType.LAYER_GOAL, menuitem);
		menu.add(menuitem);
		menuitem = new JCheckBoxMenuItem("Show Path Planned");
		menuitem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_5, KeyEvent.ALT_DOWN_MASK));
		menuitem.addItemListener(new LayerToggleListener(this, MapGrid.LayerType.LAYER_PLANPATH));
		layermenuitem.put(MapGrid.LayerType.LAYER_PLANPATH, menuitem);
		menu.add(menuitem);
		menuitem = new JCheckBoxMenuItem("Show Path Travelled");
		menuitem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_6, KeyEvent.ALT_DOWN_MASK));
		menuitem.addItemListener(new LayerToggleListener(this, MapGrid.LayerType.LAYER_TRAVELED));
		layermenuitem.put(MapGrid.LayerType.LAYER_TRAVELED, menuitem);
		menu.add(menuitem);
		menuitem = new JCheckBoxMenuItem("Show Expanded Node");
		menuitem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_7, KeyEvent.ALT_DOWN_MASK));
		menuitem.addItemListener(new LayerToggleListener(this, MapGrid.LayerType.LAYER_OPENED));
		layermenuitem.put(MapGrid.LayerType.LAYER_OPENED, menuitem);
		menu.add(menuitem);
		menuitem = new JCheckBoxMenuItem("Show Unexpanded Node");
		menuitem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_8, KeyEvent.ALT_DOWN_MASK));
		menuitem.addItemListener(new LayerToggleListener(this, MapGrid.LayerType.LAYER_CLOSED));
		layermenuitem.put(MapGrid.LayerType.LAYER_CLOSED, menuitem);
		menu.add(menuitem);
		
		// creates the run menu which controls the loop, step and stop on arrival
		menu = new JMenu("Run");
		menubar.add(menu);
		this.chk_loop = menuitem = new JCheckBoxMenuItem("Loop");
		menu.add(menuitem);
		menuitem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F5, 0));
		menuitem.addActionListener(new ControlLoop());
		this.btn_step = menuitem = new JMenuItem("Step");
		menu.add(menuitem);
		menuitem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F8, 0));
		menuitem.addActionListener(new ControlStep());
		menu.addSeparator();
		this.chk_stoponarrival = menuitem = new JCheckBoxMenuItem("Stop on Arrival");
		menu.add(menuitem);
		menuitem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F12, 0));
		menuitem.addActionListener(new ControlStopOnArrival());
		
		this.setJMenuBar(menubar);
	}
	

	/* *******************
	 * Accessors
	 *********************/
	
	/**
	 * Get the current map in active
	 *
	 * @return current instance of the map
	 */
	public MapInstance getMap() {
		return map;
	}


	/* *******************
	 * Mutator
	 *********************/
	
	/**
	 * Sets the new map as the current map
     *
	 * @param newmap    the new map to register
	 * @return          the old map (that was replaced)
	 */
	public MapInstance setMap(MapInstance newmap) {
		MapInstance oldmap = map;
		if (map != null) this.getContentPane().remove(map);
		map = newmap;
		this.getContentPane().add(map);
		// sychronise the map layer visibility
		if (map.getLayerVisibility(MapGrid.LayerType.LAYER_MAP)) layermenuitem.get(MapGrid.LayerType.LAYER_MAP).setSelected(true);
		else layermenuitem.get(MapGrid.LayerType.LAYER_TRAVERSABLEMAP).setSelected(true);
		for (MapGrid.LayerType layer: layermenuitem.keySet())
			if (layer != MapGrid.LayerType.LAYER_TRAVERSABLEMAP && layer != MapGrid.LayerType.LAYER_MAP) layermenuitem.get(layer).setSelected(map.getLayerVisibility(layer));
		this.validate();
		
		
		return oldmap;
	}
}
