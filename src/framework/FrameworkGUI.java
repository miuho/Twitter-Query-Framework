package edu.cmu.cs.cs214.hw5.framework;


import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

import javax.swing.*;

import edu.cmu.cs.cs214.hw5.plugin.Plugin;

/**
 * This is the Framework GUI class that displays the
 * Framework JPanel.
 * @author Hingon Miu & Raymond Xia
 * */
public class FrameworkGUI {
	// stores the main panel's width
	private final int MAIN_WIDTH = 1500;
	// stores the main panel's height
	private final int MAIN_HEIGHT = 1000;
	// stores the number of rows of the labels of the control panel
	private final int NUM_OF_ROWS = 6;
	// stores the number of labels of the left panel
	private final int NUM_OF_LABELS = 30;
	// input field to the groupName
	private JTextField grouptextfield;
	// input field to the groupName - userName
	private JTextField usertextfield;
	// stores the labels of all groupName and userName
    private List<JLabel> labels = new ArrayList<JLabel>();
    // stores the labels for later to update text
    private JLabel[] labelDisplays = new JLabel[NUM_OF_LABELS];
    // stores the buttons of plugins for later to update text
    private JButton[] pluginDisplays;
    // stores the boolean whether the plugins are being displayed or not
    private boolean[] pluginsDisplayed;
    // stores the panels of displaying the plugin graphs
    private JPanel[] pluginPanels;
    // stores the main JFrame
    private JFrame parentFrame;
    // stores the main Panel
    private JPanel mainPanel;
    // stores the display panel in the center of main panel
    private JPanel displayPanel;
    // stores the control panel in the left of main panel
    private JPanel controlPanel;
    // stores labels that displays users and groups
    private JPanel labelsPanel;
    // stores plugins panel that displays plugin buttons
    private JPanel pluginsPanel;
    // stores the plugin pointers
    private Plugin[] plugins;
    // stores the number of registered plugins
    private final int numOfRegPlugins;
    // the main hashtable that stores all the userNameToSearchResult hashtables
    // with groupName as keys, then the userNameToSearchResult are hashtables that stores
    // SearchResult with userName as keys
    Hashtable<String, Hashtable<String, SearchResult>> groupNameToUserNameToSearchResult;
    
    /**
	 * Returns the list of labels of users and groups
	 *
	 */
    public List<JLabel> getLabels() {
    	return labels;
    }
    
    /**
	 * Returns the main hashtable of all search results
	 *
	 */
    public Hashtable<String, Hashtable<String, SearchResult>> getHashtable() {
    	return groupNameToUserNameToSearchResult;
    }
	
    /**
	 * Constructor that initiates the FrameworkGUI class
	 * 
	 * @param plugins
	 * 				Input plugins pointers
	 * @param numOfRegPlugins
	 * 				Number of registered plugins
	 *
	 */
	public FrameworkGUI(Plugin[] plugins, int numOfRegPlugins) {
		// initialize variables
		this.pluginDisplays = new JButton[numOfRegPlugins];
		this.pluginsDisplayed = new boolean[numOfRegPlugins];
		this.pluginPanels = new JPanel[numOfRegPlugins];
		this.plugins = plugins;
		this.numOfRegPlugins = numOfRegPlugins;
		groupNameToUserNameToSearchResult = new Hashtable<String, Hashtable<String, SearchResult>>();
		
		for (int i = 0; i < numOfRegPlugins; i++) {
			pluginsDisplayed[i] = false;
		}
		
		// Set-up the menu
		parentFrame = new JFrame("Framework - by hmiu & yangyanx");
		parentFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		mainPanel = new JPanel();
		mainPanel.setLayout(new BorderLayout());
		mainPanel.setPreferredSize(new Dimension(MAIN_WIDTH, MAIN_HEIGHT));
		
		controlPanel = new JPanel();
		controlPanel.setLayout(new BorderLayout());
		
		displayPanel = new JPanel();
		displayPanel.setLayout(new GridLayout(2, 2, 10, 10));
		displayPanel.setBackground(Color.GRAY);
		
		// insert into the control panel
		createTextFields();
		createLabelDisplays();
		createPluginDisplays();
		
		mainPanel.add(controlPanel, BorderLayout.WEST);
        
		mainPanel.add(displayPanel, BorderLayout.CENTER);
		
		parentFrame.setContentPane(mainPanel);

        // Display the window.
		parentFrame.pack();
		parentFrame.setVisible(true);
	}
	
