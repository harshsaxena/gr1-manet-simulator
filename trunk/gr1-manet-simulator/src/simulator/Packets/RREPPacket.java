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

import logger.FileLogger;
import simulator.Node;
import simulator.noderelated.RREPPacketWrapper;
import simulator.noderelated.tasks.RREP_Received;

public class RREPPacket extends Packet {
	public boolean R, A;
	private long LifeTime;

	public RREPPacket() {
		this.type = 2;
	}

	public Packet copy_packet() {
		RREPPacket copy_of = new RREPPacket();
		this.copy_parentvalue(copy_of);
		copy_of.A = A;
		copy_of.R = R;
		copy_of.LifeTime = this.LifeTime;
		return copy_of;
	}

	public void receive(Node receiver, Node prev_hop) {
		new RREP_Received("RREP_Received" + receiver.getIP().toString(),
				receiver, new RREPPacketWrapper(this, prev_hop));
		FileLogger.write("Node " + receiver.getIP().toString()
				+ ": RREP received from " + this.source + " through "
				+ prev_hop, FileLogger.MSG_TYPE_INFO);
	}

	public String toString() {
		return "RREPPacket " + super.toString();
	}

	public long getLifeTime() {
		return LifeTime;
	}

	public void setLifeTime(long lifeTime) {
		LifeTime = lifeTime;
	}

}
