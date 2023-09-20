package eventOrganizer;
public enum Location {
    HILL114("Hill Center", "Busch"),
    ARC103("Allison Road Classroom", "Busch"),
    BE_AUD("Beck Hall", "Livingston"),
    TIL232("Tillett Hall", "Livingston"),
    AB2225("Academic Building", "College Avenue");

    private final String buildingName;
    private final String campusName;

    Location(String buildingName, String campusName) {
        this.buildingName = buildingName;
        this.campusName = campusName;
    }

    public String toString() {
        return this.buildingName + ", " + this.campusName;
    }


    public static void main(String[] args) {
        System.out.println(Location.HILL114 + " - " + Location.HILL114);
        System.out.println(Location.ARC103 + " - " + Location.ARC103);
    }
}
