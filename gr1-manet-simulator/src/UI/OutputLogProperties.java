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

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Box;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

@SuppressWarnings("serial")
public class OutputLogProperties extends JPanel implements ActionListener {

	public JLabel receivedDataLbl;  // move edit
	public JLabel statusLbl;  // move edit
	
	public JTextArea receivedDataText;  // move edit
	public JTextArea statusText;  // move edit
	
	//private Myform myForm;
	
	public OutputLogProperties(){}
	
	public JPanel getOutputLogProperties()
	{
		JPanel testPanel = new JPanel();
		
		// Main Box
		Box mainOutputLogBox = Box.createHorizontalBox();
		testPanel.add(mainOutputLogBox);
		
        // Action Listener
		// TODO: Create action listener to clear text
        // ActionListener okAction = new NodePropOKBtnAction(this.myForm);
		
        /* To Be Moved */
        receivedDataLbl = new JLabel("RData: ");
        receivedDataText = new JTextArea(5,20);
        receivedDataText.setEditable(false);
        JScrollPane rdataSP= new JScrollPane(receivedDataText);
        
        statusLbl = new JLabel("Status: ");
        statusText = new JTextArea(5,20);
        statusText.setEditable(false);
        JScrollPane statusSP = new JScrollPane(statusText);
        
        mainOutputLogBox.add(Box.createHorizontalStrut(10));
        
        Box receivedDataLabelAndTextBox = Box.createHorizontalBox();
        Box receivedLabelGlueBox = Box.createVerticalBox();
        receivedLabelGlueBox.add(receivedDataLbl);
        receivedLabelGlueBox.add(Box.createVerticalGlue());
        receivedDataLabelAndTextBox.add(receivedLabelGlueBox);
        receivedDataLabelAndTextBox.add(rdataSP);
        mainOutputLogBox.add(receivedDataLabelAndTextBox);
        
        mainOutputLogBox.add(Box.createHorizontalStrut(10));
        
        Box statusDataLabelAndTextBox = Box.createHorizontalBox();
        Box statusLabelGlueBox = Box.createVerticalBox();
        statusLabelGlueBox.add(statusLbl);
        statusLabelGlueBox.add(Box.createVerticalGlue());
        statusDataLabelAndTextBox.add(statusLabelGlueBox);
        statusDataLabelAndTextBox.add(statusSP);
        mainOutputLogBox.add(statusDataLabelAndTextBox);
        
        mainOutputLogBox.add(Box.createHorizontalStrut(10));
        
        return testPanel;
        
	}
	
	public void actionPerformed(ActionEvent e) {
		// TODO: Add action for clearing the output logs
    }
	
}
