package MYdataStructure;


import java.awt.Point;
import java.io.BufferedReader;

import java.io.FileReader;
import java.io.IOException;
import java.util.Collection;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Set;

import org.json.JSONArray;
import org.json.JSONObject;

import MYdataStructure.edge_data;
import MYdataStructure.graph;
import MYdataStructure.node_data;
import utils.Point3D;



public class DGraph implements graph, java.io.Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 7216877636591903122L;
	private int MC;
	private int countEdge;
	//this hash will represents the node
	Hashtable<Integer, node_data> Nodes;

	//The hash represent an edges by Hashtable<node_data, Hashtable<Integer, edge_data>>   
	Hashtable<node_data, Hashtable<Integer, edge_data>>  Edge;

	public DGraph() {
		this.Nodes = new Hashtable<Integer, node_data>();
		this.Edge= new Hashtable<node_data, Hashtable<Integer, edge_data>>();
		this.MC=0;
		this.countEdge=0;
	}

	@Override
	public node_data getNode(int key) {		
		return Nodes.get(key);
	}

	@Override
	public edge_data getEdge(int src, int dest) {
		node_data Src = Nodes.get(src);
		edge_data edge = Edge.get(Src).get(dest);
		return edge;
	}

	@Override
	public void addNode(node_data n) {
		Nodes.put(n.getKey(), n);
		Edge.put(n, new Hashtable<Integer, edge_data>());
		MC++;
	}

	@Override
	public void connect(int src, int dest, double w) {
		if (src==dest) {
			return;
		}
		if (w < 0) {
			throw new RuntimeException("You cant get a negative wight");
		}
		if (Nodes.containsKey(src) && Nodes.containsKey(dest)) {
			edge_data edge = new Edges(Nodes.get(src) , Nodes.get(dest), w);
			Edge.get(Nodes.get(src)).put(dest, edge);
			this.countEdge++;
			MC++;
		}
	}

	@Override
	public Collection<node_data> getV() {
		return Nodes.values();
	}

	@Override
	public Collection<edge_data> getE(int node_id) {
		return 	Edge.get(Nodes.get(node_id)).values();
	}


	@Override
	public node_data removeNode(int key) {
		/*we will run on the Hashtable of the edges and check for every edge if the source or the dest is 
		   the node of the key that we get*/
		node_data to_remove=Nodes.get(key); //the node that we need to remove
		Set <node_data> nodes = Edge.keySet();
		for (node_data node_data : nodes) {
			edge_data check = Edge.get(node_data).remove(key);
			if (check != null ) {
				this.countEdge--;
			}
		}
		this.countEdge -= Edge.get(to_remove).size();
		Edge.remove(to_remove);
		MC++;
		return Nodes.remove(key);
	}

	@Override
	public edge_data removeEdge(int src, int dest) {
		node_data srcNode= Nodes.get(src); //get the node_data of src
		edge_data ans = Edge.get(srcNode).remove(dest); //remove the edge that start from src and ends in dest
		if ( ans != null ) {
			this.countEdge--;
			this.MC++;
		}
		return ans;
	}

	@Override
	public int nodeSize() {
		return Nodes.size();
	}

	@Override
	public int edgeSize() {
		return this.countEdge;
	}

	@Override
	public int getMC() {
		return MC;
	}
	
/**
 * this function will get a string represent a graph and init a graph from her
 * @param g
 */
	public void init(String g)  {
		try {
			String s="";
			double x=0,y=0,z=0,counter=0;
			JSONObject obj = new JSONObject(g);
			JSONArray Edges =obj.getJSONArray("Edges");
			JSONArray Nodes =obj.getJSONArray("Nodes");
			//build Nodes
			for(int i=0;i<Nodes.length();i++) {
				JSONObject Node=(JSONObject)Nodes.get(i);
				String point=(String) Node.get("pos");
				for (int j = 0; j < point.length(); j++) {
					if (point.charAt(j) != ',') {
						s+=point.charAt(j); 
						 if (counter==2 && j == point.length()-1) {
								z= Double.parseDouble(s);
								counter=0;
								s="";
							}
					}
					else {
						if (counter==0) {
							x= Double.parseDouble(s);
							counter++;
							s="";
						}
						else if (counter==1) {
							y= Double.parseDouble(s);
							counter++;
							s="";
						}
					}
				}
				Point3D p = new Point3D(x,y,z);
				int id = Node.getInt("id");
				Nodes n=new Nodes(id,p);
				this.addNode(n);
			}
			//build Edges
			for(int i=0;i<Edges.length();i++) {
				JSONObject Edge=(JSONObject)Edges.get(i);
				int src = Edge.getInt("src");
				int dest = Edge.getInt("dest");
				double w = Edge.getDouble("w");
				this.connect(src, dest, w);
			}

		}
		catch(Exception e) {
			e.printStackTrace();
		}
		
	}

}
