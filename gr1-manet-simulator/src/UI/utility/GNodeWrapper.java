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

public class GNodeWrapper implements Comparable {
	double distace;
	GraphicalNode gnode;

	public GNodeWrapper(double distace, GraphicalNode gnode) {
		this.distace = distace;
		this.gnode = gnode;
	}

	public double getDistace() {
		return distace;
	}

	public GraphicalNode getGnode() {
		return gnode;
	}

	public int compareTo(Object o) {
		return Double.compare(this.distace, ((GNodeWrapper) o).getDistace());
	}
}
