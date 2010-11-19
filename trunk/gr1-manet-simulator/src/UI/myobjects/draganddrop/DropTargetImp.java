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
 * A class that implements action of coping or moving {@link GraphicalNode} on
 * target place
 */
@SuppressWarnings("serial")
public class DropTargetImp extends DropTarget {
	private JComponent c;

	public DropTargetImp(JComponent c) {
		this.c = c;
	}

	public void drop(DropTargetDropEvent dtde) {

		FileLogger.write("Map Panel importing data.", FileLogger.MSG_TYPE_INFO);

		if (dtde.isDataFlavorSupported(GraphicalNode.dataFlavor)) {
			dtde.acceptDrop(dtde.getDropAction());
			// JPanel panel = (JPanel) this.c;
			JLayeredPane panel = (JLayeredPane) this.c;
			Transferable t = dtde.getTransferable();
			try {
				GraphicalNode node = (GraphicalNode) t
						.getTransferData(GraphicalNode.dataFlavor);
				Dimension size = node.getPreferredSize();
				if (!node.isShouldRemoved()) {
					panel.add(node);
				}
				node.setSelectGNode();
				node.setBounds(dtde.getLocation().x, dtde.getLocation().y,
						size.width, size.height);
				node.fillNodePanel();
				panel.invalidate();
				node.myForm.refreshPowerShower();

				FileLogger.write("Map Panel after invalidate.",
						FileLogger.MSG_TYPE_DEBUG);

				dtde.dropComplete(true);
			} catch (UnsupportedFlavorException ufe) {
				FileLogger.write("importData: unsupported data flavor",
						FileLogger.MSG_TYPE_ERROR);
			} catch (IOException ioe) {
				FileLogger.write("importData: I/O exception",
						FileLogger.MSG_TYPE_ERROR);
			}
		}
	}
}
