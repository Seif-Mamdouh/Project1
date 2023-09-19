package eventOrganizer;

import java.util.Arrays;

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
        if (splitEmail.length != 2) {
            return false;
        }

        String emailAddress = splitEmail[0];
        String emailDomain = splitEmail[1];
        for (char c : emailAddress.toCharArray()) {
            if (!Character.isDigit(c) || !Character.isLetter(c)) {
                return false;
            }
        }

        return emailDomain.equals("rutgers.edu");
    }

    /**
     * Check if email and department instance variables are valid.
     *
     * @return true if email is of proper format ([a-z|A-z]+@rutgers.edu) and
     * department exists in Department enum. False otherwise
     */
    public boolean isValid() {

        boolean validEnum = Arrays.stream(Department.values())
                                  .anyMatch(dep -> department.equals(dep));
        return validEnum && this.isEmailValid();

    }
}
