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

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import UI.Myform;

import logger.FileLogger;
import logger.OutputLogger;
import simulator.Packets.Packet;
import simulator.mapmanagerrelated.TaskSpeedSimulator;
import simulator.routing.dsdv.Edge;
import simulator.routing.dsdv.RoutingEntry;
import simulator.routing.dsdv.RoutingTable;

public class Map_Manager {
	private List<Node> nodeList = new ArrayList<Node>();
	private static Map_Manager mapManager = new Map_Manager();
	private static long speedPercent = 100;
	private Protocol mode = Protocol.DSDV;
	private Myform myForm;

	private Map_Manager() {
	}

	public Protocol getMode() {
		return mode;
	}

	public void setMode(Protocol mode) {
		this.mode = mode;
	}

	public Myform getMyForm() {
		return myForm;
	}

	public void setMyForm(Myform myForm) {
		this.myForm = myForm;
	}

	public static Map_Manager get_instance() {
		return mapManager;
	}

	public List<Node> getNode_list() {
		return nodeList;
	}

	public void setNode_list(List<Node> node_list) {
		this.nodeList = node_list;
	}

	public void addNode(Node new_node) {
		nodeList.add(new_node);
	}

	public void delNode(Node garbage_node) {
		nodeList.remove(garbage_node);
	}

	public double getDistance(Node a, Node b) {
		int x_distance = a.getNode_coordinates().getX_coordinate()
				- b.getNode_coordinates().getX_coordinate();

		int y_distance = a.getNode_coordinates().getY_coordinate()
				- b.getNode_coordinates().getY_coordinate();

		double distance = Math.sqrt((x_distance * x_distance)
				+ (y_distance * y_distance));

		FileLogger.write(a.getNode_coordinates() + ", "
				+ b.getNode_coordinates() + ", " + distance + ","
				+ a.getPower(), FileLogger.MSG_TYPE_INFO);

		return distance;
	}

	private void sendSingleHop(Packet packetToSend, Node cur, Node next) {
		if (getDistance(cur, next) <= cur.getPower()) {
			// send packet after distance-based delay
			TimerTask task = new TaskSpeedSimulator(packetToSend, cur, next);
			long delay = Math
					.round(getDistance(cur, next) / 100 * speedPercent);
			new Timer("Mapmanager: Sending packet from " + cur + " to " + next,
					true).schedule(task, delay);

			// useless logging
			// FileLogger.write("Sending message to " + next,
			// FileLogger.MSG_TYPE_INFO);
			// OutputLogger.get_instance().showNodeStatus(cur,
			// "Sending message to " + next);

			// animate and wait
			OutputLogger.get_instance().startNodeAnimation(cur,
					packetToSend.type);
			try {
				Thread.sleep(delay);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		} else
			System.err
					.println("Error in sendSingleHop(): Destination out of range");
	}

	// broadcast packet
	public void sendPacket(Packet packetToSend, Node src) {
		for (Object aNode_list : nodeList) {
			Node tempNode = (Node) aNode_list;
			if (getDistance(src, tempNode) <= src.getPower()
					&& !tempNode.equals(src)) {
				new Timer("Mapmanager: Sending packet from " + src + " to "
						+ tempNode, true).schedule(new TaskSpeedSimulator(
						packetToSend, src, tempNode), Math.round(getDistance(
						src, tempNode))
						/ 100 * speedPercent);
			}
		}
	}

	/*
	 * Send a packet from src to dest. If mode is AODV, only one hop is allowed.
	 * If mode is DSDV, multiple hops are allowed.
	 */
	public boolean sendPacket(Packet packetToSend, Node src, Node dest) {

		if (mode == Protocol.AODV) {
			if (getDistance(src, dest) <= src.getPower()) {
				new Timer("Mapmanager: Sending packet from " + src + " to "
						+ dest, true).schedule(new TaskSpeedSimulator(
						packetToSend, src, dest), Math.round(getDistance(src,
						dest))
						/ 100 * speedPercent);
				return true;
			}

		} else if (mode == Protocol.DSDV) {
			// TODO advertise tables instead of running bellman-ford multiple
			// times
			updateAllDSDV();

			RoutingTable src_rt = src.getDSDVTable();
			Node cur = src;
			Node next = src_rt.getEntry(dest).getNextHop();
			while (next != null && cur != next) {
				// send packet from cur to next
				sendSingleHop(packetToSend, cur, next);
				if (next == dest)
					return true;
				RoutingTable next_rt = next.getDSDVTable();
				cur = next;
				next = next_rt.getEntry(dest).getNextHop();
			}
		}

		return false;
	}

	/* Update all routing tables */
	public void updateAllDSDV() {
		List<Edge> edges = generateEdges();
		for (Node src : nodeList) {
			updateDSDV(edges, src);
		}
	}

	/* Update routing table for src_node */
	// must be synchronized because sendPacket is multithreaded and
	// ConcurrentModificationException could occur otherwise
	public synchronized void updateDSDV(List<Edge> edges, Node src) {
		bellmanFord(edges, src);
		for (Node dest : nodeList) {
			if (dest != src)
				src.getDSDVTable().generateNextHop(dest);
		}
	}

	/* Determine whether src_node is within range of dest_node */
	private boolean isInRange(Node src, Node dest) {
		if (getDistance(src, dest) <= src.getPower())
			return true;
		return false;
	}

	/* Generates a list of edges based on the network topology. */
	private List<Edge> generateEdges() {
		List<Edge> edges = new ArrayList<Edge>();
		for (Node src : nodeList) {
			for (Node dest : nodeList) {
				if (src != dest && isInRange(src, dest))
					edges.add(new Edge(src, dest));
			}
		}
		return edges;
	}

	/* Update the source node's routing table using Bellman-Ford */
	public void bellmanFord(List<Edge> edges, Node src) {
		// This implementation takes in a graph, represented as lists of
		// vertices
		// and edges, and modifies the vertices so that their distance and
		// predecessor attributes store the shortest paths.

		RoutingTable rt = src.getDSDVTable();

		// Step 1: initialize graph
		for (Node dest : nodeList) {
			// create entry in routing table
			rt.update(dest, new RoutingEntry());

			RoutingEntry destEntry = rt.getEntry(dest);

			if (dest == src)
				destEntry.setNumHops(0);
			else
				destEntry.setNumHops(Integer.MAX_VALUE);

			destEntry.setPredecessor(null);
		}

		// Step 2: relax edges repeatedly
		for (int i = 1; i <= nodeList.size() - 1; ++i) {
			for (Edge uv : edges) { // uv is the edge from u to v
				Node u = uv.getSrc();
				Node v = uv.getDest();
				RoutingEntry uEntry = rt.getEntry(u);
				RoutingEntry vEntry = rt.getEntry(v);
				// first condition is necessary to account for overflow
				if (uEntry.getNumHops() != Integer.MAX_VALUE
						&& uEntry.getNumHops() + 1 < vEntry.getNumHops()) {
					vEntry.setNumHops(uEntry.getNumHops() + 1);
					vEntry.setPredecessor(u);
				}
			}
		}

		// Step 3: check for negative-weight cycles
		// for (Edge uv : edges) {
		// Node u = uv.getSrc();
		// Node v = uv.getDest();
		// if (src.getNumHops(u) + 1 < src.getNumHops(v))
		// System.err.println("Graph contains a negative-weight cycle");
		// }
	}
}
