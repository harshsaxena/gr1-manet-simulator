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

import java.awt.Dimension;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Scanner;

import javax.swing.JLayeredPane;

import logger.FileLogger;
import simulator.Map_Manager;
import simulator.noderelated.Coordinates;
import simulator.noderelated.IPAddress;
import UI.Myform;
import UI.myobjects.GraphicalNode;

public class ReplayFileParser {
	
	private String nodeName = "";
	private String ip = "";
	private String nodeXCoord = "";
	private String nodeYCoord = "";
	private String power = "";
	private String dropEventAction = "";
	private String dropEventLocX = "";
	private String dropEventLocY = "";
	private String dropEventSourceAction = "";
	private Myform myForm;
	
	
	
	public ReplayFileParser(Myform myform) {
		this.myForm = myform;
	}

	public String getNodeName() {
		return nodeName;
	}

	public void setNodeName(String nodeName) {
		this.nodeName = nodeName;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public String getNodeXCoord() {
		return nodeXCoord;
	}

	public void setNodeXCoord(String nodeXCoord) {
		this.nodeXCoord = nodeXCoord;
	}

	public String getNodeYCoord() {
		return nodeYCoord;
	}

	public void setNodeYCoord(String nodeYCoord) {
		this.nodeYCoord = nodeYCoord;
	}

	public String getPower() {
		return power;
	}

	public void setPower(String power) {
		this.power = power;
	}

	public String getDropEventAction() {
		return dropEventAction;
	}

	public void setDropEventAction(String dropEventAction) {
		this.dropEventAction = dropEventAction;
	}

	public String getDropEventLocX() {
		return dropEventLocX;
	}

	public void setDropEventLocX(String dropEventLocX) {
		this.dropEventLocX = dropEventLocX;
	}

	public String getDropEventLocY() {
		return dropEventLocY;
	}

	public void setDropEventLocY(String dropEventLocY) {
		this.dropEventLocY = dropEventLocY;
	}

	public String getDropEventSourceAction() {
		return dropEventSourceAction;
	}

	public void setDropEventSourceAction(String dropEventSourceAction) {
		this.dropEventSourceAction = dropEventSourceAction;
	}

	public Myform getMyForm() {
		return myForm;
	}

	public void setMyForm(Myform myForm) {
		this.myForm = myForm;
	}

	public void readLineByLine() throws FileNotFoundException{
		File logFile = new File(FileLogger.logFile);
		Scanner scanner = new Scanner(new FileReader(logFile));
		
		try {
			while(scanner.hasNextLine()){
				String next = scanner.nextLine();
				if(next.contains("ACTION=AddNode_START")){
					while(scanner.hasNextLine()){
						String next2 = scanner.nextLine();
						
							
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
							
							if(next2.contains("DropEvent_DropAction")){
								Scanner scanAddLine = new Scanner(next2);
								while(scanAddLine.hasNext()){
									scanAddLine.useDelimiter("=");
									if (!scanAddLine.next().equals("DropEvent_DropAction")) {
										dropEventAction = scanAddLine.next();
									}
								}
							}
							
							if(next2.contains("DropEvent_LocX")){
								Scanner scanAddLine = new Scanner(next2);
								while(scanAddLine.hasNext()){
									scanAddLine.useDelimiter("=");
									if (!scanAddLine.next().equals("DropEvent_LocX")) {
										dropEventLocX = scanAddLine.next();
									}
								}
							}
							
							if(next2.contains("DropEvent_LocY")){
								Scanner scanAddLine = new Scanner(next2);
								while(scanAddLine.hasNext()){
									scanAddLine.useDelimiter("=");
									if (!scanAddLine.next().equals("DropEvent_LocY")) {
										dropEventLocY = scanAddLine.next();
									}
								}
							}
							
							if(next2.contains("DropEvent_SourceAction")){
								Scanner scanAddLine = new Scanner(next2);
								while(scanAddLine.hasNext()){
									scanAddLine.useDelimiter("=");
									if (!scanAddLine.next().equals("DropEvent_SourceAction")) {
										dropEventSourceAction = scanAddLine.next();
									}
								}
							}
							
							if(next2.equals("ACTION=AddNode_END")){
								reAddNode();
							}
						}
						
					}
			}
		} finally
		{
			scanner.close();
		}
	}

	private void reAddNode() {
		
		GraphicalNode reAddGNode = new GraphicalNode(myForm.addNodeBtn.getIcon(), myForm, false);
		reAddGNode.setVisible(true);
		reAddGNode.setName(nodeName);
		IPAddress ipAddress = new IPAddress(ip);
		reAddGNode.getNode().setIP(ipAddress);
		reAddGNode.setLocation(Integer.parseInt(nodeXCoord), Integer.parseInt(nodeYCoord));
		Coordinates coords = new Coordinates(Integer.parseInt(nodeXCoord), Integer.parseInt(nodeYCoord));
		reAddGNode.getNode().setNode_coordinates(coords);
		reAddGNode.getNode().setPower(Integer.parseInt(power));
		
		myForm.getGraphicalNodes().add(reAddGNode);
		Map_Manager.get_instance().getNode_list().add(reAddGNode.getNode());
		
		JLayeredPane panel = (JLayeredPane) myForm.getMyMap();
		
		Dimension size = reAddGNode.getPreferredSize();
		
		if (!reAddGNode.isShouldRemoved()) {
			try {
				panel.add(reAddGNode);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		reAddGNode.setSelectGNode();
		reAddGNode.setBounds(reAddGNode.getX(), reAddGNode.getY(), size.width, size.height);
		reAddGNode.fillNodePanel();
		
		panel.invalidate();
		reAddGNode.myForm.refreshPowerShower();

	}

}
