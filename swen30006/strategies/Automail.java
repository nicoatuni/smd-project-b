package strategies;

import automail.IMailDelivery;
import automail.MailItem;
import automail.Robot;
import automail.StorageTube;

public class Automail {
	
	// Maybe we could change it into an array of robot? for cases where we have more than 2 robots
    private Robot robot1, robot2;
    public IMailPool mailPool;
    
    public Automail(IMailDelivery delivery) {
    	// Swap between simple provided strategies and your strategies here
    	    	
    	/** Initialize the MailPool */
    	
    	//// Swap the next line for the one below
    	mailPool = new WeakStrongMailPool();
    	
        /** Initialize the RobotAction */
    	boolean weak = false;  // Can't handle more than 2000 grams
    	boolean strong = true; // Can handle any weight that arrives at the building
    	
    	//// Swap the next two lines for the two below those
    	IRobotBehaviour robotBehaviourW = new MyRobotBehaviour(weak);
    	IRobotBehaviour robotBehaviourS = new MyRobotBehaviour(strong);
    	    	
    	/** Initialize robot */
    	robot1 = new Robot(robotBehaviourW, delivery, weak, this); /* shared behaviour because identical and stateless */
    	robot2 = new Robot(robotBehaviourS, delivery, strong, this);
    }
    
    public Robot getRobot1() {
    		return this.robot1;
    }
    
    public Robot getRobot2() {
    		return this.robot2;
	}
	
	/**
	 * 
	 */
	public void addToPool(MailItem mailItem) {
		mailPool.addToPool(mailItem);
	}

	/**
	 * 
	 */
	public void fillStorageTube(StorageTube tube, boolean strong) {
		mailPool.fillStorageTube(tube, strong);
	}
    
}
