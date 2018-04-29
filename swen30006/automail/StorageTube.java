package automail;

// import exceptions.RobotNotInMailRoomException;
import exceptions.TubeFullException;

import java.util.Stack;

/**
 * The storage tube carried by the robot.
 */
public class StorageTube {

    private int maximum_capacity;
    private Stack<MailItem> tube;

    /**
     * Constructor for the storage tube
     */
    public StorageTube(String type){
        this.tube = new Stack<MailItem>();
        
        if (type.equals("big")) {
        		this.maximum_capacity = 6;
        } else {
        		this.maximum_capacity = 4;
        }
    }

    /**
     * @return if the storage tube is full
     */
    public boolean isFull(){
        return tube.size() == this.maximum_capacity;
        
    }

    /**
     * @return if the storage tube is empty
     */
    public boolean isEmpty(){
        return tube.isEmpty();
    }
    
    /**
     * @return the first item in the storage tube (without removing it)
     */
    public MailItem peek() {
    	return tube.peek();
    }

    /**
     * Add an item to the tube
     * @param item The item being added
     * @throws TubeFullException thrown if an item is added which exceeds the capacity
     */
    public void addItem(MailItem item) throws TubeFullException {
        if(tube.size() < this.maximum_capacity){
        	tube.add(item);
        } else {
            throw new TubeFullException();
        }
    }

    /** @return the size of the tube **/
    public int getSize(){
    	return tube.size();
    }
    
    /** 
     * @return the first item in the storage tube (after removing it)
     */
    public MailItem pop(){
        return tube.pop();
    }

    	public int getMaxCapacity() {
    		return this.maximum_capacity;
    	}
}
