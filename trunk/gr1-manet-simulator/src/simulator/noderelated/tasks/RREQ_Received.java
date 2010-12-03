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

import logger.FileLogger;
import simulator.Node;
import simulator.Packets.RREPPacket;
import simulator.Packets.RREQPacket;
import simulator.noderelated.Route;

public class RREQ_Received extends Thread {
	private Node mynode;
	private RREQPacket packet;
	private Node receivedFrom;

	public RREQ_Received(String name, Node mynode, RREQPacket rreqPacket,
			Node receivedFrom) {
		super(name);
		this.mynode = mynode;
		this.packet = rreqPacket;
		this.receivedFrom = receivedFrom;
		start();
	}

	public long calculateRouteLifeTime(Route existingRoute, Route newRoute) {
		long minimalLifeTime = new Date().getTime() + 2
				* Node.NET_TRAVERSAL_TIME - 2 * newRoute.getHop_count()
				* Node.NODE_TRAVERSAL_TIME;
		if (existingRoute != null) {
			return Math.max(minimalLifeTime, existingRoute.getLifeTime());
		} else {
			return minimalLifeTime;
		}
	}

	public void run() {
		// it first increments the hop count value in the RREQ by one, to
		// account for the new hop through the intermediate node.
		packet.hop_count++;
		// Then the node searches for a reverse route to the Originator IP
		// Address
		Route newroute = mynode.generateRouteFromRREQ(packet, receivedFrom);
		Route oldroute = mynode.search(newroute.getDestination());
		Route backwardRoute = oldroute;
		// If need be, the route is created, or updated using the Originator
		// Sequence Number from the
		// RREQ in its routing table.
		if (Route.isBad(oldroute)
				|| oldroute.getSeq_no() < newroute.getSeq_no()) {
			if (oldroute != null) {
				newroute.setPrecursor(oldroute.getPrecursor());
			}
			this.mynode.add_Route(newroute);
			backwardRoute = newroute;
			// (route.getSeq_no()==r.getSeq_no() &&
			// route.getHop_count()<r.getHop_count())){
		}
		// Whenever a RREQ message is received, the Lifetime of the reverse
		// route entry for the Originator IP address is set to be the maximum of
		// (ExistingLifetime, MinimalLifetime)
		newroute.setLifeTime(calculateRouteLifeTime(oldroute, newroute));

		Route forwardRoute = this.mynode.search(packet.dest);
		if (Route.isBad(forwardRoute)
				|| forwardRoute.getSeq_no() <= packet.seq_no) {// TODO check
			// this
			FileLogger.write("Node" + mynode.getIP().toString()
					+ ":received RREQPacket from " + packet.source
					+ " which handed from " + receivedFrom
					+ ": but it is not the destination",
					FileLogger.MSG_TYPE_INFO);
			if (packet.ttl > 1) {
				packet.ttl--; // TODO check hop limit
				// packet.hop_count++;
				if (forwardRoute != null) {
					packet.seq_no = Math.max(packet.seq_no, forwardRoute
							.getSeq_no());
					forwardRoute.setSeq_no(Math.max(packet.seq_no, forwardRoute
							.getSeq_no()));
				}
				this.mynode.send(packet);
			}
		} else {
			if (packet.dest.equals(mynode)) {
				FileLogger.write("Node" + mynode + ":received RREQPacket from "
						+ packet.source + " which handed from " + receivedFrom
						+ ": it is destination; generating RREPPacket",
						FileLogger.MSG_TYPE_INFO);

				if (packet.seq_no == mynode.getSeq_no() + 1) {
					mynode.increaseseq_no();
				}
				RREPPacket rrepPacket = new RREPPacket();
				rrepPacket.source = packet.source;
				rrepPacket.dest = packet.dest;
				rrepPacket.seq_no = mynode.getSeq_no();
				rrepPacket.hop_count = 0;
				rrepPacket.setLifeTime(Node.MY_ROUTE_TIMEOUT);

				mynode.send(rrepPacket, receivedFrom);

			} else {
				if (packet.D) {
					mynode.send(packet, packet.dest);
					return;
				}

				FileLogger.write("Node " + mynode
						+ " : received RREQPacket from " + packet.source
						+ " which handed from " + receivedFrom
						+ ": I have Route; generating RREPPacket",
						FileLogger.MSG_TYPE_INFO);

				RREPPacket rrepPacket = new RREPPacket();
				rrepPacket.source = packet.source;
				rrepPacket.dest = packet.dest;
				rrepPacket.seq_no = forwardRoute.getSeq_no();
				rrepPacket.hop_count = forwardRoute.getHop_count();
				long currentTime = new Date().getTime();
				rrepPacket.setLifeTime(currentTime
						- (forwardRoute.getLifeTime() - currentTime));
				forwardRoute.getPrecursor().add(receivedFrom);
				backwardRoute.getPrecursor().add(forwardRoute.getNext_hop());
				mynode.send(rrepPacket, packet.source);

				if (packet.G) {
					RREPPacket rrepg = (RREPPacket) rrepPacket.copy_packet();
					rrepg.hop_count = backwardRoute.getHop_count();
					rrepg.dest = packet.source;
					rrepg.seq_no = packet.getSourceSeq_no();
					rrepg.source = packet.dest;
					rrepg.setLifeTime(backwardRoute.getLifeTime()
							- new Date().getTime());
					mynode.send(rrepPacket, packet.dest);
				}
			}
		}
	}
}
