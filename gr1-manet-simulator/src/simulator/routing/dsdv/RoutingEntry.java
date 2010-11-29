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

import simulator.Node;

/* An entry in a routing table for a node. 
 * The source and destination nodes are given by the routing table the entry belongs to. */
public class RoutingEntry {
	/* The next hop */
	private Node nextHop;
	/* The number of hops to the destination */
	private int numHops;
	/* The sequence number of the entry */
	private int seqNo;
	/* The node before the destination node */
	private Node predecessor;

	public Node getNextHop() {
		return nextHop;
	}

	public void setNextHop(Node nextHop) {
		this.nextHop = nextHop;
	}

	public int getNumHops() {
		return numHops;
	}

	public void setNumHops(int numHops) {
		this.numHops = numHops;
	}

	public int getSeqNo() {
		return seqNo;
	}

	public void setSeqNo(int seqNo) {
		this.seqNo = seqNo;
	}

	public Node getPredecessor() {
		return predecessor;
	}

	public void setPredecessor(Node predecessor) {
		this.predecessor = predecessor;
	}

}
