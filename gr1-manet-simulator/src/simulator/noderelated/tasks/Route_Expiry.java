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
import logger.ConsoleLogger;
import logger.FileLogger;
import logger.StatusManager;
import simulator.Node;
import simulator.noderelated.Route;

public class Route_Expiry extends TimerTask {
    Node mynode;

    public Route_Expiry ( Node mynode) {
        super();
        this.mynode = mynode;
    }


    public void run() {
        synchronized(this.mynode.getRout_Arr()){
        for (Node node : mynode.getRout_Arr().keySet()) {
            Route route = mynode.getRout_Arr().get(node);
            if (route.getLifeTime()<new Date().getTime() && !mynode.equals(route.getDestination())
                    && route.getHop_count()< Route.INFINITE){
                ConsoleLogger.logger.info("Node "+mynode +" : "+route+" Expires!");
                FileLogger.write("Node "+mynode +" : "+route+" Expires!");
                StatusManager.get_instance().showNodeStatus(mynode,"Expire: "+route);
                route.setSeq_no(route.getSeq_no()+1);
                route.setHop_count(Route.INFINITE);
            }
        }
        }
    }
}
