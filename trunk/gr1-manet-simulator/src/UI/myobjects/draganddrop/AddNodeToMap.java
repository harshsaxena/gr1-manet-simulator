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
 * A class that implements action of copying or moving {@link GraphicalNode} on
 * target place
 */
@SuppressWarnings("serial")
public class AddNodeToMap extends DropTarget {
	private JComponent component;

	public AddNodeToMap(JComponent comp) {
		this.component = comp;
	}

	public void drop(DropTargetDropEvent dropEvent) {

		if (dropEvent.isDataFlavorSupported(GraphicalNode.dataFlavor)) {
			dropEvent.acceptDrop(dropEvent.getDropAction());

			JLayeredPane panel = (JLayeredPane) this.component;
			Transferable t = dropEvent.getTransferable();
			
			try {
				FileLogger.write("Action= AddingNode**", FileLogger.MSG_TYPE_REPLAY);
				GraphicalNode node = (GraphicalNode) t.getTransferData(GraphicalNode.dataFlavor);
				Dimension size = node.getPreferredSize();
				
				if (!node.isShouldRemoved()) {
					panel.add(node);
				}
				
				node.setSelectGNode();
				node.setBounds(dropEvent.getLocation().x, dropEvent.getLocation().y, size.width, size.height);
				node.fillNodePanel();
				
				panel.invalidate();
				node.myForm.refreshPowerShower();

				FileLogger.write("NodeName= " + node.getName(), FileLogger.MSG_TYPE_REPLAY);
				FileLogger.write("NodeIP= " + node.getNode().getIP(), FileLogger.MSG_TYPE_REPLAY);
				FileLogger.write("NodeXCoord= " + node.getX(), FileLogger.MSG_TYPE_REPLAY);
				FileLogger.write("NodeYCoord= " + node.getY(), FileLogger.MSG_TYPE_REPLAY);
				
				dropEvent.dropComplete(true);
				FileLogger.write("Action= AddingNodeCompete****", FileLogger.MSG_TYPE_REPLAY);
				
			} catch (UnsupportedFlavorException ufe) {
				FileLogger.write("Error dropping node, unsupported flavor: " + ufe, FileLogger.MSG_TYPE_ERROR);
			} catch (IOException ioe) {
				FileLogger.write("Error dropping node, IO exception: " + ioe, FileLogger.MSG_TYPE_ERROR);
			}
		}
	}
}
