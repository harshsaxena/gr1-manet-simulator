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

import logger.FileLogger;
import logger.OutputLogger;
import simulator.Node;
import simulator.noderelated.BroadCastField;

public class BroadCastTable_Expiry extends TimerTask {
	private Node mynode;

	public BroadCastTable_Expiry(Node mynode) {
		super();
		this.mynode = mynode;
	}

	public void run() {
		for (Iterator<BroadCastField> itr = mynode.getBroadCastTable()
				.iterator(); itr.hasNext();) {
			BroadCastField bcf = (BroadCastField) itr.next();
			if (bcf.getLifeTime() < new Date().getTime()) {
				FileLogger.write("Node " + mynode + " : " + bcf + " Expires!",
						FileLogger.MSG_TYPE_INFO);
				OutputLogger.get_instance().showNodeStatus(mynode,
						"Broadcast expired: " + bcf);
				itr.remove();
			}
		}
	}
}
