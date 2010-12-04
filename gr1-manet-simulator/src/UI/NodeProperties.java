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
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JColorChooser;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import simulator.Node;
import UI.actions.ReplayAction;
import UI.actions.SaveNodeAction;
import UI.actions.SearchNodeAction;
import UI.actions.SendMsgAction;
import UI.myobjects.GraphicalNode;

@SuppressWarnings("serial")
public class NodeProperties extends JPanel implements ActionListener {

	private Myform myForm;

	public JButton colorBtn;
	public JButton saveBtn;
	public JButton clearBtn;
	public JButton sendBtn;
	public JButton replayBtn;

	public JComboBox modeComboBox;
	public JComboBox protocolComboBox;
	public JComboBox sendToComboBox;

	public BufferedImage wirelessTowerImg = new BufferedImage(200, 92,
			BufferedImage.TYPE_INT_RGB);

	public JComboBox getSendToComboBox() {
		return sendToComboBox;
	}

	public void setSendToComboBox(JComboBox sendToComboBox) {
		this.sendToComboBox = sendToComboBox;
	}

	public JLabel nameLabel;
	public JLabel ipLabel;
	public JLabel xCordLabel;
	public JLabel yCordLabel;
	public JLabel powerLabel;
	public JLabel colorLabel;
	public JLabel msgLabel;
	public JLabel protocolLabel;
	public JLabel sendToLabel;
	public JLabel sendFromLabel;
	public JLabel receivedDataLbl; // move edit
	public JLabel statusLbl; // move edit
	public JLabel searchNodeLabel;

	public JTextField nameText;
	public JTextField ipText;
	public JTextField xCordText;
	public JTextField yCordText;
	public JTextField powerText;
	public JTextField msgText;
	public JTextField sendToText;
	public JTextField sendFromText;
	public JTextField searchNodeText;

	public JLabel picLabel;

	public List<GraphicalNode> graphicalNodeList;
	public String[] availableNodes = new String[100];
	
	public final String KEY_AODV = "AODV";
	public final String KEY_DSDV = "DSDV";

