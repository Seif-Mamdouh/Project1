package scheduler;

/**
 * Class that represents an Event at a certain date, time, location, duration,
 * and contact information of the reserving party.
 *
 * @author Michael Muzafarov
 */
public class Event implements Comparable<Event> {
    private Date date; //the event date
    private Timeslot startTime; //the starting time
    private Location location;
    private Contact contact; //include the department name and email
    private int duration; //in minutes

    /**
     * Constructor that assigns each instance variable
     *
     * @param date      date of event
     * @param startTime time event starts
     * @param location  location of event
     * @param contact   contact of reserving party
     * @param duration  duration of event in minutes
     */
    public Event(
            Date date,
            Timeslot startTime,
            Location location,
            Contact contact,
            int duration
    ) {
        this.date = date;
        this.startTime = startTime;
        this.location = location;
        this.contact = contact;
        this.duration = duration;
    }

    /**
     * Checks if dates and times are the same.
     *
     * @param obj the other object to compare
     * @return true if obj is an Event with the same date and time, false
     * otherwise
     */
    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof Event)) {
            return false;
        }
        Event otherEvent = (Event) obj;

        return (this.compareTo(otherEvent) == 0 && this.location.equals(otherEvent.getLocation()));

    }

    /**
     * Compare Events' dates and use startTimes to break ties.
     *
     * @param event the object to be compared
     * @return negative integer if this < event, positive integer if this >
     * event, and 0 if equal
     */
    @Override
    public int compareTo(Event event) {
        int datesCompared = this.date.compareTo(event.date);
        if (datesCompared != 0) {
            return datesCompared;
        }

        return this.startTime.compareTo(event.startTime);
    }

    /**
     * Event's String representation.
     *
     * @return string containing event information.
     */
    @Override
    public String toString() {
//        logic for getEndTimesSlot
//        Timeslot endTime = startTime.getEndTimeslot(duration);

        String baseString =
                "[Event Date: %s] [Start: %s] [End: %s] " +
                "%s [Contact: %s]";
        return String.format(
                baseString,
                this.date.toString(),
                this.startTime.toString(),
                this.startTime.timeAfterDuration(this.duration),
                this.location.toString(),
                this.contact.toString()
        );
    }

    /**
     * Get location of event.
     *
     * @return location
     */
    public Location getLocation() {
        return location;
    }

    /**
     * Get contact associated with event.
     *
     * @return contact
     */
    public Contact getContact() {
        return contact;
    }

    /**
     * Get date associated with event.
     *
     * @return date
     */
    public Date getDate() {
        return this.date;
    }

    /**
     * Get timeSlot associated with event.
     *
     * @return timeSlot
     */
    public Timeslot getTimeslot() {
        return this.startTime;
    }

    public int getDuration(){
        return duration;
    }
}

