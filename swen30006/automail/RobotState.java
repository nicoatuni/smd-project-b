package automail;

import exceptions.ItemTooHeavyException;
import exceptions.ExcessiveDeliveryException;
import automail.Robot;

/**
 * The different states a Robot could be in.
 */
public abstract class RobotState {
    /** 
     * Defines the actions that the robot will carry out depending on its state.
     */
    public abstract void action(Robot r) throws ExcessiveDeliveryException, ItemTooHeavyException;

    @Override
    public abstract String toString();

    @Override
    public boolean equals(Object obj) {
        return this.toString() == obj.toString();
    }
}