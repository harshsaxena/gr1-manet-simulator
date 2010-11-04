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

public abstract class Packet {
	public Node source = null;
	public Node dest = null;
	public int ttl = 0; // TODO generate default ttl
	public int hop_count = 0;
	public int seq_no;
	public int type;

	public Packet copy_packet() {
		return this;
	}

	public Packet copy_parentvalue(Packet packet) {
		packet.source = this.source;
		packet.dest = this.dest;
		packet.ttl = this.ttl;
		packet.hop_count = this.hop_count;
		packet.seq_no = this.seq_no;
		packet.type = this.type;
		return packet;
	}

	public abstract void receive(Node receiver, Node prev_hop);

	public String toString() {
		return "seq_no=" + seq_no;
	}
}
