package eventOrganizer;

public class EventCalendar {
    private Event[] events; //the array holding the list of events
    private int numEvents; //current number of events in the array

    final static int NOT_FOUND_IN_ARRAY = -1;

    /**
     * Search all events and return the event's index if it is found.
     * Returns NOT_FOUND_IN_ARRAY if it is not in the array.
     *
     * @param event the event whose index will be found
     * @return index of event or NOT_FOUND_IN_ARRAY if it is not found
     */
    private int find(Event event) {
        for (int i = 0; i < this.events.length; i++) {
            if (this.events[i].equals(event)) {
                return i;
            }
        }
        return EventCalendar.NOT_FOUND_IN_ARRAY;
    }

    /**
     * increase the capacity of the events array by 4
     */
    private void grow() {
        Event[] newEvents = new Event[this.events.length + 4];
        System.arraycopy(this.events, 0, newEvents, 0, this.events.length);
        this.events = newEvents;
    }

    /**
     * Add a new event to calendar.
     *
     * @param event the event to add
     * @return true if successful, as of now it cannot fail, do not know why
     * we need to return boolean. More investigation needed
     */
    public boolean add(Event event) {
        if (this.events.length == this.numEvents) {
            this.grow();
        }
        this.events[numEvents] = event;
        this.numEvents++;
        return true;
    }

    /**
     * Remove specific event from events in calendar.
     *
     * @param event the event to remove
     * @return true if event existed and was removed from calendar. False if
     * the event did not exist and could not be removed
     */
    public boolean remove(Event event) {

        if (!this.contains(event)) {
            return false;
        }

        int indexOfEvent = this.find(event);
        this.numEvents--;

        System.arraycopy(
                this.events,
                indexOfEvent + 1,
                this.events,
                indexOfEvent,
                this.numEvents - indexOfEvent
        );

        return true;
    }

    /**
     * Checks if the calendar contains a particular Event
     *
     * @param event the Event object to check for
     * @return true if it is found, false if it does not exist in the calendar
     */
    public boolean contains(Event event) {
        return this.find(event) != EventCalendar.NOT_FOUND_IN_ARRAY;
    }

    /**
     * print the array as is
     */
    public void print() {
        for(Event event : this.events){
            System.out.println(event);
        }

    }

    private interface CustomComparator<T> {
        int compare(T a, T b);
    }

    private <T> void quickSort(T[] toSort, CustomComparator<T> comparator){

    }

    public void printByDate() {
    } //ordered by date and timeslot

    public void printByCampus() {
    } //ordered by campus and building/room

    public void printByDepartment() {
    } //ordered by department
}