package automail;

import java.util.Map;
import java.util.TreeMap;
import exceptions.ExcessiveDeliveryException;
import exceptions.ItemTooHeavyException;
import strategies.Automail;
import strategies.IRobotBehaviour;

/**
 * The robot delivers mail!
 */
public class Robot {

	StorageTube tube;
    IRobotBehaviour behaviour;
    protected final String id;
    /** The state that the robot currently is in. */
    private RobotState currentState;
    private int current_floor;
    private int destination_floor;
    private boolean strong;
    
    private MailItem deliveryItem;
    
    private int deliveryCounter;

    private Automail automail;

    static int count = 0;
    static Map<Integer, Integer> hashMap = new TreeMap<Integer, Integer>();

    /**
     * Initiates the robot's location at the start to be at the mailroom
     * also set it to be waiting for mail.
     * @param behaviour governs selection of mail items for delivery and behaviour on priority arrivals
     * @param delivery governs the final delivery
     * @param mailPool is the source of mail items
     * @param strong is whether the robot can carry heavy items
     */
    public Robot(IRobotBehaviour behaviour, boolean strong, Automail automail, String type){
    	id = "R" + hashCode();
        this.currentState = new ReturningState();
        current_floor = Building.MAILROOM_LOCATION;
        tube = new StorageTube(type);
        this.behaviour = behaviour;
        this.strong = strong;
        this.deliveryCounter = 0;
        this.automail = automail;
    }

    /**
     * This is called on every time step
     * @throws ExcessiveDeliveryException if robot delivers more than the capacity of the tube without refilling
     */
    public void step() throws ExcessiveDeliveryException, ItemTooHeavyException{    
        /* Carries out different actions depending on the state it's in */	
    	currentState.action(this);
    }

    /**
     * Sets the route for the robot
     */
    public void setRoute() throws ItemTooHeavyException{
        /** Pop the item from the StorageUnit */
        deliveryItem = tube.pop();
        if (!strong && deliveryItem.weight > 2000) throw new ItemTooHeavyException(); 
        /** Set the destination floor */
        destination_floor = deliveryItem.getDestFloor();
    }

    /**
     * Generic function that moves the robot towards the destination
     * @param destination the floor towards which the robot is moving
     */
    public void moveTowards(int destination){
        if(current_floor < destination){
            current_floor++;
        }
        else{
            current_floor--;
        }
    }
    
    /**
     * Prints out the change in state
     * @param nextState the state to which the robot is transitioning
     */
    public void changeState(RobotState nextState){
        if (!currentState.equals(nextState)) {
            System.out.printf("T: %3d > %11s changed from %s to %s%n", Clock.Time(), id, currentState, nextState);
    	}
    	currentState = nextState;
        if (nextState.toString().equals("DELIVERING")) {
            System.out.printf("T: %3d > %11s-> [%s]%n", Clock.Time(), id, deliveryItem.toString());
        }
    }

    /**
     * Add the mail item to the mail pool through Automail
     * @param mailItem the mail item to be added to the pool
     */
    public void addToPool(MailItem mailItem) {
        automail.addToPool(mailItem);
    }

    /**
     * Get Automail to fill the robot's storage tube
     */
    public void fillStorageTube() {
        automail.fillStorageTube(tube, strong);
    }

    @Override
    public int hashCode() {
      Integer hash0 = super.hashCode();
      Integer hash = hashMap.get(hash0);
      if (hash == null) { hash = count++; hashMap.put(hash0, hash); }
      return hash;
    }

    /**
    *
    * @return the strong boolean value of this robot
    */
    public boolean getStrong() {
        return this.strong;
    }

    /**
    *
    * @return the deliveryCounter of this robot
    */
    public int getDeliveryCounter() {
        return this.deliveryCounter;
    }

    /**
    *
    * Set the deliveryCounter of this robot
    */
    public void setDeliveryCounter(int newCounter) {
        this.deliveryCounter = newCounter;
    }

    /**
    *
    * @return the current floor of the robot
    */
    public int getCurrentFloor() {
        return this.current_floor;
    }

    /**
    *
    * @return the destination floor of robot
    */
    public int getDestinationFloor() {
        return this.destination_floor;
    }

    /**
    *
    * @return the delivery item of this robot
    */
    public MailItem getDeliveryItem() {
        return this.deliveryItem;
    }

    public Automail getAutomail() {
    	return this.automail;
    }
}
