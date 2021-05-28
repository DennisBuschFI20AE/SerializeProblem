

import java.io.ByteArrayOutputStream;
import java.io.ObjectOutputStream;
import java.sql.Blob;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class DB {
	
	private static final String CREATE_CUSTOMER = "";
	
	private static final String WRITE_CUSTOMER 	= "INSERT INTO customer(object) VALUES (?)";
	private static final String WRITE_ORDER 	= "INSERT INTO ordera(object) VALUES (?)";
	private static final String WRITE_WORKER 	= "INSERT INTO worker(object) VALUES (?)";
	private static final String WRITE_DELIVERER = "INSERT INTO deliverer(object) VALUES(?)";
	private static final String WRITE_PRODUCT 	= "INSERT INTO product(object) VALUES(?)";
	private static final String WRITE_STORAGE 	= "INSERT INTO storage(object) VALUES(?)";
	
	private static final String READ_CUSTOMER 	= "SELECT object FROM costumer WHERE id = ?";
	private static final String READ_ORDER 		= "SELECT object FROM ORDERA WHERE id = ?";
	private static final String READ_WORKER 	= "SELECT object FROM WORKER WHERE id = ?";
	private static final String READ_DELIVERER 	= "SELECT object FROM DELIVERER WHERE id = ?";
	private static final String READ_PRODUCT 	= "SELECT object FROM PRODUCT WHERE id = ?";
	private static final String READ_STORAGE 	= "SELECT object FROM STORAGE WHERE id = ?";
	private static final String TEST_QUERY 		= "SELECT 'BANANE' as object, ?";
	
	
	protected Connection getConnection() throws Exception {
		  
	    String url = "jdbc:mysql://localhost:3306/objektetest";
	    String username = "root";
	    String password = "";
	    Connection con = DriverManager.getConnection(url, username, password);
	    return con;
	}
	
	protected long writeObject(Connection con, Object object) {
		
		int id = 0;
		String className = object.getClass().getName();
	    String sql = ""; 
	    
		try 
		{
		    if( className.equals("Model.Customer") )
		    	sql = WRITE_CUSTOMER;
		    else if( className.equalsIgnoreCase("Model.Worker"))
		    	sql = WRITE_WORKER;
		    else if( className.equalsIgnoreCase("Model.OrderA"))
		    	sql = WRITE_ORDER;
		    else if( className.equalsIgnoreCase("Model.Deliverer"))
		    	sql = WRITE_DELIVERER;
		    else if( className.equalsIgnoreCase("Model.Product")) {
		    	sql = WRITE_PRODUCT;
		    }
		    
		  //  PreparedStatement pstmt 	= 	con.prepareStatement("CREATE TABLE BANANE(id int)");
		  //  pstmt.executeUpdate();
		    
		    
		    PreparedStatement pstmt 	= 	con.prepareStatement(sql);
		    ByteArrayOutputStream bos 	= 	new ByteArrayOutputStream();
		    ObjectOutputStream oos 		= 	new ObjectOutputStream(bos);
		    oos.writeObject(object);
		    oos.flush();
		    byte[] temp = bos.toByteArray();
		    System.out.println(temp);
		    // set input parameters
		    Blob blob = new com.mysql.cj.jdbc.Blob(temp, null);
		    pstmt.setBytes(1,temp);
		    
		    int i =  pstmt.executeUpdate();
		    /*
		    // get the generated key for the id
		    ResultSet rs = pstmt.getGeneratedKeys();
		    id = -1;
		    if (rs.next()) {
		      id = rs.getInt(1);
		    }
		    rs.close();
		    pstmt.close();
		    System.out.println("writeJavaObject: done serializing: " + className);
		    */
		    return id;
		}
		catch(Exception e) 
		{
			e.printStackTrace();
		    return id;
		}
	}
	  
	protected Object readObject(Connection conn, String className, int id) throws Exception {
		
	    String sql = "";
	    Object object = null;
	    try {
		    if( className.equals("customer") )
		    	sql = READ_CUSTOMER;
		    
		    else if( className.equals("testquery"))
		    	sql = TEST_QUERY;
		    
		    else if( className.equals("worker"))
		    	sql = READ_WORKER;
		    else if( className.equals("order"))
		    	sql = READ_ORDER;
		    else if( className.equals("deliverer"))
		    	sql = READ_DELIVERER;
			
		    PreparedStatement pstmt = conn.prepareStatement(sql);
		    pstmt.setLong(1, id);
		    ResultSet rs = pstmt.executeQuery();
		    rs.next();
		    object = rs.getObject(1);
		    System.out.println(object);
		    rs.close();
		    pstmt.close();
	    }
	    catch(Exception e) 
	    {
	    	System.out.println("FEHLER BEIM EINLESEN DES OBJEKTES VOM TYP " + object.getClass().getSimpleName() );
		    return object;
	    }
	    	System.out.println("readJavaObject: done de-serializing: " + className );
	    	return object;
	  }
	  
	  protected void closeCon(Connection con) {
		   
	  }
	
}//class

	
