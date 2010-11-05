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

package simulator.noderelated.tasks;

import java.util.Date;
import java.util.Iterator;
import java.util.TimerTask;

import logger.ConsoleAndFileLogger;
import logger.StatusLogger;
import simulator.Node;
import simulator.noderelated.BroadCastField;

public class BroadCastTable_Expiry extends TimerTask {
	Node mynode;

	public BroadCastTable_Expiry(Node mynode) {
		super();
		this.mynode = mynode;
	}

	public void run() {
		for (Iterator itr = mynode.getBroadCastTable().iterator(); itr
				.hasNext();) {
			BroadCastField bcf = (BroadCastField) itr.next();
			if (bcf.getLifeTime() < new Date().getTime()) {
				ConsoleAndFileLogger.write("Node " + mynode + " : " + bcf + " Expires!", ConsoleAndFileLogger.MSG_TYPE_INFO);
				StatusLogger.get_instance().showNodeStatus(mynode, "Expire: " + bcf);
				itr.remove();
			}
		}
	}
}
