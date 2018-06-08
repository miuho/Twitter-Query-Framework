package edu.cmu.cs.cs214.hw5.plugin;

import java.awt.*;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

import javax.swing.*;

import edu.cmu.cs.cs214.hw5.framework.*;

/**
 * SamplePlugin is a sample class that we provide to illustrate how 
 * plugins work in our framework.
 * @author Hingon Miu & Raymond Xia
 *
 */
public class SamplePlugin implements Plugin{
	private Framework framework;
	private JPanel pluginPanel;
	private ChartModel chartModel1;
	private PieChart pieChart1;
	
	/**
	 * Constructor that initializes the SamplePlugin
	 * 
	 */
	public SamplePlugin() {
	}
	
	/**
	 * provide a name for the plugin
	 * 
	 * @return The title of the plugin
	 */
	public String getName() {
		return "Friends Count";
	}
	
	/**
	 * Note that the whole workflow of data analysis and processing
	 * should also be included in the method to display a meaningful 
	 * diagram. It's made general because each plugin may have a huge
	 * distinction to each other in terms of data analysis implementation. 
	 * @return The display of the graph
	 */
	public JPanel getPanel() {
		String groupName = null;
		String userName = null;
		
		FrameworkGUI temp = framework.getFrameworkGUI();
		
		Hashtable<String, Hashtable<String, SearchResult>> groupNameToUserNameToSearchResult 
			= temp.getHashtable();
		
		Hashtable<String, SearchResult> userNameToSearchResult = null;
		SearchResult search = null;
		
		List<JLabel> labels = temp.getLabels();
		
		
		/* Note: the following code is vital in every plugin's implementation.
		 * We distinguish group names from usernames by looking at the "- " mark
		 * in each element of labels. If the "- " mark doesn't show up in the string,
		 * that string's known as a group name. If the "- " mark shows up at the beginning
		 * of the string, that string's known as a user name. Since we put both types of
		 * names in one array of strings, we need this distinction to separate them in order
		 * to: 1. take username as input to get the search result, thus getting the numerical
		 * data you want for the plugin, and 2. use group name to add(or whatever operation the 
		 * plugin provides) the numerical values together to form the data of that whole group.
		 * */
		// counts the number of groups registered right now
		int groupCount = 0;
		for (int i = 0; i < labels.size(); i++) {
    		JLabel tempLabel = labels.get(i);
    		String tempString = tempLabel.getText();
    		if (!tempString.contains("- ")) {
    			groupCount++;
    		}
		}
		
		int[] data = new int[groupCount];
		String[] dataName = new String[groupCount];
		
		// initializes the data list to be zeros
		for (int i = 0; i < groupCount; i++) {
			data[i] = 0;
		}
		
		int groupIndex = 0;
		for (int i = 0; i < labels.size(); i++) {
    		JLabel tempLabel = labels.get(i);
    		String tempString = tempLabel.getText();
    		// if this is a groupName
    		if (!tempString.contains("- ")) {
    			groupName = tempString;
    			
    			dataName[groupIndex] = groupName;
    			groupIndex++;
    			// get the userNameToSearchResult hashtable from the main hashtable
    			userNameToSearchResult = groupNameToUserNameToSearchResult.get(groupName);
    		}
    		// if this is a userName
    		if (tempString.contains("- ") && tempString.indexOf("- ") == 0) {
    			userName = tempString.substring("- ".length(), tempString.length());
    			
    			search = userNameToSearchResult.get(userName);
    			// accumulates the data into the right index of the list
    			data[groupIndex-1] += search.getFriendsCount();
    		}
    	}
		
		this.chartModel1 = new ChartModel(data, dataName);
		this.pieChart1 = new PieChart();
		this.pieChart1.setBackground(Color.ORANGE);
		
		pluginPanel = new JPanel();
		pluginPanel.setLayout(new GridLayout(1, 2));
		
		JLabel tempLabel = new JLabel(getName());
		this.pieChart1.add(tempLabel);

		this.pieChart1.setModel(this.chartModel1);
		pluginPanel.add(this.pieChart1);
		return pluginPanel;
	}
	
	/**
	 * Register the plugin to the framework.
	 * @param framework
	 */
	public void registerFramework(Framework framework) {
		this.framework = framework;
	}
}