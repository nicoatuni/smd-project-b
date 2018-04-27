package automail;

import exceptions.ItemTooHeavyException;
import exceptions.ExcessiveDeliveryException;
import automail.Robot;

/**
 * The state when the robot is waiting for a mail item to be delivered.
 */
public class WaitingState extends RobotState {
    /**
     * Defines the action that the robot will carry out when waiting.
     * @param r the robot that is in this state
     */
    public void action(Robot r) throws ExcessiveDeliveryException, ItemTooHeavyException {
        r.getMailPool().fillStorageTube(r.tube, r.getStrong());
        if (!r.tube.isEmpty()) {
            r.setDeliveryCounter(0);
            r.behaviour.startDelivery();
            r.setRoute();
            // r.changeState();
        }
    }
}