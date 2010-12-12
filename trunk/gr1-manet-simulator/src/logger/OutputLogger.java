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

import simulator.Data;
import simulator.Node;
import UI.Myform;
import UI.OutputLogProperties;
import UI.myobjects.GraphicalNode;

public class OutputLogger {

	private static OutputLogger outputLogger;

	public static OutputLogger get_instance() {
		return outputLogger;
	}
	public static void init() {
		outputLogger = new OutputLogger(true);
	}

	public static void init(Myform myForm) {
		outputLogger = new OutputLogger(myForm);
	}
	private StringBuffer broadcastInfo = new StringBuffer();

	private StringBuffer msgsReceived = new StringBuffer();

	public Myform myform;

	public boolean test = false;

	public OutputLogger(boolean test) {
		this.test = test;
	}

	private OutputLogger(Myform myform) {
		this.myform = myform;
	}

	private void addInfoToOutputLogs() {
		OutputLogProperties outputLogObj = myform.getOutputLogProperties();
		outputLogObj.statusText.setText(getBroadcastInfo());
		outputLogObj.receivedDataText.setText(getMsgsReceived());
	}

	public String getBroadcastInfo() {
		return broadcastInfo.toString();
	}

	public String getMsgsReceived() {
		return msgsReceived.toString();
	}

	public void resetBroadcastInfo() {
		this.broadcastInfo.delete(0, this.broadcastInfo.length());
	}

	public void resetMsgsReceived() {
		this.msgsReceived.delete(0, this.msgsReceived.length());
	}

	public void setBroadcastInfo(String info) {
		this.broadcastInfo.insert(0, info + "\n");
		addInfoToOutputLogs();
	}
	
	public void setMsgsReceived(String msgs) {
		this.msgsReceived.insert(0, msgs + "\n");
		addInfoToOutputLogs();
	}

	public void showBroadcastInfo(String msg){
		setBroadcastInfo(msg);
	}

	/**
	 * Gets the Graphical Node associated with node and adds the passed in
	 * Status to the Graphical Node.
	 * 
	 * @param node
	 * @param status
	 */
	public void showNodeStatus(Node node, String status) {
		if (!this.test) {
			GraphicalNode gNode = myform.getGnodebyNode(node);
			if (gNode != null) {
				setBroadcastInfo("Node (" + gNode.getName() + ") " + status);
				FileLogger.write("Node (" + gNode.getName() + ") is " + status, FileLogger.MSG_TYPE_INFO);
			}
		}
	}

	/**
	 * Gets the Graphical Node associated with node and adds sender and received
	 * data to the Graphical Node
	 * 
	 * @param receiver
	 * @param sender
	 * @param data
	 */
	public void showReceivedData(Node receiver, Node sender, Data data) {
		if (!this.test) {
			GraphicalNode gNode = myform.getGnodebyNode(receiver);
			if (gNode != null) {
				setMsgsReceived("Node (" + gNode.getName() + ") " + " received '" + data.getContent() + "'");
				FileLogger.write("Node (" + gNode.getName() + ") " + " received '" + data.getContent() + "'", FileLogger.MSG_TYPE_INFO);
			}
		}
	}

	public void startNodeAnimation(Node sender, int type) {
		if (!this.test) {
			GraphicalNode gNode = myform.getGnodebyNode(sender);
			if (gNode != null) {
				gNode.sending(type);
			}
		}
	}
}
