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

/**
 * 
 */
package replay;

import java.util.Iterator;
import java.util.Map;

import logger.FileLogger;
import simulator.Node;
import simulator.noderelated.Route;
import UI.myobjects.GraphicalNode;

/**
 * @author mroberts
 *
 */
public class ReplayLogger {
	
	private static final String FROM_TEXT = "SendFrom";
	private static final String TO_TEXT = "SendTo";
	
	/**
	 * Logs node properties to the file log, intended for the replay functionality.
	 * @param graphicalNode
	 * @param nodeType (integer 0 = From, 1 = To)
	 * @param nodeName 
	 */
	@SuppressWarnings("unchecked")
	public static void logSendNodeMsg(GraphicalNode graphicalNode, int nodeType, String nodeName)
	{	
		String nodeText = FROM_TEXT;
		if (nodeType == 1) {
			nodeText = TO_TEXT;
		}

		FileLogger.write("\t" + nodeText + "Name=" + graphicalNode.getName(), FileLogger.MSG_TYPE_REPLAY);
		/*FileLogger.write(nodeText + "Node_"+ nodeName +"_ActiveRouteTimeout = " + graphicalNode.getNode().getActiveRouteTimeout(), FileLogger.MSG_TYPE_REPLAY);
		FileLogger.write(nodeText + "Node_"+ nodeName +"_AllowedHelloLoss = " + graphicalNode.getNode().getAllowedHelloLoss(), FileLogger.MSG_TYPE_REPLAY);
		FileLogger.write(nodeText + "Node_"+ nodeName +"_DefaultRouteLifetime = " + graphicalNode.getNode().getDefaultRouteLifetime(), FileLogger.MSG_TYPE_REPLAY);
		FileLogger.write(nodeText + "Node_"+ nodeName +"_DeletePeriod = " + graphicalNode.getNode().getDeletePeriod(), FileLogger.MSG_TYPE_REPLAY);
		FileLogger.write(nodeText + "Node_"+ nodeName +"_HelloInterval = " + graphicalNode.getNode().getHelloInterval(), FileLogger.MSG_TYPE_REPLAY);
		FileLogger.write(nodeText + "Node_"+ nodeName +"_HelloTime = " + graphicalNode.getNode().getHelloTime(), FileLogger.MSG_TYPE_REPLAY);
		FileLogger.write(nodeText + "Node_"+ nodeName +"_LoopbackExpireTime = " + graphicalNode.getNode().getLoopbackExpireTime(), FileLogger.MSG_TYPE_REPLAY);
		FileLogger.write(nodeText + "Node_"+ nodeName +"_RouteTimeout = " + graphicalNode.getNode().getMyRouteTimeout(), FileLogger.MSG_TYPE_REPLAY);
		FileLogger.write(nodeText + "Node_"+ nodeName +"_NetDiameter = " + graphicalNode.getNode().getNetDiameter(), FileLogger.MSG_TYPE_REPLAY);
		FileLogger.write(nodeText + "Node_"+ nodeName +"_NetTraversalTime = " + graphicalNode.getNode().getNetTraversalTime(), FileLogger.MSG_TYPE_REPLAY);
		FileLogger.write(nodeText + "Node_"+ nodeName +"_NodeTraversalTime = " + graphicalNode.getNode().getNodeTraversalTime(), FileLogger.MSG_TYPE_REPLAY);
		FileLogger.write(nodeText + "Node_"+ nodeName +"_PathDiscoveryInterval = " + graphicalNode.getNode().getPathDiscoveryInterval(), FileLogger.MSG_TYPE_REPLAY);
		FileLogger.write(nodeText + "Node_"+ nodeName +"_PathDiscoveryTime = " + graphicalNode.getNode().getPathDiscoveryTime(), FileLogger.MSG_TYPE_REPLAY);
		FileLogger.write(nodeText + "Node_"+ nodeName +"_Power = " + graphicalNode.getNode().getPower(), FileLogger.MSG_TYPE_REPLAY);
		FileLogger.write(nodeText + "Node_"+ nodeName +"_RepAckRequired = " + graphicalNode.getNode().getRepAckRequired(), FileLogger.MSG_TYPE_REPLAY);
		FileLogger.write(nodeText + "Node_"+ nodeName +"_RepRetires = " + graphicalNode.getNode().getReqRetries(), FileLogger.MSG_TYPE_REPLAY);
		FileLogger.write(nodeText + "Node_"+ nodeName +"_RouteDeleteInterval = " + graphicalNode.getNode().getRouteDeleteInterval(), FileLogger.MSG_TYPE_REPLAY);
		FileLogger.write(nodeText + "Node_"+ nodeName +"_RouteExpireInterval = " + graphicalNode.getNode().getRouteExpireInterval(), FileLogger.MSG_TYPE_REPLAY);*/
		
		Map<Node, Route> routeMap = graphicalNode.getNode().getRout_Arr();
	    Iterator it = routeMap.entrySet().iterator();
	    while (it.hasNext()) {
	        Map.Entry pairs = (Map.Entry)it.next();
	        FileLogger.write("\tRouteMapKeyValue=" + pairs.getKey() + ":" + pairs.getValue(), FileLogger.MSG_TYPE_REPLAY);
	    }
	}

}
