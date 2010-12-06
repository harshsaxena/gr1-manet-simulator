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

package UI.myobjects;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.awt.event.MouseEvent;
import java.io.IOException;

import javax.swing.Icon;
import javax.swing.JComponent;

import logger.FileLogger;
import simulator.Node;
import simulator.noderelated.Coordinates;
import simulator.noderelated.IPAddress;
import UI.Myform;
import UI.NodeProperties;

/**
 * A graphical representation of a node it implements some method to make a
 * communication between graphical properties and node properties
 */
public class GraphicalNode extends NodeButton implements Transferable {

	public final IntegerWrapper currentIconNumber = new IntegerWrapper();

	private static final long serialVersionUID = 1L;
	private static final String mimeType = DataFlavor.javaJVMLocalObjectMimeType
			+ ";class=UI.myobjects.GraphicalNode";
	private static String curName = "`"; // 'a' - 1 = '`'
	private static IPAddress curIP = new IPAddress("192.168.10.1");

	public static final int ANIMATION_PERIOD = 500;
	public static DataFlavor dataFlavor;
	public static int DEFAULT_POWER = 1000;

	private Node node;
	private String name;
	private Color color;

	public String toString() {
		return this.getName();
	}

	/** Returns the next lexicographic name. **/
	private static String getNextName() {
		// TODO extend to longer names
		// assume curName is length 1
		char s = curName.charAt(0);
		StringBuffer prefix = new StringBuffer("");
		String name = curName;
		name = prefix.toString() + ++s;
		if (s > 'z') {
			prefix.append("a");
			s = 'a';
		}
		curName = name;
		return name;
	}

	/**
	 * it overrides default setBounds method of {@link JComponent} class to make
	 * a communication between x,y properties of graphical node and actual node
	 * 
	 * @param x
	 * @param y
	 * @param width
	 * @param height
	 */
	public void setBounds(int x, int y, int width, int height) {
		super.setBounds(x, y, width, height);
		this.node.setNode_coordinates(new Coordinates(x * myForm.getXScale(), y
				* myForm.getYScale()));
		FileLogger.write("Map Panel setting bound.", FileLogger.MSG_TYPE_INFO);
	}

	public GraphicalNode(Icon icon) {
		super(icon);
		try {
			dataFlavor = new DataFlavor(GraphicalNode.mimeType);
		} catch (ClassNotFoundException e) {
		}

		this.node = new Node(curIP);
		curIP = IPAddress.createNext(curIP);
		this.color = this.getBackground();
		this.node.setPower(DEFAULT_POWER);
	}

	/**
	 * makes a Graphical Node component and calls another constructor
	 * 
	 * @param icon
	 * @param myForm
	 *            the frame object that should contains this component
	 * @param shouldRemoved
	 *            says if in drag and drop action this component should be moved
	 *            or copied
	 */
	public GraphicalNode(Icon icon, Myform myForm, boolean shouldRemoved) {
		this(icon);
		this.shouldRemoved = shouldRemoved;
		this.myForm = myForm;
		this.name = getNextName();
	}

	/**
	 * sets this components x and y corresponding to x and y of actual node
	 * 
	 * @param x
	 *            x dimension of actual node
	 * @param y
	 *            y dimension of actual node
	 */
	public void setScaledCoordinates(int x, int y) {
		Dimension size = getSize();
		super.setBounds(x / myForm.getXScale(), y / myForm.getYScale(),
				size.width, size.height);
		this.node.setNode_coordinates(new Coordinates(x, y));
	}

	public void setNodePower(int power) {
		this.node.setPower(power);
	}

	public void setNodeIP(String IP) {
		this.node.setIP(new IPAddress(IP));
	}

	public Node getNode() {
		return node;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
		this.setToolTipText(name);
	}

	public Color getColor() {
		return color;
	}

	public void setColor(Color color) {
		this.setBackground(color);
		this.color = color;
	}

	public void fillNodePanel() {
		NodeProperties np = myForm.getNodePropertiesPanel();
		np.nameText.setText(this.name);
		np.ipText.setText(node.getIP().toString());
		np.xCordText.setText(Integer.toString(node.getNode_coordinates()
				.getX_coordinate()));
		np.yCordText.setText(Integer.toString(node.getNode_coordinates()
				.getY_coordinate()));
		np.powerText.setText(Integer.toString(node.getPower()));
		np.colorBtn.setBackground(this.color);
	}

	public void setSelectGNode() {
		myForm.setSelectedGNode(this);
	}

	public void sending(int type) {
		java.util.Timer atimer = new java.util.Timer("Animating " + this.name,
				true);
		atimer.schedule(new IconAnimator(this.myForm, this, atimer,
				"Animating " + this.name, type), 0,
				GraphicalNode.ANIMATION_PERIOD);
	}

	/**
	 * when mouse pressed on this component the left hand node properties should
	 * be filled it also sets frame selected node and initiates drag and drop
	 * action
	 * 
	 * @param e
	 */
	public void mousePressed(MouseEvent e) {
		super.mousePressed(e);
		myForm.setSelectedGNode(this);
	}

	public DataFlavor[] getTransferDataFlavors() {
		return new DataFlavor[] { dataFlavor };
	}

	public boolean isDataFlavorSupported(DataFlavor flavor) {
		return dataFlavor.equals(flavor);
	}

	public Object getTransferData(DataFlavor flavor)
			throws UnsupportedFlavorException, IOException {
		if (!isDataFlavorSupported(flavor)) {
			throw new UnsupportedFlavorException(flavor);
		}
		return this;

	}
}

class IntegerWrapper {
	int value;
	boolean shouldStop;
	int type = 0;

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public int getValue() {
		return value;
	}

	public boolean isShouldStop() {
		return shouldStop;
	}

	public void setShouldStop(boolean shouldStop) {
		this.shouldStop = shouldStop;
	}

	public void setValue(int value) {
		this.value = value;
	}

	public void increaseValue() {
		this.value++;
	}

	public String toString() {
		return value + "|" + shouldStop;
	}
}