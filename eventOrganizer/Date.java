package eventOrganizer;
import java.util.Calendar;

public class Date implements Comparable<Date> {
    public static final int QUADRENNIAL = 4;
    public static final int CENTENNIAL = 100;
    public static final int QUATERCENTENNIAL = 400;

    private int year;
    private int month;
    private int day;

    public Date(int year, int month, int day){
        this.year = year;
        this.month = month;
        this.day = day;
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

    private boolean isLeapYear(int year) {
        // Check if the year is a leap year (divisible by 4, not divisible by 100, or divisible by 400)
        return (year % 4 == 0 && year % 100 != 0) || (year % 400 == 0);
    }

    private boolean isValidMonth(int month) {
        // Check if the month is within a valid range (1 to 12)
        return month >= 1 && month <= 12;
    }

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
    public int compareTo(Date otherDate) {
        if (this.year != otherDate.year) {
            return Integer.compare(this.year, otherDate.year);
        }

        if (this.month != otherDate.month) {
            return Integer.compare(this.month, otherDate.month);
        }

        return Integer.compare(this.day, otherDate.day);
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



