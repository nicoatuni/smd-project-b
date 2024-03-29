// Group 115

package automail;

import exceptions.ExcessiveDeliveryException;
import exceptions.ItemTooHeavyException;
import exceptions.MailAlreadyDeliveredException;
import strategies.Automail;

import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Properties;

/**
 * This class simulates the behaviour of AutoMail
 */
public class Simulation {

    /** Constant for the mail generator */
    private static int MAIL_TO_CREATE = 180;
    
    private static ArrayList<MailItem> MAIL_DELIVERED;
    private static double total_score = 0;
    private static double penalty = 1.1;
    
    public static void main(String[] args) { //throws IOException {

    	/* Set all properties from the .properties file */
    	PropertyManager propertymanager = new PropertyManager("automail.properties");
    	int seed = Integer.parseInt(propertymanager.getProperty("Seed"));
    	Building.setFloor(Integer.parseInt(propertymanager.getProperty("Number_of_Floors")));
    	Clock.setDeliveryTime(Integer.parseInt(propertymanager.getProperty("Last_Delivery_Time")));
    	penalty = Double.parseDouble(propertymanager.getProperty("Delivery_Penalty")); // Penalty for longer delivery times
    	MAIL_TO_CREATE = Integer.parseInt(propertymanager.getProperty("Mail_to_Create"));
    		
        MAIL_DELIVERED = new ArrayList<MailItem>();
                
        /** Used to see whether a seed is initialized or not */
        HashMap<Boolean, Integer> seedMap = new HashMap<>();
        
        /** Read the first argument and save it as a seed if it exists */
        seedMap.put(true, seed);

        Automail automail = new Automail(propertymanager.getProperty("Robot_Type_1"), propertymanager.getProperty("Robot_Type_2"),
        		new ReportDelivery());
        MailGenerator generator = new MailGenerator(MAIL_TO_CREATE, automail.getMailPool(), seedMap);
        
        /** Initiate all the mail */
        generator.generateAllMail();
        PriorityMailItem priority;
        while(MAIL_DELIVERED.size() != generator.MAIL_TO_CREATE) {
        	
            priority = generator.step();
            if (priority != null) {
            	automail.getRobot1().behaviour.priorityArrival(priority.getPriorityLevel(), priority.weight);
            	automail.getRobot2().behaviour.priorityArrival(priority.getPriorityLevel(), priority.weight);
            }
            try {
				automail.getRobot1().step();
				automail.getRobot2().step();
			} catch (ExcessiveDeliveryException|ItemTooHeavyException e) {
				e.printStackTrace();
				System.out.println("Simulation unable to complete.");
				System.exit(0);
			}
            Clock.Tick();
        }
        printResults();
    }
    
    static class ReportDelivery implements IMailDelivery {
    	
    	/** Confirm the delivery and calculate the total score */
    	public void deliver(MailItem deliveryItem){
    		if(!MAIL_DELIVERED.contains(deliveryItem)){
                System.out.printf("T: %3d > Delivered     [%s]%n", Clock.Time(), deliveryItem.toString());
    			MAIL_DELIVERED.add(deliveryItem);
    			// Calculate delivery score
    			total_score += calculateDeliveryScore(deliveryItem);
    		}
    		else{
    			try {
    				throw new MailAlreadyDeliveredException();
    			} catch (MailAlreadyDeliveredException e) {
    				e.printStackTrace();
    			}
    		}
    	}

    }
    
    private static double calculateDeliveryScore(MailItem deliveryItem) {
    	double priority_weight = 0;
    	
    	// Take (delivery time - arrivalTime)**penalty * (1+sqrt(priority_weight))
    	if(deliveryItem instanceof PriorityMailItem){
   			priority_weight = ((PriorityMailItem) deliveryItem).getPriorityLevel();
    	}
    	return Math.pow(Clock.Time() - deliveryItem.getArrivalTime(),penalty)*(1+Math.sqrt(priority_weight));
   	}

    public static void printResults(){
        System.out.println("T: "+Clock.Time()+" | Simulation complete!");
        System.out.println("Final Delivery time: "+Clock.Time());
        System.out.printf("Final Score: %.2f%n", total_score);
    }
}
