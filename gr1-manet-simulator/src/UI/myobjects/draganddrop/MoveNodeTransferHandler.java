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

import UI.myobjects.GraphicalNode;

@SuppressWarnings("serial")
public class MoveNodeTransferHandler extends TransferHandler {
	
	protected Transferable createTransferable(JComponent component) {
		
		GraphicalNode source = (GraphicalNode) component;
		source.setShouldRemoved(true);
		
		return source;
	}

	public int getSourceActions(JComponent c) {
		return COPY_OR_MOVE;
	}

}
