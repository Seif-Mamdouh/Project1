package scheduler;

import java.util.Calendar;

/**
 * Enum class to represent all departments.
 *
 * @author Michael Muzafarov
 */
public enum Timeslot {
    MORNING("Morning", 10, 30, Calendar.AM),
    AFTERNOON("Afternoon", 2, 0, Calendar.PM),
    EVENING("Evening", 6, 30, Calendar.PM),
    ;

    private final String stringRepresentation;
    private final int HOUR, MINUTE, AM_PM;


    /**
     * Builds enum with given string representation.
     *
     * @param stringRepresentation enum as string
     * @param hour                 hour of the day time slot starts
     * @param minute               minute time slot starts
     * @param amPm                 represents if time is am or pm,
     *                             corresponds to Calendar.AM and Calendar.PM
     */
    Timeslot(String stringRepresentation, int hour, int minute, int amPm) {
        this.HOUR = hour;
        this.MINUTE = minute;
        this.AM_PM = amPm;
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

    /**
     * Helper function that will return the time in h:m format with AM or PM.
     * Hour will not have leading zeroes and the minute will if below 10
     *
     * @param hour   hour to display
     * @param minute minute to display
     * @param amPm   integer corresponding to Calendar.AM and calendar.PM,
     *               indicates if morning or not
     * @return string representation of the time
     */
    private String formatTime(int hour, int minute, int amPm) {
        String StringFormatWith2DigitPadding = "%02d";
        return Integer.toString(hour) + ":" +
               String.format(StringFormatWith2DigitPadding, minute) +
               (amPm == Calendar.AM ? "AM" : "PM");
    }

    /**
     * String representation of starting time of this Timeslot
     * H:M(AM/PM), minutes are padded with a leading 0 if minutes are less
     * than 10
     *
     * @return string representation of Timeslot start time
     */
    public String startTime() {
        return this.formatTime(this.HOUR, this.MINUTE, this.AM_PM);
    }

    /**
     * return the time after a duration
     *
     * @param duration the number of minutes elapsed since start time
     * @return end time as string
     */
    public String timeAfterDuration(int duration) {
        Calendar calendarForTime = Calendar.getInstance();
        calendarForTime.set(Calendar.HOUR, this.HOUR);
        calendarForTime.set(Calendar.MINUTE, this.MINUTE);
        calendarForTime.set(Calendar.AM_PM, this.AM_PM);

        calendarForTime.add(Calendar.MINUTE, duration);
        int hour = calendarForTime.get(Calendar.HOUR);

        int hour12AccordingToCalendar = 0;
        if (hour == hour12AccordingToCalendar) {
            hour = 12;
        }
        int minute = calendarForTime.get(Calendar.MINUTE);

        return this.formatTime(hour,
                               minute,
                               calendarForTime.get(Calendar.AM_PM)
        );

    }

    /**
     * Method to check if the user's input is between 30 to 120
     * returns false otherwise
     * @param duration
     * @return
     */
    public boolean isValidDuration(int duration){
        return duration >= 30 && duration <= 120;
    }


    /**
     * Unit tests for the timeAfterDuration method
     *
     * @param args unused, does not take command line arguments
     */
    public static void main(String[] args) {
        assert (Timeslot.MORNING.timeAfterDuration(5).equals("10:35AM"));
        assert (Timeslot.MORNING.timeAfterDuration(30).equals("11:00AM"));
        assert (Timeslot.MORNING.timeAfterDuration(60).equals("11:30AM"));
        assert (Timeslot.MORNING.timeAfterDuration(89).equals("11:59AM"));
        assert (Timeslot.MORNING.timeAfterDuration(90).equals("12:00PM"));
        assert (Timeslot.MORNING.timeAfterDuration(120).equals("12:30PM"));

        assert (Timeslot.AFTERNOON.timeAfterDuration(5).equals("2:05PM"));
        assert (Timeslot.AFTERNOON.timeAfterDuration(30).equals("2:30PM"));
        assert (Timeslot.AFTERNOON.timeAfterDuration(60).equals("3:00PM"));
        assert (Timeslot.AFTERNOON.timeAfterDuration(89).equals("3:29PM"));
        assert (Timeslot.AFTERNOON.timeAfterDuration(90).equals("3:30PM"));
        assert (Timeslot.AFTERNOON.timeAfterDuration(120).equals("4:00PM"));

        assert (Timeslot.EVENING.timeAfterDuration(5).equals("6:35PM"));
        assert (Timeslot.EVENING.timeAfterDuration(30).equals("7:00PM"));
        assert (Timeslot.EVENING.timeAfterDuration(60).equals("7:30PM"));
        assert (Timeslot.EVENING.timeAfterDuration(89).equals("7:59PM"));
        assert (Timeslot.EVENING.timeAfterDuration(90).equals("8:00PM"));
        assert (Timeslot.EVENING.timeAfterDuration(120).equals("8:30PM"));


    }

}
