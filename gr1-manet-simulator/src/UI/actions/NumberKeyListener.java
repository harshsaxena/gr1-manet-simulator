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

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class NumberKeyListener extends KeyAdapter {

	public void keyTyped(KeyEvent e) {
		if (e.getKeyChar() < 48 || e.getKeyChar() > 58) {
			e.consume();
			// e.setKeyCode(0);
		}
	}
}
