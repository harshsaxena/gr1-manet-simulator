/**
 * 
 * 
 * File name: $HeadURL$  
 * Revision: $Revision$  
 * Last modified: $Date$  
 * Last modified by: $Author$  
 * $Id$
 */

package logger;

import org.apache.log4j.Logger;
import org.apache.log4j.ConsoleAppender;
import org.apache.log4j.SimpleLayout;
import org.apache.log4j.Level;

/**
 * The MyLogger class is used to provide detailed logging about the state of each node, the overall
 * network environment, information about messages (i.e. size) being transferred at each
 * increment of execution.
 * 
 * As well as provide text file logging capability so users can retrieve the results
 * of their execution.
 * 
 * @author MSRoberts1
 *
 */
public class MyLogger {
     public static Logger logger = Logger.getLogger(Logger.class);
    static {
    ConsoleAppender appender = new ConsoleAppender(new SimpleLayout());
    logger.addAppender(appender);
    logger.setLevel(Level.INFO);}
    //PropertyConfigurator.configure("plainlog4jconfig.properties");
    //DOMConfigurator.configure("xmllog4jconfig.xml")
}
