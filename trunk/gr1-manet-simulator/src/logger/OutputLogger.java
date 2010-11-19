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

public class OutputLogger {

	Myform myform;
	boolean test = false;
	private static OutputLogger outputLogger;

	private OutputLogger(Myform myform) {
		this.myform = myform;
	}

	public OutputLogger(boolean test) {
		this.test = test;
	}

	public static void init(Myform myForm) {
		outputLogger = new OutputLogger(myForm);
	}

	public static void init() {
		outputLogger = new OutputLogger(true);
	}

	public static OutputLogger get_instance() {
		return outputLogger;
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
				gNode.addStatus(status);
				FileLogger.write("Node " + gNode.getName() + " is "
						+ gNode.getStatus(), FileLogger.MSG_TYPE_INFO);
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
				gNode.addReceivedData(receiver + " received " + "'"
						+ data.getContent() + "'");
				FileLogger.write(receiver + " received " + "'"
						+ data.getContent() + "'", FileLogger.MSG_TYPE_INFO);
			}
		}
	}

	public void NodeSend(Node sender, int type) {
		if (!this.test) {
			GraphicalNode gNode = myform.getGnodebyNode(sender);
			if (gNode != null) {
				gNode.sending(type);
			}
		}
	}
}
