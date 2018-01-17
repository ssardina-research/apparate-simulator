package au.edu.rmit.agtgrp.apparate.gui.simviewer.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Constructor;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.Properties;
import javax.swing.JApplet;
import javax.swing.JFrame;

import au.edu.rmit.agtgrp.apparate.gui.simviewer.model.RunEnviron;
import au.edu.rmit.agtgrp.apparate.gui.simviewer.model.listener.ITimerListener;
import au.edu.rmit.agtgrp.apparate.gui.simviewer.view.MapInstance;
import au.edu.rmit.agtgrp.apparate.gui.interfaces.PlanningAgent;
import au.edu.rmit.agtgrp.apparate.gui.simviewer.model.GridCoord;
import au.edu.rmit.agtgrp.apparate.gui.simviewer.model.GridEnviron;
import au.edu.rmit.agtgrp.apparate.gui.simviewer.model.GridView;
import au.edu.rmit.agtgrp.apparate.gui.simviewer.model.MapData;
import au.edu.rmit.agtgrp.apparate.gui.simviewer.model.script.ScriptEvents;
import au.edu.rmit.agtgrp.apparate.gui.simviewer.model.script.StopOnArrival;
import au.edu.rmit.agtgrp.apparate.gui.simviewer.model.timer.PulserTimer;
import au.edu.rmit.agtgrp.apparate.gui.simviewer.model.timer.ThreadedTimer;
import au.edu.rmit.agtgrp.apparate.gui.simviewer.view.AppContentPane;

/**
 * Entry point to the program, for CLI, GUI and Applet
 * 
 * @author Andy Heng Xie
 *
 */
public class Launcher extends JApplet {
	
	private static final long serialVersionUID = 1L;
	private static boolean isapplet; // determine if the application is running as an applet
	private static final Object launcherlock = new Object(); // lock global resource in event of changing
	private static RunEnviron curenviron; // the current running environment
	private static AppContentPane curpane; // the current interface
	private static ITimerListener runstatechanger = new RunStateDisplayUpdater(); // the event to change the display's run state
	private static PlanningAgent currentagent = null; // externally loaded planner spawner
	private static LaunchOptions initoptions = new LaunchOptions(); // the options used to initiate the appliction
	private static LaunchOptions lastlaunch; // the options used for the last launch


	/* *******************
	 * Constructor
	 *********************/
	
	public Launcher() { }
	

	/* *******************
	 * Main
	 *********************/
	
