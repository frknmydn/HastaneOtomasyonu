package Helper;
import java.sql.*;

public class DBCollection extends CoreFields implements CoreInterface{


	
	
	static {
		
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	
	@Override
	public Connection getConnection() {
		// TODO Auto-generated method stub
		
		Connection connection = null;
		try {
			connection = DriverManager.getConnection(getUrl(),getUserName(),getPassword());
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return connection;
	}
	
	
	
	/*
	
	
	
	
	Connection c = null;
	
	private String userName = "root";
	private String password = "";
	private String url = "jdbc:mysql://localhost/hastane?useUnicode=true&characterEncoding=UTF-8";
	
	public DBCollection() {
		
	}
	
	public Connection connDb() {
		try {
			this.c= DriverManager.getConnection(url,userName,password);
			return c;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return c;
		
	}
	*/
	

}
