/**
 * 
 */
package ca.bcit.comp3601.a01325163_assign2.employee.exception;

/**
 * 
 */
public class ServiceException extends RuntimeException {
	/**
	 * 
	 */
	private static final long serialVersionUID = 8412672316177161373L;
	
	public ServiceException(String message) {
        super(message);
    }

    public ServiceException(String message, Throwable cause) {
        super(message, cause);
    }
}
