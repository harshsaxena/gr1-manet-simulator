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
import UI.Myform;

public class ResetAction implements ActionListener {
    Myform myForm;

    public ResetAction(Myform myForm) {
        this.myForm = myForm;
    }

    public void actionPerformed(ActionEvent e) {
        if (myForm.getSelectedGNode()!=null){
            myForm.getSelectedGNode().fillNodePanel();
        }else{
            myForm.getNodePropertiesPanel().resetNodeProperties();
        }
    }
}
