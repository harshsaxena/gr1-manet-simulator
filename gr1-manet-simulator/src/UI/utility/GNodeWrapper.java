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

package UI.utility;

import UI.myobjects.GraphicalNode;

public class GNodeWrapper implements Comparable<GNodeWrapper> {
	double distance;
	GraphicalNode gnode;

	public GNodeWrapper(double distace, GraphicalNode gnode) {
		this.distance = distace;
		this.gnode = gnode;
	}

	public double getDistace() {
		return distance;
	}

	public GraphicalNode getGnode() {
		return gnode;
	}

	public int compareTo(GNodeWrapper o) {
		return Double.compare(this.distance, o.getDistace());
	}
}
