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

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLayeredPane;
import javax.swing.JPanel;
import javax.swing.JSplitPane;
import javax.swing.JTextField;
import javax.swing.JToolBar;

import logger.OutputLogger;
import simulator.Map_Manager;
import simulator.Node;
import UI.actions.DeleteAllNodesAction;
import UI.actions.DeleteNodeAction;
import UI.actions.HelpAction;
import UI.actions.NumberKeyListener;
import UI.actions.PanelAction;
import UI.actions.ReplayAction;
import UI.actions.SendMsgAction;
import UI.myobjects.GraphicalNode;
import UI.myobjects.NodeButton;
import UI.myobjects.PowerShower;
import UI.myobjects.draganddrop.MapActions;

public class Myform extends JFrame {

	private static final long serialVersionUID = 1L;
	private final JTextField minNumber = new JTextField("3", 3);
	private final JCheckBox doubleDirection = new JCheckBox("DoubleDirection",
			true);
	private final List<GraphicalNode> graphicalNodes = new ArrayList<GraphicalNode>();

	private int xScale = 3000 / 550; // default setting from mapForm
	private int yScale = 3000 / 550; // default setting from mapForm
	private GraphicalNode selectedGNode;
	private NodeProperties nodePropertiesPanel;
	private OutputLogProperties outputLogProperties;

	public final int mapHeight = 551;
	public final int mapWidth = 600;
	public final NumberKeyListener numKeyListener = new NumberKeyListener();
	
	public JButton generateBtn;
	public JPanel content;
	public JPanel outlogPanel;
	public JToolBar toolBar;
	public JButton helpBtn = new JButton(new ImageIcon("images/Help.png"));
	public JButton replayBtn = new JButton(new ImageIcon("images/Replay.png"));
	public JButton sendBtn = new JButton(new ImageIcon("images/Send.png"));
	public JButton delGNodeBtn = new JButton(new ImageIcon(
			"images/DeleteNode.png"));
	public JButton delAllGNodesBtn = new JButton(new ImageIcon(
			"images/DeleteAllNode.png"));
	public NodeButton addNodeBtn = new NodeButton(new ImageIcon(
			"images/AddNode.png"));
	public MyMap myMap;
	public PowerShower powerShower;

