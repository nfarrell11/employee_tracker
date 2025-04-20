/**
 * 
 */
package ca.bcit.comp3601.a01325163_assign2.employee.data;

/**
 * EmployeeTable enum defines the expected data definitions for the employee table.
 * 
 * @author Nick Farrell
 * @version 2.0
 */
public enum EmployeeTable {

	EMP_ID("id","INT",9,1),
	FIRST_NAME("firstName","VARCHAR",250,2),
	LAST_NAME("lastName","VARCHAR",250,3),
	DOB("dob","DATE",-1,4);
	
	private final String name;
	private final String type;
	private final int size;
	private final int column;
	
	/**
	 * Constructor.
	 * 
	 * @param name - name
	 * @param type - type
	 * @param size - size
	 * @param column - column 
	 */
	EmployeeTable(String name, String type, int size, int column) {
		this.name = name;
		this.type = type;
		this.size = size;
		this.column = column;
	}
	
	/**
	 * get type
	 * @return type
	 */
	public String getType() {
		return type;
	}

	/**
	 * get name
	 * @return name
	 */
	public String getName() {
		return name;
	}

	/**
	 * get size
	 * @return size
	 */
	public int getSize() {
		return size;
	}

	/**
	 * get columns
	 * @return column
	 */
	public int getColumn() {
		return column;
	}
}