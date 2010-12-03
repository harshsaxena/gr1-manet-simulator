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

package test;

import logger.FileLogger;
import logger.OutputLogger;
import simulator.Data;
import simulator.Map_Manager;
import simulator.Node;
import simulator.noderelated.IPAddress;

public class AODV_Test {

	public static TestFlag waiting = new TestFlag();
	private static final int NODE_NUMBER = 10;
	private static final int MAP_LENGTH = 90;
	private static final int MAP_WIDTH = 90;
	private static final int[][] nodexyp = new int[][] { { 15, 15, 43 },
			{ 15, 45, 30 }, { 15, 75, 30 }, { 45, 75, 30 } };

	public static void randomnode_create() {
		IPAddress ipBase = new IPAddress("192.168.10.1");
		for (int i = 0; i < NODE_NUMBER; i++) {
			Node new_node = new Node(IPAddress.createNext(ipBase));
			new_node.getNode_coordinates().setX_coordinate(
					(int) (MAP_LENGTH * Math.random()));
			new_node.getNode_coordinates().setY_coordinate(
					(int) (MAP_WIDTH * Math.random()));
			new_node.setPower(43);

			FileLogger.write("Node " + i + " created at: x = "
					+ new_node.getNode_coordinates().getX_coordinate()
					+ ", y = "
					+ new_node.getNode_coordinates().getY_coordinate()
					+ ", power = " + new_node.getPower(),
					FileLogger.MSG_TYPE_DEBUG);
		}
	}

	public static void random_send() {
		int src_number = (int) (NODE_NUMBER * Math.random());
		int dest_number = (int) (NODE_NUMBER * Math.random());

		while (src_number == dest_number) {
			dest_number = (int) (NODE_NUMBER * Math.random());
		}

		Node src_node = (Node) Map_Manager.get_instance().getNode_list().get(
				src_number);
		Node dest_node = (Node) Map_Manager.get_instance().getNode_list().get(
				dest_number);

		Data test_data = new Data();
		test_data.setContent("test");

		FileLogger.write("Trying to send data form node " + src_number
				+ " to node " + dest_number, FileLogger.MSG_TYPE_DEBUG);

		if (src_node.send_Data(test_data, dest_node)) {
			FileLogger.write("Data sent successfully!!!!",
					FileLogger.MSG_TYPE_DEBUG);
			dest_node.getNode_coordinates().setX_coordinate(
					90 - dest_node.getNode_coordinates().getX_coordinate());
			dest_node.getNode_coordinates().setY_coordinate(
					90 - dest_node.getNode_coordinates().getY_coordinate());

			if (src_node.send_Data(test_data, dest_node)) {
				FileLogger.write("Data sent successfully!!!!",
						FileLogger.MSG_TYPE_DEBUG);
			} else {
				FileLogger.write("Failed to send data.",
						FileLogger.MSG_TYPE_DEBUG);
			}
		} else {
			FileLogger.write("Failed to send data.", FileLogger.MSG_TYPE_DEBUG);
		}
	}

	public static void node_create() {
		IPAddress ipBase = new IPAddress("192.168.10.1");
		for (int i = 0; i < nodexyp.length; i++) {
			Node new_node = new Node(IPAddress.createNext(ipBase));
			new_node.getNode_coordinates().setX_coordinate(nodexyp[i][0]);
			new_node.getNode_coordinates().setY_coordinate(nodexyp[i][1]);
			new_node.setPower(nodexyp[i][2]);

			FileLogger.write("Node " + (i) + " created at: x = "
					+ new_node.getNode_coordinates().getX_coordinate()
					+ ", y = "
					+ new_node.getNode_coordinates().getY_coordinate()
					+ ", power = " + new_node.getPower(),
					FileLogger.MSG_TYPE_DEBUG);
		}
	}

	public static void send_test() {
		Node src_node = (Node) Map_Manager.get_instance().getNode_list().get(0);
		Node dest_node = (Node) Map_Manager.get_instance().getNode_list()
				.get(3);

		Data test_data = new Data();
		test_data.setContent("test");
		FileLogger.write(
				"Trying to send data form node " + 0 + " to node " + 3,
				FileLogger.MSG_TYPE_DEBUG);
		src_node.send_Data(test_data, dest_node);
		synchronized (waiting) {
			try {
				waiting.wait();
			} catch (InterruptedException e) {
				e.printStackTrace(); // To change body of catch statement use
				// File | Settings | File Templates.
			}
		}
		if (waiting.s.equals("true")) {
			FileLogger.write("Data sent successfully!!!!",
					FileLogger.MSG_TYPE_DEBUG);
			Node tempnode = (Node) Map_Manager.get_instance().getNode_list()
					.get(2);
			tempnode.getNode_coordinates().setX_coordinate(75);
			tempnode.getNode_coordinates().setY_coordinate(45);

			if (src_node.send_Data(test_data, dest_node)) {
				FileLogger.write("Data sent successfully!!!!",
						FileLogger.MSG_TYPE_DEBUG);
			} else {
				FileLogger.write("Failed to send data.",
						FileLogger.MSG_TYPE_DEBUG);
			}
		} else {
			FileLogger.write("Failed to send data.", FileLogger.MSG_TYPE_DEBUG);
		}
	}

	public static void main(String[] args) {
		OutputLogger.init();
		randomnode_create();
		random_send();
	}
}
