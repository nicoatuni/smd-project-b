package automail;

public class Clock {
	
	/** Represents the current time **/
    private static int Time = 0;
    
    /** The threshold for the latest time for mail to arrive **/
    private static int LAST_DELIVERY_TIME = 300;

    public static int Time() {
    	return Time;
    }
    
    public static void Tick() {
    	Time++;
    }
    
    public static int getDeliveryTime() {
    		return LAST_DELIVERY_TIME;
    }
    
    public static void setDeliveryTime(int newtime) {
    		LAST_DELIVERY_TIME = newtime;
    }
}
