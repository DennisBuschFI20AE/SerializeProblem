

import java.sql.Connection;

public class DB_Controller {

	private DB db 			= 	new DB();
	private Connection con 	= 	null;
	
	public DB_Controller() {
		
		db = new DB();
		try 
		{
			con = db.getConnection();
			con.setAutoCommit(false);
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
		}
	}
	
	public void writeObject(Object object) throws Exception {
			db.writeObject(con, object);
		//db.readObject(con, "testquery", 1);
		
	}
	
}
	
