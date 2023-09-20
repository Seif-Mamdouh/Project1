package eventOrganizer;


/**
 * enum class for campus locations using acronyms and provides the full name and campus location for each campus acronym
 * @Author Seifeldeen Mohamed
 */
public enum Location {
    HILL114("Hill Center, Busch"),
    ARC103("Allison Road Classroom, Busch"),
    BE_AUD("Beck Hall, Livingston"),
    TIL232("Tillet Hall, Livingston"),
    AB2225("Academic Building, College Avenue");

    private final String description;
    /**
     * Constructs a Location enum value with the specified description.
     *
     * @param description The description of the campus location.
     */
    Location(String description){
        this.description = description;
    }

    /**
     * @returns the description of the location.
     */
    public String getDescription(){
        return description;
    }

    public static void main (String[] args){
        System.out.println(Location.HILL114 + " - " + Location.HILL114.getDescription());
        System.out.println(Location.ARC103 + " - " + Location.ARC103.getDescription());
    }
}
