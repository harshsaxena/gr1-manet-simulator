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

import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.SpinnerModel;
import javax.swing.SpinnerNumberModel;

import simulator.Node;
import UI.actions.ReplayAction;
import UI.actions.SaveNodeAction;
import UI.actions.SearchNodeAction;
import UI.actions.SendMsgAction;
import UI.myobjects.GraphicalNode;

@SuppressWarnings("serial")
public class NodeProperties extends JPanel{

	private Myform myForm;

	public JButton saveBtn = new JButton("Save");
	public JButton sendBtn = new JButton("Send");
	public JButton replayBtn = new JButton("Replay");

	public JComboBox modeComboBox;
	public JComboBox protocolComboBox;
	public JComboBox sendToComboBox;

	public BufferedImage wirelessTowerImg = new BufferedImage(175, 78, BufferedImage.TYPE_INT_RGB);

	public JLabel nameLabel = new JLabel("   Name: ");
	public JLabel ipLabel = new JLabel("   IP Address: ");
	public JLabel xCordLabel = new JLabel("   X Coordinate: ");
	public JLabel yCordLabel = new JLabel("   Y Coordinate: ");
	public JLabel picLabel; 
	public JLabel powerLabel = new JLabel("   Power: ");
	public JLabel msgLabel = new JLabel("   Message: ");
	public JLabel protocolLabel = new JLabel("   Protocol: ");
	public JLabel sendToLabel = new JLabel("   Send To: ");
	public JLabel sendFromLabel = new JLabel("   Send From: ");
	public JLabel searchNodeLabel = new JLabel("   Search: ");
	
	public JSpinner xCoordSpinner;
	public JSpinner yCoordSpinner;
	public JSpinner pwrSpinner;
	
	public JTextField nameText = new JTextField(6);
	public JTextField ipText = new JTextField(6);
	public JTextField xCordText = new JTextField(2);
	public JTextField yCordText = new JTextField(2);
	public JTextField powerText = new JTextField(6);
	public JTextField msgText = new JTextField(6);
	public JTextField sendToText = new JTextField(6);
	public JTextField sendFromText = new JTextField(6);
	public JTextField searchNodeText = new JTextField(6);
	public List<GraphicalNode> graphicalNodeList;
	public String[] availableNodes = new String[100];
	
	public final String KEY_AODV = "AODV";
	public final String KEY_DSDV = "DSDV";
	
	public static final int PWR_INIT = 0;
	public static final int PWR_MIN = 0;
	public static final int PWR_MAX = 1500;
	public static final int PWR_INCR = 100;
	
	public static final int X_COORD_INIT = 0;
	public static final int X_COORD_MIN = 0;
	public static final int X_COORD_MAX = 3900;
	public static final int X_COORD_INCR = 100;
	
	public static final int Y_COORD_INIT = 0;
	public static final int Y_COORD_MIN = 0;
	public static final int Y_COORD_MAX = 2500;
	public static final int Y_COORD_INCR = 100;

