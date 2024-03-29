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

package UI.myobjects;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JComponent;

import UI.Myform;

@SuppressWarnings("serial")
public class PowerShower extends JComponent {
	final static float dash1[] = { 10.0f };
	final static BasicStroke dashed = new BasicStroke(1.0f, BasicStroke.CAP_BUTT, BasicStroke.JOIN_MITER, 10.0f, dash1, 0.0f);
	Myform myform;
	int x, y, rX, rY;

	public PowerShower(Myform myform) {
		this.myform = myform;
	}

	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D) g;
		g2.setColor(Color.black);
		g2.setStroke(dashed);
		g2.drawOval(x - rX + 16, y - rY + 16, 2 * rX, 2 * rY);
	}

	/**
	 * initializes the glass pane for painting
	 * 
	 * @param x
	 * @param y
	 * @param radiusX
	 * @param radiusY
	 */
	public void setXYrXrY(int x, int y, int radiusX, int radiusY) {
		this.x = x;
		this.y = y;
		this.rX = radiusX;
		this.rY = radiusY;
	}
}