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

package simulator;

import java.io.Serializable;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.Timer;

import logger.FileLogger;
import logger.OutputLogger;
import simulator.Packets.DataPacket;
import simulator.Packets.Packet;
import simulator.Packets.RERRPacket;
import simulator.Packets.RREQPacket;
import simulator.noderelated.BroadCastField;
import simulator.noderelated.Coordinates;
import simulator.noderelated.IPAddress;
import simulator.noderelated.RREPPacketWrapper;
import simulator.noderelated.Route;
import simulator.noderelated.tasks.BroadCastTable_Expiry;
import simulator.noderelated.tasks.Route_Delete;
import simulator.noderelated.tasks.Route_Expiry;

@SuppressWarnings("serial")
public class Node implements Serializable {

	public final static int ACTIVE_ROUTE_TIMEOUT = 18000;
	public final static int ALLOWED_HELLO_LOSS = 2;
	public final static int C = 3;
	public final static int DEFAULT_ROUTE_LIFETIME = 36000;
	public final static int DELETE_PERIOD = 270000;
	public final static int HELLO_INTERVAL = 3000;
	public final static long helloTime = 0;
	public final static long LOOPBACK_EXPIRETIME = 300000;
	public final static int MY_ROUTE_TIMEOUT = 36000;
	public final static int NET_DIAMETER = 35;
	public final static int NET_TRAVERSAL_TIME = 21000;
	public final static int NODE_TRAVERSAL_TIME = 300;
	public final static int PATH_DISCOVERY_INTERVAL = 1200;
	public final static int PATH_DISCOVERY_TIME = 600;
	public final static int ROUTE_DELETE_INTERVAL = 540000;
	public final static int ROUTE_EXPIRE_INTERVAL = 36000;
	public final static int RREP_ACK_REQUIRED = 0;
	public final static int RREQ_RETRIES = 2;
	public final static int TTL_INCREMENT = 2;
	public final static int TTL_START = 20;
	public final static int TTL_THRESHOLD = 7;

	private Set<BroadCastField> broadCastTable = new HashSet<BroadCastField>();
	private Object discoveryiswaiting;
	private IPAddress IP;
	private Coordinates node_coordinates = new Coordinates();
	private int power = 0;
	private Map<Node, Route> Rout_Arr = new HashMap<Node, Route>();
	private RREPPacketWrapper rrepPacketWrapper;
	public int RREQ_ID = 0;
	public int SEQ_NO = 0;

	private Node() {
	}

	public static Node getInstance(IPAddress IP) {
		Node node = new Node();
		node.IP = IP;
		init(node);
		Map_Manager.get_instance().addNode(node);
		return node;
	}

	/**
	 * Default node constructor which initializes this object other constructors
	 * should call this method this is for when node receives RREQPacket can
	 * understand that have a route to itself.
	 */
	private static void init(Node node) {
		Route r = new Route(node, node, '1', '0', new HashSet<Node>());
		r.setLifeTime(LOOPBACK_EXPIRETIME);
		node.Rout_Arr.put(node, r);

		new Timer(node + " expriy timer", true).schedule(
				new Route_Expiry(node), Node.ROUTE_EXPIRE_INTERVAL,
				Node.ROUTE_EXPIRE_INTERVAL);

		new Timer(node + " broadcasttable expriy timer", true).schedule(
				new BroadCastTable_Expiry(node), 0,
				Node.PATH_DISCOVERY_INTERVAL);

		new Timer(node + " delete timer", true).schedule(
				new Route_Delete(node), Node.ROUTE_DELETE_INTERVAL,
				Node.ROUTE_DELETE_INTERVAL);
	}

	/**
	 * Adds a route in the table and runs until the expiration time.
	 * 
	 * @param route
	 *            the route that should be added to table
	 */

