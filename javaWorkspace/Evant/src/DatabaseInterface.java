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
            Class.forName("com.mysql.jdbc.Driver");
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
		return null;
	}

	// get ecent by name
	public Event getEvent(String name){
		//TODO
		return null;
	}
	
}

