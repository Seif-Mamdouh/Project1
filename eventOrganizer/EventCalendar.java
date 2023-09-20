package eventOrganizer;

public class EventCalendar {
    private Event[] events; //the array holding the list of events
    private int numEvents; //current number of events in the array

    final static int NOT_FOUND_IN_ARRAY = -1;
    final static int EQUAL_IN_COMPARABLE = 0;

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
        for (Event event : this.events) {
            System.out.println(event);
        }

    }

    /**
     * Used by lambda function to define how things should be sorted.
     *
     * @param <T> the type of the things being compared
     */
    private interface CustomComparator<T> {
        int compare(T a, T b);
    }

    /**
     * Implementation of bubble sort. Could not do O(nlogn) sort because it
     * had to be in place and don't have access to random library.
     *
     * @param toSort     array to be sorted
     * @param comparator typically a lambda that will be used to compare two
     *                   things in the array. Is expected to return a
     *                   negative number if the first parameter is smaller
     *                   than the second
     * @param <T>        type of the array to be sorted
     */
    private static <T> void bubbleSort(
            T[] toSort, CustomComparator<T> comparator
    ) {

        for (int i = 0; i < toSort.length; i++) {
            for (int j = i; j < toSort.length; j++) {
                if (comparator.compare(toSort[j], toSort[i]) <
                    EQUAL_IN_COMPARABLE) {
                    T temp = toSort[i];
                    toSort[i] = toSort[j];
                    toSort[j] = temp;
                }
            }
        }
    }

    /**
     * prints events ordered by date and timeslot
     */
    public void printByDate() {
        CustomComparator<Event> dateComparator =
                (event1, event2) -> event1.getDate()
                                          .compareTo(event2.getDate());
        EventCalendar.bubbleSort(this.events, dateComparator);

        this.print();

    }

    /**
     * prints events ordered by campus and building/room
     */
    public void printByCampus() {
        CustomComparator<Event> campusBuildingComparator =
                (event1, event2) -> event1.getCampus()
                                          .compareTo(event2.getCampus());
        EventCalendar.bubbleSort(this.events, campusBuildingComparator);

        this.print();
    }

    /**
     * prints events ordered by department
     */
    public void printByDepartment() {
        CustomComparator<Event> departmentComparator =
                (event1, event2) -> event1.getDepartment()
                                          .compareTo(event2.getDepartment());
        EventCalendar.bubbleSort(this.events, departmentComparator);

        this.print();
    }
}