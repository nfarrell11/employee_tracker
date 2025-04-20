/**
 * 
 */
package ca.bcit.comp3601.a01325163_assign2.employee.presentation.controller;

import java.io.IOException;
import java.sql.SQLException;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import ca.bcit.comp3601.a01325163_assign2.employee.presentation.controller.EmployeeServlet;
import ca.bcit.comp3601.a01325163_assign2.employee.data.Database;
import ca.bcit.comp3601.a01325163_assign2.employee.domain.Employee;
import ca.bcit.comp3601.a01325163_assign2.employee.exception.DataAccessException;
import ca.bcit.comp3601.a01325163_assign2.employee.exception.ServiceException;
import ca.bcit.comp3601.a01325163_assign2.employee.exception.ValidationException;
import ca.bcit.comp3601.a01325163_assign2.employee.service.EmployeeService;
import ca.bcit.comp3601.a01325163_assign2.employee.service.EmployeeServiceImpl;

/**
 * The EmployeeServlet class acts as the Controller component for this MVC 2 application. 
 * 
 * Modifies the request object according to the action attribute of any incoming POST requests.
 * 
 * @author Nick Farrell
 * @version 2.0
 */
public class EmployeeServlet extends HttpServlet {
	private static final long serialVersionUID = -1325163402L;
	
	private static Logger logger = LogManager.getLogger(EmployeeServlet.class);
	private static final String INDEX_PATH = "/jsp/index.jsp";
	
	private EmployeeService employeeService;
	
	/**
	 * Establishes a db connection when application receives first request
	 * 
	 * @param config - the servlet configuration of the initial request, including its initialization parameters
	 * @throws ServletException - if db connection fails 
	 */
	@Override
	public void init(ServletConfig config) throws ServletException {

		try {
			new Database().init(config.getInitParameter("driver"), 
								config.getInitParameter("url"),
								config.getInitParameter("user"), 
								config.getInitParameter("password"));
		} catch (ClassNotFoundException e) {
	        logger.error("Database driver not found.", e);
	        
	        throw new ServletException("Failed to load database driver. Unable to initialize controller servlet.", e);
	        
	    } catch (SQLException e) {
	        logger.error("Database connection failed.", e);
	        
	        throw new ServletException("Database connection failed. Unable to initialize controller servlet.", e);
	    }
		logger.info("Database is connected!");
		
		employeeService = new EmployeeServiceImpl();
		
	};
	
	/*
	 * Defers any GET requests to the POST method.
	 */
	@Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        doPost(req, resp);
    }

	/*
	 * Modifies request according to any action attributes from POST requests.
	 * Handles exceptions from the data access layer and the service layer.  
	 */
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
	        throws ServletException, IOException {
		
		String action = req.getParameter("action");
		String errorMsg;

	    logger.info("Handling action: {}", action);
	    
	    try {
	    	list(req);
	    	
	    	if(action != null) {
	    		switch(action) {
		        case "find":
		            find(req);
		            list(req);
		            break;
		            
		        case "add":
		            add(req);
		            list(req);
		            break;
		            
		        case "delete":
		            delete(req);
		            list(req);
		            break;
		        default:
		        	break;
		    	}
	    	}
	    } catch (ValidationException ve) {
	    	errorMsg = "Validation errors occurred at runtime: " + ve.getMessage();
	    	logger.info(errorMsg);
	        req.setAttribute("validationErrors", ve.getErrors());
	        req.setAttribute("params", ve.getParams());  // saves previously typed input in memory
	    } catch (ServiceException se) {
	    	errorMsg = "Service error occured at runtime: " + se.getMessage();
	        logger.info(errorMsg);
	        req.setAttribute("errorMsg", errorMsg);
	    } catch (DataAccessException dae) {
	    	errorMsg = "Database error occured at runtime: " + dae.getMessage();
	        logger.error(errorMsg);
	        req.setAttribute("errorMsg", errorMsg);
	    } catch (Exception e) {
	    	errorMsg = "Unknown error occured at runtime: " + e.getMessage(); 
	        logger.error(errorMsg);
	        req.setAttribute("errorMsg", errorMsg);
	    } finally {
	    	// nothing left to do here
	    }
	    
	    req.getRequestDispatcher(INDEX_PATH).forward(req, resp);
	}

	// Calls the service layer and checks for the resulting employee object.
    private void find(HttpServletRequest req) {
        String id = req.getParameter("id");
        
        Employee employee = employeeService.findEmployee(id);
        
        if (employee != null) {
            req.setAttribute("foundEmployee", employee);
            req.setAttribute("findMsg", "Employee found: ");
        } else {
            req.setAttribute("findMsg", "Employee not found.");
        }
    }

    // Calls the service layer and checks for the resulting flag indicating the insert was successful.
    private void add(HttpServletRequest req) {
    	Map<String, String> params = new HashMap<>();
    	
        params.put("id", req.getParameter("id"));
        params.put("firstName", req.getParameter("firstName"));
        params.put("lastName", req.getParameter("lastName"));
        params.put("dob", req.getParameter("dob"));
        
        boolean added = employeeService.addEmployee(params);
        req.setAttribute("addMsg", added ? "Employee added successfully." : "Failed to add employee.");

    }

    // Calls the service layer and checks for the resulting flag indicating the insert was successful.
    private void delete(HttpServletRequest req) {
        String id = req.getParameter("id");

        boolean deleted = employeeService.deleteEmployee(id);
        req.setAttribute("deleteMsg", deleted ? "Employee deleted successfully." : "Employee not found.");
    }

    // Calls the service layer and modifies the request object.
    private void list(HttpServletRequest req) {
        List<Employee> employees = employeeService.getEmployeeList();
        
        req.setAttribute("employees", employees);
        req.setAttribute("listMsg", !employees.isEmpty() ? employees.size() + " employee records found." : "No employee records found.");
    }
}
