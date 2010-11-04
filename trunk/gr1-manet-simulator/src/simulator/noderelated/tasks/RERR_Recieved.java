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
import simulator.Node;
import simulator.Packets.RERRPacket;
import simulator.noderelated.Route;

public class RERR_Recieved extends Thread {
	Node mynode;
	private RERRPacket packet;
	private Node recievedFrom;

	public RERR_Recieved(String name, Node mynode, RERRPacket packet,
			Node recievedFrom) {
		super(name);
		this.mynode = mynode;
		this.packet = packet;
		this.recievedFrom = recievedFrom;
		start();
	}

	public void run() {
		for (Node lostNode : packet.getLost_nodes().keySet()) {
			Route lostRoute = mynode.search(lostNode);

			if (lostRoute != null
					&& lostRoute.getNext_hop().equals(recievedFrom)) {
				lostRoute.setInvalid(true);
				lostRoute
						.setLifeTime(new Date().getTime() + Node.DELETE_PERIOD);
				lostRoute.setSeq_no(packet.getLost_nodes().get(lostNode));
				lostRoute.setHop_count(Route.INFINITE);
				for (Node precursorNode : lostRoute.getPrecursor()) {
					mynode.send(packet, precursorNode);
				}
			}
		}
	}
}
