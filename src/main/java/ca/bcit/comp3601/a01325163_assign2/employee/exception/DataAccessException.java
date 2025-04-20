/**
 * 
 */
package ca.bcit.comp3601.a01325163_assign2.employee.exception;

/**
 * This exception wraps any Database-related exceptions and forwards them to the controller.
 */
public class DataAccessException extends RuntimeException {
    /**
	 * 
	 */
	private static final long serialVersionUID = 8412672316177161373L;
	
	public DataAccessException(String message) {
        super(message);
    }

    public DataAccessException(String message, Throwable cause) {
        super(message, cause);
    }
}
