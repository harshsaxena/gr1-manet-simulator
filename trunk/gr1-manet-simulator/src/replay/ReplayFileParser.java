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
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Scanner;

import javax.swing.JComponent;
import javax.swing.JFileChooser;
import javax.swing.JLayeredPane;

import logger.FileLogger;
import simulator.Data;
import simulator.Map_Manager;
import simulator.Protocol;
import simulator.noderelated.Coordinates;
import simulator.noderelated.IPAddress;
import UI.Myform;
import UI.NodeProperties;
import UI.actions.threads.SendDataThread;
import UI.myobjects.GraphicalNode;
import UI.myobjects.NodeButton;
import UI.myobjects.draganddrop.MoveNodeTransferHandler;

public class ReplayFileParser implements Runnable {

	private IPAddress ip;
	private String message;
	private Myform myForm;
	private String nodeName;
	private List<String> nodesToDelete = new ArrayList<String>();
	private int nodeXCoord;
	private int nodeXLoc;
	private int nodeYCoord;
	private int nodeYLoc;
	private int power;
	private String protocol;
	private String sendFromName;
	private List<String> sendToNames = new ArrayList<String>();

	Thread t;

	public ReplayFileParser(Myform myform) {
		this.myForm = myform;
	}

	/** Disables buttons that shouldn't be accessible during a replay. **/
	private void disableButtons() {
		myForm.replayBtn.setEnabled(false);
		myForm.delGNodeBtn.setEnabled(false);
		myForm.delAllGNodesBtn.setEnabled(false);
		myForm.getNodePropertiesPanel().replayBtn.setEnabled(false);
	}

	/** Enables buttons that shouldn't be accessible during a replay. **/
	private void enableButtons() {
		myForm.replayBtn.setEnabled(true);
		myForm.delGNodeBtn.setEnabled(true);
		myForm.delAllGNodesBtn.setEnabled(true);
		myForm.getNodePropertiesPanel().replayBtn.setEnabled(true);
	}

	public IPAddress getIp() {
		return ip;
	}

	public String getMessage() {
		return message;
	}

	public Myform getMyForm() {
		return myForm;
	}

	public String getNodeName() {
		return nodeName;
	}

	public List<String> getNodesToDelete() {
		return nodesToDelete;
	}

	public int getNodeXCoord() {
		return nodeXCoord;
	}

	public int getNodeXLoc() {
		return nodeXLoc;
	}

	public int getNodeYCoord() {
		return nodeYCoord;
	}

	public int getNodeYLoc() {
		return nodeYLoc;
	}

	public int getPower() {
		return power;
	}

	public String getProtocol() {
		return protocol;
	}

	public String getSendFromName() {
		return sendFromName;
	}

	public List<String> getSendToNames() {
		return sendToNames;
	}

	private void reAddNode() {

		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		JComponent component = myForm.addNodeBtn;
		NodeButton source = (NodeButton) component;
		GraphicalNode addGNode = new GraphicalNode(source.getIcon(), myForm,
				false);
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
		addGNode.myForm.getNodePropertiesPanel().setSearchPropertiesEnabled(
				true);
		addGNode.myForm.getNodePropertiesPanel().setPropertiesEnabled(true);
		if (myForm.getGraphicalNodes().size() > 1) {
			addGNode.myForm.getNodePropertiesPanel().setMsgPropertiesEnabled(
					true);
		}

	}

	public void readLineByLine() throws FileNotFoundException {
		t = new Thread(this);
		t.start();
	}

