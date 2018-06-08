package edu.cmu.cs.cs214.hw5.framework;
import twitter4j.*;

import java.util.Date;

/**
 * TwitterStatus is a class which contains all the information related to a status
 * of a user. It includes 5 fields: statusPlace which is the country in which the status
 * is posted, statusGeo which is the (latitude,longitude) coordinate of the location,
 * status which is the content of the post, isFavorited which is whether the status is
 * favorited, and createdAt which is the date the status is created. Notice that statusPlace
 * and statusGeo are possible to be empty("" and null), if the user doen't allow the features. 
 * @author Hingon Miu & Raymond Xia
 *
 */
public class TwitterStatus {
	
	//null if it's not available
	private String statusPlace; // the country in which the status is posted
	
	//geography coordinate in which the status is posted
	//index 0 stores the latitude of the location
	//index 1 stores the longitude of the location
	//null if it's not available
	private double[] statusGeo = new double[2];
	
	//null if not available
	private String status; //current status
	private boolean isFavorited;
	
	private Date createdAt;
		
	public TwitterStatus(User user){
		Status current = user.getStatus(); //ensured not to be null
		this.status = current.getText();
		this.createdAt = current.getCreatedAt();
		this.isFavorited = current.isFavorited();
		Place statusPlace = current.getPlace();
		if(statusPlace != null){
			System.out.println(statusPlace==null);
			this.statusPlace = statusPlace.getCountry();
			if (current.getGeoLocation() != null){
				GeoLocation statusGeo = current.getGeoLocation();
				this.statusGeo[0] = statusGeo.getLatitude();
				this.statusGeo[1] = statusGeo.getLongitude();
			}
			else this.statusGeo = null;
		}
		else{
			this.statusPlace = "";
			this.statusGeo = null;
		}
	}
	
	
	/**
	 *  
	 * @return where the status is sent
	 */
	public String getStatusPlace(){
		return this.statusPlace;
	}
	
	/**
	 * 
	 * @return the status' content
	 */
	public String getStatus(){
		return this.status;
	}
	
	/**
	 * 
	 * @return the latitude and longitude of where the status is sent
	 */
	public double[] getStatusGeo(){
		return this.statusGeo;
	}
	
	/**
	 * 
	 * @return the date when the status is sent
	 */
	public Date getCreatedAt(){
		return this.createdAt;
	}
	
	/**
	 * 
	 * @return if the status is favorited
	 */
	public boolean isFavorited(){
		return this.isFavorited;
	}
}
