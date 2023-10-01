package scheduler;

/**
 * Represents contact information of a department.
 * Contains their department and email.
 *
 * @author Michael Muzafarov
 */
public class Contact {
    private Department department;
    private String email;

    /**
     * Create contact, specifying department and email
     *
     * @param department that this contact belongs to
     * @param email      to reach this department
     */
    public Contact(Department department, String email) {
        this.department = department;
        this.email = email;
    }

    /**
     * Check if email is in valid format.
     *
     * @return true only if email is in valid format ([a-z|A-z]+@rutgers.edu)
     */
    private boolean isEmailValid() {
        String[] splitEmail = email.split("@");

        int expectedSplitLength = 2;
        if (splitEmail.length != expectedSplitLength) {
            return false;
        }

        int emailIndex = 0, domainIndex = 1;
        String emailAddress = splitEmail[emailIndex];
        String emailDomain = splitEmail[domainIndex];
        for (char c : emailAddress.toCharArray()) {
            if (!Character.isDigit(c) || !Character.isLetter(c)) {
                return false;
            }
        }

        String expectedDomainName = "rutgers.edu";
        return emailDomain.equals(expectedDomainName);
    }

    /**
     * Check if email and department instance variables are valid.
     *
     * @return true if email is of proper format ([a-z|A-z]+@rutgers.edu) and
     * department exists in Department enum. False otherwise
     */
    public boolean isValid() {

        boolean validEnum = false;
        for (Department dep : Department.values()) {
            if (dep.equals(this.department)) {
                validEnum = true;
                break;
            }
        }
        return validEnum && this.isEmailValid();

    }

    /**
     * Get department associated with contact
     *
     * @return department
     */
    public Department getDepartment() {
        return department;
    }


    /**
     * Get string representation consisting of department - email
     *
     * @return string representation
     */
    @Override
    public String toString() {
        return department.toString() + " - " + email;
    }

}
