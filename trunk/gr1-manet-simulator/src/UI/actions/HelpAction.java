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

import java.awt.Desktop;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

import UI.Myform;

public class HelpAction implements ActionListener {
	public final static String helpDoc = "doc/MANET_Simulator_UserGuide.pdf";
	private Myform myForm;

	public HelpAction(Myform myForm) {
		this.setMyForm(myForm);
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {

		File helpFile = new File(helpDoc);
		helpFile = helpFile.getAbsoluteFile();
		// String helpPath = helpFile.getAbsolutePath();
		Desktop desktop = Desktop.getDesktop();
		try {
			desktop.open(helpFile);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public Myform getMyForm() {
		return myForm;
	}

	public void setMyForm(Myform myForm) {
		this.myForm = myForm;
	}

}
