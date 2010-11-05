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
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.JSplitPane;
import javax.swing.JTextField;
import javax.swing.JToolBar;
import javax.swing.SwingConstants;

import logger.StatusLogger;
import simulator.Node;
import UI.actions.DeleteBtnAction;
import UI.actions.InitParameters;
import UI.actions.NumberKeyListener;
import UI.actions.PanelAction;
import UI.actions.SearchGNodeAction;
import UI.myobjects.GraphicalNode;
import UI.myobjects.NodeButton;
import UI.myobjects.PowerShower;
import UI.myobjects.draganddrop.DropTargetImp;

public class Myform extends JFrame {

	private static final long serialVersionUID = 1L;
	
	public static void main(String[] args) {
		Myform frame = new Myform("AODV Simulator");
		frame.newNodeBtn.myForm = frame;
		frame.setNodePanel(new Node_Properties(frame));
		JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT,
				frame.myMap, frame.getNodePanel());
		splitPane.setOneTouchExpandable(true);
		splitPane.setDividerLocation(550);
		frame.content.add(splitPane, BorderLayout.CENTER);
		frame.myMap.addMouseListener(new PanelAction(frame));
		frame.generateBtn.addActionListener(new InitParameters(frame));
		frame.powerShower = new PowerShower(frame);
		frame.delGnodeBtn.addActionListener(new DeleteBtnAction(frame));
		frame.searchText.addActionListener(new SearchGNodeAction(frame));
		frame.setGlassPane(frame.powerShower);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.pack();

		MapForm mapForm = new MapForm(frame, "Initializing Map", true, frame);
		mapForm.pack();
		mapForm.setVisible(true);
		StatusLogger.init(frame);

	}
	public JPanel content;
	public JButton delGnodeBtn = new JButton(new ImageIcon("images/delete.png"));
	private final JCheckBox doubleDirection = new JCheckBox("DoubleDirection",
			true);
	public JButton generateBtn;
	private final List<GraphicalNode> graphicalNodes = new ArrayList<GraphicalNode>();
	public final int mapHeight = 550;
	public final int mapWidth = 550;
	private final JTextField minNumber = new JTextField("3", 3);
	public MyMap myMap;
	public NodeButton newNodeBtn = new NodeButton(new ImageIcon(
			"images/SendingNode0.png"));
	public final NumberKeyListener nkl = new NumberKeyListener();
	private Node_Properties nodePanel;
	public PowerShower powerShower;
	private final JTextField searchText = new JTextField(8);
	private GraphicalNode selectedGNode;
	public JToolBar toolBar;
	public int xScale = 10;
	public int yScale = 10;

	public Myform(String title) {
		super(title);
		content = new JPanel(new BorderLayout());
		content.setOpaque(true);
		myMap = new MyMap();
		myMap.setPreferredSize(new Dimension(this.mapWidth, this.mapHeight));
		myMap.setBorder(BorderFactory.createEtchedBorder());
		this.getContentPane().add(content);
		toolBar = new JToolBar();
		toolBar.add(newNodeBtn);
		toolBar.add(delGnodeBtn);
		toolBar.add(Box.createHorizontalStrut(5));
		toolBar.add(new JSeparator(SwingConstants.VERTICAL));
		toolBar.add(Box.createHorizontalStrut(5));
		toolBar.add(new JLabel("Min Neighbor: "));
		toolBar.add(minNumber);
		toolBar.add(doubleDirection);
		generateBtn = new JButton("Fill Parameter");
		toolBar.add(generateBtn);
		minNumber.addKeyListener(new NumberKeyListener());

		toolBar.add(Box.createHorizontalStrut(5));
		toolBar.add(new JSeparator(SwingConstants.VERTICAL));
		toolBar.add(Box.createHorizontalStrut(5));
		toolBar.add(new JLabel("Search: "));
		toolBar.add(searchText);

		content.add(toolBar, BorderLayout.PAGE_START);
		myMap.setDropTarget(new DropTargetImp(myMap));
		myMap.setLayout(null);
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

	public Node_Properties getNodePanel() {
		return nodePanel;
	}

	public JTextField getSearchText() {
		return searchText;
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

	public void setNodePanel(Node_Properties nodePanel) {
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

}
