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

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

@SuppressWarnings("serial")
public class OutputLogProperties extends JPanel implements ActionListener {

	public JTextArea dataText;
	public JTextArea receivedDataText;
	public JTextArea statusText;
	
	private Myform myForm;
	
	public OutputLogProperties(Myform myForm)
	{
		this.setMyForm(myForm);
		JLabel receivedDataLabel;
		JLabel statusLabel;
		
		//create main layout
		Box outputLogBox = Box.createHorizontalBox();
		myForm.add(outputLogBox);
		
		//create third box labels
		receivedDataLabel = new JLabel("Received Data: ");
		statusLabel = new JLabel("Status:");
		
		//create third box text fields
		receivedDataText = new JTextArea(5,20);
		receivedDataText.setEditable(false);
		JScrollPane receivedDataScrollPane= new JScrollPane(receivedDataText);
		
        statusText = new JTextArea(5,20);
        statusText.setEditable(false);
        JScrollPane statusScrollOane = new JScrollPane(statusText);
        
        //create third box layouts
        JPanel thirdBox = new JPanel();
		myForm.add(thirdBox);
        thirdBox.setBorder(
                BorderFactory.createCompoundBorder(
                        BorderFactory.createTitledBorder("Output Log"),
                        BorderFactory.createEmptyBorder(5,5,5,5)));
        
        Box v31 = Box.createVerticalBox();
        thirdBox.add(v31);
        
        Box v311 = Box.createVerticalBox();
        v311.add(receivedDataLabel);
        v311.add(Box.createVerticalGlue());
        
        Box h31 = Box.createHorizontalBox();
        h31.add(v311);
        h31.add(receivedDataScrollPane);
        
        v31.add(h31);
        v31.add(Box.createVerticalStrut(3));
        
        Box v321 = Box.createVerticalBox();
        v321.add(statusLabel);
        v321.add(Box.createVerticalGlue());
        
        Box h32 = Box.createHorizontalBox();
        h32.add(v321);
        h32.add(statusScrollOane);
        
        v31.add(h32);
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	public void setMyForm(Myform myForm) {
		this.myForm = myForm;
	}

	public Myform getMyForm() {
		return myForm;
	}
	
}
