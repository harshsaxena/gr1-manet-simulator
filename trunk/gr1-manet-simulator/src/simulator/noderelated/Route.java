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

import java.util.HashSet;
import java.util.Set;
import simulator.Node;

public class Route {
    public static final int INFINITE = 1000;
    private boolean invalid=false;
    private Node destination;
    private Node next_hop;
    private int seq_no;
    private int hop_count;
    private Set <Node> precursor =  new  HashSet<Node>();
    private long lifeTime;
    private byte iswaiting=1;

    public byte getIswaiting() {
        return iswaiting;
    }

    public void resetIswaiting(){
        iswaiting=1;
    }
    public void setIswaiting() {
        this.iswaiting = 2;
    }

    public Route() {
    }

    public boolean isInvalid() {
        return invalid;
    }

    public void setInvalid(boolean invalid) {
        this.invalid = invalid;
    }

    //$masoud

    public String toString() {
        return "Route to " +this.getDestination()+" through "+this.getNext_hop();
    }

    public Route(Node destination, Node next_hop, int seq_no, int hop_count, Set<Node> precursor) {
        this.destination = destination;
        this.next_hop = next_hop;
        this.seq_no = seq_no;
        this.hop_count = hop_count;
        this.precursor = precursor;
    }

    public Node getDestination() {
        return destination;
    }

    public void setDestination(Node destination) {
        this.destination = destination;
    }

    public Node getNext_hop() {
        return next_hop;
    }

    public void setNext_hop(Node next_hop) {
        this.next_hop = next_hop;
    }

    public int getSeq_no() {
        return seq_no;
    }

    public void setSeq_no(int seq_no) {
        this.seq_no = seq_no;
    }

    public int getHop_count() {
        return hop_count;
    }

    public void setHop_count(int hop_count) {
        this.hop_count = hop_count;
    }

    public Set<Node> getPrecursor() {
        return precursor;
    }

    public void setPrecursor(Set<Node> precursor) {
        this.precursor = precursor;
    }

    public Long getLifeTime() {
        return lifeTime;
    }

    public void setLifeTime(Long lifeTime) {
        this.lifeTime = lifeTime;
    }

    /**
     * if the destination is previously unknown to the node, or if a previously
     * valid route to the destination expires or is marked as invalid
     * @param r
     * @return
     */
    public static boolean isBad(Route r){
        return r ==null || r.getHop_count()== Route.INFINITE
                || r.isInvalid() || r.getSeq_no()<0;
    }
}
