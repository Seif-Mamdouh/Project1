package eventOrganizer;


public class EventCalendar {
    private Event[] events; //the array holding the list of events
    private int numEvents; //current number of events in the array

    final static int NOT_FOUND = -1;
    final static int EQUAL_IN_COMPARABLE = 0;
    final static int INITIAL_AND_ADDITIONAL_ARRAY_CAPACITY = 4;
    final static int INITIAL_NUMBER_OF_EVENTS = 0;

    public EventCalendar() {
        this.events =
                new Event[EventCalendar.INITIAL_AND_ADDITIONAL_ARRAY_CAPACITY];
        this.numEvents = INITIAL_NUMBER_OF_EVENTS;
    }

    public int getNumEvents() {
        return numEvents;
    }

    /**
     * Search all events and return the event's index if it is found.
     * Returns NOT_FOUND if it is not in the array.
     *
     * @param event the event whose index will be found
     * @return index of event or NOT_FOUND if it is not found
     */
    private int find(Event event) {
        for (int i = 0; i < this.numEvents; i++) {
            if (this.events[i].equals(event)) {
                return i;
            }
        }
        return EventCalendar.NOT_FOUND;
    }

    /**
     * Increase the capacity of the events array by
     * INITIAL_AND_ADDITIONAL_ARRAY_CAPACITY.
     */
    private void grow() {
        Event[] newEvents = new Event[this.events.length +
                                      EventCalendar.INITIAL_AND_ADDITIONAL_ARRAY_CAPACITY];

        for (int i = 0; i < this.events.length; i++) {
            newEvents[i] = this.events[i];
        }

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

        for (int i = indexOfEvent + 1; i < this.numEvents; i++) {
            this.events[i - 1] = this.events[i];
        }

        this.numEvents--;

        return true;
    }

    /**
     * Checks if the calendar contains a particular Event
     *
     * @param event the Event object to check for
     * @return true if it is found, false if it does not exist in the calendar
     */
    public boolean contains(Event event) {
        return this.find(event) != EventCalendar.NOT_FOUND;
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
    @FunctionalInterface
    private interface CustomComparator<T> {
        int compare(T a, T b);
    }

    /**
     * Implementation of bubble sort. Could not do O(nlogn) sort because it
     * had to be in place and don't have access to random library.
     *
     * @param toSort     array to be sorted
     * @param numToSort  number of things to sort will only sort index 0
     *                   through numToSort - 1. Any index after will be ignored
     * @param comparator typically a lambda that will be used to compare two
     *                   things in the array. Is expected to return a
     *                   negative number if the first parameter is smaller
     *                   than the second
     * @param <T>        type of the array to be sorted
     */
    private static <T> void bubbleSort(
            T[] toSort, int numToSort, CustomComparator<T> comparator
    ) {

        for (int i = 0; i < numToSort; i++) {
            for (int j = i; j < numToSort; j++) {
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
        EventCalendar.bubbleSort(this.events, this.numEvents, Event::compareTo);

        this.print();

    }

    /**
     * prints events ordered by campus and building/room
     */
    public void printByCampus() {
        CustomComparator<Event> campusBuildingComparator = (event1, event2) -> event1.getLocation().getCampusName().compareTo(event2.getLocation().getCampusName());

        EventCalendar.bubbleSort(this.events,
                                 this.numEvents,
                                 campusBuildingComparator
        );

        this.print();
    }

    /**
     * prints events ordered by department
     */
    public void printByDepartment() {
        CustomComparator<Event> departmentComparator =
                (event1, event2) -> event1.getContact()
                                          .getDepartment().toString()
                                          .compareTo(event2.getContact()
                                                           .getDepartment().toString());

        EventCalendar.bubbleSort(this.events,
                                 this.numEvents,
                                 departmentComparator
        );

        this.print();
    }

    /**
     * Method to check if the event exists by checking if the DATE/TIMESLOT/LOCATION already exists
     * @param eventToCheck
     * @return
     */
    public boolean hasConflict(Event eventToCheck) {
        return this.find(eventToCheck) != -1;

    }

    /**
     * unit tests for EventCalendar class
     *
     * @param args unused, does not take command line arguments
     */
    public static void main(String[] args) {
        Event event1 = new Event(new Date(2023, 9, 20),
                                 Timeslot.MORNING,
                                 Location.ARC103,
                                 new Contact(Department.EE,
                                             "electric@rutgers.edu"
                                 ),
                                 120
        );
        System.out.println(event1);
        Event event2 = new Event(new Date(2023, 9, 21),
                                 Timeslot.AFTERNOON,
                                 Location.HILL114,
                                 new Contact(Department.CS, "cs@rutgers.edu"),
                                 70
        );
        System.out.println(event2);
        Event event3 = new Event(new Date(2023, 8, 19),
                                 Timeslot.EVENING,
                                 Location.AB2225,
                                 new Contact(Department.BAIT,
                                             "bait@rutgers.edu"
                                 ),
                                 97
        );
        System.out.println(event3);
        Event event4 = new Event(new Date(2023, 10, 19),
                                 Timeslot.MORNING,
                                 Location.BE_AUD,
                                 new Contact(Department.ITI,
                                             "iti7@rutgers.edu"
                                 ),
                                 98
        );
        System.out.println(event4);
        System.out.println("those were all of the events");

        EventCalendar eventCalendar = new EventCalendar();
        assert eventCalendar.add(event1);
        System.out.println("after first event is added:");
        eventCalendar.print();

        assert eventCalendar.add(event2);
        System.out.println("after second event is added:");
        eventCalendar.print();

        assert eventCalendar.add(event3);
        System.out.println("after third event is added:");
        eventCalendar.print();

        assert eventCalendar.contains(event1);
        assert eventCalendar.contains(event2);
        assert eventCalendar.contains(event3);
        assert !eventCalendar.contains(event4);
        assert !eventCalendar.remove(event4);
        assert eventCalendar.add(event4);
        System.out.println("Finished assertions");

        System.out.println("just print");
        eventCalendar.print();

        System.out.println("print by campus:");
        eventCalendar.printByCampus();

        System.out.println("print by date:");
        eventCalendar.printByDate();

        System.out.println("print by department:");
        eventCalendar.printByDepartment();

        assert eventCalendar.remove(event4);
        assert !eventCalendar.contains(event4);
    }
}