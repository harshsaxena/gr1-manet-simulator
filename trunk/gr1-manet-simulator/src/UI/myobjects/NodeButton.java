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

import java.awt.Dimension;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import javax.swing.Icon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.TransferHandler;

import UI.Myform;
import UI.myobjects.draganddrop.AddNodeTransferHandler;

/**
 * Represents a non-real node for Tool Bar
 */
@SuppressWarnings("serial")
public class NodeButton extends JButton implements MouseMotionListener, MouseListener {
	
	private MouseEvent firstMouseEvent = null;
	public Myform myForm;
	protected boolean shouldRemoved = false;

	public NodeButton(Icon icon) {
		super(icon);
		this.addMouseListener(this);
		this.addMouseMotionListener(this);
		this.setPreferredSize(new Dimension(40, 30));
		this.setTransferHandler(new AddNodeTransferHandler());
	}

	public boolean isShouldRemoved() {
		return shouldRemoved;
	}

	public void mouseClicked(MouseEvent e) {
	}

	/**
	 * initiates drag and drop action
	 * 
	 * @param e
	 */
	public void mouseDragged(MouseEvent e) {
		if (firstMouseEvent != null) {
			e.consume();
			
			// If they are holding down the control key, COPY rather than MOVE
			int action = TransferHandler.COPY;  // TransferHandler.MOVE;

			int dx = Math.abs(e.getX() - firstMouseEvent.getX());
			int dy = Math.abs(e.getY() - firstMouseEvent.getY());
			
			// Arbitrarily define a 5-pixel shift as the official beginning of a drag.
			if (dx > 5 || dy > 5) {
				
				// This is a drag, not a click.
				JComponent c = (JComponent) e.getSource();
				TransferHandler handler = c.getTransferHandler();
				
				// Tell the transfer handler to initiate the drag.
				handler.exportAsDrag(c, firstMouseEvent, action);
				firstMouseEvent = null;
			}
		}

	}

	public void mouseEntered(MouseEvent e) {}

	public void mouseExited(MouseEvent e) {}

	public void mouseMoved(MouseEvent e) {}

	public void mousePressed(MouseEvent e) {
		firstMouseEvent = e;
		e.consume();
	}

	public void mouseReleased(MouseEvent e) {
		firstMouseEvent = null;
	}

	public void setShouldRemoved(boolean shouldRemoved) {
		this.shouldRemoved = shouldRemoved;
	}
}