	public NodeProperties(Myform myForm) {
		this.myForm = myForm;

		String[] protocolStrings = {KEY_AODV, KEY_DSDV};
		
		try {
			wirelessTowerImg = ImageIO.read(new File(
					"images/wirelessTowerTitle.png"));
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		// Action Listener
		ActionListener saveAction = new SaveNodeAction(this.myForm);
		ActionListener sendAction = new SendMsgAction(this.myForm);
		ActionListener searchAction = new SearchNodeAction(this.myForm);
		ActionListener replayAction = new ReplayAction(this.myForm);

		// Main Box
		Box mainVerticalBox = Box.createVerticalBox();
		this.add(mainVerticalBox);
		
		/* Image Panel */
		JPanel imagePanel = new JPanel();
		mainVerticalBox.add(imagePanel);

		Box imageBox = Box.createVerticalBox();
		imagePanel.add(imageBox);
		picLabel = new JLabel(new ImageIcon(wirelessTowerImg));
		imageBox.add(picLabel);

		/* Search Panel */
		JPanel searchNodePanel = new JPanel();
		mainVerticalBox.add(searchNodePanel);
		searchNodePanel.setBorder(BorderFactory.createCompoundBorder(
				BorderFactory.createTitledBorder("Search"), BorderFactory
						.createEmptyBorder(0, 0, 8, 8)));
		
		GridLayout searchGrid = new GridLayout(1, 2);
		searchNodePanel.setLayout(searchGrid);

		searchNodeText.setToolTipText("Enter a node name to search");
		searchNodeText.addActionListener(searchAction);

		searchNodePanel.add(searchNodeLabel);
		searchNodePanel.add(searchNodeText);
		
		mainVerticalBox.add(searchNodePanel);

		/* Properties Panel */
		Box propsBox = Box.createVerticalBox();
		propsBox.setBorder(BorderFactory.createCompoundBorder(
				BorderFactory.createTitledBorder("Properties"),
				BorderFactory.createEmptyBorder(0, 0, 8, 8)));

		SpinnerModel pwrSpinnerModel = new SpinnerNumberModel(PWR_INIT, PWR_MIN, PWR_MAX, PWR_INCR);
		SpinnerModel xCoordSpinnerModel = new SpinnerNumberModel(X_COORD_INIT, X_COORD_MIN, X_COORD_MAX, X_COORD_INCR);
		SpinnerModel yCoordSpinnerModel = new SpinnerNumberModel(Y_COORD_INIT, Y_COORD_MIN, Y_COORD_MAX, Y_COORD_INCR);
		
		pwrSpinner = new JSpinner(pwrSpinnerModel);
		xCoordSpinner = new JSpinner(xCoordSpinnerModel);
		yCoordSpinner = new JSpinner(yCoordSpinnerModel);
		
		JPanel propertiesPanel = new JPanel();
		
		GridLayout propsGrid = new GridLayout(5, 2);
		propsGrid.setVgap(5);
		propertiesPanel.setLayout(propsGrid);

		propertiesPanel.add(nameLabel);
		propertiesPanel.add(nameText);
		propertiesPanel.add(ipLabel);
		propertiesPanel.add(ipText);
		propertiesPanel.add(xCordLabel);
		propertiesPanel.add(xCoordSpinner);
		propertiesPanel.add(yCordLabel);
		propertiesPanel.add(yCoordSpinner);
		propertiesPanel.add(powerLabel);
		propertiesPanel.add(pwrSpinner);

		powerText.addKeyListener(myForm.numKeyListener);

		propsBox.add(propertiesPanel);
		propsBox.add(Box.createVerticalStrut(5));
		
		JPanel saveButtonPanel = new JPanel();
		
		saveBtn.setToolTipText("Save node properties");
		saveBtn.addActionListener(saveAction);
		
		saveButtonPanel.setLayout(new BoxLayout(saveButtonPanel, BoxLayout.LINE_AXIS));
		saveButtonPanel.add(Box.createHorizontalGlue());
		saveButtonPanel.add(saveBtn);
		
		propsBox.add(Box.createVerticalStrut(8));
		propsBox.add(saveButtonPanel);
		mainVerticalBox.add(propsBox);

		/* Messaging Panel */
		Box messagingBox = Box.createVerticalBox();
		messagingBox.setBorder(BorderFactory.createCompoundBorder(
				BorderFactory.createTitledBorder("Messaging"),
				BorderFactory.createEmptyBorder(0, 0, 8, 8)));
		
		JPanel messagingPanel = new JPanel();

		GridLayout messagingGrid = new GridLayout(3, 2);
		messagingGrid.setVgap(5);
		messagingPanel.setLayout(messagingGrid);

		messagingPanel.add(sendFromLabel);
		messagingPanel.add(sendFromText);
		messagingPanel.add(sendToLabel);
		messagingPanel.add(sendToText);
		messagingPanel.add(msgLabel);
		messagingPanel.add(msgText);

		sendToText.setToolTipText("Enter node name or node names");
		sendFromText.setToolTipText("Enter node name");
		msgText.setToolTipText("Enter message to broadcast");
		
		messagingBox.add(Box.createVerticalStrut(5));
		messagingBox.add(messagingPanel);

		JPanel protocolPanel = new JPanel();

		GridLayout protocolGrid = new GridLayout(1, 2);
		protocolGrid.setVgap(5);
		protocolPanel.setLayout(protocolGrid);
		
		protocolComboBox = new JComboBox(protocolStrings); 
		protocolComboBox.setSelectedIndex(0); 
		protocolComboBox.setToolTipText("Select a messaging protocol");
		
		protocolPanel.add(protocolLabel);
		protocolPanel.add(protocolComboBox);
		
		messagingBox.add(Box.createVerticalStrut(5));
		messagingBox.add(protocolPanel);
		
		JPanel msgButtonPanel = new JPanel();
		msgButtonPanel.setLayout(new BoxLayout(msgButtonPanel, BoxLayout.LINE_AXIS));
		msgButtonPanel.add(Box.createHorizontalGlue());
		
		sendBtn.setToolTipText("Send message");
		sendBtn.addActionListener(sendAction);
		
		replayBtn.setToolTipText("Replay a simulation");
		replayBtn.addActionListener(replayAction);
		
		msgButtonPanel.add(sendBtn);
		msgButtonPanel.add(Box.createRigidArea(new Dimension(10, 0)));
		msgButtonPanel.add(replayBtn);		
		
		messagingBox.add(Box.createVerticalStrut(8));
		messagingBox.add(msgButtonPanel);
		mainVerticalBox.add(messagingBox);
	}

	public NodeProperties() {
	}

	public String getKEY_AODV() {
		return KEY_AODV;
	}

	public String getKEY_DSDV() {
		return KEY_DSDV;
	}

	public void clearNodeProperties() {
		this.nameText.setText("");
		this.ipText.setText("");
		//this.xCordText.setText("");
		//this.yCordText.setText("");
		//this.powerText.setText("");
		this.xCoordSpinner.setValue(0);
		this.yCoordSpinner.setValue(0);
		this.pwrSpinner.setValue(0);
	}
	
	public void setPropertiesEnabled(boolean enable){
		
		if(enable == false){
			this.nameText.setEnabled(false);
			this.ipText.setEnabled(false);
			this.xCoordSpinner.setEnabled(false);
			this.yCoordSpinner.setEnabled(false);
			this.pwrSpinner.setEnabled(false);
			this.saveBtn.setEnabled(false);
		}else{
			//this.nameText.setEnabled(true);
			this.ipText.setEnabled(true);
			this.xCoordSpinner.setEnabled(true);
			this.yCoordSpinner.setEnabled(true);
			this.pwrSpinner.setEnabled(true);
			this.saveBtn.setEnabled(true);
		}
		
	}
	
	public void setMsgPropertiesEnabled(boolean enable){
		if(enable == false){
			this.sendFromText.setEnabled(false);
			this.sendToText.setEnabled(false);
			this.msgText.setEnabled(false);
			this.sendBtn.setEnabled(false);
			//this.replayBtn.setEnabled(false);
			this.protocolComboBox.setEnabled(false);
		}else{
			this.sendFromText.setEnabled(true);
			this.sendToText.setEnabled(true);
			this.msgText.setEnabled(true);
			this.sendBtn.setEnabled(true);
			//this.replayBtn.setEnabled(true);
			this.protocolComboBox.setEnabled(true);
		}
	}
	
	public void setSearchPropertiesEnabled(boolean enable){
		if(enable == false){
			this.searchNodeText.setEnabled(false);
		}else{
			this.searchNodeText.setEnabled(true);
		}
	}
	
	public void resetNodeProperties(Node node, GraphicalNode gNode) {
		this.nameText.setText(gNode.getName());
		this.nameText.setEnabled(true);
		this.ipText.setText(node.getIP().toString());
		//this.xCordText.setText(Float.toString(gNode.getX()));
		//this.yCordText.setText(Float.toString(gNode.getX()));
		//this.powerText.setText(Integer.toString(node.getPower()));
		this.xCoordSpinner.setValue(Float.toString(gNode.getX()));
		this.yCoordSpinner.setValue(Float.toString(gNode.getY()));
		this.pwrSpinner.setValue(node.getPower());
	}
	
	public JComboBox getSendToComboBox() {
		return sendToComboBox;
	}

	public void setSendToComboBox(JComboBox sendToComboBox) {
		this.sendToComboBox = sendToComboBox;
	}

	public JSpinner getPwrSpinner() {
		return pwrSpinner;
	}

	public void setPwrSpinner(JSpinner pwrSpinner) {
		this.pwrSpinner = pwrSpinner;
	}

	public JSpinner getxCoordSpinner() {
		return xCoordSpinner;
	}

	public void setxCoordSpinner(JSpinner xCoordSpinner) {
		this.xCoordSpinner = xCoordSpinner;
	}

	public JSpinner getyCoordSpinner() {
		return yCoordSpinner;
	}

	public void setyCoordSpinner(JSpinner yCoordSpinner) {
		this.yCoordSpinner = yCoordSpinner;
	}
	
}