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
import simulator.noderelated.tasks.RREQ_Recieved;

public class RREQPacket extends Packet {
    int SourceSeq_no;
    public boolean J,R,D,G,U;
    int RREQ_ID;

    public int getRREQ_ID() {
        return RREQ_ID;
    }

    public void setRREQ_ID(int RREQ_ID) {
        this.RREQ_ID = RREQ_ID;
    }

    public RREQPacket() {
        this.type = 1;
    }

    public int getSourceSeq_no() {
        return SourceSeq_no;
    }

    public void setSourceSeq_no(int destSeq_no) {
        this.SourceSeq_no = destSeq_no;
    }

    public Packet copy_packet() {
        RREQPacket copy_of = new RREQPacket();
        this.copy_parentvalue(copy_of);
        copy_of.SourceSeq_no = ((RREQPacket) this).SourceSeq_no;
        copy_of.J =J;
        copy_of.R =R;
        copy_of.D =D;
        copy_of.G =G;
        copy_of.U =U;
        copy_of.RREQ_ID = RREQ_ID;
        return copy_of;
    }

    public void recieve(Node reciever, Node prev_hop) {

        reciever.add_Route(reciever.generateRouteFromRREQtoprevHop(this,prev_hop));
        if (reciever.checkAndUpdateBroadCastTable(this.source, this)){
                // checks if this packet has not broadcasted and the source node is not this
                new RREQ_Recieved("RREQ_Recieved"+reciever.getIP().toString(),reciever,
                        this,prev_hop );
                MyLogger.logger.info("Node"+ reciever.getIP().toString()+": RREQ_Recieved from "+this.source+" through "+prev_hop);
            }
    }

    public String toString() {
        return "RREQ "+super.toString();
    }

}
