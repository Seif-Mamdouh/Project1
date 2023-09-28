package eventOrganizer;
import java.util.Calendar;


/**
 * Date class
 *
 * @author Seifeldeen Mohamed
 */

public class Date implements Comparable<Date> {
    public static final int QUADRENNIAL = 4;
    public static final int CENTENNIAL = 100;
    public static final int QUATERCENTENNIAL = 400;

    private int year;
    private int month;
    private int day;

    /**
     * Default Constructor
     * @param year
     * @param month
     * @param day
     */
    public Date(int year, int month, int day){
        this.year = year;
        this.month = month;
        this.day = day;
    }


    public int getYear(){
        return this.year;
    }

    public int getMonth(){
        return this.month;
    }

    public int getDay(){
        return this.day;
    }

    /**
     * Parse a date string in the format "month/day/year" and return a Date object.
     *
     * @param dateStr the date string to parse
     * @return a Date object representing the parsed date
     * @throws IllegalArgumentException if the input is not in a valid format
     */
    public static Date parseDate(String dateStr){
        String [] dateSplit = dateStr.split("/");
        if(dateSplit.length != 3){
            throw new IllegalArgumentException("Invalid date format: " + dateStr);
        };

        int month, day, year;

        try {
            month = Integer.parseInt(dateSplit[0]);
            day = Integer.parseInt(dateSplit[1]);
            year = Integer.parseInt(dateSplit[2]);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Invalid date format: " + dateStr);
        }

        return new Date(year, month, day);
    }

    /**
     * Check if the date is in a valid format (mm/dd/yyyy).
     *
     * @return true if the date is valid, false otherwise
     */
    public boolean isValid() {
        if (month < 1 || month > 12 || year < 0) {
            return false;
        }
        int[] daysInMonth = {0, 31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31};

        if (isLeapYear(year)) {
            daysInMonth[2] = 29;
        }

        return day >= 1 && day <= daysInMonth[month];
    }

    /**
     * Check if the date is more than 6 months away from the current date.
     *
     * @return true if the date is more than 6 months away, false otherwise
     */
    public boolean isMoreThanSixMonthsAway() {
        Calendar todayDate = Calendar.getInstance();
        Calendar targetDate = Calendar.getInstance();
        targetDate.set(year, month - 1, day); //Calendar month is 0-indexed

        // Calculate the difference in months
        long monthsDifference = getMonthDifference(todayDate, targetDate);

        return monthsDifference > 6;
    }


    /**
     * Check if the date is a future date (not equal to or before the current date).
     *
     * @return true if the date is a future date, false otherwise
     */
    public boolean isFutureDate() {
        Calendar todayDate = Calendar.getInstance();
        Calendar targetDate = Calendar.getInstance();
        targetDate.set(year, month - 1, day);

        return !targetDate.before(todayDate);
    }


    private boolean isLeapYear(int year) {
        // Check if the year is a leap year (divisible by 4, not divisible by 100, or divisible by 400)
        return (year % 4 == 0 && year % 100 != 0) || (year % 400 == 0);
    }


    private long getMonthDifference(Calendar givenDate, Calendar endDate){
        int startYear = givenDate.get(Calendar.YEAR);
        int startMonth = givenDate.get(Calendar.MONTH);
        int endYear = endDate.get(Calendar.YEAR);
        int endMonth = endDate.get(Calendar.MONTH);

        return (endYear - startYear) * 12 + (endMonth - startMonth);
    }


//    private boolean isValidMonth(int month) {
//        // Check if the month is within a valid range (1 to 12)
//        return month >= 1 && month <= 12;
//    }


    public int compareTo(Date otherDate) {

        if (this.year != otherDate.getYear()) {
            return Integer.compare(this.year,  otherDate.getYear());
        }

        if (this.month != otherDate.getMonth()) {
            return Integer.compare(this.month, otherDate.getMonth());
        }

        return Integer.compare(this.day, otherDate.getDay());
    }



    @Override
    public String toString() {
        return String.format("%s/%s/%s", this.month, this.day, this.year);
    }

    public static void main(String[] args) {

        Date date1 = new Date(2023, 9, 12);
        Date date2 = new Date(2023, 9, 11);
        Date date3 = new Date(2023, 9, 11);
        System.out.println("Comparing date1 to date2: " + date1.compareTo(date2));
        System.out.println("Comparing date2 to date3: " + date2.compareTo(date3));

    }
};



