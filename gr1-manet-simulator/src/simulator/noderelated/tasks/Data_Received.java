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

import logger.FileLogger;
import logger.OutputLogger;
import simulator.Node;
import simulator.Packets.DataPacket;
import simulator.noderelated.Route;
import test.AODV_Test;

public class Data_Received extends Thread{
    Node mynode;
    private DataPacket packet;
    private Node receivedFrom;

    public Data_Received(String name, Node mynode, DataPacket packet, Node receivedFrom) {
        super(name);
        this.mynode = mynode;
        this.packet = packet;
        this.receivedFrom = receivedFrom;
        start();
    }

    public void run() {
        if (packet.dest.equals(mynode)){
            //ConsoleLogger.logger.info("Node"+ mynode+":received DataPacket from "+ packet.source+" which handded from "+receivedFrom +":I'm the destination");
                    
            FileLogger.write("Node " + mynode + " : received DataPacket from " +
                    packet.source + " which handded from " + receivedFrom
                    + " : I'm the destination", FileLogger.MSG_TYPE_INFO);

            //only for test
            synchronized(AODV_Test.waiting){
                AODV_Test.waiting.s = "true";
                AODV_Test.waiting.notify();
            }
            //
            //call UI received data
            OutputLogger.get_instance().showReceivedData(mynode,packet.source,packet.getData());
        }else{
            Route route = mynode.search(packet.dest);
            if (!Route.isBad(route)){
                //ConsoleLogger.logger.info("Node " + mynode + " : passing DataPacket from " + packet.source + " which handded from " + receivedFrom + " to " + route.getNext_hop());
                FileLogger.write("Node "+ mynode + ": passing DataPacket from "+
                        packet.source + " which handded from " + receivedFrom
                        + " to " + route.getNext_hop(), FileLogger.MSG_TYPE_INFO);
                    
                route.setLifeTime(new Date().getTime()+Node.ACTIVE_ROUTE_TIMEOUT);
                Route route2 = mynode.search(packet.source);
                if (route2!=null){
                    route2.setLifeTime(new Date().getTime()+Node.ACTIVE_ROUTE_TIMEOUT);
                }
                if (!mynode.send(packet,route.getNext_hop())){
                FileLogger.write("Node"+ mynode+": node " + route.getNext_hop() + " lost.", FileLogger.MSG_TYPE_INFO);
                        
                    route.setSeq_no(route.getSeq_no()+1);
                    route.setInvalid(true);
                    route.setLifeTime(new Date().getTime() + Node.DELETE_PERIOD);
                    mynode.send_RERR(route.getNext_hop(),route.getSeq_no());

                }
            }else{
                int lostNodeseq_no = -1;
                //ConsoleLogger.logger.debug("Node"+ mynode+":receiving DataPacket from "+ packet.source+" which handded from "+receivedFrom+"but have no route!");
                FileLogger.write("Node " + mynode + ": receiving data from " +
                        packet.source + " which handed from " + receivedFrom
                        + " but have no route!", FileLogger.MSG_TYPE_INFO);
                        
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
