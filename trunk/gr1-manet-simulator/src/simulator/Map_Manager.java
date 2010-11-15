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

package simulator;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;

import logger.FileLogger;
import simulator.Packets.Packet;
import simulator.mapmanagerrelated.TaskSpeedSimulator;

public class Map_Manager {
	private List<Node> node_list = new ArrayList<Node>();
	private static Map_Manager map_manager = new Map_Manager();
	private static long speedPercent = 200;

	private Map_Manager() {}

	public static Map_Manager get_instance() {
		return map_manager;
	}

	public List<Node> getNode_list() {
		return node_list;
	}

	public void setNode_list(List<Node> node_list) {
		this.node_list = node_list;
	}

	public void addNode(Node new_node) {
		node_list.add(new_node);
	}

	public void delNode(Node garbage_node) {
		node_list.remove(garbage_node);
	}

	public double getDistance(Node a, Node b) {
		int x_distance = a.getNode_coordinates().getX_coordinate()
				- b.getNode_coordinates().getX_coordinate();
		
		int y_distance = a.getNode_coordinates().getY_coordinate()
				- b.getNode_coordinates().getY_coordinate();
		
		double distance = Math.sqrt((x_distance * x_distance)
				+ (y_distance * y_distance));
		
		FileLogger.write(a.getNode_coordinates() + ", "
				+ b.getNode_coordinates() + ", " + distance + ","
				+ a.getPower(), FileLogger.MSG_TYPE_INFO);
		
		return distance;
	}

	public void sendPacket(Packet packet_to_send, Node src_node) {
		for (Object aNode_list : node_list) {
			Node tempNode = (Node) aNode_list;
			if (getDistance(src_node, tempNode) <= src_node.getPower()
					&& !tempNode.equals(src_node)) {
				new Timer("Mapmanager: Sending packet from " + src_node
						+ " to " + tempNode, true).schedule(
						new TaskSpeedSimulator(packet_to_send, src_node,
								tempNode), Math.round(getDistance(src_node,
								tempNode))
								/ 100 * speedPercent);
			}
		}
	}

	public boolean sendPacket(Packet packet_to_send, Node src_node,
			Node dest_node) {
		if (getDistance(src_node, dest_node) <= src_node.getPower()) {
			new Timer("Mapmanager: Sending packet from " + src_node + " to "
					+ dest_node, true).schedule(new TaskSpeedSimulator(
					packet_to_send, src_node, dest_node), Math
					.round(getDistance(src_node, dest_node))
					/ 100 * speedPercent);
			return true;
		}
		return false;
	}
}