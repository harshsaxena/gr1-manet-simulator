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
import UI.myobjects.NodeButton;

public class MyButtonTransferHandler extends TransferHandler {


    protected Transferable createTransferable(JComponent c) {
        NodeButton source = (NodeButton)c;
        MyLogger.logger.debug("NodeButton createTranserable");
        GraphicalNode nodeTransferable = new GraphicalNode(source.getIcon(),source.myForm,false);
        source.myForm.putGNode(nodeTransferable);
        nodeTransferable.setTransferHandler(new MyNodeTransferHandler());
        return nodeTransferable;
    }

    public int getSourceActions(JComponent c) {
        return COPY_OR_MOVE;
    }

}
