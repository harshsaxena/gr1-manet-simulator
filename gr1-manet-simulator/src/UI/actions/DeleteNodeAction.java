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
import simulator.Map_Manager;
import UI.Myform;

public class DeleteNodeAction implements ActionListener {
    Myform myForm;

    public DeleteNodeAction(Myform myForm) {
        this.myForm = myForm;
    }

    /**
     *
     * @param e
     */
    public void actionPerformed(ActionEvent e) {
        if (myForm.getSelectedGNode()!=null){
            myForm.getMyMap().remove(myForm.getSelectedGNode());
            myForm.getGraphicalNodes().remove(myForm.getSelectedGNode());
            Map_Manager.get_instance().getNode_list().remove(myForm.getSelectedGNode().getNode());
            myForm.setSelectedGNode(null);
        }else{
            JOptionPane.showMessageDialog(myForm,"Please Select a Node");
        }
    }
}
