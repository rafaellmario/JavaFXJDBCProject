package db;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

public class HandleDatabase {
	private static Connection conn;
	
	// return the SQL database connection
	public static Connection getConnection() {
		if(conn == null) {
			try {
				Properties prop = loadProperties();
				String url = prop.getProperty("dburl");
				conn = DriverManager.getConnection(url,prop);
			}
			catch(SQLException e) {
				throw new DbException(e.getMessage());
			}
				
		}
		return conn;
	}
	
	// save the database connection
	public static void closeConnection() {
		if(conn != null){
			try {
				conn.close();
			}
			catch(SQLException e){
				throw new DbException(e.getMessage());
			}
		}
	}
	
	
	// load connection properties
	public static Properties loadProperties() {
		try(FileInputStream fs = new FileInputStream("db.properties")){
			Properties prop = new Properties();
			prop.load(fs);
			return prop;
		}
		catch(IOException e) {
			throw new DbException(e.getMessage());
		}
			
	}
	
	
	public static void closeStatement(Statement state) {
	  if(state != null)
		try {
			 state.close();
			} catch (SQLException e) {
				throw new DbException(e.getMessage());
			}
	}
	
	public static void closeResultSet(ResultSet result) {
	  if(result != null)
		 try {
			 result.close();
			 } catch (SQLException e) {
				throw new DbException(e.getMessage());
			 }
	  }
}