	public void add_Route(Route route) {
		Rout_Arr.put(route.getDestination(), route);
		FileLogger.write("Node " + this + ": new route to "
				+ route.getDestination() + " through " + route.getNext_hop()
				+ " added", FileLogger.MSG_TYPE_INFO);
		OutputLogger.get_instance().showNodeStatus(this,
				"Attempting new route to " + route.getDestination());
	}

	/**
	 * Checks broadcasted packet tables
	 * 
	 * @param source
	 *            {@link Node} that generates broadcast packet
	 * @param RREQPacket
	 * @return true: if this packet has not been broadcasted yet false: if this
	 *         packet has been broadcasted
	 */
	public boolean checkAndUpdateBroadCastTable(Node source,
			RREQPacket RREQPacket) {
		BroadCastField bfc = null;
		for (BroadCastField broadCastField : broadCastTable) {
			if (broadCastField.getSource().equals(source)) {
				bfc = broadCastField;
				break;
			}
		}
		if (bfc == null) {
			broadCastTable.add(new BroadCastField(source, RREQPacket
					.getRREQ_ID()));
			return true;
		}
		if (bfc.getRREQ_ID() < RREQPacket.getRREQ_ID()) {
			bfc.setRREQ_ID(RREQPacket.getRREQ_ID());
			return true;
		}

		return false;
	}

	/**
	 * Deletes a route from route table
	 * 
	 * @param route
	 *            the route that should be deleted from route table
	 */
	public void del_Route(Route route) {
		Rout_Arr.remove(route.getDestination());
		FileLogger.write("Node: " + this + " : " + route + " Deleted!",
				FileLogger.MSG_TYPE_INFO);
		OutputLogger.get_instance().showNodeStatus(this, "Delete: " + route);
	}

	/**
	 * Discovers a route for sending data it attempts three time note that it is
	 * not thread safe because of rrepPacket field?????????
	 * 
	 * @param dest
	 *            the Destination Node
	 * @return the Route if it have been find </br> null if there isn't any
	 *         route
	 */
	public Route discovery(Node dest) {
		FileLogger.write("Node " + IP.toString() + ": discovery initiated to "
				+ dest, FileLogger.MSG_TYPE_INFO);
		int retry = 0;
		int ttl;
		Route route = search(dest);

		if (!Route.isBad(route)) {// if there is a route and it was not expired
			return route;
		}
		// if there isn't any route or it was expired create rreqpacket
		RREQPacket rreqPacket = new RREQPacket();
		rreqPacket.dest = dest;
		rreqPacket.source = this;

		// The Destination Sequence Number field in the RREQ message is the last
		// known destination sequence number for this destination and is copied
		// from the Destination Sequence Number field in the routing table
		if (route != null) {
			rreqPacket.seq_no = route.getSeq_no();
			route.setIswaiting();
		} else {
			rreqPacket.U = true;
		}

		// The Hop Count field is set to zero.
		rreqPacket.hop_count = 0;

		// The Originator Sequence Number in the RREQ message is the
		// node's own sequence number, which is incremented prior to insertion
		// in a RREQ.
		SEQ_NO++;
		rreqPacket.setSourceSeq_no(this.SEQ_NO);

		if (!Route.isBad(route)) {
			ttl = route.getHop_count() + Node.TTL_INCREMENT;
		} else {
			ttl = TTL_START;
		}

		discoveryiswaiting = new Object(); // note RREP Received that it is in
		// discovery

		rrepPacketWrapper = null;

		// To reduce congestion in a network, repeated attempts by a source node
		// at route discovery for a single destination MUST utilize a binary
		// exponential back off.
		for (long timeOut = Node.NET_TRAVERSAL_TIME; retry < RREQ_RETRIES; timeOut *= 2, retry++) {
			// Each new attempt MUST increment and update the RREQ ID.
			rreqPacket.setRREQ_ID(this.RREQ_ID++);
			rreqPacket.ttl = ttl;
			// Before broadcasting the RREQ, the originating node buffers the
			// RREQ
			// ID and the Originator IP address (its own address) of the RREQ
			// for PATH_DISCOVERY_TIME
			this.broadCastTable.add(new BroadCastField(this, rreqPacket
					.getRREQ_ID()));
			send(rreqPacket); // sending
			try {
				synchronized (discoveryiswaiting) {
					discoveryiswaiting.wait(timeOut);// waits for RREP
				}
			} catch (InterruptedException e) {
				FileLogger.write("Node " + IP.toString()
						+ ": discovery first wating for " + dest
						+ ": ERROR OCCURED!", FileLogger.MSG_TYPE_INFO);
				e.printStackTrace();
			}
			if (rrepPacketWrapper != null) {
				// packet received
				RREPPacketWrapper temprrepPacketWrapper = rrepPacketWrapper;// creates
				rrepPacketWrapper = null; // resets it for next try
				discoveryiswaiting = null; // we are not in discovery any more
				FileLogger.write("Node " + IP.toString()
						+ ": first discovery for " + dest + " : successful",
						FileLogger.MSG_TYPE_INFO);
				Route foundRoute = generateRouteFromRREP(temprrepPacketWrapper);
				foundRoute.resetIswaiting();
				return foundRoute;
			}
			// If a route is not received within NET_TRAVERSAL_TIME milliseconds
			if (ttl >= TTL_THRESHOLD) {
				ttl = NET_DIAMETER;
			} else {
				ttl += Node.TTL_INCREMENT;
			}
			// if wait got out after the time == rreppacket not received
			// sending second
			FileLogger.write("Node " + this + ": discovery number " + retry
					+ " for " + dest + " : failed", FileLogger.MSG_TYPE_INFO);
			// the node MAY try again to discover a route by broadcasting
			// another RREQ, up to a maximum of RREQ_RETRIES
		}

		FileLogger.write("Node " + this + ": discovery for " + dest
				+ " : failed", FileLogger.MSG_TYPE_INFO);

		if (route != null) {
			route.resetIswaiting();
		}
		return null; // route not found
	}

