package com.cs307.evant.evant;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/*
public class DatabaseInterface extends{
	// This variable will hold the connection to the data base
	private Connection con;
	
	// Constructor for the Interface
	public DatabaseInterface() throws ConnectionNotEstablishedException {
		System.out.println("establishing connection...");
		try {
			// This will load the MySQL driver, each DB has its own driver
			Class.forName("com.mysql.jdbc.Driver");
			// this will ceate the connection
			this.con = DriverManager.getConnection("jdbc:mysql://128.211.255.58:3306/humorbot", "sqluser", "sqlus    erpw");

		} catch (SQLException e) {
			System.out.println("connection failed to be established");
			System.out.println(e.getMessage());
			throw new ConnectionNotEstablishedException();
		} catch (ClassNotFoundException e) {
			System.out.println("failed to load JDBC drivers");
			throw new ConnectionNotEstablishedException();
		}
		System.out.println("connection established");
}

	public void closeConnection(){
		if (con != null) {
			try {
				con.close();
			} catch (SQLException e) {
				System.out.println("closing invalid connection");
			}
		}
		System.out.println("connection closed");
	}


	// adds a user to the database
	public int addUser(User user){
		//TODO
		return -1;
	}

	// add an event
	public int addEvent(Event event){
		//TODO
		return -1;
	}

	// add user to attending events
	public int addAttendees(Event event, User user){
		//TODO
		return -1;
	}

	// change event to already having happened
	public int EventFinished(String name){
		//TODO
		return -1;
	}

	// change event to already having happened
	public int EventFinished(Event name){
		//TODO
		return -1;
	}

	// gets a user by username
	public User getUser(String username){
		//TODO
		return null;
	}
	
	// verify login
	public User verifyLogin(String username, String password){
		//TODO
		return null
	}

	// get ecent by name
	public Event getEvent(String name){
		//TODO
		return null
	}
	
}

