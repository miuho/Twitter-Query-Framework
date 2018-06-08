package edu.cmu.cs.cs214.hw5.framework;

import javax.swing.JButton;
import javax.swing.JRadioButtonMenuItem;

import edu.cmu.cs.cs214.hw5.plugin.*;

import twitter4j.*;

/**
 * This is the Framework class that registers the plugins.
 * @author Hingon Miu & Raymond Xia
 */
public class Framework {
	// stores the framework GUI class
	private FrameworkGUI parentFrame;
	// indicates the maximum allowed plugins to be registered
	private final int NUM_OF_PLUGINS = 10;
	// stores the pointers of the plugins
	private Plugin[] plugins = new Plugin[NUM_OF_PLUGINS];
	// stores the number of plugins registered
	private static int numOfRegPlugins;
	
	/**
	 * Constructor that initiates a Framework
	 *
	 */
	public Framework() {
		numOfRegPlugins = 0;
	}
	
	/**
	 * To register each plugin to the Framework
	 * 
	 * @param newPlugin
	 * 			The input Plugin class pointer
	 *
	 */
	public void registerPlugin(Plugin newPlugin) {
		newPlugin.registerFramework(this);
		plugins[numOfRegPlugins] = newPlugin;
		numOfRegPlugins++;
	}
	
	
	/**
	 * Confirms that all the plugins are now registered and so 
	 * can now creates the framework GUI class
	 *
	 */
	public void confirmPlugins() {
		parentFrame = new FrameworkGUI(plugins, numOfRegPlugins);
	}
	
	/**
	 * Get the GUI class of the Framwork
	 *
	 */
	public FrameworkGUI getFrameworkGUI() {
		return parentFrame;
	}
}
