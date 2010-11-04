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

package utility;

import java.io.IOException;
import java.util.Properties;

/**
 * Used to load the properties file and read properties from the file.
 */
public class PropertyManager {
	public static final String FILE_NAME = "configs.properties";
	private static Properties properties;

	static {
		properties = new Properties();
	}

	/**
	 * a public function to load a property from a properties file
	 * 
	 * @param propertyName
	 *            key name for that property
	 * @return a String value of that property
	 */
	public static int readProperty(String propertyName) {
		System.out.println(properties.getProperty(propertyName));
		return Integer.parseInt(properties.getProperty(propertyName));
	}

	public static String readProperty(String fileName, String propertyName) {
		Properties propertiesfile = new Properties();
		try {
			propertiesfile.load(PropertyManager.class.getResourceAsStream("/"
					+ fileName));
		} catch (IOException e) {
			e.printStackTrace();
		}
		return propertiesfile.getProperty(propertyName);

	}
}
