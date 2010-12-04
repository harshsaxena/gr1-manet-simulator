/**  
 * ----------------------------------------------------------
 * This software is for educational purposes only.
 * The base of this software was created by IntelliJ IDEA.
 * Additions to the base have been made by the Hood College
 * Computer Science Department, Graduate Group 1.
 * ----------------------------------------------------------
 *
 * History:
 * @version: $Revision: 97 $
 * @date: $Date: 2010-11-08 16:58:38 -0500 (Mon, 08 Nov 2010) $
 * @author: $Author: quiksillvr $
 */

package UI.actions;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JOptionPane;
import UI.Myform;
import UI.myobjects.GraphicalNode;

public class SearchNodeAction  implements ActionListener{
    Myform myForm;

    public SearchNodeAction(Myform myForm) {
        this.myForm = myForm;
    }

    public void actionPerformed(ActionEvent e) {
        //GraphicalNode dest = myForm.getGNode(myForm.getSearchText().getText().trim().toLowerCase());
        GraphicalNode dest = myForm.getGNode(myForm.getNodePanel().searchNodeText.getText().trim().toLowerCase());
        if (dest!=null){
            myForm.setSelectedGNode(dest);
        }else{
            JOptionPane.showMessageDialog(myForm,"Node not found!");
        }
    }
}
