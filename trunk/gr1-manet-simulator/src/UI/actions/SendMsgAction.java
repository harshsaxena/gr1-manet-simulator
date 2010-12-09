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
		
		// get From node
		GraphicalNode fromGNode = myForm.getGNode(myForm.getNodePropertiesPanel().sendFromText.getText().trim().toLowerCase());
		
		// get To nodes
		List<GraphicalNode> destList = new ArrayList<GraphicalNode>();
		StringTokenizer tok = new StringTokenizer(myForm.getNodePropertiesPanel().sendToText.getText().trim().toLowerCase(), " ,");
		while (tok.hasMoreTokens()) {
			GraphicalNode g = myForm.getGNode(tok.nextToken());
			if (g != null){
				destList.add(g);
			}
		}
		
		// get message
		String message = myForm.getNodePropertiesPanel().msgText.getText();
		
		// get protocol
		String procotolSelection = myForm.getNodePropertiesPanel().protocolComboBox.getSelectedItem().toString();
		NodeProperties np = new NodeProperties();
		if (procotolSelection.equals(np.getKEY_DSDV())) {
			Map_Manager.get_instance().setMode(Protocol.DSDV);
		} else {
			Map_Manager.get_instance().setMode(Protocol.AODV);
		}
		
		Boolean errorFound = validateEntries(fromGNode, destList, message);

		if (errorFound == false) {
			// send to each destination
			for (GraphicalNode dest : destList) {
				new SendDataThread(fromGNode.getNode(), dest.getNode(), new Data(message));
			}
			
			// Log replay information
			FileLogger.write("ACTION=SendMsgs_START", FileLogger.MSG_TYPE_REPLAY);
			FileLogger.write("\tSendMsgs_SendFrom_Name=" + fromGNode.getName(), FileLogger.MSG_TYPE_REPLAY);
			for(GraphicalNode gNode : destList){
				FileLogger.write("\tSendMsgs_SendTo_Name=" + gNode.getName(), FileLogger.MSG_TYPE_REPLAY);
			}
			FileLogger.write("\tSendMsgs_Msg="+ myForm.getNodePropertiesPanel().msgText.getText(), FileLogger.MSG_TYPE_REPLAY);
			FileLogger.write("\tSendMsgs_Protocol=" + procotolSelection, FileLogger.MSG_TYPE_REPLAY);
			FileLogger.write("ACTION=SendMsgs_END", FileLogger.MSG_TYPE_REPLAY);
		}
	}

	private Boolean validateEntries(GraphicalNode fromGNode,
			List<GraphicalNode> destList, String message) {

		Boolean errorFound = false;
		
		if(fromGNode == null || fromGNode.getName().equals("")){
			JOptionPane.showMessageDialog(myForm, "Enter a 'Send from' node name", "Send Error", JOptionPane.ERROR_MESSAGE);
			errorFound = true;
		}
		
		if (destList == null || destList.size() == 0) {
			JOptionPane.showMessageDialog(myForm, "Enter one or more valid 'Send to' node names, ex: b c", "Send Error", JOptionPane.ERROR_MESSAGE);
			errorFound = true;
		}
		
		if(message.equals("")){
			JOptionPane.showMessageDialog(myForm, "Enter a 'Message'", "Send Error", JOptionPane.ERROR_MESSAGE);
			errorFound = true;
		}
		
		return errorFound;
	}

}
