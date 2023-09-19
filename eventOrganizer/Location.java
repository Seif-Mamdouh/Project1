package eventOrganizer;
public enum Location {
    HILL114("Hill Center, Busch"),
    ARC103("Allison Road Classroom, Busch"),
    BE_AUD("Beck Hall, Livingston"),
    TIL232("Tillettt Hall, Livingston"),
    AB2225("Academic Building, College Avenue");

    private final String description;

    Location(String description){
        this.description = description;
    }

    public String getDescription(){
        return description;
    }


    public static void main (String[] args){
        System.out.println(Location.HILL114 + " - " + Location.HILL114.getDescription());
        System.out.println(Location.ARC103 + " - " + Location.ARC103.getDescription());
    }

}