	private void reDeleteAllNodes() {
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		// for (int i = nodesToDelete.size() - 1; i >= 0; i--) {
		// String nodeName = nodesToDelete.get(i);
		// GraphicalNode gNode = myForm.getGNode(nodeName);
		// myForm.getMyMap().remove(gNode);
		// myForm.getGraphicalNodes().remove(gNode);
		// Map_Manager.get_instance().getNode_list().remove(gNode.getNode());
		// }
		// myForm.getNodePropertiesPanel().clearNodeProperties();
		// myForm.setSelectedGNode(null);

		myForm.clearNodesFromMap();
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

	/**
	 * We are getting duplicate sendTo node names after a replay with 2 or more
	 * sendTo node names, I'm not sure why just yet, but regardless their should
	 * only be unique node names entered on the Send To entry field, so the
	 * below method removes any duplicates found and returns a new list of
	 * strings.
	 * 
	 * @param sendToList
	 * @return
	 */
	public List<String> removeDuplicates(List<String> sendToList) {

		HashSet<String> h = new HashSet<String>(sendToList);
		sendToList.clear();
		sendToList.addAll(h);

		return sendToList;
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
		// moveGNode.setTransferHandler(new MoveNodeTransferHandler());
		myForm.setSelectedGNode(moveGNode);
		myForm.refreshPowerShower();
		moveGNode.fillNodePanel(moveGNode);
	}

	private void reSendMsg() {
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		if (protocol.equals(Protocol.DSDV.toString())) {
			Map_Manager.get_instance().setMode(Protocol.DSDV);
			myForm.getNodePropertiesPanel().getProtocolComboBox()
					.setSelectedIndex(0);
		} else {
			Map_Manager.get_instance().setMode(Protocol.AODV);
			myForm.getNodePropertiesPanel().getProtocolComboBox()
					.setSelectedIndex(1);
		}

		myForm.getNodePropertiesPanel().getSendFromText().setText(sendFromName);

		StringBuilder sb = new StringBuilder();
		List<String> sendToList = removeDuplicates(sendToNames);
		for (String sendToName : sendToList) {
			sb.append(sendToName + " ");
		}
		myForm.getNodePropertiesPanel().getSendToText().setText(
				sb.toString().trim());
		myForm.getNodePropertiesPanel().getMsgText().setText(message);

		for (String sendToName : sendToList) {
			GraphicalNode dest = myForm.getGNode(sendToName);
			GraphicalNode fromGNode = myForm.getGNode(sendFromName);
			new SendDataThread(fromGNode.getNode(), dest.getNode(), new Data(
					message));
		}
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
		// updateGNode.fillNodePanel(updateGNode);

		NodeProperties np = updateGNode.myForm.getNodePropertiesPanel();
		np.nameText.setText(nodeName);
		np.ipText.setText(ip.toString());
		np.xCordText.setText(Integer.toString(nodeXCoord));
		np.yCordText.setText(Integer.toString(nodeYCoord));
		// np.powerText.setText(Integer.toString(power));
		np.pwrSpinner.setValue(power);

		myForm.refreshPowerShower();
	}

	@Override
	public void run() {
		File logFile;
		// open file chooser dialog to allow user to select log file
		JFileChooser fc = new JFileChooser();
		fc.setCurrentDirectory(new File(FileLogger.logDirPath));
		int returnVal = fc.showOpenDialog(myForm);
		if (returnVal == JFileChooser.APPROVE_OPTION)
			logFile = fc.getSelectedFile();
		else
			// logFile = new File(FileLogger.logFile);
			return;
		
		// disable buttons that shouldn't be accessible during replay
		disableButtons();
		
		// clear the map
		myForm.clearNodesFromMap();
		myForm.getNodePropertiesPanel().clearMsgProperties();

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

				} else if (nextLine.contains("DeleteAllNodes")) {
					// Scanner addScanner = new Scanner(nextLine);
					// addScanner.useDelimiter("=");
					// String nextInnerLine = addScanner.next();

					// if (nextInnerLine.contains("DeleteAllNodes_NodeName")) {
					// nodesToDelete.add(addScanner.next());
					// }

					if (nextLine.contains("DeleteAllNodes_END")) {
						reDeleteAllNodes();
					}

				} else if (nextLine.contains("SendMsgs")) {

					Scanner addScanner = new Scanner(nextLine);
					addScanner.useDelimiter("=");
					String nextInnerLine = addScanner.next();

					if (nextInnerLine.contains("SendMsgs_SendFrom_Name")) {
						sendFromName = addScanner.next();
					}

					if (nextInnerLine.contains("SendMsgs_SendTo_Name")) {
						String sendToName = addScanner.next();
						sendToNames.add(sendToName);
					}

					if (nextInnerLine.contains("SendMsgs_Msg")) {
						message = addScanner.next();
					}

					if (nextInnerLine.contains("SendMsgs_Protocol")) {
						protocol = addScanner.next();
					}

					if (nextLine.contains("SendMsgs_END")) {
						reSendMsg();
					}
				}
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} finally {
			scanner.close();
			// reenable buttons
			enableButtons();
		}
	}

	public void setIp(IPAddress ip) {
		this.ip = ip;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public void setMyForm(Myform myForm) {
		this.myForm = myForm;
	}

	public void setNodeName(String nodeName) {
		this.nodeName = nodeName;
	}

	public void setNodesToDelete(List<String> nodesToDelete) {
		this.nodesToDelete = nodesToDelete;
	}

	public void setNodeXCoord(int nodeXCoord) {
		this.nodeXCoord = nodeXCoord;
	}

	public void setNodeXLoc(int nodeXLoc) {
		this.nodeXLoc = nodeXLoc;
	}

	public void setNodeYCoord(int nodeYCoord) {
		this.nodeYCoord = nodeYCoord;
	}

	public void setNodeYLoc(int nodeYLoc) {
		this.nodeYLoc = nodeYLoc;
	}

	public void setPower(int power) {
		this.power = power;
	}

	public void setProtocol(String protocol) {
		this.protocol = protocol;
	}

	public void setSendFromName(String sendFromName) {
		this.sendFromName = sendFromName;
	}

	public void setSendToNames(List<String> sendToNames) {
		this.sendToNames = sendToNames;
	}
}
