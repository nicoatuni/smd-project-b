package automail;

import exceptions.ItemTooHeavyException;
import exceptions.ExcessiveDeliveryException;
import automail.Robot;

public class DeliveringState extends RobotState {
    public void action(Robot r) throws ExcessiveDeliveryException, ItemTooHeavyException {
        boolean wantToReturn = r.behaviour.returnToMailRoom(r.tube);
        if (r.getCurrentFloor() == r.getDestinationFloor()) {
            r.delivery.deliver(r.getDeliveryItem());
            r.setDeliveryCounter(r.getDeliveryCounter() + 1);
            if (r.getDeliveryCounter() > 4) {
                throw new ExcessiveDeliveryException();
            }
            
            if (wantToReturn || r.tube.isEmpty()) {
                // r.changeState(RETURNING);
            } else {
                r.setRoute();
                // r.changeState(DELIVERING);
            }
        } else {
            r.moveTowards(r.getDestinationFloor());
        }
    }
}