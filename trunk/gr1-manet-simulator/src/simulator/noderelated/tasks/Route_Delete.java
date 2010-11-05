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
import logger.ConsoleLogger;
import logger.FileLogger;
import logger.StatusManager;
import simulator.Node;
import simulator.noderelated.Route;

public class Route_Delete extends TimerTask {
	Node mynode;

	public Route_Delete(Node mynode) {
		super();
		this.mynode = mynode;
	}

	public void run() {
		synchronized (mynode.getRout_Arr()) {
			for (Iterator itr = mynode.getRout_Arr().keySet().iterator(); itr
					.hasNext();) {
				Node node = (Node) itr.next();
				Route route = mynode.getRout_Arr().get(node);
				if (route.getLifeTime() + Node.DELETE_PERIOD
						* route.getIswaiting() < new Date().getTime()
						&& !mynode.equals(route.getDestination())) {
					ConsoleLogger.logger.info("Node " + mynode + " : " + route
							+ " Deleted!");
					FileLogger.write("Node " + mynode + " : " + route
							+ " Deleted!");
					StatusManager.get_instance().showNodeStatus(mynode,
							"Delete: " + route);
					itr.remove();
				}
			}
		}
	}
}
