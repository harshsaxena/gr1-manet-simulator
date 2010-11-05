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
 */
public class FileLogger {

	private static String logFile;
	private final static String logDirPath = "./logs/";
	private final static String fileName = "simlog_";
	private final static String ext = ".txt";
	private final static DateFormat df = new SimpleDateFormat(
			"yyyy.MM.dd  hh:mm:ss");
	private final static DateFormat shortDf = new SimpleDateFormat("yyyy.MM.dd");

	private FileLogger() {
	}

	private static void setFileNameAndPath() {
		Date currDate = new Date();
		String currDateString = FileLogger.shortDf.format(currDate);
		logFile = logDirPath + fileName + currDateString + ext;
	}

	/**
	 * Takes in a String message, updates the file name and path if needed and
	 * sends the message to the write method.
	 * 
	 * @param msg
	 */
	public static void write(String msg) {
		setFileNameAndPath();
		write(logFile, msg);
	}

	/**
	 * Takes in an Exception message, updates the file name and path if needed
	 * and sends the message to the write method.
	 * 
	 * @param e
	 */
	public static void write(Exception e) {
		setFileNameAndPath();
		write(logFile, stack2string(e));
	}

	/**
	 * Writes a log to the log file.
	 * 
	 * @param file
	 * @param msg
	 */
	public static void write(String file, String msg) {

		try {
			Date now = new Date();
			String currentTime = FileLogger.df.format(now);
			FileWriter aWriter = new FileWriter(file, true);
			aWriter.write(currentTime + " " + msg
					+ System.getProperty("line.separator"));
			System.out.println(currentTime + " " + msg);
			aWriter.flush();
			aWriter.close();
		} catch (Exception e) {
			System.out.println(stack2string(e));
		}
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

}
