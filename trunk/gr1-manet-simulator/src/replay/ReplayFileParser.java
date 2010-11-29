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

package replay;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Scanner;

import logger.FileLogger;

public class ReplayFileParser {
	
	public static void readLineByLine() throws FileNotFoundException{
		File logFile = new File(FileLogger.logFile);
		Scanner scanner = new Scanner(new FileReader(logFile));
		
		try {
			while(scanner.hasNextLine()){
				String next = scanner.nextLine();
				if(!next.contains("End_Node_Properties")){
					processLine(scanner.nextLine());
				}
			}
		} finally
		{
			scanner.close();
		}
	}

	private static void processLine(String nextLine) {
		Scanner scanner = new Scanner(nextLine);
		scanner.useDelimiter("=");
		if(scanner.hasNext()){
			String test = scanner.next();
			System.out.println(test);
		}
	}

}