	public NodeProperties(Myform myForm) {
		this.myForm = myForm;

		String[] protocolStrings = {KEY_AODV, KEY_DSDV};
		
		try {
			wirelessTowerImg = ImageIO.read(new File(
					"images/wirelessTowerTitle.png"));
		} catch (Exception e) {
			// TODO: handle exception
		}

		// Main Box
		Box mainVerticalBox = Box.createVerticalBox();
		mainVerticalBox.add(Box.createGlue());
		this.add(mainVerticalBox);

		// Action Listener
		ActionListener saveAction = new SaveNodeAction(this.myForm);
		ActionListener sendAction = new SendMsgAction(this.myForm);
		ActionListener searchAction = new SearchNodeAction(this.myForm);
		ActionListener replayAction = new ReplayAction(this.myForm);

		/* Find a Node Panel */
		JPanel findNodePanel = new JPanel();
		mainVerticalBox.add(findNodePanel);
		findNodePanel.setBorder(BorderFactory.createCompoundBorder(
				BorderFactory.createTitledBorder("Find a Node"), BorderFactory
						.createEmptyBorder(0, 0, 0, 0)));

		searchNodeLabel = new JLabel("Search: ");
		searchNodeText = new JTextField(10);
		searchNodeText.setToolTipText("Enter a node name to search");
		searchNodeText.addActionListener(searchAction);

		Box searchLabelAndTextBox = Box.createHorizontalBox();
		searchLabelAndTextBox.add(searchNodeLabel);
		searchLabelAndTextBox.add(searchNodeText);
		findNodePanel.add(searchLabelAndTextBox);

		/* Node Data Panel */
		JPanel nodeDataPanel = new JPanel();
		mainVerticalBox.add(nodeDataPanel);
		nodeDataPanel.setBorder(BorderFactory.createCompoundBorder(
				BorderFactory.createTitledBorder("Node Properties"),
				BorderFactory.createEmptyBorder(0, 0, 0, 0)));

		Box nodeDataBox = Box.createVerticalBox();
		nodeDataPanel.add(nodeDataBox);

		// Name
		nameLabel = new JLabel("Name: ");
		nameText = new JTextField(8);
		//nameText.addActionListener(saveAction);

		Box nameLabelAndTextBox = Box.createHorizontalBox();
		nameLabelAndTextBox.add(nameLabel);
		nameLabelAndTextBox.add(nameText);
		nodeDataBox.add(nameLabelAndTextBox);

		nodeDataBox.add(Box.createVerticalStrut(5));

		// IP
		ipLabel = new JLabel("IP: ");
		ipText = new JTextField(8);
		//ipText.addActionListener(saveAction);

		Box ipLabelAndTextBox = Box.createHorizontalBox();
		ipLabelAndTextBox.add(ipLabel);
		ipLabelAndTextBox.add(ipText);
		nodeDataBox.add(ipLabelAndTextBox);

		nodeDataBox.add(Box.createVerticalStrut(5));

		// X Y Coordinates
		xCordLabel = new JLabel("X: ");
		xCordText = new JTextField(1);
		//xCordText.addKeyListener(myForm.numKeyListener);

		yCordLabel = new JLabel(" Y: ");
		yCordText = new JTextField(1);
		//yCordText.addKeyListener(myForm.numKeyListener);

		Box xyCordsLabelAndTextBox = Box.createHorizontalBox();
		xyCordsLabelAndTextBox.add(xCordLabel);
		xyCordsLabelAndTextBox.add(xCordText);
		xyCordsLabelAndTextBox.add(yCordLabel);
		xyCordsLabelAndTextBox.add(yCordText);
		nodeDataBox.add(xyCordsLabelAndTextBox);

		nodeDataBox.add(Box.createVerticalStrut(5));

		// Power & Color
		powerLabel = new JLabel("Power: ");
		powerText = new JTextField(3);
		powerText.addKeyListener(myForm.numKeyListener);
		//powerText.addActionListener(saveAction);

		colorLabel = new JLabel("Color: ");
		colorBtn = new JButton();
		colorBtn.setMaximumSize(new Dimension(25, 25));
		//colorBtn.addActionListener(this);

		Box pwrColorLabelAndTextBox = Box.createHorizontalBox();
		pwrColorLabelAndTextBox.add(powerLabel);
		pwrColorLabelAndTextBox.add(powerText);
		pwrColorLabelAndTextBox.add(colorLabel);
		pwrColorLabelAndTextBox.add(colorBtn);
		nodeDataBox.add(pwrColorLabelAndTextBox);

		nodeDataBox.add(Box.createVerticalStrut(5));

		// Buttons
		saveBtn = new JButton("Save");
		saveBtn.setToolTipText("Save node properties");
		saveBtn.addActionListener(saveAction);
		
		Box saveButtonBox = Box.createHorizontalBox();
		saveButtonBox.add(saveBtn);
		nodeDataBox.add(saveButtonBox);

		nodeDataBox.add(Box.createVerticalStrut(5));

		/* Messaging Panel */
		JPanel messagingPanel = new JPanel();
		mainVerticalBox.add(messagingPanel);
		messagingPanel.setBorder(BorderFactory.createCompoundBorder(
				BorderFactory.createTitledBorder("Messaging"), BorderFactory
						.createEmptyBorder(0, 0, 0, 0)));

		Box messagingBox = Box.createVerticalBox();
		messagingPanel.add(messagingBox);

		sendFromLabel = new JLabel("Send from: ");
		sendFromText = new JTextField(10);
		sendFromText.setToolTipText("Enter node name");
		//sendFromText.addActionListener(sendAction);

		Box sendFromLabelAndTextBox = Box.createHorizontalBox();
		sendFromLabelAndTextBox.add(sendFromLabel);
		sendFromLabelAndTextBox.add(sendFromText);
		messagingBox.add(sendFromLabelAndTextBox);

		messagingBox.add(Box.createVerticalStrut(5));

		sendToLabel = new JLabel("Send to: ");
		sendToText = new JTextField(10);
		sendToText.setToolTipText("Enter node name or node names");
		//sendToText.addActionListener(sendAction);
		
		Box sendToLabelAndTextBox = Box.createHorizontalBox();
		sendToLabelAndTextBox.add(sendToLabel);
		sendToLabelAndTextBox.add(sendToText);
		messagingBox.add(sendToLabelAndTextBox);

		messagingBox.add(Box.createVerticalStrut(5));
		
		msgLabel = new JLabel("Message: ");
		msgText = new JTextField(10);
		//msgText.addActionListener(sendAction);

		Box msgLabelAndTextBox = Box.createHorizontalBox();
		msgLabelAndTextBox.add(msgLabel);
		msgLabelAndTextBox.add(msgText);
		messagingBox.add(msgLabelAndTextBox);

		messagingBox.add(Box.createVerticalStrut(5));
		
		protocolLabel = new JLabel("Protocol: ");
		protocolComboBox = new JComboBox(protocolStrings); 
		protocolComboBox.setSelectedIndex(0); 
		//protocolComboBox.addActionListener(sendAction);
		
		Box protocolLabelAndTextBox = Box.createHorizontalBox();
		protocolLabelAndTextBox.add(protocolLabel);
		protocolLabelAndTextBox.add(protocolComboBox);
		messagingBox.add(protocolLabelAndTextBox);
		
		messagingBox.add(Box.createVerticalStrut(5));
		
		Box msgBtnBox = Box.createHorizontalBox();
		messagingBox.add(msgBtnBox);
		
		sendBtn = new JButton("Send");
		sendBtn.setToolTipText("Send message");
		sendBtn.addActionListener(sendAction);
		msgBtnBox.add(sendBtn);
		
		msgBtnBox.add(Box.createHorizontalStrut(5));
		
		replayBtn = new JButton("Replay");
		replayBtn.setToolTipText("Replay last broadcast");
		replayBtn.addActionListener(replayAction);
		
		msgBtnBox.add(replayBtn);

		/* Image Panel */
		JPanel imagePanel = new JPanel();
		mainVerticalBox.add(imagePanel);

		Box imageBox = Box.createVerticalBox();
		imagePanel.add(imageBox);
		picLabel = new JLabel(new ImageIcon(wirelessTowerImg));
		imageBox.add(picLabel);

	}

