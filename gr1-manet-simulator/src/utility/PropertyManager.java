/**  
 * ----------------------------------------------------------
 * This software is for educational purposes only.
 * The base of this software was created by IntelliJ IDEA.
 * Additions to the base have been made by the Hood College
 * Computer Science Department, Graduate Group 1.
 * ----------------------------------------------------------
 *
 * History:
 * @version: $Revision$
 * @date: $Date$
 * @author: $Author$
 */

/**
 * 
 */
package utility;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

/**
 * @author mroberts
 * 
 */
public class PropertyManager {

	String propertyVal;

	public PropertyManager() {
		propertyVal = new String("Not found");
	}

	public void setPropertyVal() {
		Properties props = new Properties();

		try {
			props.load(new FileInputStream("./config.properties"));
			if (props.getProperty("logger_type") != null) {
				propertyVal = props.getProperty("logger_type");
			}

		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public String getPropertyVal(String prop) {
		Properties props = new Properties();

		try {
			props.load(new FileInputStream("./config.properties"));
			if (props.getProperty(prop) != null) {
				propertyVal = props.getProperty(prop);
			}

		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return propertyVal;
	}
}