	public Myform(String title) {
		super(title);
		content = new JPanel(new BorderLayout());
		content.setOpaque(true);

		myMap = new MyMap();
		myMap.setPreferredSize(new Dimension(this.mapWidth, this.mapHeight));
		myMap.setBorder(BorderFactory.createEtchedBorder());
		this.getContentPane().add(content);

		toolBar = new JToolBar();
		JPanel leftToolBarPanel = new JPanel();
		leftToolBarPanel.setLayout(new BoxLayout(leftToolBarPanel, BoxLayout.LINE_AXIS));
		leftToolBarPanel.setBorder(BorderFactory.createEmptyBorder(0, 5, 0, 0));
		leftToolBarPanel.add(addNodeBtn);
		leftToolBarPanel.add(delGNodeBtn);
		leftToolBarPanel.add(delAllGNodesBtn);
		leftToolBarPanel.add(sendBtn);
		leftToolBarPanel.add(replayBtn);
		leftToolBarPanel.setAlignmentX(Component.LEFT_ALIGNMENT);
		toolBar.add(leftToolBarPanel);
		
		toolBar.addSeparator();
		
		JPanel rightToolBarPanel = new JPanel();
		rightToolBarPanel.setLayout(new BoxLayout(rightToolBarPanel, BoxLayout.LINE_AXIS));
		rightToolBarPanel.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 5));
		rightToolBarPanel.add(Box.createHorizontalGlue());
		rightToolBarPanel.add(helpBtn);
		rightToolBarPanel.setAlignmentX(Component.RIGHT_ALIGNMENT);
		toolBar.add(rightToolBarPanel);

		content.add(toolBar, BorderLayout.PAGE_START);

		outputLogProperties = new OutputLogProperties();
		outlogPanel = outputLogProperties.getOutputLogProperties();
		content.add(outlogPanel, BorderLayout.AFTER_LAST_LINE);

		myMap.setDropTarget(new MapActions(myMap));
		myMap.setLayout(null);

		Map_Manager.get_instance().setMyForm(this);
	}

	public static void main(String[] args) {
		Myform myForm = new Myform("MANET Simulator");
		myForm.addNodeBtn.myForm = myForm;
		myForm.addNodeBtn.setToolTipText("Drag and drop to add node");
		myForm.setNodePropertiesPanel(new NodeProperties(myForm));

		JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT,
				myForm.myMap, myForm.getNodePropertiesPanel());
		splitPane.setOneTouchExpandable(true);
		splitPane.setDividerLocation(575);

		myForm.content.add(splitPane, BorderLayout.CENTER);
		myForm.myMap.addMouseListener(new PanelAction(myForm));
		myForm.powerShower = new PowerShower(myForm);
		myForm.delGNodeBtn.setToolTipText("Delete selected node");
		myForm.delGNodeBtn.addActionListener(new DeleteNodeAction(myForm));
		myForm.delGNodeBtn.setEnabled(false);
		myForm.delAllGNodesBtn.setToolTipText("Delete all nodes");
		myForm.delAllGNodesBtn.addActionListener(new DeleteAllNodesAction(
				myForm));
		myForm.delAllGNodesBtn.setEnabled(false);
		myForm.sendBtn.setToolTipText("Send message");
		myForm.sendBtn.addActionListener(new SendMsgAction(myForm));
		myForm.sendBtn.setEnabled(false);
		myForm.replayBtn.setToolTipText("Replay simulation");
		myForm.replayBtn.addActionListener(new ReplayAction(myForm));
		myForm.helpBtn.setToolTipText("Help");
		myForm.helpBtn.addActionListener(new HelpAction());

		myForm.powerShower.setBounds(0, 0, 9999, 9999);
		myForm.myMap.add(myForm.powerShower, JLayeredPane.PALETTE_LAYER);
		myForm.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		myForm.pack();
		myForm.setVisible(true);

		OutputLogger.init(myForm);
	}

	/**
	 * finds GraphicalNode in graphicalNode list
	 * 
	 * @param name
	 * @return null: if it didn't find the gnode with name<br/>
	 *         reference to that node if it found
	 */
	public GraphicalNode getGNode(String name) {
		for (GraphicalNode graphicalNode : graphicalNodes) {
			if (graphicalNode.getName().equals(name)) {
				return graphicalNode;
			}
		}
		return null;
	}

	/**
	 * Searches the Graphical Nodes for a matching Node. If found the matching
	 * Graphical Node is returned.
	 * 
	 * @param node
	 */
	public GraphicalNode getGnodebyNode(Node node) {
		for (GraphicalNode graphicalNode : graphicalNodes) {
			if (graphicalNode.getNode().equals(node)) {
				return graphicalNode;
			}
		}
		return null;
	}

	public List<GraphicalNode> getGraphicalNodes() {
		return graphicalNodes;
	}

	public int getMinNumberForFillParameter() {
		if (this.minNumber.getText().trim().length() > 0) {
			return Integer.parseInt(this.minNumber.getText());
		} else {
			return 0;
		}
	}

	public MyMap getMyMap() {
		return myMap;
	}

	public NodeProperties getNodePropertiesPanel() {
		return nodePropertiesPanel;
	}

	/**
	 * returns the {@link GraphicalNode} that currently selected
	 */
	public GraphicalNode getSelectedGNode() {
		return selectedGNode;
	}

	public boolean isDoubleDirection() {
		return this.doubleDirection.isSelected();
	}

	public void putGNode(GraphicalNode gNode) {
		graphicalNodes.add(gNode);
	}

	public void refreshPowerShower() {
		this.powerShower.setVisible(false);
		this.powerShower
				.setXYrXrY(selectedGNode.getLocation().x, selectedGNode
						.getLocation().y, selectedGNode.getNode().getPower()
						/ this.xScale, selectedGNode.getNode().getPower()
						/ this.yScale);
		this.powerShower.setVisible(true);
		this.powerShower.invalidate();
	}

	public void setNodePropertiesPanel(NodeProperties nodePanel) {
		this.nodePropertiesPanel = nodePanel;
	}

	public void setSelectedGNode(GraphicalNode selectedGNode) {
		this.selectedGNode = selectedGNode;
		if (selectedGNode != null) {
			this.getNodePropertiesPanel().nameText.setEnabled(selectedGNode
					.getName().trim().length() == 0);
			selectedGNode.fillNodePanel();
			this.refreshPowerShower();
		} else {
			this.powerShower.setVisible(false);
		}
	}

	public int getXScale() {
		return xScale;
	}

	public void setXScale(int xScale) {
		this.xScale = xScale;
	}

	public int getYScale() {
		return yScale;
	}

	public void setYScale(int yScale) {
		this.yScale = yScale;
	}

	public void setOutputLogProperties(OutputLogProperties outputLogProperties) {
		this.outputLogProperties = outputLogProperties;
	}

	public OutputLogProperties getOutputLogProperties() {
		return outputLogProperties;
	}

	/**
	 * Clears nodes from the map.
	 */
	public void clearNodesFromMap() {
		// List<GraphicalNode> gNodeList = myForm.getGraphicalNodes();
		// for (int i = gNodeList.size() - 1; i >= 0; i--) {
		// GraphicalNode gNode = gNodeList.get(i);
		// myForm.getMyMap().remove(gNode);
		// myForm.getGraphicalNodes().remove(gNode);
		// }

		List<GraphicalNode> gNodeList = getGraphicalNodes();
		List<Node> nodeList = Map_Manager.get_instance().getNode_list();
		for (GraphicalNode node : gNodeList) {
			getMyMap().remove(node);
		}
		gNodeList.clear();
		nodeList.clear();

		getNodePropertiesPanel().clearNodeProperties();
		setSelectedGNode(null);
	}
}
