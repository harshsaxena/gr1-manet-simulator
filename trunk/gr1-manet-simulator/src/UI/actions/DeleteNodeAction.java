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

package UI.actions;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JOptionPane;

import logger.FileLogger;
import simulator.Map_Manager;
import UI.Myform;

public class DeleteNodeAction implements ActionListener {
    Myform myForm;

    public DeleteNodeAction(Myform myForm) {
        this.myForm = myForm;
    }

    public void actionPerformed(ActionEvent e) {
        if (myForm.getSelectedGNode()!=null){
        	
            FileLogger.write("ACTION=DeleteNode_START", FileLogger.MSG_TYPE_REPLAY);
			FileLogger.write("\tNodeName=" + myForm.getSelectedGNode().getName(), FileLogger.MSG_TYPE_REPLAY);
			FileLogger.write("\tNodeIP=" + myForm.getSelectedGNode().getNode().getIP(), FileLogger.MSG_TYPE_REPLAY);
            FileLogger.write("ACTION=DeleteNode_END", FileLogger.MSG_TYPE_REPLAY);
        	
            myForm.getMyMap().remove(myForm.getSelectedGNode());
            myForm.getGraphicalNodes().remove(myForm.getSelectedGNode());
            Map_Manager.get_instance().getNode_list().remove(myForm.getSelectedGNode().getNode());
            myForm.setSelectedGNode(null);
            
        }else{
            JOptionPane.showMessageDialog(myForm,"Please Select a Node");
        }
    }
}
