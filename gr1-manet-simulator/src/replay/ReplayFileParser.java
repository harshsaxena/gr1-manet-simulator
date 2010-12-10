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
import java.awt.dnd.DropTarget;
import java.awt.dnd.DropTargetContext;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Scanner;

import javax.swing.JComponent;
import javax.swing.JLayeredPane;

import logger.FileLogger;
import simulator.Map_Manager;
import simulator.noderelated.Coordinates;
import simulator.noderelated.IPAddress;
import UI.Myform;
import UI.NodeProperties;
import UI.myobjects.GraphicalNode;
import UI.myobjects.NodeButton;
import UI.myobjects.draganddrop.MoveNodeTransferHandler;

public class ReplayFileParser implements Runnable {

	private String nodeName;
	private IPAddress ip;
	private int nodeXCoord;
	private int nodeYCoord;
	private int nodeXLoc;
	private int nodeYLoc;
	private int power;
	private Myform myForm;

	Thread t;

	public void readLineByLine() throws FileNotFoundException {

		t = new Thread(this);
		t.start();
	}

	@Override
	public void run() {
		File logFile = new File(FileLogger.logFile);
		Scanner scanner = null;

		try {
			scanner = new Scanner(new FileReader(logFile));
			while (scanner.hasNextLine()) {
				String nextLine = scanner.nextLine();

				if (nextLine.contains("AddNode")
						|| nextLine.contains("MoveNode")
						|| (nextLine.contains("UpdateNodeProps"))) {

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
					if (nextInnerLine.contains("NodeXLoc")) {
						nodeXLoc = Integer.parseInt(addScanner.next());
					}
					if (nextInnerLine.contains("NodeYLoc")) {
						nodeYLoc = Integer.parseInt(addScanner.next());
					}
					if (nextInnerLine.contains("Power")) {
						power = Integer.parseInt(addScanner.next());
					}

					if (nextLine.contains("AddNode_END")) {
						reAddNode();
					}

					if (nextLine.contains("MoveNode_END")) {
						reMoveNode();
					}

					if (nextLine.contains("UpdateNodeProps_END")) {
						reUpdateNode();
					}

				} else if (nextLine.contains("DeleteNode")) {
					Scanner addScanner = new Scanner(nextLine);
					addScanner.useDelimiter("=");
					String nextInnerLine = addScanner.next();

					if (nextInnerLine.contains("NodeName")) {
						nodeName = addScanner.next();
					}
					if (nextInnerLine.contains("NodeIP")) {
						ip = new IPAddress(addScanner.next());
					}

					if (nextLine.contains("DeleteNode_END")) {
						reDeleteNode();
					}
				}
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} finally {
			scanner.close();
		}
	}

	private void reDeleteNode() {

		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		GraphicalNode removeGNode = myForm.getGNode(nodeName);
		myForm.getMyMap().remove(removeGNode);
		myForm.getGraphicalNodes().remove(removeGNode);
		Map_Manager.get_instance().getNode_list().remove(removeGNode.getNode());
		myForm.setSelectedGNode(null);
		NodeProperties np = removeGNode.myForm.getNodePropertiesPanel();
		np.clearNodeProperties();

	}

	private void reUpdateNode() {

		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		GraphicalNode updateGNode = myForm.getGNode(nodeName);
		myForm.setSelectedGNode(updateGNode);
		updateGNode.setName(nodeName);
		updateGNode.getNode().setIP(ip);
		updateGNode.setLocation(nodeXLoc, nodeYLoc);
		Coordinates coords = new Coordinates(nodeXCoord, nodeYCoord);
		updateGNode.getNode().setNode_coordinates(coords);
		updateGNode.setScaledCoordinates(nodeXCoord, nodeYCoord);
		updateGNode.getNode().setPower(power);
		//updateGNode.fillNodePanel(updateGNode);

		NodeProperties np = updateGNode.myForm.getNodePropertiesPanel();
		np.nameText.setText(nodeName);
		np.ipText.setText(ip.toString());
		np.xCordText.setText(Integer.toString(nodeXCoord));
		np.yCordText.setText(Integer.toString(nodeYCoord));
		np.powerText.setText(Integer.toString(power));
		
		
		myForm.refreshPowerShower();
	}

	private void reMoveNode() {

		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		GraphicalNode moveGNode = myForm.getGNode(nodeName);
		moveGNode.setLocation(nodeXLoc, nodeYLoc);
		Coordinates coords = new Coordinates(nodeXCoord, nodeYCoord);
		moveGNode.getNode().setNode_coordinates(coords);
		moveGNode.setTransferHandler(new MoveNodeTransferHandler());
		myForm.setSelectedGNode(moveGNode);
		myForm.refreshPowerShower();
		moveGNode.fillNodePanel(moveGNode);
	}

	private void reAddNode() {

		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		JComponent component = myForm.addNodeBtn;
		NodeButton source = (NodeButton) component;
		GraphicalNode addGNode = new GraphicalNode(source.getIcon(), myForm, false);
		addGNode.setName(nodeName);
		addGNode.getNode().setIP(ip);
		addGNode.setLocation(nodeXLoc, nodeYLoc);
		Coordinates coords = new Coordinates(nodeXCoord, nodeYCoord);
		addGNode.getNode().setNode_coordinates(coords);
		addGNode.getNode().setPower(power);

		myForm.putGNode(addGNode);
		addGNode.setTransferHandler(new MoveNodeTransferHandler());

		myForm.getGraphicalNodes().add(addGNode);

		DropTarget dropTarget = addGNode.getDropTarget();
		DropTargetContext dtc = dropTarget.getDropTargetContext();
		dtc.dropComplete(true);

		JLayeredPane panel = (JLayeredPane) source.myForm.getMyMap();
		Dimension size = addGNode.getPreferredSize();

		if (!addGNode.isShouldRemoved()) {
			panel.add(addGNode);
		}

		addGNode.setSelectGNode();
		addGNode.setBounds(nodeXLoc, nodeYLoc, size.width, size.height);
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

	public void setNodeXLoc(int nodeXLoc) {
		this.nodeXLoc = nodeXLoc;
	}

	public int getNodeXLoc() {
		return nodeXLoc;
	}

	public void setNodeYLoc(int nodeYLoc) {
		this.nodeYLoc = nodeYLoc;
	}

	public int getNodeYLoc() {
		return nodeYLoc;
	}
}
