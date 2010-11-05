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

package logger;

import org.apache.log4j.ConsoleAppender;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.apache.log4j.SimpleLayout;

/**
 * The ConsoleLogger class is used to provide detailed logging about the state of
 * each node, the overall network environment, information about messages (i.e.
 * size) being transferred at each increment of execution...
 * 
 * As well as provide text file logging capability so users can retrieve the
 * results of their execution.
 * 
 * @author MSRoberts1
 * 
 */
public class ConsoleLogger {
	public static Logger logger = Logger.getLogger(Logger.class);

	static {
		ConsoleAppender appender = new ConsoleAppender(new SimpleLayout());
		logger.addAppender(appender);
		logger.setLevel(Level.INFO);
	}
	
	// PropertyConfigurator.configure("plainlog4jconfig.properties");
	// DOMConfigurator.configure("xmllog4jconfig.xml")
}
