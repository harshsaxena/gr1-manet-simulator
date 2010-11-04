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

package simulator.noderelated;

public class Coordinates {
	private int x_coordinate = 0;
	private int y_coordinate = 0;

	public Coordinates() {
	}

	public Coordinates(int x_coordinate, int y_coordinate) {
		this.x_coordinate = x_coordinate;
		this.y_coordinate = y_coordinate;
	}

	public String toString() {
		return "[" + x_coordinate + ", " + y_coordinate + "]";
	}

	public int getX_coordinate() {
		return x_coordinate;
	}

	public void setX_coordinate(int x_coordinate) {
		this.x_coordinate = x_coordinate;
	}

	public int getY_coordinate() {
		return y_coordinate;
	}

	public void setY_coordinate(int y_coordinate) {
		this.y_coordinate = y_coordinate;
	}
}
