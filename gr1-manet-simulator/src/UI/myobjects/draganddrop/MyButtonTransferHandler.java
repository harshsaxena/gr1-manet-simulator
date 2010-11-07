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
import UI.myobjects.NodeButton;

@SuppressWarnings("serial")
public class MyButtonTransferHandler extends TransferHandler {

    protected Transferable createTransferable(JComponent c) {
    	
        NodeButton source = (NodeButton)c;
        GraphicalNode nodeTransferable = new GraphicalNode(source.getIcon(),source.myForm,false);
        source.myForm.putGNode(nodeTransferable);
        nodeTransferable.setTransferHandler(new MyNodeTransferHandler());
        
        FileLogger.write("Node button create transferable", FileLogger.MSG_TYPE_DEBUG);
        
        return nodeTransferable;
    }

    public int getSourceActions(JComponent c) {
        return COPY_OR_MOVE;
    }

}
