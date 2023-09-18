package eventOrganizer;

import java.util.Objects;

public class Event implements Comparable<Event> {
    private Date date; //the event date
    private Timeslot startTime; //the starting time
    private Location location;
    private Contact contact; //include the department name and email
    private int duration; //in minutes

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Event)) {
            return false;
        }
        Event otherEvent = (Event) o;

        return (this.compareTo(otherEvent) == 0);

    }

    @Override
    public int compareTo(Event event) {
        int datesCompared = this.date.compareTo(event.date);
        if (datesCompared != 0) {
            return datesCompared;
        }

        return this.startTime.compareTo(event.startTime);
    }

    /**
     *
     * @return
     */
    @Override
    public String toString() {
        String baseString = "[Event Date: %s] [Start: %s] [End: IMPLEMENTATION PENDING] %s [Contact: %s]";
        return String.format(
                baseString,
                this.date.toString(),
                this.startTime.toString(),
                this.location.toString(),
                this.contact.toString()
        );
    }
}
