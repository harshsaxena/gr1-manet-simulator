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
	Node recievedFrom;

	public RREPPacketWrapper(RREPPacket rrepPacket, Node recievedFrom) {
		this.rrepPacket = rrepPacket;
		this.recievedFrom = recievedFrom;
	}

	public RREPPacket getRrepPacket() {
		return rrepPacket;
	}

	public void setRrepPacket(RREPPacket rrepPacket) {
		this.rrepPacket = rrepPacket;
	}

	public Node getRecievedFrom() {
		return recievedFrom;
	}

	public void setRecievedFrom(Node recievedFrom) {
		this.recievedFrom = recievedFrom;
	}
}
