package automail;

import exceptions.ItemTooHeavyException;
import exceptions.ExcessiveDeliveryException;
import automail.Robot;
import automail.MailItem;

/**
 * The state when the robot is returning back to the mailroom.
 */
public class ReturningState extends RobotState {
    /**
     * Defines the action that the robot will carry out when returning.
     * @param r the robot in this state
     */
    public void action(Robot r) throws ExcessiveDeliveryException, ItemTooHeavyException {
        if (r.getCurrentFloor() == Building.MAILROOM_LOCATION) {
            while (!r.tube.isEmpty()) {
                MailItem mailItem = r.tube.pop();
                r.getMailPool().addToPool(mailItem);
                System.out.printf("T: %3d > old addToPool [%s]%n", Clock.Time(), mailItem.toString());
            }
            // r.changeState(WAITING);
        } else {
            r.moveTowards(Building.MAILROOM_LOCATION);
        }
    }

}