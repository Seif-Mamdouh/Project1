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

        return (this.compareTo(otherEvent) == 0 &&
                this.location.equals(otherEvent.getLocation())
        );

    }

    /**
     * Compare Events' dates and use startTimes to break ties.
     *
     * @param event the object to be compared
     * @return negative integer if this event is before passed in
     * event, positive integer if this event is after passed in
     * event, and 0 if the events are equal
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
                "[Event Date: %s] [Start: %s] [End: %s] " + "%s [Contact: %s]";
        return String.format(
                baseString,
                this.date.toString(),
                this.startTime.startTime(),
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

    /**
     * Getter for duration
     *
     * @return duration
     */
    public int getDuration() {
        return duration;
    }
    public static void main(String [] args){
        //Two dates are equal if time, date, and locations are the same

        //times, dates, and locations not the same
        Event notSame1 = new Event(
                new Date(2023, 10, 2),
                Timeslot.MORNING,
                Location.HLL114,
                new Contact(Department.EE, "a@rutgers.edu"),
                35
        );
        Event notSame2 = new Event(
                new Date(2023, 9, 2),
                Timeslot.AFTERNOON,
                Location.ARC103,
                new Contact(Department.EE, "a@rutgers.edu"),
                35
        );
        assert !notSame1.equals(notSame2);

        //just time are the same
        Event justTime1 = new Event(
                new Date(2023, 9, 2),
                Timeslot.AFTERNOON,
                Location.ARC103,
                new Contact(Department.EE, "a@rutgers.edu"),
                35
        );
        Event justTime2 = new Event(
                new Date(2023, 9, 3),
                Timeslot.AFTERNOON,
                Location.HLL114,
                new Contact(Department.EE, "a@rutgers.edu"),
                35
        );
        assert !justTime1.equals(justTime2);

        //just date is the same
        Event justDate1 = new Event(
                new Date(2023, 9, 2),
                Timeslot.AFTERNOON,
                Location.ARC103,
                new Contact(Department.EE, "a@rutgers.edu"),
                35
        );
        Event justDate2 = new Event(
                new Date(2023, 9, 2),
                Timeslot.MORNING,
                Location.HLL114,
                new Contact(Department.EE, "a@rutgers.edu"),
                35
        );
        assert !justDate1.equals(justDate2);

        //just location is the same
        Event justLocation1 = new Event(
                new Date(2023, 9, 2),
                Timeslot.AFTERNOON,
                Location.ARC103,
                new Contact(Department.EE, "a@rutgers.edu"),
                35
        );
        Event justLocation2 = new Event(
                new Date(2023, 9, 3),
                Timeslot.MORNING,
                Location.ARC103,
                new Contact(Department.EE, "a@rutgers.edu"),
                35
        );
        assert !justLocation1.equals(justLocation2);


        //just time and date are the same
        Event timeAndDate1 = new Event(
                new Date(2023, 9, 2),
                Timeslot.MORNING,
                Location.ARC103,
                new Contact(Department.EE, "a@rutgers.edu"),
                35
        );
        Event timeAndDate2 = new Event(
                new Date(2023, 9, 2),
                Timeslot.MORNING,
                Location.HLL114,
                new Contact(Department.EE, "a@rutgers.edu"),
                35
        );
        assert !timeAndDate1.equals(timeAndDate2);

        //just date and location are the same
        Event dateLocation1 = new Event(
                new Date(2023, 9, 2),
                Timeslot.AFTERNOON,
                Location.ARC103,
                new Contact(Department.EE, "a@rutgers.edu"),
                35
        );
        Event dateLocation2 = new Event(
                new Date(2023, 9, 2),
                Timeslot.MORNING,
                Location.ARC103,
                new Contact(Department.EE, "a@rutgers.edu"),
                35
        );
        assert !dateLocation1.equals(dateLocation2);

        //time date and location are the same
        Event same1 = new Event(
                new Date(2023, 9, 2),
                Timeslot.AFTERNOON,
                Location.ARC103,
                new Contact(Department.EE, "a@rutgers.edu"),
                35
        );
        Event same2 = new Event(
                new Date(2023, 9, 2),
                Timeslot.AFTERNOON,
                Location.ARC103,
                new Contact(Department.EE, "a@rutgers.edu"),
                35
        );
        assert same1.equals(same2);

        System.out.println("tests pass");


    }
}

