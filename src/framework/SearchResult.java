package edu.cmu.cs.cs214.hw5.framework;
import twitter4j.*;

import java.util.List;
import java.util.ArrayList;

/**
 * SearchResult is the class of results returned from the query.
 * The class takes in a Twitter user name(note: it's not the "@username"
 * but the name displayed on that user's home page. e.g. "Justin Bieber"
 * instead of "@justinbieber"). Also the user name it takes in should be
 * an exact match of the user name if a non-empty result is expected. If 
 * there's no perfect match of the string input, an instance of SearchResult
 * will still be created, but the fields are are initialized to be null, "",
 * or 0, regarding to their specific types.
 * @author Hingon Miu & Raymond Xia
 *
 */
public class SearchResult {
	
	private String name = "";
	private long userID = 0;
	private List<String> friends = null;
	private List<String> followers = null;
	private String location = ""; //location of the user
	
	private TwitterStatus status = null;
	private int favoritesCount = 0;
	private int friendsCount = 0;
	private int followersCount = 0;
	private String timeZone = "";
	
	private Twitter twitter;
	
	private boolean found; //signal valid search result or not
	
	public SearchResult(String userName) { //take in the userName to query
		TwitterFactory factory = new TwitterFactory();
		this.twitter = factory.getInstance();

		this.found = false;
		if (isValidConnection(userName)){
			try{
				//should never raise exception
				List<User> users = twitter.searchUsers(userName, 1); //looking for first 100 matches
				if (users.size() != 0){
					//there is search result
					User current = null;
					for (int i = 0; i < users.size(); i++){
						current = users.get(i);
						if (current.getName().equals(userName)){
							this.found = true; //found the exact user name!
							break;
						}
					}
					
					if (this.found){
						
						//found the exact user name, fetch information now
						this.name = userName;
						this.userID = current.getId();
						IDs friendsID = twitter.getFriendsIDs(userID, -1);
						long[] friendsIDs = friendsID.getIDs();
						
						
						
						this.friends = getNames(friendsIDs);
						
						
						IDs followersID = twitter.getFollowersIDs(userID, -1);
						long[] followersIDs = followersID.getIDs();
						
						
						
						this.followers = getNames(followersIDs);
						
						this.location = current.getLocation();
						
						this.favoritesCount = current.getFavouritesCount();
						this.friendsCount = current.getFriendsCount();
						this.followersCount = current.getFollowersCount();
						
						
						this.timeZone = current.getTimeZone();
						if(current.getStatus() != null){
							this.status = new TwitterStatus(current);
						}
						else this.status = null;
						
					}

				}
			}
			catch(TwitterException e){	
				System.out.println(e.getErrorMessage());
			}
		}
	}
	
	//Convert an array of Twitter IDs to an array of user names.
	//If the array's length is bigger than 5, the returned array
	//will only show up the first 5 user names that are converted 
	//from the IDs.
	private List<String> getNames(long[] IDs){
		List<String> names = new ArrayList<String>();
		TwitterFactory factory = new TwitterFactory();
		Twitter twit = factory.getInstance();
		if(IDs == null) return names;
		int min = (IDs.length < 5) ? IDs.length : 5;
		try{
			for (int i = 0; i < min; i++){
				User current = twit.showUser(IDs[i]);
				String currentName = current.getName();
				names.add(currentName);
			}
			return names;
		}
		catch(TwitterException e){
			return null;
		}
	}
	
	//check if the Internet connection is valid
	private boolean isValidConnection(String userName){
		try{
			List<User> users = twitter.searchUsers(userName, 1);
		}
		catch (TwitterException e) {
			return false;
		}
		return true;
	}
	
	/**
	 * 
	 * @return the userName of the user
	 */
	public String getName(){
		return this.name;
	}
	
	/**
	 * 
	 * @return the user's ID
	 */
	public long getUserID(){
		return this.userID;
	}
	
	/**
	 * 
	 * @return a list of user's friends' names
	 */
	public List<String> getFriends(){
		return this.friends;
	}
	
	/**
	 * 
	 * @return a list of user's followers' names
	 */
	public List<String> getFollowers(){
		return this.followers;
	}
	
	/**
	 * 
	 * @return the location of the user
	 */
	public String getLocation(){
		return this.location;
	}
	
	/**
	 * 
	 * @return the user's TwitterSatus
	 */
	public TwitterStatus getTwitterStatus(){
		return this.status;
	}
	
	/**
	 * 
	 * @return if the input string has a perfect match in Twitter database
	 */
	public boolean isValidInput(){
		return this.found;
	}
	
	/**
	 * 
	 * @return the time zone in which the user's in
	 */
	public String getTimeZone(){
		return this.timeZone;
	}
	
	/**
	 * 
	 * @return the favorites count of the user
	 */
	public int getFavoritesCount(){
		return this.favoritesCount;
	}
	
	
	/**
	 * 
	 * @return the number of friends the user has
	 */
	public int getFriendsCount(){
		return this.friendsCount;
	}
	
	/**
	 * 
	 * @return the number of followers the user has
	 */
	public int getFollowersCount(){
		return this.followersCount;
	}
}
