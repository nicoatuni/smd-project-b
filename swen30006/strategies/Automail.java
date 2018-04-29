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

		String mailPosition1, mailPosition2;
		
		if (robotType1.equals("weak") || robotType2.equals("weak")) {
			if (robotType1.equals("weak")) {
				mailPosition1 = "upper";
				mailPosition2 = "lower";
			} else {
				mailPosition1 = "lower";
				mailPosition2 = "upper";
			}
		} else {
			// no weak robot (either big-big, strong-strong, or strong-big), and
			// further check if one of them is a big one
			if (robotType1.equals("big") || robotType2.equals("big")) {
				if (robotType1.equals("big")) {
					mailPosition1 = "upper";
					mailPosition2 = "lower";
				} else {
					mailPosition1 = "lower";
					mailPosition2 = "upper";
				}
			} else {
				// no weak nor big robots, only strong ones
				mailPosition1 = "upper";
				mailPosition2 = "lower";
			}
		}
    	
    	//// Swap the next two lines for the two below those
    	IRobotBehaviour robotBehaviour1 = new MyRobotBehaviour(mailPosition1);
    	IRobotBehaviour robotBehaviour2 = new MyRobotBehaviour(mailPosition2);
    	    	
    	/** Initialize robot */
    	robot1 = new Robot(robotBehaviour1, delivery, robotType1, this, mailPosition1);
		robot2 = new Robot(robotBehaviour2, delivery, robotType2, this, mailPosition2);
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
	 * @param mailPosition upper or lower robot
	 */
	public void fillStorageTube(StorageTube tube, String mailPosition) {
		mailPool.fillStorageTube(tube, mailPosition);
	}
    
}
