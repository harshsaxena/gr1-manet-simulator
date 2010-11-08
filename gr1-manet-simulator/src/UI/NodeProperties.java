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

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JColorChooser;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import UI.actions.NodePropOKBtnAction;
import UI.actions.NodePropResetBtnAction;
import UI.actions.NodePropSendtoBtnAction;
import UI.actions.SearchGNodeAction;

@SuppressWarnings("serial")
public class NodeProperties extends JPanel implements ActionListener {
    
	private Myform myForm;
	
	public JButton colorBtn;
	public JButton okBtn;
	public JButton resetBtn;
	public JButton sendBtn;
	
	public JComboBox modeComboBox;
	public JComboBox protocolComboBox;
	
	public JLabel nameLabel;
	public JLabel ipLabel;
	public JLabel xCordLabel;
	public JLabel yCordLabel;
	public JLabel powerLabel;
	public JLabel colorLabel;
	public JLabel msgLabel;
	public JLabel sendToLabel;
	public JLabel receivedDataLbl;  // move edit
	public JLabel statusLbl;  // move edit
	public JLabel searchNodeLabel;
	
	public JTextField nameText;
	public JTextField ipText;
	public JTextField xCordText;
	public JTextField yCordText;
	public JTextField powerText;
	public JTextField msgText;
	public JTextField sendToText;
	public JTextField searchNodeText;
	
	public JTextArea receivedDataText;  // move edit
	public JTextArea statusText;  // move edit
    
