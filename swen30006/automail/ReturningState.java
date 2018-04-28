package automail;

import exceptions.ItemTooHeavyException;
import exceptions.ExcessiveDeliveryException;

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
                r.addToPool(mailItem);
                System.out.printf("T: %3d > old addToPool [%s]%n", Clock.Time(), mailItem.toString());
            }

            r.changeState(new WaitingState());
            r.step();

        } else {
            r.moveTowards(Building.MAILROOM_LOCATION);
        }
    }

    @Override
    public String toString() {
        return "RETURNING";
    }
}