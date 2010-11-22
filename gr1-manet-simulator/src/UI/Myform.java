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
import java.awt.Dimension;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.Box;
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
import simulator.Node;
import UI.actions.DeleteBtnAction;
import UI.actions.NumberKeyListener;
import UI.actions.PanelAction;
import UI.myobjects.GraphicalNode;
import UI.myobjects.NodeButton;
import UI.myobjects.PowerShower;
import UI.myobjects.draganddrop.DropTargetImp;

public class Myform extends JFrame {

	private static final long serialVersionUID = 1L;
	public final int mapHeight = 551;
	public final int mapWidth = 600;
	private int xScale = 3000 / 550; // default setting from mapForm
	private int yScale = 3000 / 550; // default setting from mapForm

	public JPanel content;
	public JPanel outlogPanel;
	public JToolBar toolBar;

	public JButton delGNodeBtn = new JButton(new ImageIcon("images/delete.png"));
	public NodeButton addNodeBtn = new NodeButton(new ImageIcon(
			"images/SendingNode0.png"));

	private NodeProperties nodePanel;
	private OutputLogProperties outputLogProperties;

	private final JCheckBox doubleDirection = new JCheckBox("DoubleDirection",
			true);
	public JButton generateBtn;
	private final JTextField minNumber = new JTextField("3", 3);
	// private final JTextField searchText = new JTextField(8);

	private final List<GraphicalNode> graphicalNodes = new ArrayList<GraphicalNode>();
	private GraphicalNode selectedGNode;
	public MyMap myMap;
	public final NumberKeyListener numKeyListener = new NumberKeyListener();
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
		toolBar.add(Box.createHorizontalStrut(10));
		toolBar.add(addNodeBtn);
		toolBar.add(Box.createHorizontalStrut(10));
		toolBar.add(delGNodeBtn);
		toolBar.add(Box.createHorizontalStrut(10));
		content.add(toolBar, BorderLayout.PAGE_START);
		
		outputLogProperties = new OutputLogProperties();
		outlogPanel = outputLogProperties.getOutputLogProperties();
		content.add(outlogPanel, BorderLayout.AFTER_LAST_LINE);

		myMap.setDropTarget(new DropTargetImp(myMap));
		myMap.setLayout(null);

	}

	public static void main(String[] args) {
		Myform frame = new Myform("MANET Simulator");
		frame.addNodeBtn.myForm = frame;
		frame.setNodePanel(new NodeProperties(frame));

		JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT,
				frame.myMap, frame.getNodePanel());

		splitPane.setOneTouchExpandable(true);
		splitPane.setDividerLocation(575);
		frame.content.add(splitPane, BorderLayout.CENTER);
		frame.myMap.addMouseListener(new PanelAction(frame));
		frame.powerShower = new PowerShower(frame);
		frame.delGNodeBtn.addActionListener(new DeleteBtnAction(frame));
		frame.powerShower.setBounds(0, 0, 9999, 9999);
		frame.myMap.add(frame.powerShower, JLayeredPane.PALETTE_LAYER);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.pack();
		frame.setVisible(true);

		OutputLogger.init(frame);
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
	 * @return
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

	public NodeProperties getNodePanel() {
		return nodePanel;
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

	public void setNodePanel(NodeProperties nodePanel) {
		this.nodePanel = nodePanel;
	}

	public void setSelectedGNode(GraphicalNode selectedGNode) {
		this.selectedGNode = selectedGNode;
		if (selectedGNode != null) {
			this.getNodePanel().nameText.setEnabled(selectedGNode.getName()
					.trim().length() == 0);
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
}
