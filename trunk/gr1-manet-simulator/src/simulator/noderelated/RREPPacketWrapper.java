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

package simulator.noderelated;

import simulator.Node;
import simulator.Packets.RREPPacket;

public class RREPPacketWrapper {
	RREPPacket rrepPacket;
	Node receivedFrom;

	public RREPPacketWrapper(RREPPacket rrepPacket, Node receivedFrom) {
		this.rrepPacket = rrepPacket;
		this.receivedFrom = receivedFrom;
	}

	public RREPPacket getRrepPacket() {
		return rrepPacket;
	}

	public void setRrepPacket(RREPPacket rrepPacket) {
		this.rrepPacket = rrepPacket;
	}

	public Node getReceivedFrom() {
		return receivedFrom;
	}

	public void setReceivedFrom(Node receivedFrom) {
		this.receivedFrom = receivedFrom;
	}
}
