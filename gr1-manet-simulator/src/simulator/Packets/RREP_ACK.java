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

package simulator.Packets;

import simulator.Node;

public class RREP_ACK extends Packet {
	public RREP_ACK() {
		this.type = 4;
	}

	public void receive(Node receiver, Node prev_hop) {

	}
}