	/**
	 * Main entry point, in charge of loading both the gui and the cli version
	 * @param arg
	 * @throws InterruptedException
	 */
	public static void main(String[] arg) throws InterruptedException {
		isapplet = false;
		
		
		/** Check if config file loaded
		 **/
		if(arg.length<1){
			exitWithError("Please pass the config file as argument.");
		}
		
		Properties prop = new Properties();
		try {
			prop.load(new FileInputStream(arg[0]));
		} catch (Exception e) {
			exitWithError("Could not load configuration file given.");
		}

		
		
		// Create thislaunch
		LaunchOptions thislaunch = initoptions;
		

		//set grid properties
		String viewvalue = prop.getProperty("grid");// arg[++i];
		if (viewvalue == null || 
				!(viewvalue.equals("manhatten") || 
				  viewvalue.equals("manhattan") || 
				  viewvalue.equals("euclidean") || 
				  viewvalue.equals("diagonal") || 
				  viewvalue.equals("manworldA") )
			)
			exitWithError("[grid=euclidean|manhatten|manhattan] should be specified in the config file");
		if (viewvalue.equals("manhatten") || viewvalue.equals("manhattan")) {
			thislaunch.hasview = true;
			thislaunch.view = GridView.MANHATTENGRID;
		} else if (viewvalue.equals("euclidean") || viewvalue.equals("diagonal")) {
			thislaunch.hasview = true;
			thislaunch.view = GridView.EUCLIDEANGRID;
		}
		
		
		//set pause time between steps (when for GUI)
		if(prop.getProperty("pulsertime")!=null){
			thislaunch.hasintermission = true;
			thislaunch.intermission = Integer.parseInt(prop.getProperty("pulsertime"));
		}
		
		// set time limite pers tep
		if(prop.getProperty("steptime")!=null){
			thislaunch.hassteptimelimit = true;
			thislaunch.steptimelimit = Integer.parseInt(prop.getProperty("steptime"));
		}

		
		//set time limit
		if(prop.getProperty("totaltime")!=null){
			thislaunch.hastimelimit = true;
			thislaunch.timelimit = Integer.parseInt(prop.getProperty("totaltime"));
		}

		//set min time limit (below this, does not consider step)
		if(prop.getProperty("mintimestep")!=null){
			thislaunch.hasmintimestep = true;
			thislaunch.mintimestep = Integer.parseInt(prop.getProperty("mintimestep"));
		}

		//set step time
		if(prop.getProperty("steps")!=null){
			thislaunch.hassteplimit = true;
			thislaunch.steplimit = Integer.parseInt(prop.getProperty("steps"));
		}
		
		//set start position
		if(prop.getProperty("startpos")!=null){
			if (prop.getProperty("startpos").equals("random")) {
				thislaunch.hasstart = false;
			} else
			{
				String[] tokens = prop.getProperty("startpos").split(",");
				thislaunch.hasstart = true;
				thislaunch.startx = Integer.parseInt(tokens[0]);
				thislaunch.starty = Integer.parseInt(tokens[1]);
			}				
		}
		
		//set start position
		if(prop.getProperty("destpos")!=null){
			if (prop.getProperty("destpos").equals("random")) {
				thislaunch.hasgoal = false;
			} else
			{
				String[] tokens = prop.getProperty("destpos").split(",");
				thislaunch.hasgoal = true;
				thislaunch.goalx = Integer.parseInt(tokens[0]);
				thislaunch.goaly = Integer.parseInt(tokens[1]);
			}
		}
				
		//set map file
		if(prop.getProperty("map")!=null){
			try {
				thislaunch.mapfile = (new File(prop.getProperty("map"))).toURI().toURL();
			} catch (Exception e) { exitWithError("Could not load map file"); }
		}
				
		//set script file
		if(prop.getProperty("script")!=null){
			try {
				thislaunch.scriptfile = (new File(prop.getProperty("script"))).toURI().toURL();
			} catch (Exception e) {exitWithError("Could not load script file"); }
		}
		
		//set garbage collection preference
		if(prop.getProperty("managegc")!=null){
			Boolean flag = new Boolean(prop.getProperty("managegc"));
			thislaunch.hasdogc = flag;
			thislaunch.dogc = flag;
		}

		//set quiet reference
		if(prop.getProperty("quiet") == null) {
			exitWithError("[quiet=true|false] should be specified in the config file");
		} else {
			// extract quiet value
			Boolean flag = new Boolean(prop.getProperty("quiet"));
			thislaunch.hasquiet = flag;
			thislaunch.quiet = flag;


		}


		//set kill agent if time has passed
		if(prop.getProperty("killonlimit")!=null){
			Boolean flag = new Boolean(prop.getProperty("killonlimit"));
			thislaunch.haskillonlimit = flag;
			thislaunch.killonlimit = flag;
		}
		
		// set agents
		if (prop.getProperty("agentclass") != null) {
			thislaunch.hasexternalspawner = true;
			File file = new File(prop.getProperty("agentloc"));
			String classpath = prop.getProperty("agentclass");
			String name;
			if (prop.containsKey("agentname"))
					name = prop.getProperty("agentname");
			else
					name = classpath.toString();
			
			try {
				URL[] fileurl = new URL[] { file.toURI().toURL() };
				ClassLoader loader = new URLClassLoader(fileurl);
				Class<?> clazz = Class.forName(classpath, true, loader);
				Class<? extends PlanningAgent> runClass = clazz.asSubclass(PlanningAgent.class);
				Constructor<? extends PlanningAgent> ctor = runClass.getConstructor();
				PlanningAgent agent = ctor.newInstance();
				thislaunch.agentName=name;
				thislaunch.jarfile = file;
				thislaunch.classpath=classpath;
				thislaunch.planner = agent;		
				Launcher.setExternalSpawner(agent);
			} catch (Exception e) {
				e.printStackTrace();
			}

		} else {
			exitWithError("You need to specify a proper agent specification in the config file");
		}
		

		 
		// FINALLY, lauch the application!
		Launcher.setRunEnviron(initRunEnviron(thislaunch));	// Set the current environment to thislaunch
		Launcher.setLastLaunchOption(thislaunch);

		
		// THIS IS WHERE WE LAUNCH THE SYSTEM IN QUITE OR NON-QUIET MODE
			if (thislaunch.quiet) {
					// This bit should not be here, but for some reason it speeds up the execution?
					// Abhijeet hypthesis: this creates a shared object that otherwise needs to be created later!
				// ----------------------
				AppContentPane applicationPanel = new AppContentPane();
				Launcher.setAppPane(applicationPanel);
				applicationPanel.setMap(init(Launcher.getRunEnviron()));

				JFrame newframe = new JFrame();
				newframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				newframe.setContentPane(Launcher.getAppPane());
				newframe.setSize(1024, 768);
				newframe.setVisible(false);
				// ----------------------
				
				
				Launcher.getRunEnviron().getTimer().AddTimerEvent(new CLIResultPrinter());
				Thread.sleep(1000);	// Give some time to settle down?
				Launcher.getRunEnviron().getTimer().Loop();	// Run it!
			} else	{
				// Attach application panel to a Java frame and display it
				AppContentPane applicationPanel = new AppContentPane();
				Launcher.setAppPane(applicationPanel);
				
				// Initiate current environment as a GUI
				applicationPanel.setMap(init(Launcher.getRunEnviron()));
				
				JFrame newframe = new JFrame();
				newframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				newframe.setContentPane(Launcher.getAppPane());
				newframe.setSize(1024, 768);
				newframe.setVisible(true);

				Launcher.getRunEnviron().getTimer().AddTimerEvent(new CLIResultPrinter());
}
				

		
//		if (thislaunch.quiet) {
//			// Initiate current environment silently
//			initCLI(Launcher.getRunEnviron());
//		} else {
//			// No QUITE, we show GUI!
//			AppContentPane newpane = new AppContentPane();
//			// Initiate current environment as a GUI
//			newpane.setMap(initGUI(Launcher.getRunEnviron()));
//			Launcher.setAppPane(newpane);
//		
//			JFrame newframe = new JFrame();
//			newframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//			newframe.setContentPane(Launcher.getAppPane());
//			newframe.setSize(1024, 768);
//			newframe.setVisible(true);
//		}
	}


