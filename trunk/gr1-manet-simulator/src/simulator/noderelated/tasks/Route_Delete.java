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
import simulator.noderelated.Route;

public class Route_Delete extends TimerTask {
	private Node mynode;

	public Route_Delete(Node mynode) {
		super();
		this.mynode = mynode;
	}

	public void run() {
		synchronized (mynode.getRout_Arr()) {
			for (Iterator<Node> itr = mynode.getRout_Arr().keySet().iterator(); itr
					.hasNext();) {
				Node node = (Node) itr.next();
				Route route = mynode.getRout_Arr().get(node);
				if (route.getLifeTime() + Node.DELETE_PERIOD
						* route.getIswaiting() < new Date().getTime()
						&& !mynode.equals(route.getDestination())) {
					FileLogger.write("Node " + mynode + " : " + route
							+ " Deleted!", FileLogger.MSG_TYPE_INFO);
					OutputLogger.get_instance().showNodeStatus(mynode,
							"Delete: " + route);
					itr.remove();
				}
			}
		}
	}
}
