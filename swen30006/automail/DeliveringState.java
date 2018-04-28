package automail;

import exceptions.ItemTooHeavyException;
import exceptions.ExcessiveDeliveryException;

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