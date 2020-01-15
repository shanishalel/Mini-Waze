package gameClient;

import MYdataStructure.edge_data;
import MYdataStructure.graph;
import utils.Point3D;

/**
 * 
 * this interfance is represented a fruit struct that have all the function bellow:
 * 
	this class contains the following methods :
	counstructors, 
* @author USER
*
*/
	
public interface Fruits {

	/**
	 * get point of the fruit
	 * @return
	 */
	
	public Point3D getPoint3D ();
	
	/**
	 * get the value of the fruit
	 * @return
	 */
	public int getValue () ;
	
	/**
	 * get the type of the fruit
	 * @return
	 */
	public int getType();
	
	/**
	 * get the src of the fruit
	 * @return
	 */
	public int getSrc ();
	
	/**
	 * set the src
	 * @param Src
	 */
	public void setSrc(int Src);
	
	/**
	 * get the dest of the fruit
	 * @return
	 */
	public int getDest();
	
	/**
	 * set the dest od the fruit
	 * @param Dest
	 */
	public void setDest(int Dest);
	
	/**
	 * get the edge that the fruit on him
	 * @return
	 */
	public edge_data getEdge();
	
	/**
	 * set the edge that the fruit  on him
	 * @param Edge
	 */
	public void setEdge(edge_data Edge);
	
	/**
	 * boolean that mark the fruit as visited
	 * @return
	 */
	public boolean getVisited();
	
	/**
	 * set the boolean that mark the fruit as visited
	 * @param Visited
	 */
	public void setVisited(boolean Visited);
	
	/**
	 * init a fruit from  string 
	 * @param s
	 */
	public void init(String s);
	
	/**
	 * find a fruit place 
	 * @param gg graph
	 * @param f fruit
	 */
	public void findFruitPlace(graph gg , Fruit f) ;
	

}
