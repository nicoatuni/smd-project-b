package strategies;

import automail.IMailDelivery;
import automail.MailItem;
import automail.Robot;
import automail.StorageTube;

public class Automail {
	
	// Maybe we could change it into an array of robot? for cases where we have more than 2 robots
    private Robot robot1, robot2;
    public IMailPool mailPool;
    
    
    
    public Automail(IMailDelivery delivery, String robot1_type, String robot2_type) {
    	    	
    		/** Initialize the MailPool */
    		mailPool = new WeakStrongMailPool();
    		
    		boolean weak = false;  // Can't handle more than 2000 grams
    		boolean strong = true; // Can handle any weight that arrives at the building
    		
    		boolean roboact1 = true;
    		boolean roboact2 = true;
    		boolean case1 = false;
    		
    		if (robot1_type.equals("weak") && robot2_type.equals("weak")) {
    			System.out.println("INVALID INPUT, PROGRAM SHUT DOWN !!!");
    			System.exit(0);
    		} else if(robot1_type.equals("weak") || robot2_type.equals("weak")) {
    			if (robot1_type.equals("weak")) {
    				roboact1 = weak;
    				roboact2 = strong;
    			} else {
    				roboact1 = strong;
    				roboact2 = weak;
    			}		
    		} else if((case1=(robot1_type.equals("big") && robot2_type.equals("strong"))) || (robot1_type.equals("strong") && robot2_type.equals("big"))) {
    			if (case1) {
    				roboact1 = weak;
    				roboact2 = strong;
    			} else {
    				roboact1 = strong;
    				roboact2 = weak;
    			}
    		} else {
    			roboact1 = weak;
    			roboact2 = strong;
    		}
    		
    		/** Initialize robot */
    		robot1 = generateRobot(robot1_type, delivery, roboact1); /* shared behaviour because identical and stateless */
    		robot2 = generateRobot(robot2_type, delivery, roboact2);
    }
    
    /**
    *
    * @return the first robot
    */
    public Robot getRobot1() {
    		return this.robot1;
    }
    
    /**
    *
    * @return the second robot
    */
    public Robot getRobot2() {
    		return this.robot2;
	}
	
    /**
    *
    * @return the instantiated robot
    */
    private Robot generateRobot(String robot_type, IMailDelivery delivery, boolean strong) {
    	
    		boolean behaviour = false;
    		
    		if (robot_type.equals("big") || robot_type.equals("strong")) {
    			behaviour = strong;
    		}
    		
    		IRobotBehaviour robotBehaviour = new MyRobotBehaviour(behaviour);
    		
    		return new Robot(robotBehaviour, delivery, strong, this, robot_type);
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
	 * @param strong the type of the robot the tube belongs to
	 */
	public void fillStorageTube(StorageTube tube, boolean strong) {
		mailPool.fillStorageTube(tube, strong);
	}
    
}
