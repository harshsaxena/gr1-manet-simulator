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

package simulator.mapmanagerrelated;

import java.util.TimerTask;

import logger.ConsoleLogger;
import logger.FileLogger;
import simulator.Node;
import simulator.Packets.Packet;

public class TaskSpeedSimulator extends TimerTask {
    private Packet packet;
    private Node src,dest;

    public TaskSpeedSimulator(Packet packet, Node src, Node dest) {
        this.packet = packet;
        this.src = src;
        this.dest = dest;
    }

    public void run() {
        ConsoleLogger.logger.info("MapManager Sending broadcast packet From " + src + " to " + dest);
        FileLogger.write("MapManager Sending broadcast packet From " + src + " to " + dest);
        dest.receive(packet.copy_packet(), src);
    }
}
