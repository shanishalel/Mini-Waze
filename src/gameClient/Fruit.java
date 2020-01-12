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
 	every fruit have :
 	graph graph;
	edge_data edge;
	int TYPE;
	int VALUE;
	Point3D POINT;
	int DEST;
	int SRC;
	
	this class contains the following methods :
	counstructors, 
 * @author USER
 *
 */
public class Fruit {
	static double EPS= 0.0000001;
	graph graph;
	edge_data edge;
	int TYPE;
	int VALUE;
	Point3D POINT;
	int DEST;
	int SRC;
	
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
	}
	
	/**
	 * get the point
	 * @return
	 */
	public Point3D getPoint3D () {
		return this.POINT;
	}
	
	/**
	 * get the value
	 * @return
	 */
	public int getValue () {
		return this.VALUE;
	}
	
	/**
	 * get the type
	 * @return
	 */
	public int getType() {
		return this.TYPE;
	}
	
	/**
	 * get the src
	 * @return
	 */
	public int getSrc () {
		return this.SRC;
	}
	
	/**
	 * set the src
	 * @param Src
	 */
	public void setSrc(int Src) {
		this.SRC =Src;
	}
	
	/**
	 * get the dest
	 * @return
	 */
	public int getDest() {
		return this.DEST;
	}

	/**
	 * set the dest 
	 * @param Dest
	 */
	public void setDest(int Dest) {
		this.DEST = Dest;
	}
	
	/**
	 * get the edge
	 * @return
	 */
	public edge_data getEdge() {
		return this.edge;
	}
	
	
	/**
	 *set the edge 
	 * @param Edge
	 */
	public void setEdge(edge_data Edge) {
		this.edge = Edge;
	}
	
	 /**
	  * This function init a fruit from the string s by reading from the json 
	  * @param s
	  */
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
	 * @param gg
	 * @param f
	 */
	public static void findFruitPlace(graph gg , Fruit f) {
		Point3D p = f.getPoint3D();
		Collection <node_data> Nodes = gg.getV();
		for (node_data node_data : Nodes) {
			Collection<edge_data> neighbors=gg.getE(node_data.getKey());
			for (edge_data edge_data : neighbors) {
				double x = Math.abs(node_data.getLocation().x() - p.x());
				int dest = edge_data.getDest();
				double x2 =Math.abs(gg.getNode(dest).getLocation().x() - p.x());
				double xAll = Math.abs(gg.getNode(dest).getLocation().x() - node_data.getLocation().x());
				if (Math.abs((x2+x) - xAll )<= EPS) {
					f.setDest(dest);
					f.setSrc(node_data.getKey());
					f.setEdge(edge_data);
				}
			}
		}
	}
}




