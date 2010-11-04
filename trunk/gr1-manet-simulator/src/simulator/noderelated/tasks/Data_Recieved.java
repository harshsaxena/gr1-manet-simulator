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

import logger.MyLogger;
import logger.StatusManager;
import simulator.Node;
import simulator.Packets.DataPacket;
import simulator.noderelated.Route;
import test.AODV_Test;

public class Data_Recieved extends Thread{
    Node mynode;
    private DataPacket packet;
    private Node recievedFrom;

    public Data_Recieved(String name, Node mynode, DataPacket packet, Node recievedFrom) {
        super(name);
        this.mynode = mynode;
        this.packet = packet;
        this.recievedFrom = recievedFrom;
        start();
    }

    public void run() {
        if (packet.dest.equals(mynode)){
            MyLogger.logger.info("Node"+ mynode+":recieved DataPacket from "+
                    packet.source+" which handded from "+recievedFrom
                    +":I'm the destination");

            //only for test
            synchronized(AODV_Test.waiting){
                AODV_Test.waiting.s = "true";
                AODV_Test.waiting.notify();
            }
            //
            //call UI recieved data
            StatusManager.get_instance().showRecievedData(mynode,packet.source,packet.getData());
        }else{
            Route route = mynode.search(packet.dest);
            if (!Route.isBad(route)){
                MyLogger.logger.info("Node"+ mynode+":passing DataPacket from "+
                        packet.source+" which handded from "+recievedFrom
                        +" to "+ route.getNext_hop());
                route.setLifeTime(new Date().getTime()+Node.ACTIVE_ROUTE_TIMEOUT);
                Route route2 = mynode.search(packet.source);
                if (route2!=null){
                    route2.setLifeTime(new Date().getTime()+Node.ACTIVE_ROUTE_TIMEOUT);
                }
                if (!mynode.send(packet,route.getNext_hop())){
                    MyLogger.logger.info("Node"+ mynode+": node "
                            + route.getNext_hop() + " lost.");
                    route.setSeq_no(route.getSeq_no()+1);
                    route.setInvalid(true);
                    route.setLifeTime(new Date().getTime() + Node.DELETE_PERIOD);
                    mynode.send_RERR(route.getNext_hop(),route.getSeq_no());

                }
            }else{
                int lostNodeseq_no = -1;
                MyLogger.logger.debug("Node"+ mynode+":receiving DataPacket from "+
                        packet.source+" which handded from "+recievedFrom
                        +"but have no route!");
                if (route!=null){
                    route.setInvalid(true);
                    route.setSeq_no(route.getSeq_no()+1);
                    route.setLifeTime(new Date().getTime() + Node.DELETE_PERIOD);
                    lostNodeseq_no = route.getSeq_no();
                }

                mynode.send_RERROneDest(route.getDestination(),lostNodeseq_no);
            }
        }
    }
}
