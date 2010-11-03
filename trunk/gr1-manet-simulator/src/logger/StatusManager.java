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

package logger;

import simulator.Node;
import simulator.Data;
import UI.Myform;
import UI.myobjects.GraphicalNode;

public class StatusManager {
    Myform myform;
    boolean test = false;
    private static StatusManager status_manager;

    private StatusManager(Myform myform) {
        this.myform = myform;
    }

    public StatusManager(boolean test) {
        this.test = test;
    }

    public static void init (Myform myForm){
         status_manager = new StatusManager(myForm);
    }
    public static void init (){
         status_manager = new StatusManager(true);
    }
    public static StatusManager get_instance(){
        return status_manager;
    }

    public void showNodeStatus(Node node,String status){
        if (!this.test){
            GraphicalNode gNode = myform.getGnodebyNode(node);
            gNode.addStatus(status);
        }
    }
    public void showRecievedData(Node receiver,Node sender, Data data){
        if (!this.test){
            GraphicalNode gNode = myform.getGnodebyNode(receiver);
            gNode.addRecievedData("From: "+sender+"\n"+data.getContent());
        }
    }
    public void NodeSend(Node sender,int type){
         if (!this.test){
            GraphicalNode gNode = myform.getGnodebyNode(sender);
            gNode.sending(type);
        }
    }
}
