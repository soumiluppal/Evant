import static org.junit.Assert.*;

import org.junit.Test;


public class DatabaseInterfaceTest {

	@Test
	public void ConnectToDatabase() {
		boolean pass = false;
		try {
			DatabaseInterface db = new DatabaseInterface();
			pass = true;
		} catch (ConnectionNotEstablishedException e) {
			// TODO Auto-generated catch block
			pass = false;
		}
		assertTrue(pass);
	}
	
	@Test
	public void DisconnectFromDatabase() {
		boolean pass = false;
		try {
			DatabaseInterface db = new DatabaseInterface();
			db.closeConnection();
			pass = true;
		} catch (ConnectionNotEstablishedException e) {
			// TODO Auto-generated catch block
			pass = false;
		}
		assertTrue(pass);
	}
	
	@Test
	public void ReConnectToDatabase() {
		boolean pass = false;
		try {
			DatabaseInterface db = new DatabaseInterface();
			db.closeConnection();
			db.connect();
			pass = true;
		} catch (ConnectionNotEstablishedException e) {
			// TODO Auto-generated catch block
			pass = false;
		}
		assertTrue(pass);
	}

}
