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

import logger.FileLogger;
import simulator.Packets.Packet;
import simulator.mapmanagerrelated.TaskSpeedSimulator;
import simulator.routing.dsdv.Edge;
import simulator.routing.dsdv.RoutingEntry;
import simulator.routing.dsdv.RoutingTable;

public class Map_Manager {
	private List<Node> nodeList = new ArrayList<Node>();
	private static Map_Manager mapManager = new Map_Manager();
	private static long speedPercent = 200;

	private Protocol mode = Protocol.AODV;

	private Map_Manager() {
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

	// broadcast packet (probably)
	public void sendPacket(Packet packet_to_send, Node src_node) {
		for (Object aNode_list : nodeList) {
			Node tempNode = (Node) aNode_list;
			if (getDistance(src_node, tempNode) <= src_node.getPower()
					&& !tempNode.equals(src_node)) {
				new Timer("Mapmanager: Sending packet from " + src_node
						+ " to " + tempNode, true).schedule(
						new TaskSpeedSimulator(packet_to_send, src_node,
								tempNode), Math.round(getDistance(src_node,
								tempNode))
								/ 100 * speedPercent);
			}
		}
	}

	public boolean sendPacket(Packet packet_to_send, Node src_node,
			Node dest_node) {

		if (mode == Protocol.AODV) {
			if (getDistance(src_node, dest_node) <= src_node.getPower()) {
				new Timer("Mapmanager: Sending packet from " + src_node
						+ " to " + dest_node, true).schedule(
						new TaskSpeedSimulator(packet_to_send, src_node,
								dest_node), Math.round(getDistance(src_node,
								dest_node))
								/ 100 * speedPercent);
				return true;
			}
		} else if (mode == Protocol.DSDV) {
			// TODO
		}

		return false;
	}

	/* Update routing table for src_node */
	public void updateDSDV(Node src) {
		bellmanFord(generateEdges(), src);
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
			RoutingEntry destEntry = rt.getEntry(dest);

			// create entry in routing table
			rt.update(dest, new RoutingEntry());

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
				if (uEntry.getNumHops() + 1 < vEntry.getNumHops()) {
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