    public NodeProperties(Myform myForm) {
        this.myForm = myForm;
        
        String[] modeStrings = {"Mode - Node Management", "Mode - Simulation Mode"};
        String[] protocolStrings = {"Protocol - AODV", "Protocol - DSDV"};
        
        // Main Box
        Box mainVerticalBox = Box.createVerticalBox();
        this.add(mainVerticalBox);
        
        // Action Listener 
        ActionListener okAction = new NodePropOKBtnAction(this.myForm);
        
        /* Find a Node Panel */
        JPanel findNodePanel = new JPanel();
        mainVerticalBox.add(findNodePanel);
        findNodePanel.setBorder(
                BorderFactory.createCompoundBorder(
                        BorderFactory.createTitledBorder("Find a Node"),
                        BorderFactory.createEmptyBorder(0,2,4,2)));
        
        searchNodeLabel = new JLabel("Search: ");
        searchNodeText = new JTextField(10);
        searchNodeText.addActionListener(new SearchGNodeAction(myForm));
        
        Box searchLabelAndTextBox = Box.createHorizontalBox();
        searchLabelAndTextBox.add(searchNodeLabel);
        searchLabelAndTextBox.add(searchNodeText);
        findNodePanel.add(searchLabelAndTextBox);
        
        /* Node Data Panel */
        JPanel nodeDataPanel = new JPanel();
        mainVerticalBox.add(nodeDataPanel);
        nodeDataPanel.setBorder(
                BorderFactory.createCompoundBorder(
                        BorderFactory.createTitledBorder("Node Data"),
                        BorderFactory.createEmptyBorder(0,2,4,2)));
        
        nameLabel = new JLabel("Name: ");
        nameText = new JTextField(8);
        nameText.addActionListener(okAction);
        
        ipLabel = new JLabel("IP: ");
        ipText = new JTextField(8);
        ipText.addActionListener(okAction);
        
        xCordLabel = new JLabel("X: ");
        xCordText = new JTextField(1);
        xCordText.addKeyListener(myForm.numKeyListener);
        
        yCordLabel = new JLabel(" Y: ");
        yCordText = new JTextField(1);
        yCordText.addKeyListener(myForm.numKeyListener);
        
        powerLabel = new JLabel("Power: ");
        powerText = new JTextField(3);
        powerText.addKeyListener(myForm.numKeyListener);
        powerText.addActionListener(okAction);
        
        colorLabel = new JLabel("Color: ");
        colorBtn = new JButton();
        colorBtn.setMaximumSize(new Dimension(50,50));
        colorBtn.addActionListener(this);
        
        msgLabel = new JLabel("Message: ");
        msgText = new JTextField(10);
        msgText.addActionListener(okAction);
        
        sendToLabel = new JLabel("Send to: ");
        sendToText = new JTextField(10);
        sendToText.addActionListener(okAction);

        okBtn = new JButton("OK");
        okBtn.addActionListener(okAction );
        
        resetBtn = new JButton("Reset");
        resetBtn.addActionListener(new NodePropResetBtnAction(this.myForm));
        
        // Build Node Data Panel
        Box nodeDataBox = Box.createVerticalBox();
        nodeDataPanel.add(nodeDataBox);
        
        Box nameLabelAndTextBox = Box.createHorizontalBox();
        nameLabelAndTextBox.add(nameLabel);
        nameLabelAndTextBox.add(nameText);
        nodeDataBox.add(nameLabelAndTextBox);
        
        nodeDataBox.add(Box.createVerticalStrut(5));
        
        Box ipLabelAndTextBox = Box.createHorizontalBox();
        ipLabelAndTextBox.add(ipLabel);
        ipLabelAndTextBox.add(ipText);
        nodeDataBox.add(ipLabelAndTextBox);
        
        nodeDataBox.add(Box.createVerticalStrut(5));
        
        Box xyCordsLabelAndTextBox = Box.createHorizontalBox();
        xyCordsLabelAndTextBox.add(xCordLabel);
        xyCordsLabelAndTextBox.add(xCordText);
        xyCordsLabelAndTextBox.add(yCordLabel);
        xyCordsLabelAndTextBox.add(yCordText);
        nodeDataBox.add(xyCordsLabelAndTextBox);
        
        nodeDataBox.add(Box.createVerticalStrut(5));
        
        Box pwrColorLabelAndTextBox = Box.createHorizontalBox();
        pwrColorLabelAndTextBox.add(powerLabel);
        pwrColorLabelAndTextBox.add(powerText);
        pwrColorLabelAndTextBox.add(colorLabel);
        pwrColorLabelAndTextBox.add(colorBtn);
        nodeDataBox.add(pwrColorLabelAndTextBox);
        
        nodeDataBox.add(Box.createVerticalStrut(5));
        
        nodeDataBox.add(new JSeparator(SwingConstants.HORIZONTAL));
        
        nodeDataBox.add(Box.createVerticalStrut(5));
        
        Box msgLabelAndTextBox = Box.createHorizontalBox();
        msgLabelAndTextBox.add(msgLabel);
        msgLabelAndTextBox.add(msgText);
        nodeDataBox.add(msgLabelAndTextBox);
        
        nodeDataBox.add(Box.createVerticalStrut(5));
        
        Box sendToLabelAndTextBox = Box.createHorizontalBox();
        sendToLabelAndTextBox.add(sendToLabel);
        sendToLabelAndTextBox.add(sendToText);
        nodeDataBox.add(sendToLabelAndTextBox);
        
        nodeDataBox.add(Box.createVerticalStrut(5));
        
        Box resetAndOkBtnsBox = Box.createHorizontalBox();
        resetAndOkBtnsBox.add(Box.createHorizontalGlue());
        resetAndOkBtnsBox.add(resetBtn);
        resetAndOkBtnsBox.add(Box.createHorizontalStrut(5));
        resetAndOkBtnsBox.add(okBtn);
        nodeDataBox.add(resetAndOkBtnsBox);
        
        nodeDataBox.add(Box.createVerticalStrut(5));
        
        /* Configuration and Miscellaneous Panel */
        JPanel configMiscPanel = new JPanel();
        mainVerticalBox.add(configMiscPanel);
        configMiscPanel.setBorder(
              BorderFactory.createCompoundBorder(
              BorderFactory.createTitledBorder("Configuration and Misc"),
              BorderFactory.createEmptyBorder(0,2,4,2)));
        
        // Mode ComboBox
        modeComboBox = new JComboBox(modeStrings);
        modeComboBox.setSelectedIndex(0);
        // TODO: Add action listener
        //modeComboBox.addActionListener(this);
        
        // Protocol ComboBox
        protocolComboBox = new JComboBox(protocolStrings);
        protocolComboBox.setSelectedIndex(0);
        // TODO: Add action listener
        //protocolComboBox.addActionListener(this);
        
        // Build Configuration and Miscellaneous Panel
        Box configMiscBox = Box.createVerticalBox();
        configMiscPanel.add(configMiscBox);
        configMiscBox.add(modeComboBox);
        configMiscBox.add(Box.createVerticalStrut(5));
        configMiscBox.add(protocolComboBox);
        
        configMiscBox.add(Box.createVerticalStrut(5));
        
        /* To Be Moved */
        receivedDataLbl = new JLabel("RData: ");
        receivedDataText = new JTextArea(5,20);
        receivedDataText.setEditable(false);
        JScrollPane rdataSP= new JScrollPane(receivedDataText);
        
        statusLbl = new JLabel("Status: ");
        statusText = new JTextArea(5,20);
        statusText.setEditable(false);
        JScrollPane statusSP = new JScrollPane(statusText);
        
        // Send Button, to be the play button?
        sendBtn = new JButton("Send");
        sendBtn.addActionListener(new NodePropSendtoBtnAction(this.myForm));
        
        mainVerticalBox.add(Box.createVerticalStrut(10));
        
        Box sendBtnBox = Box.createHorizontalBox();
        sendBtnBox.add(Box.createHorizontalGlue());
        sendBtnBox.add(sendBtn);
        mainVerticalBox.add(sendBtnBox);
        
        mainVerticalBox.add(Box.createVerticalStrut(10));
        
        Box receivedDataLabelAndTextBox = Box.createHorizontalBox();
        Box receivedLabelGlueBox = Box.createVerticalBox();
        receivedLabelGlueBox.add(receivedDataLbl);
        receivedLabelGlueBox.add(Box.createVerticalGlue());
        receivedDataLabelAndTextBox.add(receivedLabelGlueBox);
        receivedDataLabelAndTextBox.add(rdataSP);
        mainVerticalBox.add(receivedDataLabelAndTextBox);
        
        mainVerticalBox.add(Box.createVerticalStrut(5));
        
        Box statusDataLabelAndTextBox = Box.createHorizontalBox();
        Box statusLabelGlueBox = Box.createVerticalBox();
        statusLabelGlueBox.add(statusLbl);
        statusLabelGlueBox.add(Box.createVerticalGlue());
        statusDataLabelAndTextBox.add(statusLabelGlueBox);
        statusDataLabelAndTextBox.add(statusSP);
        mainVerticalBox.add(statusDataLabelAndTextBox);

    }

	public void actionPerformed(ActionEvent e) {
        Color newColor = JColorChooser.showDialog(
                NodeProperties.this,
                "Choose Background Color",
                colorBtn.getBackground());
        if (newColor != null) {
            colorBtn.setBackground(newColor);
        }
    }

    public void resetNodePropertiest() {
        this.nameText.setText("");

        this.ipText.setText("");
        this.nameText.setText("");
        this.nameText.setEnabled(true);
        this.xCordText.setText("");
        this.yCordText.setText("");
        this.powerText.setText("");
        this.colorBtn.setBackground(this.getBackground());
    }

    public void resetNodePanel() {
        resetNodePropertiest();
        this.sendToText.setText("");
        this.msgText.setText("");
        this.receivedDataText.setText("");
        this.statusText.setText("");
    }
}