package edu.cmu.cs.cs214.hw5.framework;

import java.util.ArrayList;
import java.util.List;

import edu.cmu.cs.cs214.hw5.plugin.*;
import twitter4j.*;

/**
 * The framework is responsible to write the main method, plugin
 * developers can simply write their plugins implementing the
 * Plugin interface, then register their plugins below.
 * @author Hingon Miu & Raymond Xia 
 */
public class main {

	/**
	 * @param args
	 */
	public static void main(String[] args) {		
		Framework framework = new Framework();
		
		// register plugins below (at most 10 plugins registered)
		framework.registerPlugin(new SamplePlugin());
		framework.registerPlugin(new SamplePlugin());
		framework.registerPlugin(new SamplePlugin());
		framework.registerPlugin(new SamplePlugin());
		framework.registerPlugin(new SamplePlugin());
		
		framework.confirmPlugins(); // signifies that all plugins are registered
	}

}
