package eventOrganizer;

/**
 * Enum class to represent all departments
 *
 * @author Michael Muzafarov
 */
public enum Timeslot {
    MORNING("Morning"), AFTERNOON("Afternoon"), EVENING("Evening"),
    ;

    private final String stringRepresentation;

    /**
     * Builds enum with given string representation.
     *
     * @param stringRepresentation enum as string
     */
    Timeslot(String stringRepresentation) {
        this.stringRepresentation = stringRepresentation;
    }


    /**
     * Get enum as string.
     *
     * @return string of enum
     */
    @Override
    public String toString() {
        return this.stringRepresentation;
    }

}
