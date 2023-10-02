package scheduler;

/**
 * Enum class to represent all departments.
 *
 * @author Michael Muzafarov
 */
public enum Department {
    CS("Computer Science"),
    EE("Electrical Engineering"),
    ITI("Information Technology and Informatics"),
    MATH("Mathematics"),
    BAIT("Business Analytics and Information Technology");

    private final String fullName;

    /**
     * Only used internally to give each constant a name
     *
     * @param fullName entire name of constant
     */
    Department(String fullName) {
        this.fullName = fullName;
    }

    /**
     * turn constant into string representation
     *
     * @return string with full department name
     */
    @Override
    public String toString() {
        return fullName;
    }

    public static boolean isValidDepartment(String givenUserDepartment) {
        for (Department department : values()) {
            if (department.name().equalsIgnoreCase(givenUserDepartment)) {
                return true;
            }
        }
        return false;
    }
}
