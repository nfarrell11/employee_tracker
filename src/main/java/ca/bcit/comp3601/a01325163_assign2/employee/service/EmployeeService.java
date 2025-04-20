/**
 * 
 */
package ca.bcit.comp3601.a01325163_assign2.employee.service;

import java.util.List;
import java.util.Map;

import ca.bcit.comp3601.a01325163_assign2.employee.domain.Employee;

/**
 * Defines the required services for any service implementation of this interface
 * 
 * @author Nick Farrell
 * @version 2.0
 */
public interface EmployeeService {
	List<Employee> getEmployeeList();
	boolean		   addEmployee(Map<String, String> params);
	Employee 	   findEmployee(String id);
	boolean 	   deleteEmployee(String id);
}
