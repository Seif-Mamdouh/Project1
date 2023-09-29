package eventOrganizer;
import java.util.Scanner;


/**
 * The main Method to trigger all classes
 *
 * @author Seifeldeen Mohamed
 */

public class EventOrganizer {
    private EventCalendar eventCalendar;


    public void setEventCalendar(EventCalendar eventCalendar) {
        this.eventCalendar = eventCalendar;
    }


    /**
     * private static numbers to avoid magic numbers
     */
    private static final int DATE_TOKEN_INDEX = 1;
    private static final int TIMESLOT_TOKEN_INDEX = 2;
    private static final int LOCATION_TOKEN_INDEX = 3;
    private static final int DEPARTMENT_TOKEN_INDEX = 4;
    private static final int CONTACT_EMAIL_TOKEN_INDEX = 5;
    private static final int DURATION_TOKEN_INDEX = 6;


    /**
     * Method is used to add a new event to the event calendar.
     * The Method parses the 'A' command to trigger the addEvent logic to add a new event to the event calendar
     *
     * @param commandAdd A string containing information to create and add an event.
     *                   The expected format is "A DATE TIMESLOT LOCATION DEPARTMENT VALID-EMAIL TIME(min)".
     *
     * @throws IllegalArgumentException If the commandAdd string does not match the expected format.
     */


    public void addEvent(String commandAdd) {
        // parse the command and split it to read it
        String[] tokens = commandAdd.split(" ");

        int numberOfTokensExpected = 7;
        if (tokens.length != numberOfTokensExpected) {
            System.out.println("Invalid command/data, please add an event in the following order: ");
            System.out.println("A DATE TIMESLOT LOCATION DEPARTMENT VALID-EMAIL TIME(min)");
            return;
        }

        try {
            // parse the date from the user's input and see if it matches the data from each class
            Date eventDate = Date.parseDate(tokens[DATE_TOKEN_INDEX]);
            // Check if the date is in a valid format
            if (!eventDate.isValid()) {
                System.out.println("Invalid date format. Please use mm/dd/yyyy format.");
                return;
            }

            // Check if the date is more than 6 months away from the current date
            if (eventDate.isMoreThanSixMonthsAway()) {
                System.out.println("Event date is more than 6 months away from today’s date.");
                return;
            }

            // Check if the date is a future date
            if (!eventDate.isFutureDate()) {
                System.out.println("Event date should be a future date.");
                return;
            }

            Timeslot timeSlot = Timeslot.valueOf(tokens[TIMESLOT_TOKEN_INDEX].toUpperCase());
            Location location = Location.valueOf(tokens[LOCATION_TOKEN_INDEX].toUpperCase());
            Department department = Department.valueOf(tokens[DEPARTMENT_TOKEN_INDEX].toUpperCase());
            String contactEmail = tokens[CONTACT_EMAIL_TOKEN_INDEX];
            int duration = Integer.parseInt(tokens[DURATION_TOKEN_INDEX]);

            Contact contact = new Contact(department, contactEmail);
            Event newEvent = new Event(eventDate, timeSlot, location, contact, duration);
            boolean hasConflict = eventCalendar.hasConflict(newEvent);

            if (hasConflict) {
                System.out.println("Conflict of schedule - an event with the same date/timeslot/location is already on the calendar.");
            } else if (eventCalendar.add(newEvent)) {
                    System.out.println("Event added successfully.");
            } else {
                System.out.println("Maybe there's a scheduling conflict. Event couldn't be added.");
            }
        } catch (Exception e) {
            System.out.println("Invalid Add input, please recheck your input");
        }
    }

    /**
     * Displays all the current events in the event calendar
     *
     */
    public void displayCalendar() {
        int numberEvents = eventCalendar.getNumEvents();

        if (numberEvents == 0) {
            System.out.println("The Event Calendar is empty");
            return;
        } else {
            eventCalendar.print();
        }
    }

