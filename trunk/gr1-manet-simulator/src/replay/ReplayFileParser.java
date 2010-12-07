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

import javax.swing.Icon;
import javax.swing.ImageIcon;

import UI.myobjects.NodeButton;

import logger.FileLogger;

public class ReplayFileParser {
	
	public static void readLineByLine() throws FileNotFoundException{
		File logFile = new File(FileLogger.logFile);
		Scanner scanner = new Scanner(new FileReader(logFile));
		
		String nodeName = "";
		String ip = "";
		String nodeXCoord = "";
		String nodeYCoord = "";
		String power;
		
		try {
			while(scanner.hasNextLine()){
				String next = scanner.nextLine();
				if(next.contains("ACTION=AddNode_START")){
					while(scanner.hasNextLine()){
						String next2 = scanner.nextLine();
						if(!next2.contains("ACTION=AddNode_STOP")){
							
							if(next2.contains("NodeName")){
								Scanner scanAddLine = new Scanner(next2);
								while(scanAddLine.hasNext()){
									scanAddLine.useDelimiter("=");
									if (!scanAddLine.next().equals("NodeName")) {
										nodeName = scanAddLine.next();
									}
								}
							}

							if(next2.contains("NodeIP")){
								Scanner scanAddLine = new Scanner(next2);
								while(scanAddLine.hasNext()){
									scanAddLine.useDelimiter("=");
									if (!scanAddLine.next().equals("NodeIP")) {
										ip = scanAddLine.next();
									}
								}
							}
						
							if(next2.contains("NodeXCoord")){
								Scanner scanAddLine = new Scanner(next2);
								while(scanAddLine.hasNext()){
									scanAddLine.useDelimiter("=");
									if (!scanAddLine.next().equals("NodeXCoord")) {
										nodeXCoord = scanAddLine.next();
									}
								}
							}
						
							if(next2.contains("NodeYCoord")){
								Scanner scanAddLine = new Scanner(next2);
								while(scanAddLine.hasNext()){
									scanAddLine.useDelimiter("=");
									if (!scanAddLine.next().equals("NodeYCoord")) {
										nodeYCoord = scanAddLine.next();
									}
								}
							}
							
							if(next2.contains("Power")){
								Scanner scanAddLine = new Scanner(next2);
								while(scanAddLine.hasNext()){
									scanAddLine.useDelimiter("=");
									if (!scanAddLine.next().equals("Power")) {
										power = scanAddLine.next();
									}
								}
							}
						}else
						{
							processAddNode();
						}
						
						
					}
				}
			}
		} finally
		{
			scanner.close();
		}
	}

	private static void processAddNode() {
		NodeButton nodeButton = new NodeButton(new ImageIcon("images/SendingNode0.png"));
		
	}

}
