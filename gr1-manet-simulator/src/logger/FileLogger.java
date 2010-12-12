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

import utility.PropertyManager;

/**
 * Writes log information or exceptions to a log file. Also writes log info to
 * the system console.
 */
public class FileLogger {

	private final static DateFormat df = new SimpleDateFormat("yyyy.MM.dd_hh.mm.ss");
	private final static String ext = ".txt";
	private final static String fileName = "simlog_";
	public final static String logDirPath = "./logs/";
	public static String logFile;
	public final static String MSG_TYPE_DEBUG = "DEBUG";
	public final static String MSG_TYPE_ERROR = "ERROR";
	public final static String MSG_TYPE_INFO = "INFO";
	public final static String MSG_TYPE_REPLAY = "REPLAY";
	private static boolean createLoggerFile = true;
	private static String loggerType;

	/**
	 * Writes a log to the log file.
	 * 
	 * @param file
	 * @param msg
	 */
	public static void commitMsg(String file, String msg, String msgeType) {

		try {
			FileWriter aWriter = new FileWriter(file, true);

			PropertyManager propMngr = new PropertyManager();
			loggerType = propMngr.getPropertyVal("logger_type");

			if (loggerType.equals(MSG_TYPE_DEBUG)) {
				aWriter.write(msg + System.getProperty("line.separator"));
				System.out.println(msgeType + ": " + msg);
			}

			if (loggerType.equals(MSG_TYPE_ERROR)
					&& loggerType.equals(msgeType)) {
				aWriter.write(msg + System.getProperty("line.separator"));
				System.out.println(msgeType + ": " + msg);
			}

			if (loggerType.equals(MSG_TYPE_INFO) && loggerType.equals(msgeType)) {
				aWriter.write(msg + System.getProperty("line.separator"));
				System.out.println(msgeType + ": " + msg);
			}

			if (loggerType.equals(MSG_TYPE_REPLAY)
					&& loggerType.equals(msgeType)) {
				aWriter.write(msg + System.getProperty("line.separator"));
				System.out.println(msgeType + ": " + msg);
				//OutputLogger.get_instance().showBroadcastInfo(msg);
			}

			aWriter.flush();
			aWriter.close();
		} catch (Exception e) {
			System.out.println(stack2string(e));
		}
	}

	private static void setFileNameAndPath() {
		if (createLoggerFile == true) {
			Date currDate = new Date();
			createLoggerFile = false;
			String currDateString = FileLogger.df.format(currDate);
			logFile = logDirPath + fileName + currDateString + ext;
		}
	}

	/**
	 * Returns the stack traces error message as a String.
	 * 
	 * @param e
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
	 */
	public static void write(String message, String messageType) {
		setFileNameAndPath();
		commitMsg(logFile, message, messageType);
	}

	private FileLogger() {
	}

}
