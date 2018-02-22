import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;


public class DatabaseInterface {
    // this variable will hold if the connection is currently established
    public boolean connected = false;

    // This variable stores the connection to the database
    private Connection conn;

    // This variable stores the sql statment to be sent to the database
    private Statement stmt;

    // Constructor for the Interface
    public DatabaseInterface() throws ConnectionNotEstablishedException {
        connect();
    }

    // this function will either connect to the database or throw an error no need for a return value
    public void connect() throws ConnectionNotEstablishedException {
        System.out.println("establishing connection...");
        try {
            // This will load the MySQL driver, each DB has its own driver
            Class.forName("com.mysql.jdbc.Driver").newInstance();
            // this will ceate the connection
            this.conn = DriverManager.getConnection("jdbc:mysql://128.211.255.58:3306/evant", "sqluser", "sqluserpw");
            // this will create the statment through which queries are passed
            stmt = conn.createStatement();
        } catch (SQLException e) {
            // this exception occurs if the connection can not be established for any reason
            // we print information to the console for ease of debuging, and then throw a more general exception up
            System.out.println("connection failed to be established");
            System.out.println(e.getMessage());
            throw new ConnectionNotEstablishedException();
        } catch (ClassNotFoundException e) {
            // This exception happens if the JDBC library can not be found. Check that the project is set up correctly
            System.out.println("failed to load JDBC drivers");
            throw new ConnectionNotEstablishedException();
        } catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        // if no excceptions have been called we are okay and we should tell the user
        System.out.println("connection established");
        connected = true;
    }

    public void closeConnection(){
        if (conn != null) {
            try {
                conn.close();
            } catch (SQLException e) {
                System.out.println("closing invalid connection");
            }
        }
        System.out.println("connection closed");
        connected = false;
    }


	// adds a user to the database
	public boolean addUser(User user) throws ConnectionNotEstablishedException{
		// ensure that the connection is up
		if (!connected){
			connect();
		}
		
		// stores the username of a user
		String username = user.username;
		
		
		// stores a password of a user
		String password = user.password;
		
		// Stores the displayname of the user
		String displayname = user.displayname;
		
		// stores the users host rating
		int host_rating = user.host_rating;
		
		// I think these will change later
		int location = user.location;
		int radius = user.radius;
		String link_to_profile_picture = user.link_to_profile_picture;
		String link_to_facebook = user.link_to_facebook;
		String email = user.email;
		
		// the actual mysql query
		String query = "INSERT into user (username, display_name, password, host_rating, location, " +
				"radius, link_to_profile_picture, link_to_facebook, email) values ('" + username + "','" + displayname + "','" +
				password + "'," + host_rating + "," + location + "," + radius + ",'" + link_to_profile_picture + "','" + 
				link_to_facebook + "','" + email + "')";
		System.out.println("'" + username + "' added to database");
		try {
			stmt.execute(query);
			return true; 
		} catch (SQLException e) {
			System.out.println("Error with connection!");
			closeConnection();
			return false;
		}
	}

	// add an event
	public boolean addEvent(Event event){
		//TODO
		return false;
	}

	// add user to attending events
	public boolean addAttendees(Event event, User user){
		//TODO
		return false;
	}

	// change event to already having happened
	public boolean EventFinished(String name){
		//TODO
		return false;
	}

	// change event to already having happened
	public boolean EventFinished(Event name){
		//TODO
		return false;
	}

	// gets a user by username
	public User getUser(String username){
		//TODO
		return null;
	}
	
	// verify login
	public User verifyLogin(String username, String password){
		//TODO
		return null;
	}

	// get ecent by name
	public Event getEvent(String name){
		//TODO
		return null;
	}
	
}

