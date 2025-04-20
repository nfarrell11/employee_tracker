/**
 * 
 */
package ca.bcit.comp3601.a01325163_assign2.employee.data;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import ca.bcit.comp3601.a01325163_assign2.employee.data.Database;

/**
 * Represents a database connection instance, whose life-cycle is managed by the client browser session
 * @author Nick Farrell
 * @version 2.0
 */
public class Database {
	
	private static Connection connection;
	private static String driver;
	private static String user;
	private static String password;
	private static String url;
	
	private static Logger logger = LogManager.getLogger(Database.class);
	
	public Database() {}
	
	public void init(String driver, String url, String user, String password) throws ClassNotFoundException, SQLException {
		Database.driver 	= driver;
		Database.user 		= user;
		Database.password 	= password;
		Database.url 		= url;

		Class.forName(driver);
		connection = DriverManager.getConnection(url, user, password);

    }
	
	protected static Connection getConnection() throws ClassNotFoundException, SQLException
	{

			if(connection!=null && !connection.isClosed())
			{
				return connection;
			}
			else
			{
				Class.forName(driver);
				connection = DriverManager.getConnection(url, user, password);
				
			}
		logger.info("Database connection was successful.");
		
		return connection;
	}
}
