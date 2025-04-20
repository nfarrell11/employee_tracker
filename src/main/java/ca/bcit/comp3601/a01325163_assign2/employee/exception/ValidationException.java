/**
 * 
 */
package ca.bcit.comp3601.a01325163_assign2.employee.exception;

import java.util.Map;

/**
 * This ValidationException is bean-compatible and is used to gather all error messages regarding input validation.
 */
public class ValidationException extends RuntimeException {
	private static final long serialVersionUID = 541457727701081096L;
	
	private Map<String, String> errors;
	private Map<String, String> params;

	public ValidationException() {};
	
	/**
	 * The first of two constructors, only accepts the errors map.
	 * 
	 * @param message - a general error message
	 * @param errors - a map of the validation errors where the key is the field name and the value is the error message.
	 */
    public ValidationException(String message, Map<String, String> errors) {
        super(message);
        this.errors = errors;
    }
    
    /**
     * This constructor also retains the input parameters so they may be saved in the form. 
     * 
     * @param message - a general error message
	 * @param errors - a map of the validation errors where the key is the field name and the value is the error message.
     * @param params - a map of the input parameters
     */
    public ValidationException(String message, Map<String, String> errors, Map<String, String> params) {
        super(message);
        this.errors = errors;
        this.params = params;
    }
    
    /**
     * @return a map of any errors 
     */
    public Map<String, String> getErrors() {
        return errors;
    }
    
    /**
     * @return a map of input parameters 
     */
    public Map<String, String> getParams() {
        return params;
    }
}
