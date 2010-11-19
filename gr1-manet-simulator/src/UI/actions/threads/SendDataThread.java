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

package UI.actions.threads;

import simulator.Data;
import simulator.Node;

public class SendDataThread extends Thread {
	Node source, dest;
	Data data;

	public SendDataThread(Node source, Node dest, Data data) {
		super("Swing send data from : " + source.getIP() + " to "
				+ dest.getIP());
		this.source = source;
		this.dest = dest;
		this.data = data;
		start();
	}

	public void run() {
		source.send_Data(data, dest);
	}
}
