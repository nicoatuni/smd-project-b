package automail;

import exceptions.ExcessiveDeliveryException;
import exceptions.ItemTooHeavyException;
import strategies.IMailPool;
import strategies.IRobotBehaviour;

/**
 * The robot delivers mail!
 */
public class Robot {

	StorageTube tube;
    IRobotBehaviour behaviour;
    IMailDelivery delivery;
    protected final String id;
    /** Possible states the robot can be in */
    // public enum RobotState { DELIVERING, WAITING, RETURNING }
    // public RobotState current_state;
    private RobotState currentState;
    private int current_floor;
    private int destination_floor;
    private IMailPool mailPool;
    private boolean strong;
    
    private MailItem deliveryItem;
    
    private int deliveryCounter;
    

    /**
     * Initiates the robot's location at the start to be at the mailroom
     * also set it to be waiting for mail.
     * @param behaviour governs selection of mail items for delivery and behaviour on priority arrivals
     * @param delivery governs the final delivery
     * @param mailPool is the source of mail items
     * @param strong is whether the robot can carry heavy items
     */
    public Robot(IRobotBehaviour behaviour, IMailDelivery delivery, IMailPool mailPool, boolean strong){
    	id = "R" + hashCode();
        // current_state = RobotState.WAITING;
        // current_state = RobotState.RETURNING;
        this.currentState = new ReturningState();
        current_floor = Building.MAILROOM_LOCATION;
        tube = new StorageTube();
        this.behaviour = behaviour;
        this.delivery = delivery;
        this.mailPool = mailPool;
        this.strong = strong;
        this.deliveryCounter = 0;
    }

    /**
     * This is called on every time step
     * @throws ExcessiveDeliveryException if robot delivers more than the capacity of the tube without refilling
     */
    public void step() throws ExcessiveDeliveryException, ItemTooHeavyException{    	
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
    
    public IMailPool getMailPool() {
        return this.mailPool;
    }

    public boolean getStrong() {
        return this.strong;
    }

    public int getDeliveryCounter() {
        return this.deliveryCounter;
    }

    public void setDeliveryCounter(int newCounter) {
        this.deliveryCounter = newCounter;
    }

    public int getCurrentFloor() {
        return this.current_floor;
    }

    public int getDestinationFloor() {
        return this.destination_floor;
    }

    public MailItem getDeliveryItem() {
        return this.deliveryItem;
    }
}
