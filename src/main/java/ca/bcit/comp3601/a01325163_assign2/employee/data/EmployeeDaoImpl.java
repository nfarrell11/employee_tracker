/**
 * 
 */
package ca.bcit.comp3601.a01325163_assign2.employee.data;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import ca.bcit.comp3601.a01325163_assign2.employee.domain.Employee;
import ca.bcit.comp3601.a01325163_assign2.employee.exception.DataAccessException;
import ca.bcit.comp3601.a01325163_assign2.employee.presentation.controller.EmployeeServlet;

/**
 * Defines the data access operations required for the employee table.
 * 
 * @author Nick Farrell
 * @version 2.0 
 */
public class EmployeeDaoImpl implements EmployeeDao {
	
	private static Logger logger = LogManager.getLogger(EmployeeDaoImpl.class);
	
	/**
	 * Returns a list of all records for the employee table
	 */
	@Override
	public List<Employee> getEmployeeList() {
		List<Employee> employees = new ArrayList<Employee>();
		
		try(Connection connection = Database.getConnection()) {
			
			PreparedStatement statement = connection.prepareStatement(SELECT_ALL_EMPLOYEES);
			ResultSet resultSet = statement.executeQuery();
			while(resultSet.next())
			{
				String empId = resultSet.getString(EmployeeTable.EMP_ID.getName());
				String firstName = resultSet.getString(EmployeeTable.FIRST_NAME.getName());
				String lastName = resultSet.getString(EmployeeTable.LAST_NAME.getName());
	        
				// Get DOB from the result set, check for null
				Date sqlDob = resultSet.getDate(EmployeeTable.DOB.getName());
				LocalDate dob = (sqlDob != null) ? sqlDob.toLocalDate() : null; // Set DOB to null if it is null in DB

				employees.add(new Employee.Builder(empId, firstName, lastName)
	                        .dob(dob)  // Pass the dob (null or converted LocalDate)
	                        .build());
			}
		} catch (ClassNotFoundException cnfe) {
	        logger.error("Database driver not found.", cnfe);
	        throw new DataAccessException("Failed to load database driver.", cnfe);
	    } catch (SQLException sqle) {
	        logger.error("SQL error while retrieving employees.", sqle);
	        throw new DataAccessException("Error accessing employee data.", sqle);
	    }
		return employees;
	}
	
	/**
	 * Creates a new SQL insert statement to access the database.
	 * 
	 * @param employee - the employee to insert
	 * @return true if a record was deleted, else false.
	 */
	@Override
	public boolean addEmployee(Employee employee) {
		int rowsAffected;
		
		try(Connection connection = Database.getConnection()) {
			
			PreparedStatement statement = connection.prepareStatement(ADD_EMPLOYEE);
			statement.setString(1, employee.getId());
		    statement.setString(2, employee.getFirstName());
		    statement.setString(3, employee.getLastName());
		    
		    if(employee.getDob() != null) {
		    	statement.setDate(4, Date.valueOf(employee.getDob())); // Casts java.util.Date as java.sql.Date
		    } else {
		    	statement.setDate(4, null);
		    }
		    
		    rowsAffected = statement.executeUpdate();
		    
		} catch (ClassNotFoundException cnfe) {
	        logger.error("Database driver not found.", cnfe);
	        throw new DataAccessException("Failed to load database driver.", cnfe);
	    } catch (SQLException sqle) {
	        logger.error("SQL error while retrieving employees.", sqle);
	        throw new DataAccessException("Error accessing employee data.", sqle);
	    }

		return rowsAffected > 0;
	}

	/**
	 * Creates a new SQL query on the given ID.
	 * 
	 * @param id - the id to look up 
	 * @return employee whose ID matches the given ID
	 */
	@Override
	public Employee findEmployee(String id) {
		
		try(Connection connection = Database.getConnection()) {
			
			PreparedStatement statement = connection.prepareStatement(FIND_EMPLOYEE_BY_ID);
			statement.setString(1, id);
		    ResultSet resultSet = statement.executeQuery();
		    
		    if(resultSet.next()) {
		    	// Date is stored as SQL date type that can be null
		    	Date sqlDob = resultSet.getDate(EmployeeTable.DOB.getName());
		    	// Only converts to LocalDate if value is non-null
		        LocalDate dob = (sqlDob != null) ? sqlDob.toLocalDate() : null; // Set dob to null if database value is null
		        return new Employee.Builder(
		        	resultSet.getString(EmployeeTable.EMP_ID.getName()),
		        	resultSet.getString(EmployeeTable.FIRST_NAME.getName()),
		        	resultSet.getString(EmployeeTable.LAST_NAME.getName()) )
		            .dob(dob)
		            .build();
		    }
		} catch (ClassNotFoundException cnfe) {
	        logger.error("Database driver not found.", cnfe);
	        throw new DataAccessException("Failed to load database driver.", cnfe);
	    } catch (SQLException sqle) {
	        logger.error("SQL error while retrieving employee data for ID: " + id, sqle);
	        throw new DataAccessException("Error accessing employee data.", sqle);
	    }
	    return null;
	}

	/**
	 * Creates a new SQL query on the given ID.
	 * 
	 * @param id - the id to look up.
	 * @return true if a record was deleted, else false.
	 */
	@Override
	public boolean deleteEmployee(String id) {
		int rowsAffected;
		
		try(Connection connection = Database.getConnection()) {
			PreparedStatement statement = connection.prepareStatement(DELETE_EMPLOYEE);
			statement.setString(1, id);
		    rowsAffected = statement.executeUpdate();
		} catch(ClassNotFoundException cnfe) {
	        logger.error("Database driver not found.", cnfe);
	        throw new DataAccessException("Failed to load database driver.", cnfe);
	    } catch (SQLException sqle) {
	        logger.error("SQL error while retrieving employees.", sqle);
	        throw new DataAccessException("Error accessing employee data.", sqle);
	    }
		return rowsAffected > 0;
	}
	
	/**
	 * Pulls all IDs from the database.
	 * 
	 * @return ids - list of all IDs
	 */
	public List<String> getAllIds() {
		List<String> ids = new ArrayList<>();
		
		try(Connection connection = Database.getConnection()) {
			
			PreparedStatement statement = connection.prepareStatement(GET_ALL_IDS);
		    ResultSet resultSet = statement.executeQuery();
		    while(resultSet.next()) {
		    	ids.add(resultSet.getString("id"));
		    }
		} catch(ClassNotFoundException cnfe) {
	        logger.error("Database driver not found.", cnfe);
	        throw new DataAccessException("Failed to load database driver.", cnfe);
	    } catch(SQLException sqle) {
	        logger.error("SQL error while retrieving employees.", sqle);
	        throw new DataAccessException("Error accessing employee data.", sqle);
	    }
		return ids;
	}
}
