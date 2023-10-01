package scheduler;

/**
 * Class that represents a Location consisting of a building and campus.
 *
 * @author Michael Muzafarov
 */
public enum Location {
    HLL114("Hill Center", "Busch"),
    ARC103("Allison Road Classroom", "Busch"),
    BE_AUD("Beck Hall", "Livingston"),
    TIL232("Tillett Hall", "Livingston"),
    AB2225("Academic Building", "College Avenue");

    private final String buildingName;
    private final String campusName;

    /**
     * Construct location with a building name and campus name
     *
     * @param buildingName name of building associated with location
     * @param campusName   name of campus associated with location
     */
    Location(String buildingName, String campusName) {
        this.buildingName = buildingName;
        this.campusName = campusName;
    }

    /**
     * Checks if a given location string is valid by comparing it to the known building names
     * in the Location enum.
     *
     * @param givenUserLocation The location string to validate.
     * @return true if the location string is a valid building name, false otherwise.
     */
    public static boolean isValidLocation(String givenUserLocation) {
        for (Location location : values()) {
            if (location.buildingName.equalsIgnoreCase(givenUserLocation)) {
                return true;
            }
        }
        return false;
    }

    /**
     * String representation of location
     *
     * @return building name concatenated with campus name
     */
    public String toString() {
        return this.buildingName + ", " + this.campusName;
    }

    /**
     * Getter for building name
     *
     * @return buildingName belonging to this location
     */
    public String getBuildingName() {
        return buildingName;
    }

    /**
     * Getter for campus name
     *
     * @return campusName belonging to this location
     */
    public String getCampusName() {
        return campusName;
    }

//    public static boolean isValidLocation(Location location) {

//    }

    public static void main(String[] args) {
        System.out.println(Location.HLL114 + " - " + Location.HLL114);
        System.out.println(Location.ARC103 + " - " + Location.ARC103);
    }
}
