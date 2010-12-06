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

package simulator.routing.dsdv;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import simulator.Node;

public class RoutingTable {
	/* The node to which this routing table belongs */
	private Node owner;
	/* A map of destination nodes to routing entries */
	private Map<Node, RoutingEntry> entries;

	public RoutingTable(Node owner) {
		this.owner = owner;
		entries = new HashMap<Node, RoutingEntry>();
	}

	public void update(Node dest, RoutingEntry entry) {
		entries.put(dest, entry);
	}

	public RoutingEntry getEntry(Node dest) {
		if (entries.get(dest) != null)
			return entries.get(dest);
		else {
			System.err.println("Error in routing table: node not found.");
			return null;
		}
	}

	public void generateNextHop(Node dest) {
		Node next = dest;
		while (next != null && next != owner) {
			if (getEntry(next).getPredecessor() == owner) {
				getEntry(dest).setNextHop(next);
				break;
			}
			next = getEntry(next).getPredecessor();
		}
	}

	/*
	 * Generates the path from owner to dest (not including owner).
	 * Preconditions: Bellman-Ford has been run on the routing table.
	 */
	public List<Node> generatePath(Node dest) {
		List<Node> path = new LinkedList<Node>();
		Node next = dest;
		while (next != null && next != owner) {
			if (getEntry(next).getPredecessor() == owner) {
				path.add(0, next);
				break;
			}
			path.add(0, next);
			next = getEntry(next).getPredecessor();
		}
		return path;
	}
}
