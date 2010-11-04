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

import java.util.HashMap;
import java.util.Map;
import logger.MyLogger;
import simulator.Node;
import simulator.noderelated.tasks.RERR_Received;

public class RERRPacket extends Packet {
    private Map<Node,Integer> lost_nodes = new HashMap<Node,Integer>();

    boolean N;
    public Map <Node,Integer> getLost_nodes() {
        return lost_nodes;
    }

    public RERRPacket() {
        this.type = 3;
    }

    public void receive(Node receiver, Node prev_hop) {
        new RERR_Received("RERR_Received"+receiver.getIP().toString(),receiver,this,prev_hop);
            MyLogger.logger.info("Node"+ receiver.getIP().toString()+": RERR_Received from "+this.source+" through "+prev_hop);
    }

    public String toString() {
        return "RERRPacket "+super.toString();
    }

}