	private static void exitWithError(String msg) {
		System.err.println("ERROR MESSAGE: " + msg);
		System.exit(0);
	}	

	/* *******************
	 * Applet
	 *********************/

	
	
	


	public void stop() {
		
	}
	
	/* *******************
	 * Accessors
	 *********************/

	public static RunEnviron getRunEnviron() {
		synchronized (launcherlock) {
			return curenviron;
		}
	}

	public static LaunchOptions getInitLauchOptions() {
		return initoptions;
	}
	
	public static LaunchOptions getLastLauchOptions() {
		return lastlaunch;
	}
	
	public static AppContentPane getAppPane() {
		synchronized (launcherlock) {
			return curpane;
		}
	}
	
	public static PlanningAgent getExternalSpawner() {
		synchronized (launcherlock) {
			return currentagent;
		}
	}
	
	public static boolean isApplet() {
		return isapplet;
	}

	/* *******************
	 * Mutators
	 *********************/
	
	/**
	 * Set the current running environment
	 * This is a central point for all controllers to access
	 * @param environ
	 */
	public static void setRunEnviron(RunEnviron environ) {
		synchronized (launcherlock) {
			if (curenviron != null) curenviron.getTimer().RemoveTimerEvent(runstatechanger);
			if (environ != null) environ.getTimer().AddTimerEvent(runstatechanger);
			if (curpane != null && environ != null) linkPaneAndEnviron(curpane, environ);
			curenviron = environ;
		}
	}
	
