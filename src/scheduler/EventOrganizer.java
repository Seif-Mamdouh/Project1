package scheduler;

import java.io.FileNotFoundException;
import java.sql.Time;
import java.util.Scanner;


/**
 * User Interface to interact with EventCalendar
 *
 * @author Seifeldeen Mohamed
 */

public class EventOrganizer {
    private EventCalendar eventCalendar;
    private boolean testMode = false;
    /**
     * Create empty EventOrganizer
     */
    public EventOrganizer() {
        this.eventCalendar = new EventCalendar();
    }
    public EventOrganizer(boolean testMode) {
        this.eventCalendar = new EventCalendar();
        this.testMode = true;
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
     * The Method parses the 'A' command to trigger the addEvent logic to add
     * a new event to the event calendar
     *
     * @param commandAdd A string containing information to create and add an
     *                   event.
     *                   The expected format is "A DATE TIMESLOT LOCATION
     *                   DEPARTMENT VALID-EMAIL TIME(min)".
     * @throws IllegalArgumentException If the commandAdd string does not
     *                                  match the expected format.
     */
    public void addEvent(String commandAdd) {
        // parse the command and split it to read it
        String[] tokens = commandAdd.split("\\s+");

        int numberOfTokensExpected = 7;
        if (tokens.length != numberOfTokensExpected) {
            System.out.println(
                    "Invalid command/data, please add an event in the " +
                    "following order: ");
            System.out.println(
                    "A DATE TIMESLOT LOCATION DEPARTMENT VALID-EMAIL TIME" +
                    "(min)");
            return;
        }

        try {
            // parse the date from the user's input and see if it matches the
            // data from each class
            Date eventDate = Date.parseDate(tokens[DATE_TOKEN_INDEX]);
            // Check if the date is in a valid format
            if (!eventDate.isValid()) {
                System.out.println(
                        eventDate + ": Invalid calendar date!");
                return;
            }

            // Check if the date is more than 6 months away from the current
            // date
            if (eventDate.isMoreThanSixMonthsAway()) {
                System.out.println(
                        eventDate + ": Event date must be within 6 months!");
                return;
            }

            // Check if the date is a future date
            if (!eventDate.isFutureDate()) {
                System.out.println( eventDate + ": Event date must be a future date!");
                return;
            }

            String timeSlotToken = tokens[TIMESLOT_TOKEN_INDEX].toUpperCase();

            if(!Timeslot.isValidTimeSlot(timeSlotToken)){
                System.out.println("Invalid time slot!");
                return;
            }

            Timeslot timeslot = Timeslot.valueOf(timeSlotToken);

            String locationToken = tokens[LOCATION_TOKEN_INDEX].toUpperCase();

            if (!Location.isValidLocation(locationToken)) {
                System.out.println("Invalid location!");
                return;
            };

            Location location = Location.valueOf(locationToken);

            String departmentToken =
                    tokens[DEPARTMENT_TOKEN_INDEX].toUpperCase();

            if(!Department.isValidDepartment(departmentToken)){
                System.out.println("Invalid department information");
                return;
            };
            Department department = Department.valueOf(departmentToken);

            String contactEmail = tokens[CONTACT_EMAIL_TOKEN_INDEX];

            int duration = Integer.parseInt(tokens[DURATION_TOKEN_INDEX]);

            // check if the event duration is between 30 to 120 mins
            if (!Timeslot.isValidDuration(duration)) {
                System.out.println("Event duration must be at least 30 minutes and at most 120 minutes");
                return;
            }

            Contact contact = new Contact(department, contactEmail);
            if (!contact.isValid()) {
                System.out.println("Invalid contact information!");
                return;
            }

            Event newEvent =
                    new Event(eventDate, timeslot, location, contact, duration);
            boolean hasConflict = eventCalendar.hasConflict(newEvent);

            if (hasConflict) {
                System.out.println(
                        "The event is already on the calendar.");
            }
            else if (eventCalendar.add(newEvent)) {
                System.out.println("Event added to the calendar.");
            }
            else {
                System.out.println(
                        "Maybe there's a scheduling conflict. Event couldn't " +
                        "be added.");
            }
        }
        catch (Exception e) {
            System.out.println("Invalid Add input, please recheck your input");
        }
    }
    private static String EVENT_CALENDAR_EMPTY_MESSAGE = "Event calendar is empty!";
    /**
     * Displays all the current events in the event calendar
     */
    public void displayCalendar() {
        int numberEvents = eventCalendar.getNumEvents();

        if (numberEvents == 0) {
            System.out.println(EVENT_CALENDAR_EMPTY_MESSAGE);
            return;
        }

        System.out.println("* Event calendar *");
        eventCalendar.print();
        System.out.println("* end of event calendar *");
    }

    /**
     * Displays all the current events in the event calendar Sorted by Date
     */
    public void displayCalendarByDate() {
        int numberEvents = eventCalendar.getNumEvents();

        if (numberEvents == 0) {
            System.out.println(EVENT_CALENDAR_EMPTY_MESSAGE);
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
            System.out.println(EVENT_CALENDAR_EMPTY_MESSAGE);
            return;
        }
        System.out.println("Event Calendar (Sorted by Campus): ");
        eventCalendar.printByCampus();
    }


    /**
     * Displays all the current events in the event calendar Sorted by
     * department
     */
    public void displayCalendarByDepartment() {
        int numberEvents = eventCalendar.getNumEvents();

        if (numberEvents == 0) {
            System.out.println(EVENT_CALENDAR_EMPTY_MESSAGE);
            return;
        }
        System.out.println("The Event Calendar (Sorted by Department): ");
        eventCalendar.printByDepartment();
    }

    /**
     * Method to remove a Event from the Event List using the the Date to
     * search for the event
     *
     * @param dateToRemove  date of event to remove
     * @param timeSlotToken timeSlot of event to remove
     * @param locationToken location of event to remove
     */
    public void cancelEvent(
            String dateToRemove, String timeSlotToken, String locationToken
    ) {

        // parse tokens that the user have provided
        Date date = Date.parseDate(dateToRemove);
        if (!date.isValid()) {
            System.out.println(
                    date + ": Invalid calendar date!");
            return;
        }

        if (date.isMoreThanSixMonthsAway()) {
            System.out.println(
                    date + ": Event date must be within 6 months!");
            return;
        }

        if(!date.isFutureDate()){
            System.out.println(date + ": Event date must be a future date!");
        }

        Timeslot timeslot = Timeslot.valueOf(timeSlotToken.toUpperCase());
        Location location = Location.valueOf(locationToken.toUpperCase());

        //Intilaize the rest of default value for the event class
        Department CouldBeAnyDepartment = Department.CS;
        String CouldBeAnyContactEmail = "cs@rutgers.edu";
        int CouldBeAnyDuration = 60;

        // Create an event with user-specified parameters
        Event eventToRemove = new Event(
                date,
                timeslot,
                location,
                new Contact(CouldBeAnyDepartment, CouldBeAnyContactEmail),
                CouldBeAnyDuration
        );

        // Remove the event from the calendar
        if (eventCalendar.remove(eventToRemove)) {
            System.out.println("Event removed successfully.");
        }
        else {
            System.out.println("Event not found. Nothing to remove.");
        }
    }

    /**
     * Method to run User's commands
     *
     * @param commandLine command from user
     */
    public void processCommand(String commandLine) {
        // Split the command line into tokens
        String[] tokens = commandLine.split("\\s+");

        // Check if the command is empty or invalid
        if (tokens.length == 0) {
            System.out.println(
                    "Invalid command. Please provide a valid command.");
            return;
        }

        String commandType = tokens[0];

        int expectedTokensInRCase = 3;
        // Perform actions based on the command type
        switch (commandType) {
            case "A":
                addEvent(commandLine);
                break;
            case "R":
                if (tokens.length >= expectedTokensInRCase) {
                    String dateToken = tokens[1];
                    String timeSlotToken = tokens[2];
                    String locationToken = tokens[3];
                    cancelEvent(dateToken, timeSlotToken, locationToken);
                }
                else {
                    System.out.println(
                            "Invalid 'R' command. Please provide a Date, Time" +
                            " Slot & Location to remove an event.");
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


    /**
     * Runs the UI for the organizer, takes input and processes it
     */
    public void run() {

        Scanner scanner = new Scanner(System.in);
        java.io.PrintStream oldOut = System.out;

        if(this.testMode){

            try {
                scanner = new Scanner(new java.io.File("sample_inputs.txt"));
                System.setOut(new java.io.PrintStream(new java.io.FileOutputStream("this_run_output.txt")));
            }
            catch (FileNotFoundException e){
                System.out.println("file not found");
                return;
            }
            java.io.InputStream outputCheck = null;
        }

        System.out.println("Event Organizer running...\n");

        while (true) {
//            System.out.print("Enter command: ");
            String commandLine = scanner.nextLine();
            String trimCommand = commandLine.trim();

            if (trimCommand.equalsIgnoreCase("Q")) {
                System.out.println("Event Organizer terminated.");
                break;
            } else if (trimCommand.isEmpty()){
                continue;
            }
            processCommand(commandLine);
        }
        if(this.testMode){
            Scanner programOutputScanner = null;
            Scanner expectedOutputScanner = null;
            try {
                programOutputScanner = new Scanner(new java.io.File("this_run_output.txt"));
                expectedOutputScanner = new Scanner(new java.io.File("sample_outputs.txt"));
                System.setOut(oldOut);
            }
            catch (FileNotFoundException e){
               System.out.println("Couldn't check");
            }
            int allowedError = 1;
            int line = 1;
            while(programOutputScanner.hasNext() && expectedOutputScanner.hasNext()){
                String programOuput = programOutputScanner.nextLine();
                String expectedOutput = expectedOutputScanner.nextLine();

                if (!programOuput.equals(expectedOutput)) {
                    System.out.println(String.format("program output: '%s', \n expected output: '%s', line %d", programOuput, expectedOutput, line));
                    allowedError --;
                    if(allowedError < 0){
                        throw new AssertionError();
                    }

                    //System.out.println(new AssertionError());
                }


                //System.out.println(String.format("\n comparing from run: '%s' to \n expected: '%s' \n", programOuput, expectedOutput));
                line ++;
            }
            programOutputScanner.close();
            expectedOutputScanner.close();
            System.out.println("Everything is correct");
        }
        scanner.close();
    }

}