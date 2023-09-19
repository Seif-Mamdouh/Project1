package eventOrganizer;


/**
 * @Author Seifeldeen Mohamed
 * Represents a date in the MM/DD/YYYY format and provides methods for date validation.
 */
public class Date implements Comparable<Date> {
    /**
     *
     *
     */
    public static final int QUADRENNIAL = 4;
    public static final int CENTENNIAL = 100;
    public static final int QUATERCENTENNIAL = 400;

    private int year;
    private int month;
    private int day;

    /**
     * A Constructor that has a new Date object with the specified year, month, and day.
     *
     * @param year (YYYY)
     * @param month The month (1-12)
     * @param day The day of the month (1-31).
     */
    public Date(int year, int month, int day){
        this.year = year;
        this.month = month;
        this.day = day;
    }


    /**
     * A public method that checks if the date is a valid calendar date.
     *
     * @return True if the date is valid; otherwise, false.
     */
    public boolean isValid(){
        if (month < 1 || month > 12){
            return false;
        }
        int [] daysInMonth = {0, 31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31};

        if(isLeapYear(year)){
            daysInMonth[2] = 29;
        };

        return day >= 1 && day <= daysInMonth[month];
    }

    /**
     * Compares this date to another date.
     *
     * @param otherDate The other date to compare to.
     * @return A negative integer if this date is before the other date,
     *         zero if they are equal, and a positive integer if this date is after the other date.
     */
    public int compareTo(Date otherDate) {
        if (this.year != otherDate.year) {
            return Integer.compare(this.year, otherDate.year);
        }

        if (this.month != otherDate.month) {
            return Integer.compare(this.month, otherDate.month);
        }

        return Integer.compare(this.day, otherDate.day);
    }


    /**
     * Checks if a given year is a leap year or not
     *
     * @param year The int year to check for leap year status.
     * @return True if the year is a leap year; otherwise, false.
     */
    private boolean isLeapYear(int year){
        /**
         * To determine whether a year is a leap year according to the Gregorian calendar:
         * 1) If the year is evenly divisible by 4, go to step 2. Otherwise, go to step 5.
         * 2) If the year is evenly divisible by 100, go to step 3. Otherwise, go to step 4.
         * 3) If the year is evenly divisible by 400, go to step 4. Otherwise, go to step 5.
         * 4) The year is a leap year.
         * 5) The year is not a leap year.
         */
        if((year % 4 == 0 && year % 100 != 0) || (year % 400 == 0)) {
            return true;
        }
        return false;
    };

    public static void main(String[] args) {
        Date date1 = new Date(2023, 9, 12);
        Date date2 = new Date(2023, 9, 11);
        Date date3 = new Date(2023, 9, 11);
        System.out.println("Comparing date1 to date2: " + date1.compareTo(date2));
        System.out.println("Comparing date2 to date3: " + date2.compareTo(date3));

        // Test cases for isValid method
        Date validDate = new Date(2023, 9, 12);
        Date invalidDate1 = new Date(2023, 9, 31); // Invalid day for September
        Date invalidDate2 = new Date(2023, 2, 29); // Invalid day for February in a non-leap year
        Date leapYearDate = new Date(2024, 2, 29); // Valid date in a leap year

        System.out.println("Is validDate valid? " + validDate.isValid()); // Should be true
        System.out.println("Is invalidDate1 valid? " + invalidDate1.isValid()); // Should be false
        System.out.println("Is invalidDate2 valid? " + invalidDate2.isValid()); // Should be false
        System.out.println("Is leapYearDate valid? " + leapYearDate.isValid()); // Should be true
    }
};