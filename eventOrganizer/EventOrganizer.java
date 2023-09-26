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
            Date eventDate = Date.parseDate(tokens[1]);
            Timeslot timeSlot = Timeslot.valueOf(tokens[2].toUpperCase());
            Location location = Location.valueOf(tokens[3].toUpperCase());
            Department department = Department.valueOf(tokens[4].toUpperCase());
            String contactEmail = tokens[5];
            int duration = Integer.parseInt(tokens[6]);

            Contact contact = new Contact(department, contactEmail);

            Event newEvent = new Event(eventDate, timeSlot, location, contact, duration);

            if (eventCalendar.add(newEvent)) {
                System.out.println("Event added successfully.");
            } else {
                System.out.println("Maybe there's a scheduling conflict. Event couldn't be added");
            }
        } catch (Exception e) {
            System.out.println("Invalid input, please recheck your input");
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
            eventCalendar.printByDate();
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
        } else {
            System.out.println("Event Calendar (Sorted by Date):");
            eventCalendar.printByDate();
        }

    }

    /**
     * Displays all the current events in the event calendar Sorted by Campus
     */

    public void displayCalendarByCampus() {
        int numberEvents = eventCalendar.getNumEvents();

        if (numberEvents == 0) {
            System.out.println("The Event Calendar is empty");
            return;
        } else {
            System.out.println("Event Calendar (Sorted by Campus): ");
            eventCalendar.printByCampus();
        }
    }


    /**
     * Displays all the current events in the event calendar Sorted by Department
     */

    public void displayCalendarByDepartment() {
        int numberEvents = eventCalendar.getNumEvents();

        if (numberEvents == 0) {
            System.out.println("The Event Calendar is empty");
            return;
        } else {
            System.out.println("The Event Calendar (Sorted by Department): ");
            eventCalendar.printByDepartment();
        }
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


    public void processCommand(String commandLine) {
        // Split the command line into tokens
        String[] tokens = commandLine.split(" ");

        // Check if the command is empty or invalid
        if (tokens.length == 0) {
            System.out.println("Invalid command. Please provide a valid command.");
            return;
        }

        String commandType = tokens[0].toUpperCase();

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
                // Display the calendar based on the provided criteria
                if (tokens.length >= 2) {
                    String criteria = tokens[1].toUpperCase(); // Convert to uppercase for case insensitivity

                    if (criteria.equals("E")) {
                        displayCalendarByDate();
                    } else if (criteria.equals("C")) {
                        displayCalendarByCampus();
                    } else if (criteria.equals("D")) {
                        displayCalendarByDepartment();
                    } else {
                        System.out.println("Invalid 'P' command. Please specify a valid criteria (PE, PC, PD).");
                    }
                } else {
                    // Display the calendar with the current order
                    displayCalendar();
                }
                break;

            default:
                System.out.println("Invalid command. Please provide a valid command (A, R, P, Q).");
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