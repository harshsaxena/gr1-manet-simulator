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

public class SendMsgAction implements ActionListener {
	private Myform myForm;

	public SendMsgAction(Myform myForm) {
		this.myForm = myForm;
	}

	public void actionPerformed(ActionEvent e) {
		FileLogger.write("ACTION=SendMsgs_START", FileLogger.MSG_TYPE_REPLAY);

		// get From node
		GraphicalNode fromGNode = myForm.getGNode(myForm.getNodePropertiesPanel().sendFromText.getText().trim().toLowerCase());
		String procotolSelection = myForm.getNodePropertiesPanel().protocolComboBox.getSelectedItem().toString();
		NodeProperties np = new NodeProperties();
		if(procotolSelection.equals(np.getKEY_DSDV()))
		{
			Map_Manager.get_instance().setMode(Protocol.DSDV);
		}else{
			Map_Manager.get_instance().setMode(Protocol.AODV);
		}
		
		ReplayLogger.logSendNodeMsg(fromGNode, 0, fromGNode.getName());

		// get To nodes
		List<GraphicalNode> destList = new ArrayList<GraphicalNode>();
		StringTokenizer tok = new StringTokenizer(myForm.getNodePropertiesPanel().sendToText.getText().trim().toLowerCase()," ,");
		while (tok.hasMoreTokens()) {
			GraphicalNode g = myForm.getGNode(tok.nextToken());
			if (g != null)
				ReplayLogger.logSendNodeMsg(g, 1, g.getName());
				destList.add(g);
		}
		
		FileLogger.write("\tMsg=" + myForm.getNodePropertiesPanel().msgText.getText(), FileLogger.MSG_TYPE_REPLAY);
		FileLogger.write("\tProtocol=" + procotolSelection, FileLogger.MSG_TYPE_REPLAY);

		// send to each destination
		for (GraphicalNode dest : destList) {
			if (fromGNode != null && dest != null) {
				new SendDataThread(fromGNode.getNode(), dest.getNode(), new Data(myForm.getNodePropertiesPanel().msgText.getText()));
			} else {
				JOptionPane.showMessageDialog(myForm, "Destination node not found!");
			}
		}
		
		FileLogger.write("ACTION=SendMsgs_END", FileLogger.MSG_TYPE_REPLAY);
	}
}
