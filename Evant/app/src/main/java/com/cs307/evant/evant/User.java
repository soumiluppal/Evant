package com.cs307.evant.evant;

// This class will be used to pass user info around
public class User {
	// stores the username of a user
	public String username;
	
	// stores a password of a user
	public String pass;
	
	// Stores the displayname of the user
	public String displayname;
	public String email;
	// stores the users host rating
	public int host_rating;
	
	// I think these will change later
	public int location;
	public int radius;
	public String link_to_progile_picture;
	public String link_to_facebook;

	public  User(String name,String password,String emailN)
	{
		username = name;
		pass = password;
		email = emailN;
	}
}
