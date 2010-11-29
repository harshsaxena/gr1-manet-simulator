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

/**
 * 
 */
package UI.actions;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;

import replay.ReplayFileParser;

import logger.FileLogger;

import UI.Myform;

/**
 * @author mroberts
 *
 */
public class NodePropReplayBtnAction implements ActionListener {
	private Myform myForm;

	public NodePropReplayBtnAction(Myform myForm) {
		this.myForm = myForm;
	}
	
	public void actionPerformed(ActionEvent e) {
		FileLogger.write("*****Replay_Initiated*****", FileLogger.MSG_TYPE_REPLAY);
		
		// Parse log for node properties
		try {
			ReplayFileParser.readLineByLine();
		} catch (FileNotFoundException e1) {
			e1.printStackTrace();
		}
	}
	
}