	/**
	 * Set the current running gui
	 * This is a central point for all controllers to access
	 * @param apppane
	 */
	public static void setAppPane(AppContentPane apppane) {
		synchronized (launcherlock) {
			linkPaneAndExternalSpawner(apppane, currentagent);
			if (apppane != null && curenviron != null) linkPaneAndEnviron(apppane, curenviron);
			if (apppane != null) {
				apppane.btn_openmap.setEnabled(!isapplet);
				apppane.btn_openscript.setEnabled(!isapplet);	
			}
			curpane = apppane;
		}
	}
	
	public static void setExternalSpawner(PlanningAgent spawner) {
		synchronized (launcherlock) {
			linkPaneAndExternalSpawner(curpane, spawner);
			currentagent = spawner;			
		}
	}
	
	public static void setLastLaunchOption(LaunchOptions options) {
		lastlaunch = options;
	}

	/* *******************
	 * Utility
	 *********************/
	
	/**
	 * Synchronise the state between the running environment and the graphical interface
	 * @param thispane
	 * @param thisenviron
	 */
	private static void linkPaneAndEnviron(AppContentPane thispane, RunEnviron thisenviron) {
		// check the correct planner has been selected
		//PlanningAgent externalspawner = Launcher.getExternalSpawner();
		PlanningAgent spawner = thisenviron.getGridEnviron().getPlannerSpawner();
		//PlannerSelector selector = PlannerSelector.getSelectorByPlannerSpawner(spawner);
		if (spawner != null) thispane.rdo_useexternalplanner.setSelected(true);

		// check the correct view has been selected
		switch (Launcher.getRunEnviron().getGridEnviron().getGridView()) {
		case MANHATTENGRID:
			thispane.rdo_settomanhatten.setSelected(true);
			break;
		case EUCLIDEANGRID:
			thispane.rdo_settoeuclidean.setSelected(true);
			break;
		}
		
		// check if environment is running or set at stop on arrival at destination
		thispane.chk_loop.setSelected(thisenviron.getTimer().isLooping());
		thispane.chk_stoponarrival.setSelected(thisenviron.getStopOnArrival().getDoStop());
	}
	
	/**
	 * Synchronise the state between the graphical interface and the external planner spawner
	 * @param thispane
	 * @param thisspawner
	 */
	private static void linkPaneAndExternalSpawner(AppContentPane thispane, PlanningAgent thisspawner) {
		if (thispane != null) {
			thispane.rdo_useexternalplanner.setEnabled(thisspawner != null);
			if (thisspawner == null) thispane.rdo_useexternalplanner.setText("External Source");
			else thispane.rdo_useexternalplanner.setText(initoptions.agentName);			
		}
	}
	
