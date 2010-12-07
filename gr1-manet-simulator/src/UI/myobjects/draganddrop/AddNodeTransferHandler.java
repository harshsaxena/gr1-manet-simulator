/**  
 * ----------------------------------------------------------
 * This software is for educational purposes only.
 * The base of this software was created by IntelliJ IDEA.
 * Additions to the base have been made by the Hood College
 * Computer Science Department, Graduate Group 1.
 * ----------------------------------------------------------
 *
 * History:
 * @version: $Revision: 92 $
 * @date: $Date: 2010-11-07 01:55:48 -0500 (Sun, 07 Nov 2010) $
 * @author: $Author: jwoozeR $
 */

package UI.myobjects.draganddrop;

import java.awt.datatransfer.Transferable;

import javax.swing.JComponent;
import javax.swing.TransferHandler;

import logger.FileLogger;
import UI.myobjects.GraphicalNode;
import UI.myobjects.NodeButton;

@SuppressWarnings("serial")
public class AddNodeTransferHandler extends TransferHandler {

	// Transfers node data to the form or node properties
    protected Transferable createTransferable(JComponent component) {

        NodeButton source = (NodeButton)component;
        GraphicalNode nodeTransferable = new GraphicalNode(source.getIcon(),source.myForm,false);
        source.myForm.putGNode(nodeTransferable);
        nodeTransferable.setTransferHandler(new MoveNodeTransferHandler());
        
        FileLogger.write("Node button create transferable", FileLogger.MSG_TYPE_DEBUG);
        
        return nodeTransferable;
    }

    public int getSourceActions(JComponent component) {
        return COPY_OR_MOVE;
    }

}