	public boolean equals(Object obj) {

		return IP.equals(((Node) obj).IP); // To change body of overridden
	}

	/**
	 * generates a route from RREPPacket that received
	 * 
	 * @param rrepPacketWrapper
	 *            contains RREPPacket and the last node that passed it
	 * @return the {@link Route} object
	 */
	public Route generateRouteFromRREP(RREPPacketWrapper rrepPacketWrapper) {
		Route route = new Route();
		route.setDestination(rrepPacketWrapper.getRrepPacket().dest);
		route.setHop_count(rrepPacketWrapper.getRrepPacket().hop_count);
		route.setNext_hop(rrepPacketWrapper.getReceivedFrom());
		route.setSeq_no(rrepPacketWrapper.getRrepPacket().seq_no);
		return route;
	}

	/**
	 * generates a route from RREQPacket that received
	 * 
	 * @param rreqPacket
	 * @param receivedFrom
	 *            the last node that passed it to this node
	 * @return the {@link Route} object
	 */
	public Route generateRouteFromRREQ(RREQPacket rreqPacket, Node receivedFrom) {
		Route route = new Route();
		route.setDestination(rreqPacket.source);
		route.setHop_count(rreqPacket.hop_count);
		route.setNext_hop(receivedFrom);
		route.setSeq_no(rreqPacket.getSourceSeq_no());
		return route;
	}

	public Route generateRouteFromRREQtoprevHop(RREQPacket rreqPacket,
			Node receivedFrom) {
		Route route = new Route();
		route.setDestination(receivedFrom);
		route.setHop_count(rreqPacket.hop_count);
		route.setNext_hop(receivedFrom);
		route.setSeq_no(-1);
		route.setLifeTime(new Date().getTime() + Node.DEFAULT_ROUTE_LIFETIME);
		return route;
	}

