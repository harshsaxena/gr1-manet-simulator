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

public class Edge {
	private Node src;
	private Node dest;

	public Edge(Node src, Node dest) {
		this.src = src;
		this.dest = dest;
	}

	public Node getSrc() {
		return src;
	}

	public Node getDest() {
		return dest;
	}
}
