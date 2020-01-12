package gameClient;

import java.util.Collection;
import java.util.Hashtable;

import org.json.JSONArray;
import org.json.JSONObject;

import MYdataStructure.*;
import utils.Point3D;

public class Fruit {
	static double EPS= 0.0000001;
	graph graph;
	edge_data edge;
	int TYPE;
	int VALUE;
	Point3D POINT;
	int DEST;
	int SRC;
	
	public Fruit () {
		this.graph=graph;
		this.edge = edge;
		this.TYPE=TYPE;
		this.POINT=POINT;
		this.VALUE= VALUE;
		this.SRC =SRC;
		this.DEST=DEST;
	}
	
	public Fruit (int type , int value , Point3D p ,  graph graph) {
		this.graph=graph;
		this.edge = edge;
		this.VALUE=value;
		this.TYPE=type;
		this.POINT= new Point3D(p);
		this.SRC =SRC;
		this.DEST=DEST;
	}
	
	public Point3D getPoint3D () {
		return this.POINT;
	}
	
	public int getValue () {
		return this.VALUE;
	}
	
	public int getType() {
		return this.TYPE;
	}
	
	public int getSrc () {
		return this.SRC;
	}
	
	public void setSrc(int Src) {
		this.SRC =Src;
	}
	
	public int getDest() {
		return this.DEST;
	}
	
	public void setDest(int Dest) {
		this.DEST = Dest;
	}
	
	public edge_data getEdge() {
		return this.edge;
	}
	
	public void setEdge(edge_data Edge) {
		this.edge = Edge;
	}
	

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
//				Fruit f = new Fruit(type, value, p);
		}
		catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	
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




