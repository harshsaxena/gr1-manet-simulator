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

import simulator.noderelated.Coordinates;

import logger.FileLogger;
import UI.Myform;
import UI.NodeProperties;
import UI.myobjects.GraphicalNode;

public class SaveNodeAction implements ActionListener {
    Myform myForm;

    public SaveNodeAction(Myform myForm) {
        this.myForm = myForm;
    }

    /**
     * Saves/Updates node properties entered from the properties section.
     * @param e
     */
    public void actionPerformed(ActionEvent e) {
    	
        if (myForm.getSelectedGNode()!=null){
            
        	GraphicalNode gNode = myForm.getSelectedGNode();
            NodeProperties np = myForm.getNodePropertiesPanel();
            gNode.setName(np.nameText.getText().trim().toLowerCase());
            gNode.setNodeIP(np.ipText.getText());
            Coordinates coords = new Coordinates((Integer)np.getxCoordSpinner().getValue(), (Integer)np.getyCoordSpinner().getValue());
            gNode.getNode().setNode_coordinates(coords);
            gNode.setScaledCoordinates((Integer)np.getxCoordSpinner().getValue(), (Integer)np.getyCoordSpinner().getValue());
            //gNode.setNodePower(Integer.parseInt(np.powerText.getText()));
            gNode.setNodePower((Integer)np.getPwrSpinner().getValue());
            
            if (np.nameText.getText().trim().length()>0){
                np.nameText.setEnabled(false);
            }
            
            myForm.refreshPowerShower();
            
            FileLogger.write("ACTION=UpdateNodeProps_START", FileLogger.MSG_TYPE_REPLAY);
            FileLogger.write("\tUpdateNodeProps_NodeName=" + np.nameText.getText().trim().toLowerCase(), FileLogger.MSG_TYPE_REPLAY);
			FileLogger.write("\tUpdateNodeProps_NodeIP=" + np.ipText.getText(), FileLogger.MSG_TYPE_REPLAY);
			FileLogger.write("\tUpdateNodeProps_NodeXCoord=" + np.getxCoordSpinner().getValue().toString(), FileLogger.MSG_TYPE_REPLAY);
			FileLogger.write("\tUpdateNodeProps_NodeYCoord=" + np.getyCoordSpinner().getValue().toString(), FileLogger.MSG_TYPE_REPLAY);
			FileLogger.write("\tUpdateNodeProps_NodePower=" + np.getPwrSpinner().getValue().toString(), FileLogger.MSG_TYPE_REPLAY);
            FileLogger.write("ACTION=UpdateNodeProps_END", FileLogger.MSG_TYPE_REPLAY);

        }else{
            JOptionPane.showMessageDialog(myForm,"Please Select a Node");
        }
    }
}
