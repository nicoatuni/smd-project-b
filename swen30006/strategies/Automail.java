package strategies;

import automail.IMailDelivery;
import automail.Robot;

public class Automail {
	
	// Maybe we could change it into an array of robot? for cases where we have more than 2 robots
    private Robot robot1, robot2;
    public IMailPool mailPool;
    
    public Automail(IMailDelivery delivery, String robot1_action, String robot2_action) {
    	// Swap between simple provided strategies and your strategies here
    	    	
    	/** Initialize the MailPool */
    	
    	//// Swap the next line for the one below
    	mailPool = new WeakStrongMailPool();
    	
        /** Initialize the RobotAction */
    	boolean weak = false;  // Can't handle more than 2000 grams
    	boolean strong = true; // Can handle any weight that arrives at the building
    	
    	boolean roboact1;
    	boolean roboact2;
    	
    	if (robot1_action.equals("weak")) {
    		roboact1 = weak;
    	} else {
    		roboact1 = strong;
    	}
    	
    	if (robot2_action.equals("weak")) {
    		roboact2 = weak;
    	} else {
    		roboact2 = strong;
    	}
    	
    	
    	//// Swap the next two lines for the two below those
    	IRobotBehaviour robotBehaviourW = new MyRobotBehaviour(weak);
    	IRobotBehaviour robotBehaviourS = new MyRobotBehaviour(strong);
    	    	
    	/** Initialize robot */
    	robot1 = new Robot(robotBehaviourW, delivery, mailPool, roboact1); /* shared behaviour because identical and stateless */
    	robot2 = new Robot(robotBehaviourS, delivery, mailPool, roboact2);
    }
    
    public Robot getRobot1() {
    		return this.robot1;
    }
    
    public Robot getRobot2() {
    		return this.robot2;
    }
    
}
