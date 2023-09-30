package scheduler;

/**
 * Enum class to represent all departments.
 *
 * @author Michael Muzafarov
 */
public enum Department {
    CS("computer science"),
    EE("electrical engineering"),
    ITI("information technology and informatics"),
    MATH("mathematics"),
    BAIT("business analytics and information technology");

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
}
