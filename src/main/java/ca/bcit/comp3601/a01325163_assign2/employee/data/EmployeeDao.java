/**
 * 
 */
package ca.bcit.comp3601.a01325163_assign2.employee.data;

import java.util.List;

import ca.bcit.comp3601.a01325163_assign2.employee.domain.Employee;

/**
 * Defines the required contract for database operations
 * 
 * @author Nick Farrell
 * @version 2.0
 */
public interface EmployeeDao {
	
	final static String EMP_TABLE_NAME = "a01325163_Employee";
	
	final static String SELECT_ALL_EMPLOYEES = "SELECT * from " + EMP_TABLE_NAME;
	
	final static String ADD_EMPLOYEE = "INSERT INTO " + EMP_TABLE_NAME + " (id,firstName,lastName,dob) VALUES (?, ?, ?, ?)";
	
	final static String FIND_EMPLOYEE_BY_ID = "SELECT * FROM " + EMP_TABLE_NAME + " WHERE id = ?";
	
	final static String DELETE_EMPLOYEE = "DELETE FROM " + EMP_TABLE_NAME + " WHERE id = ?";
	
	final static String GET_ALL_IDS = "SELECT id FROM " + EMP_TABLE_NAME;
	
	List<Employee> getEmployeeList();
	boolean 	   addEmployee(Employee employee);
	Employee 	   findEmployee(String id);
	boolean 	   deleteEmployee(String id);
	List<String>   getAllIds();
}
