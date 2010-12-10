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

/**
 * 
 */
package UI.actions;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;

import replay.ReplayFileParser;
import UI.Myform;

/**
 * @author mroberts
 * 
 */
public class ReplayAction implements ActionListener {
	private Myform myForm;

	public ReplayAction(Myform myForm) {
		this.setMyForm(myForm);
	}

	public void actionPerformed(ActionEvent e) {
		// FileLogger.write("ACTION=Replay_START", FileLogger.MSG_TYPE_REPLAY);

		// NOTE ReplayFileParser will call clearNodesFromMap
		// Clear nodes for replay
		// clearNodesFromMap();

		// Parse log for node properties
		try {
			ReplayFileParser replayFileParser = new ReplayFileParser(myForm);
			replayFileParser.readLineByLine();
		} catch (FileNotFoundException e1) {
			e1.printStackTrace();
		}

		// FileLogger.write("ACTION=Replay_END", FileLogger.MSG_TYPE_REPLAY);
	}

	// NOTE this function has moved to UI.Myform
	// /**
	// * Clears nodes from the map.
	// */
	// public void clearNodesFromMap() {
	// // List<GraphicalNode> gNodeList = myForm.getGraphicalNodes();
	// // for (int i = gNodeList.size() - 1; i >= 0; i--) {
	// // GraphicalNode gNode = gNodeList.get(i);
	// // myForm.getMyMap().remove(gNode);
	// // myForm.getGraphicalNodes().remove(gNode);
	// // }
	//
	// List<GraphicalNode> gNodeList = myForm.getGraphicalNodes();
	// List<Node> nodeList = Map_Manager.get_instance().getNode_list();
	// for (GraphicalNode node : gNodeList) {
	// myForm.getMyMap().remove(node);
	// }
	// gNodeList.clear();
	// nodeList.clear();
	//
	// myForm.getNodePropertiesPanel().clearNodeProperties();
	// myForm.setSelectedGNode(null);
	// }

	public void setMyForm(Myform myForm) {
		this.myForm = myForm;
	}

	public Myform getMyForm() {
		return myForm;
	}

}
