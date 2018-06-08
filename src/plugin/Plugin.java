package edu.cmu.cs.cs214.hw5.plugin;

import javax.swing.JPanel;

import edu.cmu.cs.cs214.hw5.framework.*;

/**
 * Plugin Interface
 * @author Hingon Miu & Raymond Xia
 *
 */
public interface Plugin {
	
	/**
	 * 
	 * @return The title of the plugin
	 */
	public String getName();
	
	/**
	 * Note that the whole workflow of data analysis and processing
	 * should also be included in the method to display a meaningful 
	 * diagram. It's made general because each plugin may have a huge
	 * distinction to each other in terms of data analysis implementation. 
	 * @return The display of the graph
	 */
	public JPanel getPanel();
	
	/**
	 * Register the plugin to the framework.
	 * @param framework
	 */
	public void registerFramework(Framework framework);
}