	/**
	 * Check whether the hashtable contain the specific user search result
	 * 
	 * @param groupName
	 * 				String input of groupName
	 * @param userName
	 * 				String input of userName
	 * @return
	 * 				Whether the hashtable contain the specific user search result
	 *
	 */
	private boolean checkContainsUser(String groupName, String userName) {
		// check if the groupName is a key
		if (!groupNameToUserNameToSearchResult.containsKey(groupName)) {
			return false;
		}
		
		Hashtable<String, SearchResult> temp = 
				groupNameToUserNameToSearchResult.get(groupName);
		
		// check if the userName is a key
		if (!temp.containsKey(userName)) {
			return false;
		}
		
		return true;
	}
	
	/**
	 * Get the data with the SearhResult class
	 * 
	 * @param groupName
	 * 				String input of groupName
	 * @param userName
	 * 				String input of userName
	 * @return
	 * 				The SearchResult created, or null if invalid input
	 *
	 */
	private SearchResult getData(String groupName, String userName) {
		// check if the hashtable contains the SearchResult
		if (checkContainsUser(groupName, userName)) {
			return groupNameToUserNameToSearchResult.get(groupName).get(userName);
		}
		
		else {
			
			SearchResult search = new SearchResult(userName);
			boolean valid = search.isValidInput();
			// check whether the input userName is valid
			if (!valid) {
				return null;
			}
			
			return search;
		}
	}
	
	/**
	 * Create the plugins display panel.
	 * 
	 *
	 */
	private void createPluginDisplays() {
		pluginsPanel = new JPanel();
		pluginsPanel.setLayout(new GridLayout(numOfRegPlugins + 1, 1, 1, 1));
		
		JLabel label = new JLabel("Plugins:");
		label.setFont(new Font("Serif", Font.PLAIN, 18));
		label.setForeground(Color.BLACK);
		pluginsPanel.add(label);
		
		// add the buttons of each plugins 
		for (int i = 0; i < numOfRegPlugins; i++) {
			final String name = plugins[i].getName();
			JButton temp = new JButton(name);
			temp.setFont(new Font("Serif", Font.PLAIN, 15));
			pluginDisplays[i] = temp;
			final int index = i;
			temp.setFocusable(false);
			temp.addActionListener(new ActionListener() {
	            public void actionPerformed(ActionEvent event) {
	            	if (pluginsDisplayed[index] == false) {
	            		// display the plugin panel
	            		JPanel temp = plugins[index].getPanel();
		            	displayPanel.add(temp);
		        		parentFrame.setResizable(true);
		        		parentFrame.pack();
		        		pluginPanels[index] = temp;
		        		pluginsDisplayed[index] = true;
		        		JOptionPane.showMessageDialog(parentFrame,
		        				name + " has just been added!\n",
		                       	"Successful!",
		                		JOptionPane.INFORMATION_MESSAGE);
		        		return;
	            	}
	            	else {
	            		// if click the plugin button more than once
	            		JOptionPane.showMessageDialog(parentFrame,
	                        name + " has aleady been added!\n",
	                       	"Click Another Plugin!",
	                		JOptionPane.INFORMATION_MESSAGE);
	            		return;
	            	}
	            }
			});
			pluginsPanel.add(temp);
        }
		
		controlPanel.add(pluginsPanel, BorderLayout.SOUTH);
	}
	
	/**
	 * Create the labels display panel.
	 * 
	 * 
	 */
	private void createLabelDisplays() {
		labelsPanel = new JPanel();
		labelsPanel.setLayout(new GridLayout(NUM_OF_LABELS + 1, 1, 1, 1));
		labelsPanel.setBackground(Color.WHITE);
		
		JLabel label = new JLabel("Groups & Users:");
		label.setFont(new Font("Serif", Font.PLAIN, 15));
		labelsPanel.add(label);
		// displays the users and groups labels on panel
		for (int i = 0; i < NUM_OF_LABELS; i++) {
			JLabel temp = new JLabel(" ");
			temp.setFont(new Font("Serif", Font.PLAIN, 13));
			labelDisplays[i] = temp;
			labelsPanel.add(temp);
        }
		
		controlPanel.add(labelsPanel, BorderLayout.CENTER);
	}
	
	/**
	 * Refresh all the labels on the panel
	 * 
	 * 
	 */
	private void resetLabels() {
        for (int i = 0; i < labels.size(); i++) {
            labelDisplays[i].setText(labels.get(i).getText());
        }
	}

