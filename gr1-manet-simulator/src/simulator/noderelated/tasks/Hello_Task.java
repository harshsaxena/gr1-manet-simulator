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

package simulator.noderelated.tasks;

import java.util.Date;
import java.util.TimerTask;
import simulator.Node;
import simulator.Packets.RREPPacket;
import simulator.noderelated.Route;

public class Hello_Task extends TimerTask {
	private Node mynode;

	public Hello_Task(Node mynode) {
		super();
		this.mynode = mynode;
	}

	public void run() {
		if (Node.helloTime + Node.HELLO_INTERVAL < new Date().getTime()) {
			boolean haveActive = false;
			synchronized (this.mynode.getRout_Arr()) {
				for (Route r : this.mynode.getRout_Arr().values()) {
					if (!Route.isBad(r)
							&& !r.getDestination().equals(this.mynode)) {
						haveActive = true;
					}
				}
			}
			if (haveActive) {
				RREPPacket rrepPacket = new RREPPacket();
				rrepPacket.dest = this.mynode;
				rrepPacket.seq_no = this.mynode.getSeq_no();
				rrepPacket.hop_count = 0;
				rrepPacket.setLifeTime(Node.ALLOWED_HELLO_LOSS
						* Node.HELLO_INTERVAL);
				rrepPacket.ttl = 1;
				this.mynode.send(rrepPacket);
			}
		}
	}
}
