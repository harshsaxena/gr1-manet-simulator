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

import java.awt.datatransfer.Transferable;

import javax.swing.JComponent;
import javax.swing.TransferHandler;

import logger.FileLogger;
import UI.myobjects.GraphicalNode;

@SuppressWarnings("serial")
public class MyNodeTransferHandler extends TransferHandler {
	protected Transferable createTransferable(JComponent c) {
		
		GraphicalNode source = (GraphicalNode) c;
		source.setShouldRemoved(true);
		
		FileLogger.write("Node button create transerable", FileLogger.MSG_TYPE_DEBUG);
		
		return source;
	}

	public int getSourceActions(JComponent c) {
		return COPY_OR_MOVE;
	}

	protected void exportDone(JComponent c, Transferable data, int action) {
		// if (shouldRemove && (action == MOVE)) {
		// sourcePic.setImage(null);
		// }
		// sourcePic = null;
	}
}