	public NodeProperties() {
	}

	public String getKEY_AODV() {
		return KEY_AODV;
	}

	public String getKEY_DSDV() {
		return KEY_DSDV;
	}

	public void actionPerformed(ActionEvent e) {
		Color newColor = JColorChooser.showDialog(NodeProperties.this,
				"Choose Background Color", colorBtn.getBackground());
		if (newColor != null) {
			colorBtn.setBackground(newColor);
		}
	}

	public void clearNodeProperties() {
		this.nameText.setText("");
		this.ipText.setText("");
		this.nameText.setText("");
		this.nameText.setEnabled(true);
		this.xCordText.setText("");
		this.yCordText.setText("");
		this.powerText.setText("");
		this.colorBtn.setBackground(this.getBackground());
	}
	
	public void resetNodeProperties(Node node, GraphicalNode gNode) {
		this.nameText.setText(gNode.getName());
		this.nameText.setEnabled(true);
		this.ipText.setText(node.getIP().toString());
		this.xCordText.setText(Float.toString(gNode.getAlignmentX()));
		this.yCordText.setText(Float.toString(gNode.getAlignmentY()));
		this.powerText.setText(Integer.toString(node.getPower()));
		this.colorBtn.setBackground(gNode.getBackground());
	}

	public void resetNodeProperties() {
		this.nameText.setText("");
		this.nameText.setEnabled(true);
		this.ipText.setText("");
		this.xCordText.setText("");
		this.yCordText.setText("");
		this.powerText.setText("");
		this.colorBtn.setBackground(this.getBackground());
	}
	
}