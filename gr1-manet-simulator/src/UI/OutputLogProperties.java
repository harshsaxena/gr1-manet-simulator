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

package UI;

//import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Box;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

@SuppressWarnings("serial")
public class OutputLogProperties extends JPanel implements ActionListener {

	public JTextArea receivedDataText; // move edit
	public JTextArea statusText; // move edit

	// private Myform myForm;

	public OutputLogProperties() {
	}

	public JPanel getOutputLogProperties() {
		JPanel outputLogPanel = new JPanel();

		// Main Box
		Box mainOutputLogBox = Box.createHorizontalBox();
		outputLogPanel.add(mainOutputLogBox);

		// Action Listener
		// TODO: Create action listener to clear text
		// ActionListener okAction = new NodePropOKBtnAction(this.myForm);

		/* Message Data Received */
		receivedDataText = new JTextArea(5, 25);
		receivedDataText.setEditable(false);
		JScrollPane rdataSP = new JScrollPane(receivedDataText);

		Box msgReceivedBox = Box.createVerticalBox();
		msgReceivedBox.setAlignmentX(Box.LEFT_ALIGNMENT);
		msgReceivedBox.add(rdataSP);
		mainOutputLogBox.add(msgReceivedBox);

		mainOutputLogBox.add(Box.createHorizontalStrut(10));

		/* Routing Data */
		statusText = new JTextArea(5, 45);
		statusText.setEditable(false);
		JScrollPane statusSP = new JScrollPane(statusText);

		Box routingDataBox = Box.createVerticalBox();
		routingDataBox.add(statusSP);
		mainOutputLogBox.add(routingDataBox);
		mainOutputLogBox.setAlignmentX(Box.LEFT_ALIGNMENT);

		return outputLogPanel;

	}

	public void actionPerformed(ActionEvent e) {
		// TODO: Add action for clearing the output logs
	}

}
