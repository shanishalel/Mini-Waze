package gameClient;

import MYdataStructure.node_data;
import utils.Point3D;





/**
 * this interface represent a robot struct, the location of the robots on the graph is located by us, we locate them in the most closet location
 * to the fruit (so we will be able to get to the max point in the game). the robots moves and points from the fruot that he eat is update from
 * the server. the robot have the next fields:
 * * int id- represent the key of the node that he is located.
 * * int speed- gets from the server the speed change during the game (accourding to the fruit the robot eat). 
 * * point 3D- location of the robot. 
 * * int value- value of the robot according to the fruit he eat.
 * * int src- the src of the point the robot locate.
 * * int dest - the dest of the robot (used for biluding path).
 * 
 * 
 * main function:
 * * init- this fucntion init the robot from json file by reading from json file and set the according field.
 * * boolean IsDone- this function mark as done robot who gets to his dest 
 * @author USER
 *
 */
public interface Robots {
	
	

	/**
	 * gets the id
	 * @return
	 */
	
	public int getID ();
	
	/**
	 * set the id
	 * @param ID
	 */
	public void setID(int ID);
	
	/**
	 * get the speed
	 * @return
	 */
	public int getSpeed ();
	
	
	/**
	 * set the speed
	 * @param SPEED
	 */
	public void setSpeed(int SPEED) ;
	
	
	/**
	 * get the point of the location
	 * @return
	 */
	public Point3D getPoint3D ();
	
	
	

	/**
	 * set the point 3d
	 * @param p
	 */
	public void setPoint3D (Point3D p);
	
	
	/**
	 * get the value
	 * @return
	 */
	public int getValue ();
	
	/**
	 * get the src
	 * @return
	 */
	public int getSrc();
	
	/**
	 * set the src
	 * @param Src
	 */
	public void setSrc(int Src) ;
	
	
	/**
	 * get the dest
	 * @return
	 */
	public int getDest();
	
	/**
	 * set the dest
	 * @param Dest
	 */
	public void setDest(int Dest);
	
	/**
	 * This function gets string s and init from her the robot all the information she needs, 
	   by reading from the json. 
	 * @param s
	 */
	public void init (String s);

	/**
	 * boolean that will mark if the robot is done the path that was made for him
	 * @param dest
	 * @return
	 */
	public boolean IsDone(node_data dest);

}
