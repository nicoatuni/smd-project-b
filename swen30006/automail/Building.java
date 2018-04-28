package automail;

public class Building {
	
	
    /** The number of floors in the building **/
    private static int FLOORS = 14;
    
    /** Represents the ground floor location */
    public static final int LOWEST_FLOOR = 1;
    
    /** Represents the mailroom location */
    public static final int MAILROOM_LOCATION = 1;
    
    public static int getFloor() {
    		return FLOORS;
    }
    
    public static void setFloor(int new_floor) {
    		FLOORS = new_floor;
    }

}
