package gameClient;

import java.util.Collection;
import java.util.Hashtable;

import org.json.JSONArray;
import org.json.JSONObject;

import MYdataStructure.*;
import utils.Point3D;



/**
 * This class represent fruit struct that have 2 types of fruit : 
 	1- that represent a fruit from high to low and -1 - that represent a fruit from low to high . 
 	Every fruit composed by :    
* graph - graph that represent the graph of the scenario of the game.
* edge_data - that represent the edge that the fruit on here.  
* int type - that represent the type of the fruit (-1 is for low to high, 1 is from high to low). Notice: in our game the fruit type are girl or boy . 
* int value - that represent the value that the fruit equals to. 
* Point 3D - that represent the point on the graph were the fruit is located.
* int src - that represent the src of the edge that the fruit is located on.  
* int dest - that represent the dest of the edge that the fruit is located on.
* boolean visited - that represent if the fruit is on the path of one of the robots or no.
  
	
	this class contains the interface function . 
 * @author USER
 *
 */
public class Fruit implements Fruits{
	static double EPS= 0.0000001;
	graph graph; 
	edge_data edge;
	int TYPE;
	int VALUE;
	Point3D POINT;
	int DEST;
	int SRC;
	boolean VISITED;
	
	/**
	  *This function is the default counstrctor 
	  */
	public Fruit () {
		this.graph=graph;
		this.edge = edge;
		this.TYPE=TYPE;
		this.POINT=POINT;
		this.VALUE= VALUE;
		this.SRC =SRC;
		this.DEST=DEST;
		this.VISITED=VISITED;
	}
	
	/**
	 * This function is cunstrctor
	 * @param type - the type of the fruit (1 or -1)
	 * @param value - represent the key value
	 * @param p - represent the location f p 
	 * @param graph - this represent the graph
	 */
	public Fruit (int type , int value , Point3D p ,  graph graph) {
		this.graph=graph;
		this.edge = edge;
		this.VALUE=value;
		this.TYPE=type;
		this.POINT= new Point3D(p);
		this.SRC =SRC;
		this.DEST=DEST;
		this.VISITED=VISITED;
	}
	
	/**
	 * get the point
	 * @return
	 */
	@Override
	public Point3D getPoint3D () {
		return this.POINT;
	}
	
	/**
	 * get the value
	 * @return
	 */
	@Override
	public int getValue () {
		return this.VALUE;
	}
	
	/**
	 * get the type
	 * @return
	 */
	@Override
	public int getType() {
		return this.TYPE;
	}
	
	/**
	 * get the src
	 * @return
	 */
	@Override
	public int getSrc () {
		return this.SRC;
	}
	
	/**
	 * set the src
	 * @param Src
	 */
	@Override
	public void setSrc(int Src) {
		this.SRC =Src;
	}
	
	/**
	 * get the dest
	 * @return
	 */
	@Override
	public int getDest() {
		return this.DEST;
	}

	/**
	 * set the dest 
	 * @param Dest
	 */
	@Override
	public void setDest(int Dest) {
		this.DEST = Dest;
	}
	
	/**
	 * get the edge
	 * @return
	 */
	@Override
	public edge_data getEdge() {
		return this.edge;
	}
	
	
	/**
	 *set the edge 
	 * @param Edge
	 */
	@Override
	public void setEdge(edge_data Edge) {
		this.edge = Edge;
	}
	/**
	 * gets
	 */
	@Override
	public boolean getVisited() {
		return this.VISITED;
	}
	
	/**
	 * set boolean
	 */
	@Override
	public void setVisited(boolean Visited) {
		this.VISITED=Visited;
	}
	
	 /**
	  * This function init a fruit from the string s by reading from the json  
	  * the function reading from the json and init it to fruit struct to the 
	  * correct field. 
	  * @param s
	  */
	@Override
	public void init(String s) {
		try {
			double x=0,y=0,z=0,counter=0;
			String k = "";
			JSONObject obj = new JSONObject(s);
			JSONObject Fruits2 =obj.getJSONObject("Fruit");
				String point=(String) Fruits2.get("pos");
				for (int j = 0; j < point.length(); j++) {
					if (point.charAt(j) != ',') {
						k+=point.charAt(j); 
						 if (counter==2 && j == point.length()-1) {
								z= Double.parseDouble(k);
								counter=0;
								k="";
							}
					}
					else {
						if (counter==0) {
							x= Double.parseDouble(k);
							counter++;
							k="";
						}
						else if (counter==1) {
							y= Double.parseDouble(k);
							counter++;
							k="";
						}
					}
				}
				Point3D p = new Point3D(x,y,z);
				this.VALUE = Fruits2.getInt("value");
				this.TYPE =  Fruits2.getInt("type");
				this.POINT=new Point3D(p);
		}
		catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * This function gets graph and fruit and set the dest, src and edge 
	 * the function pass all the nodes and check for every node if the dest between the 2 nodes is
	 * equals to the dest between the first node to the fruit and form the fruit to the next node. 
	 * if it does she will set the edge of the fruit to be the edge btween them also the src and dest.
	 * @param gg
	 * @param f
	 */
	@Override
	public void findFruitPlace(graph gg , Fruit f) {
		Point3D p = f.getPoint3D();
		Collection <node_data> Nodes = gg.getV();
		for (node_data node_data : Nodes) {
			Collection<edge_data> neighbors=gg.getE(node_data.getKey());
			for (edge_data edge_data : neighbors) {
				double dis1 = distans(node_data.getLocation().x() , node_data.getLocation().y(), p.x() , p.y());
				int dest = edge_data.getDest();
				double dis2 = distans(gg.getNode(dest).getLocation().x() , gg.getNode(dest).getLocation().y() ,p.x() , p.y());
				double dis_All = distans(node_data.getLocation().x() , node_data.getLocation().y() ,gg.getNode(dest).getLocation().x() , gg.getNode(dest).getLocation().y() );	
				if (Math.abs((dis1+dis2) - dis_All)<= EPS) {
					f.setDest(dest);
					f.setSrc(node_data.getKey());
					f.setEdge(edge_data);
				}
			}
		}
	}
	
	
	private double distans(double x, double y, double x2, double y2) {
		double x_dis = Math.pow((x-x2), 2);
		double y_dis = Math.pow((y-y2), 2);
		double dis = Math.sqrt((x_dis + y_dis));
		return dis;
		
	}
}




