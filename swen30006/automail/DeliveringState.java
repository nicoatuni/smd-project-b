package automail;

import exceptions.ItemTooHeavyException;
import exceptions.ExcessiveDeliveryException;

/**
 * The state when the robot is delivering mail items.
 */
public class DeliveringState extends RobotState {
    /**
     * Defines the action that the robot will carry out when delivering.
     * @param r the robot in this state
     */
    public void action(Robot r) throws ExcessiveDeliveryException, ItemTooHeavyException {
        boolean wantToReturn = r.behaviour.returnToMailRoom(r.tube);

        if (r.getCurrentFloor() == r.getDestinationFloor()) {
            
            r.getAutoMail().setItem(r.getDeliveryItem());
            r.setDeliveryCounter(r.getDeliveryCounter() + 1);

            if (r.getDeliveryCounter() > r.tube.getMaxCapacity()) {
                throw new ExcessiveDeliveryException();
            }
            
            if (wantToReturn || r.tube.isEmpty()) {
                r.changeState(new ReturningState());
            } else {
                r.setRoute();
                r.changeState(new DeliveringState());
            }

        } else {
            r.moveTowards(r.getDestinationFloor());
            
        }
    }

    @Override
    public String toString() {
        return "DELIVERING";
    }
}