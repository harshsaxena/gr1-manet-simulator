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
import UI.Myform;
import UI.myobjects.GraphicalNode;

public class SearchGNodeAction  implements ActionListener{
    Myform myForm;

    public SearchGNodeAction(Myform myForm) {
        this.myForm = myForm;
    }

    public void actionPerformed(ActionEvent e) {
        GraphicalNode dest = myForm.getGNode(
                myForm.getSearchText().getText().trim().toLowerCase());
        if (dest!=null){
            myForm.setSelectedGNode(dest);
        }else{
            JOptionPane.showMessageDialog(myForm,"Node not found!");
        }
    }
}
