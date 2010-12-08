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

	private String nodeName;
	private IPAddress ip;
	private int nodeXCoord;
	private int nodeYCoord;
	private int power;
	private Myform myForm;

	public void readLineByLine() throws FileNotFoundException {
		File logFile = new File(FileLogger.logFile);
		Scanner scanner = new Scanner(new FileReader(logFile));

		try {
			while (scanner.hasNextLine()) {
				String nextLine = scanner.nextLine();

				if (nextLine.contains("AddNode")
						|| nextLine.contains("MoveNode")) {

					Scanner addScanner = new Scanner(nextLine);
					addScanner.useDelimiter("=");
					String nextInnerLine = addScanner.next();

					if (nextInnerLine.contains("NodeName")) {
						nodeName = addScanner.next();
					}
					if (nextInnerLine.contains("NodeIP")) {
						ip = new IPAddress(addScanner.next());
					}
					if (nextInnerLine.contains("NodeXCoord")) {
						nodeXCoord = Integer.parseInt(addScanner.next());
					}
					if (nextInnerLine.contains("NodeYCoord")) {
						nodeYCoord = Integer.parseInt(addScanner.next());
					}
					if (nextInnerLine.contains("Power")) {
						power = Integer.parseInt(addScanner.next());
					}

					if (nextLine.contains("AddNode_END")) {
						reAddNode();
					}

					if (nextLine.contains("MoveNode_END")) {
						// reMoveNode();
					}

				} else if (true) {
					// Stub
				}
			}
		} finally {
			scanner.close();
		}
	}

	private void reMoveNode() {
		try {
			GraphicalNode moveGNode = myForm.getGNode(nodeName);
			// TODO: Fix null error, can't find node
			moveGNode.setName(nodeName);
			moveGNode.getNode().setIP(ip);
			moveGNode.setLocation(nodeXCoord, nodeYCoord);
			Coordinates coords = new Coordinates(nodeXCoord, nodeYCoord);
			moveGNode.getNode().setNode_coordinates(coords);
			moveGNode.getNode().setPower(power);
			myForm.setSelectedGNode(moveGNode);
		} catch (Exception e) {
			// TODO: handle exception
			String test = "";
		}

	}

	private void reAddNode() {

		// TODO: Add thread to slow processing down

		GraphicalNode addGNode = new GraphicalNode(myForm.addNodeBtn.getIcon(),
				myForm, false);
		addGNode.setName(nodeName);
		addGNode.getNode().setIP(ip);
		addGNode.setLocation(nodeXCoord, nodeYCoord);
		Coordinates coords = new Coordinates(nodeXCoord, nodeYCoord);
		addGNode.getNode().setNode_coordinates(coords);
		addGNode.getNode().setPower(power);

		myForm.getGraphicalNodes().add(addGNode);
		Map_Manager.get_instance().getNode_list().add(addGNode.getNode());

		JLayeredPane panel = (JLayeredPane) myForm.getMyMap();
		Dimension size = addGNode.getPreferredSize();

		if (!addGNode.isShouldRemoved()) {
			panel.add(addGNode);
		}

		addGNode.setSelectGNode();
		addGNode.setBounds(addGNode.getX(), addGNode.getY(), size.width,
				size.height);
		addGNode.fillNodePanel();

		panel.invalidate();
		addGNode.myForm.refreshPowerShower();

	}

	public ReplayFileParser(Myform myform) {
		this.myForm = myform;
	}

	public String getNodeName() {
		return nodeName;
	}

	public void setNodeName(String nodeName) {
		this.nodeName = nodeName;
	}

	public Myform getMyForm() {
		return myForm;
	}

	public void setMyForm(Myform myForm) {
		this.myForm = myForm;
	}

	public IPAddress getIp() {
		return ip;
	}

	public void setIp(IPAddress ip) {
		this.ip = ip;
	}

	public int getNodeXCoord() {
		return nodeXCoord;
	}

	public void setNodeXCoord(int nodeXCoord) {
		this.nodeXCoord = nodeXCoord;
	}

	public int getNodeYCoord() {
		return nodeYCoord;
	}

	public void setNodeYCoord(int nodeYCoord) {
		this.nodeYCoord = nodeYCoord;
	}

	public int getPower() {
		return power;
	}

	public void setPower(int power) {
		this.power = power;
	}
}
