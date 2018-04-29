package strategies;

import automail.IMailDelivery;
import automail.MailItem;
import automail.Robot;
import automail.StorageTube;

public class Automail {
	
	// Maybe we could change it into an array of robot? for cases where we have more than 2 robots
    private Robot robot1, robot2;
    public IMailPool mailPool;
    
    public Automail(IMailDelivery delivery, String robotType1, String robotType2) {
    	// Swap between simple provided strategies and your strategies here
    	    	
    	/** Initialize the MailPool */
    	
    	//// Swap the next line for the one below
    	mailPool = new WeakStrongMailPool();
    	
        /** Initialize the RobotAction */
    	boolean weak = false;  // Can't handle more than 2000 grams
    	boolean strong = true; // Can handle any weight that arrives at the building
    	
    	boolean roboact1;
    	boolean roboact2;
    	
    	if (robotType1.equals("weak")) {
    		roboact1 = weak;
    	} else {
    		roboact1 = strong;
    	}
    	
    	if (robotType2.equals("weak")) {
    		roboact2 = weak;
    	} else {
    		roboact2 = strong;
    	}
    	
    	//// Swap the next two lines for the two below those
    	IRobotBehaviour robotBehaviour1 = new MyRobotBehaviour(roboact1);
    	IRobotBehaviour robotBehaviour2 = new MyRobotBehaviour(roboact2);
    	    	
    	/** Initialize robot */
    	robot1 = new Robot(robotBehaviour1, delivery, robotType1, this); /* shared behaviour because identical and stateless */
    	robot2 = new Robot(robotBehaviour2, delivery, robotType2, this);
    }
    
    public Robot getRobot1() {
    		return this.robot1;
    }
    
    public Robot getRobot2() {
    		return this.robot2;
	}
	
	/**
	 * Add the mail item to the mail pool
	 * @param mailItem the mail item to be added to the pool
	 */
	public void addToPool(MailItem mailItem) {
		mailPool.addToPool(mailItem);
	}

	/**
	 * Fill the specified storage tube with a mail item
	 * @param tube the storage tube to be filled with mail item(s)
	 * @param robotType the type of robot the tube belongs to
	 */
	public void fillStorageTube(StorageTube tube, String robotType) {
		mailPool.fillStorageTube(tube, robotType);
	}
    
}