	public int getActiveRouteTimeout() {
		return ACTIVE_ROUTE_TIMEOUT;
	}

	public int getAllowedHelloLoss() {
		return ALLOWED_HELLO_LOSS;
	}

	public Set<BroadCastField> getBroadCastTable() {
		return broadCastTable;
	}

	public int getDefaultRouteLifetime() {
		return DEFAULT_ROUTE_LIFETIME;
	}

	public int getDeletePeriod() {
		return DELETE_PERIOD;
	}

	public Object getDiscoveryiswaiting() {
		return discoveryiswaiting;
	}

	public int getHelloInterval() {
		return HELLO_INTERVAL;
	}

	public long getHelloTime() {
		return helloTime;
	}

	public IPAddress getIP() {
		return IP;
	}

	public long getLoopbackExpireTime() {
		return LOOPBACK_EXPIRETIME;
	}

	public int getMyRouteTimeout() {
		return MY_ROUTE_TIMEOUT;
	}

	public int getNetDiameter() {
		return NET_DIAMETER;
	}

	public int getNetTraversalTime() {
		return NET_TRAVERSAL_TIME;
	}

	public Coordinates getNode_coordinates() {
		return node_coordinates;
	}

	public int getNodeTraversalTime() {
		return NODE_TRAVERSAL_TIME;
	}

	public int getPathDiscoveryInterval() {
		return PATH_DISCOVERY_INTERVAL;
	}

	public int getPathDiscoveryTime() {
		return PATH_DISCOVERY_TIME;
	}

	public int getPower() {
		return power;
	}

	public int getRepAckRequired() {
		return RREP_ACK_REQUIRED;
	}

	public int getReqRetries() {
		return RREQ_RETRIES;
	}

	public Map<Node, Route> getRout_Arr() {
		return Rout_Arr;
	}

	public int getRouteDeleteInterval() {
		return ROUTE_DELETE_INTERVAL;
	}

	public int getRouteExpireInterval() {
		return ROUTE_EXPIRE_INTERVAL;
	}

	public RREPPacketWrapper getRrepPacketWrapper() {
		return rrepPacketWrapper;
	}

	public int getSeq_no() {
		return SEQ_NO;
	}

	public int getTtlIncrement() {
		return TTL_INCREMENT;
	}

	public int getTtlStart() {
		return TTL_START;
	}

	public int getTtlThreshold() {
		return TTL_THRESHOLD;
	}

	public void increaseseq_no() {
		this.SEQ_NO++;
	}

	/**
	 * recognizes the packet type that this node received and run a thread for
	 * it
	 * 
	 * @param packet
	 *            the packet that is received
	 */
	public void receive(Packet packet, Node prev_hop) {
		packet.ttl--;
		OutputLogger.get_instance().showNodeStatus(this, packet + " Received");
		packet.receive(this, prev_hop);
	}

	/**
	 * searches if there is a route to a node in route table
	 * 
	 * @param dest
	 *            the destination node
	 * @return route : if it finds any route <br/>
	 *         null : if there isn't any route to that destination
	 */
	public Route search(Node dest) {

		FileLogger.write("Node " + IP.toString() + ": Searching for " + dest,
				FileLogger.MSG_TYPE_INFO);
		OutputLogger.get_instance().showNodeStatus(this,
				"Searching for " + dest);

		Route result = Rout_Arr.get(dest);

		if (result == null) {
			FileLogger.write(dest + " not found!", FileLogger.MSG_TYPE_INFO);
			OutputLogger.get_instance().showNodeStatus(this,
					dest + " not found!");
		}

		return result;
	}

	/**
	 * calls map_manager to broadcast a packet
	 * 
	 * @param packet
	 *            the packet that should be broadcasted
	 */
	public void send(Packet packet) {
		FileLogger.write("Node" + IP.toString() + ": Sending Broadcast Packet",
				FileLogger.MSG_TYPE_INFO);
		OutputLogger.get_instance().showNodeStatus(this,
				"Broadcasting message...");
		OutputLogger.get_instance().NodeSend(this, packet.type);
		Map_Manager.get_instance().sendPacket(packet, this);
	}

