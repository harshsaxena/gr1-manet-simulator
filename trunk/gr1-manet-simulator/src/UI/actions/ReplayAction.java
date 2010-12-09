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

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;

import replay.ReplayFileParser;
import simulator.Map_Manager;
import UI.Myform;
import UI.myobjects.GraphicalNode;

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
		//FileLogger.write("ACTION=Replay_START", FileLogger.MSG_TYPE_REPLAY);
		
		// Clear nodes for replay
		clearNodesFromMap();
		
		// Parse log for node properties
		try {
			ReplayFileParser replayFileParser = new ReplayFileParser(myForm);
			replayFileParser.readLineByLine();
		} catch (FileNotFoundException e1) {
			e1.printStackTrace();
		}
		
		//FileLogger.write("ACTION=Replay_END", FileLogger.MSG_TYPE_REPLAY);
	}

	/**
	 * Clears nodes from the map.
	 */
	private void clearNodesFromMap() {
		Boolean copied1stNode = false;
		for(Component comp: myForm.getMyMap().getComponents()){
			if(myForm.getGraphicalNodes().size() > 0){
				if (comp.getClass().isInstance(myForm.getGraphicalNodes().get(0))) {
					GraphicalNode gNode = (GraphicalNode) comp;
					if(copied1stNode == false){
						myForm.setGNodeGhost(gNode);
						myForm.getGNodeGhost().setVisible(false);
						copied1stNode = true;
					}
					//FileLogger.write("\tClearNode=" + gNode.getName(), FileLogger.MSG_TYPE_REPLAY);
					myForm.getMyMap().remove(gNode);
					myForm.getGraphicalNodes().remove(gNode);
					myForm.getNodePropertiesPanel().clearNodeProperties();
					Map_Manager.get_instance().getNode_list().remove(gNode.getNode());
					myForm.setSelectedGNode(null);
				}
			}
		}
	}

	public void setMyForm(Myform myForm) {
		this.myForm = myForm;
	}

	public Myform getMyForm() {
		return myForm;
	}
	
}
