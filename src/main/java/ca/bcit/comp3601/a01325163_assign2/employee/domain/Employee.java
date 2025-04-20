/**
 * 
 */
package ca.bcit.comp3601.a01325163_assign2.employee.domain;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

import ca.bcit.comp3601.a01325163_assign2.employee.domain.Employee;

/**
 * Employee models the attributes and behaviors of a company employee record. 
 * An inner builder class is provided to distinguish manual instantiation from instantiation by the container.
 * 
 * @author Nick Farrell
 * @version 2.0
 */
public class Employee implements Serializable{

	private static final long serialVersionUID = -1325163402L;
	
	protected String    id;
	protected String    firstName;
	protected String    lastName;
	
	protected LocalDate dob;
	
	// no-args constructor for container to manage object life cycle
	public Employee() {}
	
	// Private constructor for Builder class
    private Employee(Builder builder) {
        this.id = builder.id;
        this.firstName = builder.firstName;
        this.lastName = builder.lastName;
        this.dob = builder.dob;
    }
	
	// Static inner Builder class
    public static class Builder {
        // Required fields
        private final String id;
        private final String firstName;
        private final String lastName;
        
        // Optional fields
        private LocalDate dob;

        public Builder(String id, String firstName, String lastName) {
            this.id = id;
            this.firstName = firstName;
            this.lastName = lastName;
        }

        public Builder dob(LocalDate dob) {
            this.dob = dob;
            return this;
        }

        public Employee build() {
            return new Employee(this);
        }
    }

	/**
	 * @return the id
	 */
	public String getId() {
		return id;
	}

	/**
	 * @return the firstName
	 */
	public String getFirstName() {
		return firstName;
	}

	/**
	 * @return the lastName
	 */
	public String getLastName() {
		return lastName;
	}

	/**
	 * @return the dob
	 */
	public LocalDate getDob() {
		return dob;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(String id) {
		this.id = id;
	}

	/**
	 * @param firstName the firstName to set
	 */
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	/**
	 * @param lastName the lastName to set
	 */
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	/**
	 * @param dob the date of birth to set
	 */
	public void setDob(LocalDate dob) {
		this.dob = dob;
	}

	@Override
	public String toString() {
		return "Employee [id=" + id + ", firstName=" + firstName + ", lastName=" + lastName + ", dob=" + dob + "]";
	}

	@Override
	public int hashCode() {
		return Objects.hash(id);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Employee other = (Employee) obj;
		return Objects.equals(id, other.id);
	};
}
