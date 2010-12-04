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

package UI.actions;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

import javax.swing.JOptionPane;

import logger.FileLogger;

import replay.ReplayLogger;
import simulator.Data;
import simulator.Map_Manager;
import simulator.Protocol;
import UI.Myform;
import UI.NodeProperties;
import UI.actions.threads.SendDataThread;
import UI.myobjects.GraphicalNode;

public class SendAction implements ActionListener {
	private Myform myForm;

	public SendAction(Myform myForm) {
		this.myForm = myForm;
	}

	public void actionPerformed(ActionEvent e) {
		FileLogger.write("*****Start_Node_Properties*****", FileLogger.MSG_TYPE_REPLAY);
		
		// if (myForm.getSelectedGNode()!=null){
		// GraphicalNode dest = myForm.getGNode(
		// myForm.getNodePanel().sendToText.getText().trim().toLowerCase());
		// if (dest!=null){
		// new
		// SendDataThread(myForm.getSelectedGNode().getNode(),dest.getNode(),
		// new Data(myForm.getNodePanel().msgText.getText()));
		// }else{
		// JOptionPane.showMessageDialog(myForm,"Destination node not found!");
		// }
		// }

		// get From node
		GraphicalNode fromGNode = myForm.getGNode(myForm.getNodePanel().sendFromText.getText().trim().toLowerCase());
		String procotolSelection = myForm.getNodePanel().protocolComboBox.getSelectedItem().toString();
		NodeProperties np = new NodeProperties();
		if(procotolSelection.equals(np.getKEY_DSDV()))
		{
			Map_Manager.get_instance().setMode(Protocol.DSDV);
		}else{
			Map_Manager.get_instance().setMode(Protocol.AODV);
		}
		
		ReplayLogger.logNodePropertiesForReplay(fromGNode, 0, fromGNode.getName());

		// get To nodes
		List<GraphicalNode> destList = new ArrayList<GraphicalNode>();
		StringTokenizer tok = new StringTokenizer(myForm.getNodePanel().sendToText.getText().trim().toLowerCase()," ,");
		while (tok.hasMoreTokens()) {
			GraphicalNode g = myForm.getGNode(tok.nextToken());
			if (g != null)
				ReplayLogger.logNodePropertiesForReplay(g, 1, g.getName());
				destList.add(g);
		}

		// send to each destination
		for (GraphicalNode dest : destList) {
			if (fromGNode != null && dest != null) {
				new SendDataThread(fromGNode.getNode(), dest.getNode(), new Data(myForm.getNodePanel().msgText.getText()));
			} else {
				JOptionPane.showMessageDialog(myForm, "Destination node not found!");
			}
		}
		
		FileLogger.write("*****End_Node_Properties*****", FileLogger.MSG_TYPE_REPLAY);
	}
}
