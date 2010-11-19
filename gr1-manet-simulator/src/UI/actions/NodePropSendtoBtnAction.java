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
		GraphicalNode src = myForm.getGNode(myForm.getNodePanel().sendFromText
				.getText().trim().toLowerCase());

		// get list of destinations
		List<GraphicalNode> destList = new ArrayList<GraphicalNode>();
		StringTokenizer tok = new StringTokenizer(
				myForm.getNodePanel().sendToText.getText().trim().toLowerCase(),
				" ,");
		while (tok.hasMoreTokens()) {
			GraphicalNode g = myForm.getGNode(tok.nextToken());
			if (g != null)
				destList.add(g);
		}

		// send to each destination
		for (GraphicalNode dest : destList) {
			if (src != null && dest != null) {
				new SendDataThread(src.getNode(), dest.getNode(), new Data(
						myForm.getNodePanel().msgText.getText()));
			} else {
				JOptionPane.showMessageDialog(myForm,
						"Destination node not found!");
			}
		}
	}
}
