package logger;

import org.apache.log4j.Logger;
import org.apache.log4j.ConsoleAppender;
import org.apache.log4j.SimpleLayout;
import org.apache.log4j.Level;

/**
 * Created by IntelliJ IDEA
 * Date: August 7, 2006
 * 
 * Modified by Matt Roberts, Hood College, Group 1
 * Date: November 3, 2010
 **/
public class MyLogger {
     public static Logger logger = Logger.getLogger(Logger.class);
    static {
    ConsoleAppender appender = new ConsoleAppender(new SimpleLayout());
    logger.addAppender(appender);
    logger.setLevel(Level.INFO);}
    //PropertyConfigurator.configure("plainlog4jconfig.properties");
    //DOMConfigurator.configure("xmllog4jconfig.xml");
}