	/**
	 * Create the text fields in the display panel.
	 * 
	 * 
	 */
	private void createTextFields() {
		JPanel textsPanel = new JPanel();
		textsPanel.setLayout(new GridLayout(NUM_OF_ROWS, 1, 5, 5));
		
        JLabel grouplabel = new JLabel("Add Group:");
        grouplabel.setFont(new Font("Serif",Font.PLAIN, 16));

        textsPanel.add(grouplabel);

        grouptextfield = new JTextField("GroupName", 20);
        grouptextfield.addActionListener(new ActionListener()
            {
                public void actionPerformed(ActionEvent evento1)
                {
                    String sentence = grouptextfield.getText();
                    // check if the same group exist
                    if (checkGroup(sentence)) {
                    	JOptionPane.showMessageDialog(parentFrame,
                                "Please Retry!\nThere is already a group of " + sentence + "!",
                                "Wrong Input!",
                                JOptionPane.INFORMATION_MESSAGE);
                        return;
                    }
                    
                    JLabel label = new JLabel();
                    label.setText(sentence);
                    label.setFont(new Font("Serif",Font.PLAIN, 14));
                    labels.add(label); // add in the group label
                    
                    // refresh the display panel
                    resetLabels();
                    
                    JOptionPane.showMessageDialog(parentFrame,
                            "Created a new group of " + sentence + "!",
                            "Great!",
                            JOptionPane.INFORMATION_MESSAGE);
                    return;
                }
            });
        
        textsPanel.add(grouptextfield);
        
        JLabel userlabel = new JLabel("Add User:");
        userlabel.setFont(new Font("Serif",Font.PLAIN, 16));

        textsPanel.add(userlabel);

        usertextfield = new JTextField("GroupName - UserName", 20);
        usertextfield.addActionListener(new ActionListener()
            {
                public void actionPerformed(ActionEvent evento1)
                {

                    String sentence = usertextfield.getText();
                    // check if a group existed first
                    if (labels.size() == 0) {
                    	JOptionPane.showMessageDialog(parentFrame,
                                "Please Retry!\nThere is no group yet!\n" +
                                "Create a group first before adding a user!\n" +
                                "eg. Celebrity - Justin Bieber\n" +
                                "Hit \"refresh\" to display plugins' graphics!",
                                "Wrong Input!",
                                JOptionPane.INFORMATION_MESSAGE);
                        return;
                    }
                    // check if the format is correct
                    else if (!checkFormat(sentence)) {
                    	JOptionPane.showMessageDialog(parentFrame,
                                "Please Retry!\nFollow the proper format!\n" +
                                "\"GroupName - UserName\"!\n" +
                                "eg. Celebrity - Justin Bieber\n" +
                                "Hit \"refresh\" to display plugins' graphics!",
                                "Wrong Input!",
                                JOptionPane.INFORMATION_MESSAGE);
                        return;
                    }
                    
                    String groupName = getGroupName(sentence);
                    String userName = getUserName(sentence);
                    
                    // check if the group is correct
                    if (!checkGroup(groupName)) {
                    	JOptionPane.showMessageDialog(parentFrame,
                                "Please Retry!\nThere is no such group!\n" +
                                "Create a new group of " + groupName + " first!\n" +
                                "eg. Celebrity - Justin Bieber\n" +
                                "Hit \"refresh\" to display plugins' graphics!",
                                "Wrong Input!",
                                JOptionPane.INFORMATION_MESSAGE);
                        return;
                    }
                    
                    // check if the user already existed
                    if (checkUser(userName)) {
                    	JOptionPane.showMessageDialog(parentFrame,
                                "Please Retry!\nThere is already a user of " + userName + "!\n" +
                                "eg. Celebrity - Justin Bieber\n" +
                                "Hit \"refresh\" to display plugins' graphics!",
                                "Wrong Input!",
                                JOptionPane.INFORMATION_MESSAGE);
                        return;
                    }

                    SearchResult search = getData(groupName, userName);
                    
                    // check if the input user if valid
                    if (search == null) {
                    	JOptionPane.showMessageDialog(parentFrame,
                                userName + " does not exist!\n" +
                                "eg. Celebrity - Justin Bieber\n" +
                                "Hit \"refresh\" to display plugins' graphics!",
                                "Wrong Input!",
                                JOptionPane.INFORMATION_MESSAGE);
                        return;
                    }
                    
                    else {
                    	// if no such user search result is stored
                    	if (!checkContainsUser(groupName, userName)) {
                    		// if no such group is created
                    		if (!groupNameToUserNameToSearchResult.containsKey(groupName)) {
                    			Hashtable<String, SearchResult> userNameToSearchResult = 
                        				new Hashtable<String, SearchResult>();
                    			userNameToSearchResult.put(userName, search);
                    			groupNameToUserNameToSearchResult.put(groupName, userNameToSearchResult);
                    		}
                    		else {
                    			// stores under the same group name if a group exists
                    			Hashtable<String, SearchResult> userNameToSearchResult = 
                    					groupNameToUserNameToSearchResult.get(groupName);
	                    		userNameToSearchResult.put(userName, search);
                    		}
                    	}
                    	
                    	int index = userPosition(groupName);
	                    JLabel label = new JLabel();
	                    label.setText("- " + userName);
	                    label.setFont(new Font("Serif",Font.PLAIN, 14));
	                    labels.add(index, label); // add in the user label
	                    
	                    // refresh the display panel
	                    resetLabels();
	                    
	                    JOptionPane.showMessageDialog(parentFrame,
                                "Created a new user of " + userName + "!\n" +
                                "Hit \"refresh\" to display plugins' graphics!",
                                "Great!",
                                JOptionPane.INFORMATION_MESSAGE);
	                    
                        return;
                    }
                }
            });
        
        textsPanel.add(usertextfield);
        
        JButton refresh = new JButton("Refresh Plugins' Displays");
        refresh.setFocusable(false);
        refresh.addActionListener(new ActionListener() {
		    public void actionPerformed(ActionEvent event) {
		    	// check if a group exists, or if an empty group exists
		    	if ((labels.size() == 0) || checkAdjacentGroups() || 
		    		(labels.size() == 1 && !labels.get(0).getText().contains("- "))) {
		    		JOptionPane.showMessageDialog(parentFrame,
	                        "It needs at least one User per Group to analyze data!",
	                        "Wrong Input!",
	                        JOptionPane.INFORMATION_MESSAGE);
			    	return;
		    	}
		    	
		    	// redraw the plugin panels
		    	redraw();
		    	
		    	JOptionPane.showMessageDialog(parentFrame,
                        "All displays are refreshed!",
                        "Refreshed!",
                        JOptionPane.INFORMATION_MESSAGE);
		    	
		    	return;
		    }
		});
        textsPanel.add(refresh);
        
        JButton update = new JButton("Update Search Results");
        update.setFocusable(false);
        update.addActionListener(new ActionListener() {
		    public void actionPerformed(ActionEvent event) {
		    	// check if a group exists, or if an empty group exists
		    	if ((labels.size() == 0) || checkAdjacentGroups() || 
			    	(labels.size() == 1 && !labels.get(0).getText().contains("- "))) {
			    	JOptionPane.showMessageDialog(parentFrame,
		                "It needs at least one User per Group to analyze data!",
		                "Wrong Input!",
		                JOptionPane.INFORMATION_MESSAGE);
			    	return;
			    }
		    	
		    	// recreates a new main hashtable
		    	groupNameToUserNameToSearchResult = new Hashtable<String, Hashtable<String, SearchResult>>();
		    	
		    	String groupName = null;
		    	
		    	// use SearchResult class to use twitter4j to resend all 
		    	// username to update newest info
		    	for (int i = 0; i < labels.size(); i++) {
		    		JLabel tempLabel = labels.get(i);
		    		String tempString = tempLabel.getText();
		    		if (!tempString.contains("- ")) {
		    			groupName = tempString;
		    		}
		    		if (tempString.contains("- ") && tempString.indexOf("- ") == 0) {
		    			String tempSubString 
		    					= tempString.substring("- ".length(), tempString.length());
		    			SearchResult search 
		    					= getData(groupName, tempSubString);
		    			
		    			// redo all the searches to update information
		    			if (search != null) {
			    			if (!groupNameToUserNameToSearchResult.containsKey(groupName)) {
	                			Hashtable<String, SearchResult> userNameToSearchResult = 
	                    				new Hashtable<String, SearchResult>();
	                			userNameToSearchResult.put(tempSubString, search);
	                			groupNameToUserNameToSearchResult.put(groupName, userNameToSearchResult);
	                		}
	                		else {
	                			// stores under the same group name if a group exists
	                			Hashtable<String, SearchResult> userNameToSearchResult = 
	                					groupNameToUserNameToSearchResult.get(groupName);
	                    		userNameToSearchResult.put(tempSubString, search);
	                		}
		    			}
		    		}
		    	}
		    	
		    	// redraw the plugin panels
		    	redraw();
		    	
		    	JOptionPane.showMessageDialog(parentFrame,
                        "All rearch results are updated!",
                        "Updated!",
                        JOptionPane.INFORMATION_MESSAGE);
		    	
		    	return;
		    }
		});
        textsPanel.add(update);
        
        controlPanel.add(textsPanel, BorderLayout.NORTH);
    }
	
