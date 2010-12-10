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
import java.util.List;

import logger.FileLogger;
import UI.Myform;
import UI.myobjects.GraphicalNode;

/**
 * @author MSRoberts1
 * 
 */
public class DeleteAllNodesAction implements ActionListener {
	Myform myForm;

	public DeleteAllNodesAction(Myform myForm) {
		this.myForm = myForm;
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		FileLogger.write("ACTION=DeleteAllNodes_START", FileLogger.MSG_TYPE_REPLAY);
		
		List<GraphicalNode> gNodeList = myForm.getGraphicalNodes();
		for(int i = gNodeList.size()-1; i >=0; i--){
			GraphicalNode gNode = gNodeList.get(i);
			
			FileLogger.write("\tDeleteAllNodes_NodeName=" + gNode.getName(), FileLogger.MSG_TYPE_REPLAY);
			FileLogger.write("\tDeleteAllNodes_NodeIP=" + gNode.getNode().getIP(), FileLogger.MSG_TYPE_REPLAY);
			
			myForm.getMyMap().remove(gNode);
			myForm.getGraphicalNodes().remove(gNode);
		}
		myForm.getNodePropertiesPanel().clearNodeProperties();
		myForm.setSelectedGNode(null);
		
        FileLogger.write("ACTION=DeleteAllNodes_END", FileLogger.MSG_TYPE_REPLAY);
	}
}
