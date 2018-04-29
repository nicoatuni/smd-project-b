package strategies;

import automail.IMailDelivery;
import automail.MailItem;
import automail.Robot;
import automail.StorageTube;

public class Automail {
	
	// Maybe we could change it into an array of robot? for cases where we have more than 2 robots
    private Robot robot1, robot2;
    private IMailPool mailPool;
    private MailItem item;
    
    public Automail(String robot1_type, String robot2_type) {
    		// Swap between simple provided strategies and your strategies here
    	    	
    		/** Initialize the MailPool */
    		item = null;
    		//// Swap the next line for the one below
    		mailPool = new WeakStrongMailPool();
    		
    		//robot1_type itu weak -> upper; else if robot1_type itu strong ->
    		//robot1_type itu weak juga -> exit() print invalid input robot type
    		//robot
    		boolean weak = false;  // Can't handle more than 2000 grams
    		boolean strong = true; // Can handle any weight that arrives at the building
    		
    		boolean roboact1 = true;
    		boolean roboact2 = true;
    		boolean case1 = false;
    		
    		if (robot1_type.equals("weak") && robot2_type.equals("weak")) {
    			//exit
    			System.out.println("INVALID INPUT, PROGRAM SHUT DOWN !!!");
    			System.exit(0);
    		} else if(robot1_type.equals("weak") || robot2_type.equals("weak")) {
    			//look which one is weak
    			if (robot1_type.equals("weak")) {
    				roboact1 = weak;
    				roboact2 = strong;
    			} else {
    				roboact1 = strong;
    				roboact2 = weak;
    			}
    			
    		} else if((case1=(robot1_type.equals("big") && robot2_type.equals("strong"))) || (robot1_type.equals("strong") && robot2_type.equals("big"))) {
    			//big upper, strong lower
    			if (case1) {
    				roboact1 = weak;
    				roboact2 = strong;
    			} else {
    				roboact1 = strong;
    				roboact2 = weak;
    			}
    			
    		} else {
    			//robot1 = upper, robot2 = lower
    			roboact1 = weak;
			roboact2 = strong;
    		}
    		
    		/** Initialize robot */
    		robot1 = generateRobot(robot1_type, roboact1); /* shared behaviour because identical and stateless */
    		robot2 = generateRobot(robot2_type, roboact2);
    }
    
    public Robot getRobot1() {
    		return this.robot1;
    }
    
    public Robot getRobot2() {
    		return this.robot2;
	}
	
    public IMailPool getMailPool() {
    		return this.mailPool;
    }
    
    private Robot generateRobot(String robot_type, boolean strong) {
    	
    		boolean behaviour = false;
    		
    		if (robot_type.equals("big") || robot_type.equals("strong")) {
    			behaviour = strong;
    		}
    		
    		IRobotBehaviour robotBehaviour = new MyRobotBehaviour(behaviour);
    		
    		return new Robot(robotBehaviour, strong, this, robot_type);
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
    
	public MailItem getItem() {
		return this.item;
	}
	
	public void setItem(MailItem item) {
		this.item = item;
	}
}