	/**
	 * calls map_manager to send a packet to a particular node
	 * 
	 * @param packet
	 *            the packet that should be sent
	 * @param dest
	 *            the destination of the packet
	 * @return the output that map_manager generates
	 */
	public boolean send(Packet packet, Node dest) {
		FileLogger
				.write("Sending message to " + dest, FileLogger.MSG_TYPE_INFO);
		OutputLogger.get_instance().showNodeStatus(this,
				"Sending message to " + dest);
		OutputLogger.get_instance().NodeSend(this, packet.type);
		if (Map_Manager.get_instance().sendPacket(packet, this, dest)) {
			return true;
		}
		Route route = this.search(dest);
		if (Route.isBad(route)) {
			return false;
		}
		return Map_Manager.get_instance().sendPacket(packet, this,
				route.getNext_hop());
	}

	/**
	 * sends a data packet to a particular destination
	 * 
	 * @param data
	 *            the data that should be distributed
	 * @param dest
	 *            the destination of data
	 * @return true: if it can send the data <br/>
	 *         false: if it can't send the data
	 */

	public boolean send_Data(Data data, Node dest) {
		FileLogger.write("Node" + IP + ": Sending data to " + dest,
				FileLogger.MSG_TYPE_INFO);
		Route route = discovery(dest);
		if (route == null) {
			OutputLogger.get_instance().showNodeStatus(this,
					"Failed to send data to " + dest);
			return false;
		}
		DataPacket dataPacket = new DataPacket(data, route.getDestination(),
				this);
		if (send(dataPacket, route.getNext_hop())) {
			return true;
		}
		route.setHop_count(Route.INFINITE);
		route.setInvalid(true);
		return send_Data(data, dest);
	}

	/**
	 * send a RERR packet which contains all lost nodes to its previous node in
	 * the route to the destination.
	 * 
	 * @param next_hop
	 */
	public void send_RERR(Node next_hop, int lostSeq_no) {
		RERRPacket RERR = new RERRPacket();
		RERR.source = this;
		RERR.getLost_nodes().put(next_hop, lostSeq_no);
		for (Node next_node : Rout_Arr.keySet()) {
			if (Rout_Arr.get(next_node).getNext_hop().equals(next_hop)) {
				RERR.getLost_nodes().put(next_node, lostSeq_no);
			}
		}
		for (Node node : RERR.getLost_nodes().keySet()) {
			Route lostRoute = Rout_Arr.get(node);
			if (lostRoute != null) {
				Collection<Node> pre = lostRoute.getPrecursor();
				for (Node pre_node : pre) {
					this.send(RERR, pre_node);
				}
			}
		}
	}

	public void send_RERROneDest(Node dest, int lostSeq_no) {
		RERRPacket RERR = new RERRPacket();
		RERR.source = this;
		RERR.getLost_nodes().put(dest, lostSeq_no);
		for (Node node : RERR.getLost_nodes().keySet()) {
			Route lostRoute = Rout_Arr.get(node);
			if (lostRoute != null) {
				Collection<Node> pre = lostRoute.getPrecursor();
				for (Node pre_node : pre) {
					this.send(RERR, pre_node);
				}
			}
		}
	}

	public void setIP(IPAddress IP) {
		this.IP = IP;
	}

	public void setNode_coordinates(Coordinates xy) {
		this.node_coordinates = xy;
	}

	public void setPower(int power) {
		this.power = power;
	}

	public void setRrepPacketWrapper(RREPPacketWrapper rrepPacketWrapper) {
		this.rrepPacketWrapper = rrepPacketWrapper;
	}

	public String toString() {
		return IP.toString();
	}

}
