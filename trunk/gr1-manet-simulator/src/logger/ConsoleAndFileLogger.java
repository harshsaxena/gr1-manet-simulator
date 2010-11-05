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

import java.io.FileWriter;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Writes log information or exceptions to a log file.
 * Also writes log information to the system console.
 */
public class ConsoleAndFileLogger {

	private final static DateFormat df = new SimpleDateFormat("yyyy.MM.dd  hh:mm:ss");
	private final static String ext = ".txt";
	private final static String fileName = "simlog_";
	private final static String logDirPath = "./logs/";
	private static String logFile;
	public final static String MSG_TYPE_DEBUG = "DEBUG";
	public final static String MSG_TYPE_ERROR = "ERROR";
	public final static String MSG_TYPE_INFO = "INFO";
	private final static DateFormat shortDf = new SimpleDateFormat("yyyy.MM.dd");

	/**
	 * Writes a log to the log file.
	 * 
	 * @param file
	 * @param msg
	 */
	public static void commitMsg(String file, String msg, String msgeType) {

		try {
			Date now = new Date();
			String currentTime = ConsoleAndFileLogger.df.format(now);
			FileWriter aWriter = new FileWriter(file, true);
			aWriter.write(msgeType + " - " + currentTime + " " + msg
					+ System.getProperty("line.separator"));
			System.out.println(msgeType + " - " + currentTime + " " + msg);
			aWriter.flush();
			aWriter.close();
		} catch (Exception e) {
			System.out.println(stack2string(e));
		}
	}

	private static void setFileNameAndPath() {
		Date currDate = new Date();
		String currDateString = ConsoleAndFileLogger.shortDf.format(currDate);
		logFile = logDirPath + fileName + currDateString + ext;
	}

	/**
	 * Returns the stack traces error message as a String.
	 * 
	 * @param e
	 * @return
	 */
	public static String stack2string(Exception e) {
		try {
			StringWriter sw = new StringWriter();
			PrintWriter pw = new PrintWriter(sw);
			e.printStackTrace(pw);
			return "-----\r\n " + sw.toString() + "-----\r\n";
		} catch (Exception e2) {
			return "bad stack2string";
		}
	}

	/**
	 * Takes in an Exception message, updates the file name and path if needed
	 * and sends the error message to the commit message method.
	 * 
	 * @param e
	 */
	public static void write(Exception e) {
		setFileNameAndPath();
		commitMsg(logFile, stack2string(e), MSG_TYPE_ERROR);
	}

	/**
	 * Takes in a String message, updates the file name and path if needed and
	 * sends the message to the commit message method.
	 * 
	 * Message Types: INFO, DEBUG, ERROR
	 * 
	 * @param msg
	 */
	public static void write(String message, String messageType) {
		setFileNameAndPath();
		commitMsg(logFile, message, messageType);
	}

	private ConsoleAndFileLogger() {
	}

}