    /**
     * Displays all the current events in the event calendar Sorted by Date
     */
    public void displayCalendarByDate() {
        int numberEvents = eventCalendar.getNumEvents();

        if (numberEvents == 0) {
            System.out.println("The Event Calendar is empty");
            return;
        }

        System.out.println("Event Calendar (Sorted by Date):");
        eventCalendar.printByDate();
    }

    /**
     * Displays all the current events in the event calendar Sorted by Campus
     */

    public void displayCalendarByCampus() {
        int numberEvents = eventCalendar.getNumEvents();

        if (numberEvents == 0) {
            System.out.println("The Event Calendar is empty");
            return;
        }
        System.out.println("Event Calendar (Sorted by Campus): ");
        eventCalendar.printByCampus();
    }


    /**
     * Displays all the current events in the event calendar Sorted by Department
     */

    public void displayCalendarByDepartment() {
        int numberEvents = eventCalendar.getNumEvents();

        if (numberEvents == 0) {
            System.out.println("The Event Calendar is empty");
            return;
        }
            System.out.println("The Event Calendar (Sorted by Department): ");
            eventCalendar.printByDepartment();
    }

    /**
     * Method to remove a Event from the Event List using the the Date to search for the event
     *
     * @param dateToRemove
     * @param timeSlotToken
     * @param locationToken
     */
    public void cancelEvent(String dateToRemove, String timeSlotToken, String locationToken) {

        // parse tokens that the user have provided
        Date date = Date.parseDate(dateToRemove);
        Timeslot timeslot = Timeslot.valueOf(timeSlotToken.toUpperCase());
        Location location = Location.valueOf(locationToken.toUpperCase());

        //Intilaize the rest of default value for the event class
        Department department = Department.CS;
        String contactEmail = "cs@rutgers.edu";
        int duration = 60;

        // Create an event with user-specified parameters
        Event eventToRemove = new Event(date, timeslot, location, new Contact(department, contactEmail), duration);

        // Remove the event from the calendar
        if (eventCalendar.remove(eventToRemove)) {
            System.out.println("Event removed successfully.");
        } else {
            System.out.println("Event not found. Nothing to remove.");
        }
    }

    /**
     * Method to run User's commands
     * @param commandLine
     */
    public void processCommand(String commandLine) {
        // Split the command line into tokens
        String[] tokens = commandLine.split("\\s+");

        // Check if the command is empty or invalid
        if (tokens.length == 0) {
            System.out.println("Invalid command. Please provide a valid command.");
            return;
        }

        String commandType = tokens[0];

        // Perform actions based on the command type
        switch (commandType) {
            case "A":
                addEvent(commandLine);
                break;
            case "R":
                if (tokens.length >= 3) {
                    String dateToken = tokens[1];
                    String timeSlotToken = tokens[2];
                    String locationToken = tokens[3];
                    cancelEvent(dateToken, timeSlotToken, locationToken);
                } else {
                    System.out.println("Invalid 'R' command. Please provide a Date, Time Slot & Location to remove an event.");
                }
                break;
            case "P":
                displayCalendar();
                break;
            case "PE":
                displayCalendarByDate();
                break;
            case "PC":
                displayCalendarByCampus();
                break;
            case "PD":
                displayCalendarByDepartment();
                break;
            default:
                System.out.println(commandType + " is an invalid command!");
                break;
        }
    }


    public void run() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Event Organizer running...");

        while (true) {
            System.out.print("Enter command: ");
            String commandLine = scanner.nextLine();

            if (commandLine.trim().equalsIgnoreCase("Q")) {
                System.out.println("Event Organizer terminated.");
                break;
            }
            processCommand(commandLine);
        }
        scanner.close();
    }

    public static void main(String[] args){
        EventCalendar eventCalendar = new EventCalendar();

        // Create EventOrganizer and set the EventCalendar
        EventOrganizer organizer = new EventOrganizer();
        organizer.setEventCalendar(eventCalendar);
        organizer.run();
    }
}


//logic