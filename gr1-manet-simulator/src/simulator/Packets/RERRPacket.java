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
import simulator.noderelated.tasks.RERR_Recieved;

public class RERRPacket extends Packet {
    private Map<Node,Integer> lost_nodes = new HashMap<Node,Integer>();

    boolean N;
    public Map <Node,Integer> getLost_nodes() {
        return lost_nodes;
    }

    public RERRPacket() {
        this.type = 3;
    }

    public void recieve(Node reciever, Node prev_hop) {
        new RERR_Recieved("RERR_Recieved"+reciever.getIP().toString(),reciever,this,prev_hop);
            MyLogger.logger.info("Node"+ reciever.getIP().toString()+": RERR_Recieved from "+this.source+" through "+prev_hop);
    }

    public String toString() {
        return "RERRPacket "+super.toString();
    }

}
