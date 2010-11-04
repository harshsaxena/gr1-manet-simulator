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

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import UI.Myform;

public class PanelAction extends MouseAdapter {
    Myform myForm;

    public PanelAction(Myform myForm) {
        this.myForm = myForm;
    }
    public void mouseClicked(MouseEvent e) {
        myForm.setSelectedGNode(null);
        myForm.getNodePanel().resetNodePanel();
    }
}