	/**
	 * load the running environment from the setting given
	 * @param initsetting
	 * @return null if setting is invalid
	 */
	public static RunEnviron initRunEnviron(LaunchOptions initsetting) {
		RunEnviron environ = new RunEnviron();
		int runpause;
		InputStream datastream;
		StopOnArrival stoponarrival;
		boolean hasplanner = false;

		// load map if given, else give empty grid
		datastream = null;
		if (initsetting.mapfile != null)
			try {
				datastream = initsetting.mapfile.openStream();
			} catch (IOException e) { }
		
		if (datastream != null) {
			MapData newdata = MapData.readExternalData(datastream);
			environ.setGridEnviron(new GridEnviron(newdata));
		} else {
			GridEnviron grid = new GridEnviron(512, 512);
			environ.setGridEnviron(grid);
		}


		if (!hasplanner) if (initsetting.hasexternalspawner && Launcher.getExternalSpawner() != null) {
			environ.getGridEnviron().setPlannerSpawner(Launcher.getExternalSpawner());
			hasplanner = true;
		}
		if (!hasplanner) if (initsetting.planner != null) {
			environ.getGridEnviron().setPlannerSpawner(initsetting.planner);
			hasplanner = true;
		}
		// if no planner given, default to Base AStar
		/**/
		if (!hasplanner) {
			environ.getGridEnviron().setPlannerSpawner(/*PlannerSelector.BASEASTAR.Spawner*/initsetting.planner);
			hasplanner = true;
		}
	
		
		// If no grid view is given, use Manhattan by default
		if (!initsetting.hasview) environ.getGridEnviron().setGridView(GridView.MANHATTENGRID);
		else environ.getGridEnviron().setGridView(initsetting.view);

		
		
		
		// set start or destination if given
		if (initsetting.hasstart) 
			environ.getGridEnviron().setStartPosition(new GridCoord(initsetting.startx, initsetting.starty));
		else { // pick start randomly
			environ.getGridEnviron().setStartPosition(environ.getGridEnviron().getRandomNonBlockedPosition());
		}
		if (initsetting.hasgoal) 
			environ.getGridEnviron().setGoalPosition(new GridCoord(initsetting.goalx, initsetting.goaly));
		else { // pick goal randomly
			environ.getGridEnviron().setGoalPosition(environ.getGridEnviron().getRandomNonBlockedPosition());
		}


		// Set up intermission time, that is, time to pause between steps (40ms the default when GUI active)
		if (initsetting.quiet) 
			runpause = 0;
		else if (initsetting.hasintermission) 
			runpause = initsetting.intermission;
		else runpause = 40;

	

		environ.setTimer(new PulserTimer(environ.getGridEnviron(), runpause, initsetting.steplimit, initsetting.steptimelimit, initsetting.timelimit));


		// Load Stop on arrival
		if (initsetting.quiet) 
			stoponarrival = new StopOnArrival(true, environ.getTimer());
		else 
			stoponarrival = new StopOnArrival(true, new ThreadedTimer(environ.getTimer()));
		
		environ.setStopOnArrival(stoponarrival);
		environ.getGridEnviron().addGridEnvironUpdateListener(stoponarrival);

		environ.getTimer().setForceAbort(initsetting.killonlimit);
		
		if (initsetting.hasdogc) 
			environ.getGridEnviron().setDoGarbageCollection(initsetting.dogc);
		
		
		// Load the dynamic map script if any given
		datastream = null;
		if (initsetting.scriptfile != null)
			try {
				datastream = initsetting.scriptfile.openStream();
			} catch (IOException e) { }
		
		if (datastream != null) environ.setScriptEvents(ScriptEvents.readExternalData(datastream));
		environ.getGridEnviron().addGridEnvironUpdateListener(new ScriptExecutor(environ));
		return environ;
	}
	

	

	/**
	 * Run the system
	 * @param environ
	 */
	public static MapInstance init(RunEnviron environ) {
		MapInstance map = new MapInstance(environ.getGridEnviron().getWidth(), environ.getGridEnviron().getHeight(), 2);
		MapGridAlteration mapalterer = new MapGridAlteration(environ.getGridEnviron(), map);
		MapUpdater mapupdater = new MapUpdater(environ.getGridEnviron(), map);
		ITimerListener stateupdater = new RunStatusUpdater(map, initoptions.steplimit);
		
		map.AddGridMouseListener(mapalterer);
		map.AddGridMotionListener(mapalterer);
		map.AddGridKeyListener(mapalterer);
		environ.getGridEnviron().addGridEnvironUpdateListener(mapupdater);
		environ.getTimer().AddTimerEvent(mapupdater);
		environ.getTimer().AddTimerEvent(stateupdater);
		
		
		return map;
	}

	
	/**
	 * Load the run environment as in GUI
	 * @param environ
	 */
	public static MapInstance initGUI(RunEnviron environ) {
		MapInstance map = new MapInstance(environ.getGridEnviron().getWidth(), environ.getGridEnviron().getHeight(), 2);
		MapGridAlteration mapalterer = new MapGridAlteration(environ.getGridEnviron(), map);
		MapUpdater mapupdater = new MapUpdater(environ.getGridEnviron(), map);
		ITimerListener stateupdater = new RunStatusUpdater(map, initoptions.steplimit);
		
		map.AddGridMouseListener(mapalterer);
		map.AddGridMotionListener(mapalterer);
		map.AddGridKeyListener(mapalterer);
		environ.getGridEnviron().addGridEnvironUpdateListener(mapupdater);
		environ.getTimer().AddTimerEvent(mapupdater);
		environ.getTimer().AddTimerEvent(stateupdater);
		
		return map;
	}
}
