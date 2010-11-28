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
import UI.Myform;
import UI.actions.threads.SendDataThread;
import UI.myobjects.GraphicalNode;

public class NodePropSendtoBtnAction implements ActionListener {
	private Myform myForm;

	public NodePropSendtoBtnAction(Myform myForm) {
		this.myForm = myForm;
	}

	public void actionPerformed(ActionEvent e) {
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

		// get source node
		GraphicalNode src = myForm.getGNode(myForm.getNodePanel().sendFromText.getText().trim().toLowerCase());
		
		GraphicalNode fromNode = myForm.getGNode(myForm.getNodePanel().sendFromText.getText().trim().toLowerCase());
		FileLogger.write("SendFromGNode_AlignX = " + fromNode.getAlignmentX(), FileLogger.MSG_TYPE_REPLAY);
		FileLogger.write("SendFromGNode_AlignY = " + fromNode.getAlignmentY(), FileLogger.MSG_TYPE_REPLAY);
		FileLogger.write("SendFromGNode_Name = " + fromNode.getName(), FileLogger.MSG_TYPE_REPLAY);
		FileLogger.write("SendFromGNode_X = " + fromNode.getX(), FileLogger.MSG_TYPE_REPLAY);
		FileLogger.write("SendFromGNode_Y = " + fromNode.getY(), FileLogger.MSG_TYPE_REPLAY);
		FileLogger.write("SendFromGNode_Color = " + fromNode.getColor().toString(), FileLogger.MSG_TYPE_REPLAY);
		
		FileLogger.write("SendFromNode_ActiveRouteTimeout = " + fromNode.getNode().getActiveRouteTimeout(), FileLogger.MSG_TYPE_REPLAY);
		FileLogger.write("SendFromNode_AllowedHelloLoss = " + fromNode.getNode().getAllowedHelloLoss(), FileLogger.MSG_TYPE_REPLAY);
		FileLogger.write("SendFromNode_DefaultRouteLifetime = " + fromNode.getNode().getDefaultRouteLifetime(), FileLogger.MSG_TYPE_REPLAY);
		FileLogger.write("SendFromNode_DeletePeriod = " + fromNode.getNode().getDeletePeriod(), FileLogger.MSG_TYPE_REPLAY);
		FileLogger.write("SendFromNode_HelloInterval = " + fromNode.getNode().getHelloInterval(), FileLogger.MSG_TYPE_REPLAY);
		FileLogger.write("SendFromNode_HelloTime = " + fromNode.getNode().getHelloTime(), FileLogger.MSG_TYPE_REPLAY);
		FileLogger.write("SendFromNode_LoopbackExpireTime = " + fromNode.getNode().getLoopbackExpireTime(), FileLogger.MSG_TYPE_REPLAY);
		FileLogger.write("SendFromNode_RouteTimeout = " + fromNode.getNode().getMyRouteTimeout(), FileLogger.MSG_TYPE_REPLAY);
		FileLogger.write("SendFromNode_NetDiameter = " + fromNode.getNode().getNetDiameter(), FileLogger.MSG_TYPE_REPLAY);
		FileLogger.write("SendFromNode_NetTraversalTime = " + fromNode.getNode().getNetTraversalTime(), FileLogger.MSG_TYPE_REPLAY);
		FileLogger.write("SendFromNode_NodeTraversalTime = " + fromNode.getNode().getNodeTraversalTime(), FileLogger.MSG_TYPE_REPLAY);
		FileLogger.write("SendFromNode_PathDiscoveryInterval = " + fromNode.getNode().getPathDiscoveryInterval(), FileLogger.MSG_TYPE_REPLAY);
		FileLogger.write("SendFromNode_PathDiscoveryTime = " + fromNode.getNode().getPathDiscoveryTime(), FileLogger.MSG_TYPE_REPLAY);
		FileLogger.write("SendFromNode_Power = " + fromNode.getNode().getPower(), FileLogger.MSG_TYPE_REPLAY);
		FileLogger.write("SendFromNode_RepAckRequired = " + fromNode.getNode().getRepAckRequired(), FileLogger.MSG_TYPE_REPLAY);
		FileLogger.write("SendFromNode_RepRetires = " + fromNode.getNode().getReqRetries(), FileLogger.MSG_TYPE_REPLAY);
		FileLogger.write("SendFromNode_RouteDeleteInterval = " + fromNode.getNode().getRouteDeleteInterval(), FileLogger.MSG_TYPE_REPLAY);
		FileLogger.write("SendFromNode_RouteExpireInterval = " + fromNode.getNode().getRouteExpireInterval(), FileLogger.MSG_TYPE_REPLAY);
		//FileLogger.write("SendFromNode_Power = " + fromNode.getNode().getRout_Arr(), FileLogger.MSG_TYPE_REPLAY);

		// get list of destinations
		List<GraphicalNode> destList = new ArrayList<GraphicalNode>();
		StringTokenizer tok = new StringTokenizer(myForm.getNodePanel().sendToText.getText().trim().toLowerCase(),
				" ,");
		while (tok.hasMoreTokens()) {
			GraphicalNode g = myForm.getGNode(tok.nextToken());
			if (g != null)
				destList.add(g);
		}

		// send to each destination
		for (GraphicalNode dest : destList) {
			if (src != null && dest != null) {
				new SendDataThread(src.getNode(), dest.getNode(), new Data(myForm.getNodePanel().msgText.getText()));
				FileLogger.write("SentToNode = " + dest.getName(), FileLogger.MSG_TYPE_REPLAY);
			} else {
				JOptionPane.showMessageDialog(myForm,
						"Destination node not found!");
			}
		}
	}
}
