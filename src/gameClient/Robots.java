package gameClient;

import MYdataStructure.node_data;
import utils.Point3D;





/**
 * this interface represent a robot struct 
 * every robot will have the following function
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
