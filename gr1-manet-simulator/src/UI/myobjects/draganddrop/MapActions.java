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

package UI.myobjects.draganddrop;

import java.awt.Dimension;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.awt.dnd.DropTarget;
import java.awt.dnd.DropTargetDropEvent;
import java.io.IOException;

import javax.swing.JComponent;
import javax.swing.JLayeredPane;

import logger.FileLogger;
import UI.myobjects.GraphicalNode;

/**
 * Implements drag, drop or move actions of {@link GraphicalNode} on map.
 */
@SuppressWarnings("serial")
public class MapActions extends DropTarget {
	private JComponent component;
	private final String ACTION_MOVE = "MoveNode";
	private final String ACTION_ADD = "AddNode";

	public MapActions(JComponent comp) {
		this.component = comp;
	}

	public void drop(DropTargetDropEvent dropEvent) {

		if (dropEvent.isDataFlavorSupported(GraphicalNode.dataFlavor)) {
			dropEvent.acceptDrop(dropEvent.getDropAction());

			JLayeredPane panel = (JLayeredPane) this.component;
			Transferable t = dropEvent.getTransferable();
			
			try {
				String actionType = ACTION_ADD;
				GraphicalNode node = (GraphicalNode) t.getTransferData(GraphicalNode.dataFlavor);
				Dimension size = node.getPreferredSize();
				
				if (!node.isShouldRemoved()) {
					panel.add(node);
					FileLogger.write("ACTION=" + actionType + "_START", FileLogger.MSG_TYPE_REPLAY);
				}else
				{
					actionType = ACTION_MOVE;
					FileLogger.write("ACTION=" + actionType + "_START", FileLogger.MSG_TYPE_REPLAY);
				}
				
				node.setSelectGNode();
				node.setBounds(dropEvent.getLocation().x, dropEvent.getLocation().y, size.width, size.height);
				node.fillNodePanel();
				
				panel.invalidate();
				node.myForm.refreshPowerShower();

				if (actionType.equals(ACTION_ADD)) {
					FileLogger.write("\tNodeName=" + node.getName(), FileLogger.MSG_TYPE_REPLAY);
					FileLogger.write("\tNodeIP=" + node.getNode().getIP(), FileLogger.MSG_TYPE_REPLAY);
					FileLogger.write("\tNodeXCoord=" + node.getX(), FileLogger.MSG_TYPE_REPLAY);
					FileLogger.write("\tNodeYCoord=" + node.getY(), FileLogger.MSG_TYPE_REPLAY);
				}else{
					FileLogger.write("\tNodeName=" + node.getName(), FileLogger.MSG_TYPE_REPLAY);
					FileLogger.write("\tNodeXCoord=" + node.getX(), FileLogger.MSG_TYPE_REPLAY);
					FileLogger.write("\tNodeYCoord=" + node.getY(), FileLogger.MSG_TYPE_REPLAY);
				}
				
				dropEvent.dropComplete(true);
				FileLogger.write("ACTION=" + actionType + "_END", FileLogger.MSG_TYPE_REPLAY);
				
			} catch (UnsupportedFlavorException ufe) {
				FileLogger.write("Error dropping node, unsupported flavor: " + ufe, FileLogger.MSG_TYPE_ERROR);
			} catch (IOException ioe) {
				FileLogger.write("Error dropping node, IO exception: " + ioe, FileLogger.MSG_TYPE_ERROR);
			}
		}
	}
}