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

import logger.MyLogger;
import UI.myobjects.GraphicalNode;

public class MyNodeTransferHandler extends TransferHandler {
	protected Transferable createTransferable(JComponent c) {
		GraphicalNode source = (GraphicalNode) c;
		MyLogger.logger.debug("NodeButton createTranserable");
		source.setShouldRemoved(true);
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
