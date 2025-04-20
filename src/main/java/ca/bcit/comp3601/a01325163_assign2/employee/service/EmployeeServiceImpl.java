/**
 * 
 */
package ca.bcit.comp3601.a01325163_assign2.employee.service;


import java.time.LocalDate;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import ca.bcit.comp3601.a01325163_assign2.employee.domain.Employee;
import ca.bcit.comp3601.a01325163_assign2.employee.exception.ValidationException;

import ca.bcit.comp3601.a01325163_assign2.employee.data.EmployeeDao;
import ca.bcit.comp3601.a01325163_assign2.employee.data.EmployeeDaoImpl;

/**
 * Contains the business logic for this service implementation.
 * 
 * @author Nick Farrell
 * @version 2.0
 */
public class EmployeeServiceImpl implements EmployeeService {

	private EmployeeDao employeeDao;
	
	private static Logger logger = LogManager.getLogger(EmployeeServiceImpl.class);
	
	private static final String EMP_ID_REGEX = "^A0\\d{7}$";
	private static final String DOB_REGEX = "^\\d{4}-\\d{2}-\\d{2}$";
	private static final int MAX_NAME_LENGTH = 250;
	
	/**
	 * Constructor
	 */
	public EmployeeServiceImpl()
	{
		employeeDao = new EmployeeDaoImpl();
	}
	
	/**
	 * Calls the data layer to access the employee list.
	 */
	@Override
    public List<Employee> getEmployeeList() {

		return employeeDao.getEmployeeList();
	   
    }

	/**
	 * Calls the data layer to access a specific employee record. 
	 * First queries all IDs in the database before requesting data from any other columns.
	 * Any db exceptions are wrapped as a DataAccessException and thrown directly to the Controller.
	 * 
	 * @param id - the id to look up
	 */
	@Override
    public Employee findEmployee(String id) {
		Employee found = null;
		if(isExistingId(id)) {
			found = employeeDao.findEmployee(id);
		}
        return found;
    }

	/**
	 * Calls the data layer to insert a new employee. 
	 * Validation is handled by the validateNewEmployee() method.
	 * Any db exceptions are wrapped as a DataAccessException and thrown directly to the Controller.
	 * 
	 * @param params - a map of all parameter names and values from the add employee form
	 */
    @Override
    public boolean addEmployee(Map<String, String> params) {
    	validateNewEmployee(params);

        Employee employee = new Employee.Builder(
            params.get("id"), 
            params.get("firstName"), 
            params.get("lastName"))
        	.build();
        
        String dob = params.get("dob");
        if(validateStr(dob)) {
        	employee.setDob(LocalDate.parse(params.get("dob")));
        }
        		
        return employeeDao.addEmployee(employee);
         
    }
    
    /*
     * Gathers error messages based on user form input.
     * Throws ValidationException if any of the user input parameters violates implementation logic.
     */
    private void validateNewEmployee(Map<String, String> params) { 
    	
    	Map<String, String> errors = new HashMap<>();
        
    	String id = params.get("id");
    	String firstName = params.get("firstName");
    	String lastName = params.get("lastName");
    	String dob = params.get("dob");
    	
    	if(isExistingId(id)) {
    		errors.put("id", "Employee ID " + id + " already exists.");
    	}
        if(!validateStr(id)) {
            errors.put("id", "Employee ID cannot be null or blank.");
        }
        if(!validateId(id)) {
            errors.put("id", "Employee ID must begin with 'A0' followed by 7 numeric digits.");
        }
        if(!validateStr(firstName)) {
            errors.put("firstName", "Employee first name cannot be blank.");
        }
        if(!validateNamePart(firstName)) {
    		errors.put("firstName","Employee first name cannot exceed 250 characters. That's being generous!");
    	}
        if(!validateStr(lastName)) {
            errors.put("lastName", "Employee last name cannot be blank.");
        }
        if(!validateNamePart(lastName)) {
            errors.put("lastName", "Employee last name cannot exceed 250 characters. That's being generous!");
        }
        if(!validateDob(dob)) {
            errors.put("dob", "Date of Birth must follow pattern 'yyyy-mm-dd' or be left blank.");
        }
        if (!errors.isEmpty()) {
            throw new ValidationException("Validation failed", errors, params);
        }
    }

    /**
     * Calls the data layer to insert a new employee.
	 * Any db exceptions are wrapped as a DataAccessException and thrown directly to the Controller.
     */
    @Override
    public boolean deleteEmployee(String id) {
    	boolean deleted = false;
    	if(isExistingId(id)) {
    		deleted =  employeeDao.deleteEmployee(id);
    	}
    	return deleted;
        
    }
    
    // employee parameter validation methods
    
    /*
     * Queries just the primary keys of the Employee table to check for existence.
     */
    private boolean isExistingId(String id) {
    	List<String> ids = employeeDao.getAllIds();
        return ids.contains(id);
    }
    
    /*
     * Returns true if the given string is neither null nor blank, else false.
     */
    private static boolean validateStr(String str) { return !(str == null || str.isBlank()); }

    /*
     * Returns true if the name value given exceeds MAX_NAME_LENGTH.
     */
    private static boolean validateNamePart(String str) { return str.length() <= MAX_NAME_LENGTH;}
    
    /*
     * Checks if the given ID value follows the EMP_ID_REGEX pattern for Employee ID.
     */
    private static boolean validateId(String id) {
        Pattern pattern = Pattern.compile(EMP_ID_REGEX);
        Matcher matcher = pattern.matcher(id);
        return matcher.matches();
    }
    
    /*
     * Checks if the date string provided follows the DOB_REGEX pattern for dates of birth.
     */
    private static boolean validateDob(String date) {
    	boolean isValid;
    	if(date == null || date.isBlank()) {
    		// overrides the pattern matching check if no string is provided
    		isValid = true;
    	} else {
    		Pattern pattern = Pattern.compile(DOB_REGEX);
            Matcher matcher = pattern.matcher(date);
            isValid = matcher.matches();
    	}
    	return isValid;
    }

}
