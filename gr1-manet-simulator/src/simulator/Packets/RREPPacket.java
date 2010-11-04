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

import logger.MyLogger;
import simulator.Node;
import simulator.noderelated.RREPPacketWrapper;
import simulator.noderelated.tasks.RREP_Recieved;

public class RREPPacket extends Packet {
    public boolean R,A;
    private long LifeTime;
    public RREPPacket() {
        this.type=2;
    }

    public Packet copy_packet() {
        RREPPacket copy_of = new RREPPacket();
        this.copy_parentvalue(copy_of);
        copy_of.A =A;
        copy_of.R =R;
        copy_of.LifeTime = this.LifeTime;
        return copy_of;
    }


    public void recieve(Node reciever, Node prev_hop) {
        new RREP_Recieved("RREP_Recieved"+reciever.getIP().toString(),reciever,
                    new RREPPacketWrapper(this,prev_hop));
            MyLogger.logger.info("Node"+ reciever.getIP().toString()+": RREP_Recieved from "+this.source+" through "+prev_hop);
    }

    public String toString() {
        return "RREPPacket "+super.toString();
    }

    public long getLifeTime() {
        return LifeTime;
    }

    public void setLifeTime(long lifeTime) {
        LifeTime = lifeTime;
    }

}
