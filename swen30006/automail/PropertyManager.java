package automail;

import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

public class PropertyManager {

	Properties automailProperties;
	FileReader inStream = null;// = new Properties();
	
	/**
    *
    * Instantiate automailProperties
    */
	public PropertyManager(String filename) {
		automailProperties = new Properties();
		
		try {
			inStream = new FileReader(filename);
			automailProperties.load(inStream);
		} catch (Exception e) {
			e.printStackTrace();
		}
		finally {
			 if (inStream != null) {
	                try {
						inStream.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
	            }
		}
	}
	
	/**
    *
    * @return the property value based on a property key
    */
	public String getProperty(String propertyname) {
		return automailProperties.getProperty(propertyname);
	}
}