	/**
	 * Check if two groups are adjacent which means at least one group is empty.
	 * 
	 * @return
	 * 			whether there are adjacent groups
	 */
	private boolean checkAdjacentGroups() {
		for (int i = 0; i < labels.size(); i++) {
    		JLabel tempLabel1 = labels.get(i);
    		String tempString1 = tempLabel1.getText();
    		if ((i+1 < labels.size()) && !tempString1.contains("- ")) {
    			JLabel tempLabel2 = labels.get(i+1);
        		String tempString2 = tempLabel2.getText();
    			if (!tempString2.contains("- ")) {
    				return true;
    			}
    		}
		}
		return false;
	}
	
	/**
	 * Redraw all plugins panels. (refresh)
	 * 
	 */
	private void redraw() {
		for (int i = 0; i < numOfRegPlugins; i++) {
			if (pluginsDisplayed[i] == true) {
				JPanel temp = plugins[i].getPanel();
				displayPanel.remove(pluginPanels[i]);
				pluginPanels[i] = temp;
            	displayPanel.add(temp);
        		parentFrame.setResizable(true);
        		parentFrame.pack();
			}
		}
	}
	
	/**
	 * Return the user poistion of the label list.
	 * 
	 * @param groupName
	 * 			The String groupName
	 * @return
	 * 			The int user position
	 * 
	 */
	private int userPosition(String groupName) {
		for (int i = 0; i < labels.size(); i++) {
    		JLabel tempLabel = labels.get(i);
    		String tempString = tempLabel.getText();
    		if (tempString.equals(groupName)) {
    			return i + 1;
    		}
    	}
		// should never get here
    	return 0;
	}
	
