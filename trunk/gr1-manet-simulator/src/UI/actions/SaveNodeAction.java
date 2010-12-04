/**  
 * ----------------------------------------------------------
 * This software is for educational purposes only.
 * The base of this software was created by IntelliJ IDEA.
 * Additions to the base have been made by the Hood College
 * Computer Science Department, Graduate Group 1.
 * ----------------------------------------------------------
 *
 * History:
 * @version: $Revision: 151 $
 * @date: $Date: 2010-11-28 16:08:50 -0500 (Sun, 28 Nov 2010) $
 * @author: $Author: quiksillvr $
 */

package UI.actions;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JOptionPane;
import UI.Myform;
import UI.NodeProperties;
import UI.myobjects.GraphicalNode;

public class SaveNodeAction implements ActionListener {
    Myform myForm;

    public SaveNodeAction(Myform myForm) {
        this.myForm = myForm;
    }

    /**
     * it should load information from right pane (Node Panel Properties) and set selected graphical node properties
     * @param e
     */
    public void actionPerformed(ActionEvent e) {
        if (myForm.getSelectedGNode()!=null){
            GraphicalNode gNode = myForm.getSelectedGNode();
            NodeProperties np = myForm.getNodePanel();
            gNode.setName(np.nameText.getText().trim().toLowerCase());
            gNode.setNodeIP(np.ipText.getText());
            gNode.setScaledCoordinates(Integer.parseInt(np.xCordText.getText()), Integer.parseInt(np.yCordText.getText()));
            gNode.setColor(np.colorBtn.getBackground());
            gNode.setNodePower(Integer.parseInt(np.powerText.getText()));
            if (np.nameText.getText().trim().length()>0){
                np.nameText.setEnabled(false);
            }
            myForm.refreshPowerShower();

        }else{
            JOptionPane.showMessageDialog(myForm,"Please Select a Node");
        }
    }
}
