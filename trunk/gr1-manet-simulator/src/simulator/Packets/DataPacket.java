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
import simulator.Data;
import simulator.Node;
import simulator.noderelated.tasks.Data_Recieved;

public class DataPacket extends Packet {
    public DataPacket(Data data, Node dest, Node src) {
        this.data = data;
        this.dest = dest;
        this.source = src;
        this.type = 0;
    }

    private Data data;

    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }

    public void recieve(Node reciever, Node prev_hop) {
        new Data_Recieved("Data_Recieved"+reciever.getIP().toString(),reciever,this,prev_hop);
            MyLogger.logger.info("Node"+ reciever.getIP().toString()+": Data_Recieved from "+this.source+" through "+prev_hop);
    }

    public String toString() {
        return "DataPacket "+super.toString();
    }
}
