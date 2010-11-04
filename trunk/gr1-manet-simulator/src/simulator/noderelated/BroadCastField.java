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

import java.util.Date;

import simulator.Node;

public class BroadCastField {
    Node source;
    int RREQ_ID;
    long lifeTime;


    public boolean equals(Object obj) {
        return this.source.equals(obj);
    }

    public BroadCastField(Node source, int RREQ_ID) {
        this.source = source;
        this.RREQ_ID = RREQ_ID;
        this.lifeTime = new Date().getTime()+Node.PATH_DISCOVERY_TIME;
    }

    public String toString() {
        return "Broadcast field: "+getSource()+" : "+getRREQ_ID();
    }

    public Node getSource() {
        return source;
    }

    public void setSource(Node source) {
        this.source = source;
    }

    public int getRREQ_ID() {
        return RREQ_ID;
    }

    public void setRREQ_ID(int RREQ_ID) {
        this.RREQ_ID = RREQ_ID;
    }

    public long getLifeTime() {
        return lifeTime;
    }

    public void setLifeTime(long lifeTime) {
        this.lifeTime = lifeTime;
    }
}