	/**
	 * Check if the user is already added.
	 * 
	 * @param userName
	 * 			The String userName
	 * @return
	 * 			The boolean whether the user is already added.
	 * 
	 */
	private boolean checkUser(String userName) {
    	for (int i = 0; i < labels.size(); i++) {
    		JLabel tempLabel = labels.get(i);
    		String tempString = tempLabel.getText();
    		if (tempString.contains("- ") && tempString.indexOf("- ") == 0) {
    			String tempSubString
    					= tempString.substring("- ".length(), tempString.length());
    			if (tempSubString.equals(userName)) {
    				return true;
    			}
    		}
    	}
    	return false;
    }
	
	/**
	 * Check if the group is already added.
	 * 
	 * @param userName
	 * 			The String groupName
	 * @return
	 * 			The boolean whether the group is already added.
	 * 
	 */
	private boolean checkGroup(String groupName) {
    	for (int i = 0; i < labels.size(); i++) {
    		JLabel tempLabel = labels.get(i);
    		String tempString = tempLabel.getText();
    		if (tempString.equals(groupName)) {
    			return true;
    		}
    	}
    	return false;
    }
	
	/**
	 * Check if the format is correct.
	 * 
	 * @param sentence
	 * 			The String sentence input
	 * @return
	 * 			The boolean whether the format is correct.
	 * 
	 */
    private boolean checkFormat(String sentence) {
    	if (sentence.length() < (" - ".length() + 2)) {
    		return false;
    	}
    	
    	if (!sentence.contains(" - ")) {
    		return false;
    	}
    	
    	if (sentence.indexOf(" - ") == 0) {
    		return false;
    	}
    	
    	return true;
    }
    
    /**
	 * Returns the groupName from the input sentence.
	 * 
	 * @param sentence
	 * 			The String sentence input
	 * @return
	 * 			The String groupName.
	 * 
	 */
    private String getGroupName(String sentence) {
    	return sentence.substring(0, sentence.indexOf(" - "));
    }
    
    /**
	 * Returns the userName from the input sentence.
	 * 
	 * @param sentence
	 * 			The String sentence input
	 * @return
	 * 			The String userName.
	 * 
	 */
    private String getUserName(String sentence) {
    	return sentence.substring(sentence.indexOf(" - ") + " - ".length(), sentence.length());
    }
}
