// Group 115

package automail;

import exceptions.ItemTooHeavyException;
import exceptions.ExcessiveDeliveryException;

/**
 * The state when the robot is waiting to be given mail items to be delivered.
 */
public class WaitingState extends RobotState {
    /**
     * Defines the action that the robot will carry out when waiting.
     * @param r the robot in this state
     */
    public void action(Robot r) throws ExcessiveDeliveryException, ItemTooHeavyException {
        r.fillStorageTube();
        
        if (!r.tube.isEmpty()) {
            r.setDeliveryCounter(0);
            r.behaviour.startDelivery();
            r.setRoute();
            r.changeState(new DeliveringState());
        }
    }

    @Override
    public String toString() {
        return "WAITING";
    }
}