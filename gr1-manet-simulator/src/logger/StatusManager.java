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
import UI.myobjects.GraphicalNode;

public class StatusManager {

	Myform myform;
	boolean test = false;
	private static StatusManager status_manager;

	private StatusManager(Myform myform) {
		this.myform = myform;
	}

	public StatusManager(boolean test) {
		this.test = test;
	}

	public static void init(Myform myForm) {
		status_manager = new StatusManager(myForm);
	}

	public static void init() {
		status_manager = new StatusManager(true);
	}

	public static StatusManager get_instance() {
		return status_manager;
	}

	/**
	 * Creates a Graphical Node and add the passed in Status to the Graphical
	 * Node.
	 * 
	 * @param node
	 * @param status
	 */
	public void showNodeStatus(Node node, String status) {
		if (!this.test) {
			GraphicalNode gNode = myform.getGnodebyNode(node);
			gNode.addStatus(status);
			MyLogger.logger.info("Node " + gNode.getName() + " is " + gNode.getStatus());
		}
	}

	/**
	 * Creates a Graphical Node and adds sender and received data to the
	 * Graphical Node
	 * 
	 * @param receiver
	 * @param sender
	 * @param data
	 */
	public void showReceivedData(Node receiver, Node sender, Data data) {
		if (!this.test) {
			GraphicalNode gNode = myform.getGnodebyNode(receiver);
			gNode.addReceivedData("From: " + sender + "\n" + data.getContent());
			MyLogger.logger.info("Node " + gNode.getName() + " received "
					+ data.getContent() + " from " + sender);
		}
	}

	public void NodeSend(Node sender, int type) {
		if (!this.test) {
			GraphicalNode gNode = myform.getGnodebyNode(sender);
			gNode.sending(type);
		}
	}
}
