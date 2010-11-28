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
import java.util.HashSet;

import logger.FileLogger;
import simulator.Node;
import simulator.Packets.RREPPacket;
import simulator.noderelated.RREPPacketWrapper;
import simulator.noderelated.Route;

public class RREP_Received extends Thread {
	private Node mynode;
	private RREPPacketWrapper packetWrapper;

	public RREP_Received(String name, Node mynode,
			RREPPacketWrapper packetWrapper) {
		super(name);
		this.mynode = mynode;
		this.packetWrapper = packetWrapper;
		start();
	}

	public void run() {
		RREPPacket rrepPacket = packetWrapper.getRrepPacket();
		// it searches for a route to the previous hop.
		Route prevroute = this.mynode.search(packetWrapper.getReceivedFrom());
		if (Route.isBad(prevroute)) {
			// If needed, a route is created for the previous hop, but without a
			// valid sequence number
			prevroute = new Route(packetWrapper.getReceivedFrom(),
					packetWrapper.getReceivedFrom(), -1, 1, new HashSet<Node>());
			prevroute.setLifeTime(new Date().getTime()
					+ Node.DEFAULT_ROUTE_LIFETIME);
			this.mynode.add_Route(prevroute);
		}
		// the node then increments the hop countvalue in the RREP by one, to
		// account for the new hop through the
		// intermediate node.
		rrepPacket.hop_count++;
		// Then the forward route for this destination is created if it does not
		// already exist.
		Route forwardRoute = this.mynode.search(rrepPacket.dest);

		FileLogger.write(
				"Node: " + mynode + " forwardRoute is " + forwardRoute,
				FileLogger.MSG_TYPE_INFO);
		boolean routeAddedorUpdated = false;
		if (forwardRoute == null) {
			forwardRoute = mynode.generateRouteFromRREP(packetWrapper);
			this.mynode.add_Route(forwardRoute);
			routeAddedorUpdated = true;
		}// Otherwise, the node compares the Destination Sequence
		// Number in the message with its own stored destination sequence number
		// for the Destination IP Address in the RREP message.
		else {

			FileLogger.write("Node: " + mynode + " :"
					+ forwardRoute.getSeq_no() + "," + rrepPacket.seq_no + ","
					+ forwardRoute.isInvalid() + ","
					+ (rrepPacket.hop_count < forwardRoute.getHop_count()),
					FileLogger.MSG_TYPE_INFO);

			if (forwardRoute.getSeq_no() < 0 ||
			// the sequence number in the routing table is marked as invalid in
					// route table entry.
					rrepPacket.seq_no > forwardRoute.getSeq_no() ||
					// the Destination Sequence Number in the RREP is greater
					// than
					// the node's copy of the destination sequence number and
					// the known value is valid
					(rrepPacket.seq_no == forwardRoute.getSeq_no() && forwardRoute
							.isInvalid()) ||
					// the sequence numbers are the same, but the route is is
					// marked as inactive,
					(rrepPacket.seq_no == forwardRoute.getSeq_no() && rrepPacket.hop_count < forwardRoute
							.getHop_count())
			// the sequence numbers are the same, and the New Hop Count is
			// smaller than the hop count in route table entry.
			) {
				FileLogger.write("Node: " + mynode + " :one if is true",
						FileLogger.MSG_TYPE_INFO);
				forwardRoute.setSeq_no(rrepPacket.seq_no);
				routeAddedorUpdated = true;
			}
		}
		if (routeAddedorUpdated) {
			// the route is marked as active,
			forwardRoute.setInvalid(false);
			// the next hop in the route entry is assigned to be the node from
			// which the RREP is received, which is indicated by the source IP
			// address field in the IP header,
			forwardRoute.setNext_hop(packetWrapper.getReceivedFrom());
			// the hop count is set to the value of the New Hop Count,
			forwardRoute.setHop_count(rrepPacket.hop_count);
			// the expiry time is set to the current time plus the value of the
			// Lifetime in the RREP message,
			forwardRoute.setLifeTime(new Date().getTime()
					+ rrepPacket.getLifeTime());
			// the destination sequence number is marked as valid,
			// and the destination sequence number is the Destination Sequence
			// Number in the RREP message
			forwardRoute.setSeq_no(rrepPacket.seq_no);
		}
		// if this node is source

		if (mynode.equals(rrepPacket.source)) {
			// if (mynode.getDiscoveryiswaiting()!=null){ //if this node is in
			// discovery method wake it
			FileLogger.write("Node " + mynode.getIP().toString()
					+ " : received RREPPacket from "
					+ packetWrapper.getRrepPacket().source
					+ " which handded from " + packetWrapper.getReceivedFrom()
					+ ": It's the destination!,I was waiting for it",
					FileLogger.MSG_TYPE_INFO);
			mynode.setRrepPacketWrapper(this.packetWrapper);

			// this was added to get rid of a null pointer exception
			if (mynode != null && mynode.getDiscoveryiswaiting() != null) {
				synchronized (mynode.getDiscoveryiswaiting()) {
					mynode.getDiscoveryiswaiting().notify();
				}
			}
			return;

		}

		// if it is not the source
		if (routeAddedorUpdated) {
			Route backRoute = this.mynode.search(rrepPacket.source);
			if (!Route.isBad(backRoute)) {
				FileLogger.write("Node" + mynode + ": Passing RREPPacket from "
						+ rrepPacket.dest + " which handded from "
						+ packetWrapper.getReceivedFrom() + " to "
						+ backRoute.getNext_hop(), FileLogger.MSG_TYPE_INFO);
				forwardRoute.getPrecursor().add(backRoute.getNext_hop());
				backRoute.setLifeTime(Math.max(backRoute.getLifeTime(),
						new Date().getTime() + Node.ACTIVE_ROUTE_TIMEOUT));
				backRoute.getPrecursor().add(forwardRoute.getNext_hop());
				this.mynode.send(rrepPacket, backRoute.getNext_hop());
			} else {
				FileLogger.write("Node" + mynode
						+ ": receiving RREPPacket from " + rrepPacket.dest
						+ " which handded from "
						+ packetWrapper.getReceivedFrom()
						+ " but route is expired", FileLogger.MSG_TYPE_INFO);
			}

		}
	}
}