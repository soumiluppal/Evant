package com.cs307.evant.evant;
import java.sql.Date;
import java.sql.Time;


public class Event {
	// name of the event
	public String eventName;
	// time of the event
	public Time time;
	// date of the event
	public Date date;
	// rating of the event
	public int rating;
	// Description of the event
	public String discription;
	// Wither or not the event is private
	public boolean is_private;
	// max number of attendees
	public int max_attendees;
	public User host;
	
	// i think these two will change 
	public String link_to_picture;
	public int eventLocation;
}
