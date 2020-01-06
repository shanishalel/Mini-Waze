package algorithms;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Set;

import dataStructure.dGraph;
import dataStructure.Nodes;
import dataStructure.edge_data;
import dataStructure.graph;
import dataStructure.node_data;
import utils.Point3D;
/**
 * This empty class represents the set of graph-theory algorithms
 * which should be implemented as part of Ex2 - Do edit this class.
 * @author 
 *
 */
public class Graph_Algo implements graph_algorithms, java.io.Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -6724965554869338604L;
	private graph graph ;

	public Graph_Algo(graph graph) {
		this.graph=graph;
	}
	
	public Graph_Algo() {
		this.graph=new dGraph();
	}

	@Override
	public void init(graph g) {
		this.graph=g;
	}

	@Override
	public void init(String file_name) {
		Graph_Algo save=new Graph_Algo();
		save=null;
		try {
			FileInputStream file = new FileInputStream(file_name); 
			ObjectInputStream in = new ObjectInputStream(file); 
			save =(Graph_Algo) in.readObject();
			this.graph = save.graph;
			in.close();
			file.close();
		}
		catch(Exception ex) {
			ex.printStackTrace();		}
	}



	@Override
	public void save(String file_name) {
		try
		{    
			FileOutputStream file = new FileOutputStream(file_name);
			ObjectOutputStream out = new ObjectOutputStream(file); 
			out.writeObject(this); 
			out.close(); 
			file.close(); 


		}   
		catch(IOException ex) 
		{ 

			System.out.println("IOException is caught"); 
		} 


	}

	@Override
	public boolean isConnected() {
		int count =0;
		Collection <node_data> Nodes = this.graph.getV();
		int size=0;
		for (node_data node_data : Nodes) {
			setWeight(node_data.getKey());
			size=0;
			for (node_data node_data2 : Nodes) {
				size=+NodeCanGetTo (node_data2, size);
				if(size==Nodes.size()) {
					count++;
				}
			}


		}
		if(count==Nodes.size()) {
			return true;
		}

		return false;
	}

/**
 * this function will return the number of nodes she can gets to
 * @param current
 * @param size
 * @return
 */
	private int NodeCanGetTo ( node_data current, int size) {
		Collection<edge_data> neighbors=graph.getE(current.getKey()); //the neighbors of current  
		for(edge_data edge:neighbors) {
			if(graph.getNode(edge.getSrc()).getTag()==0) {
				graph.getNode(edge.getSrc()).setTag(1);
				size++;
			}else {
				return size;
			}
		}

		return size;
	}

	
	@Override
	public double shortestPathDist(int src, int dest) {
		setWeight(src);
		Dijkstras(src);	
		return graph.getNode(dest).getWeight();
	}


	/**
	 * This Function is finding the shorts path in the graph by check what path is cost less
	 * and choose her every junction, she will change the weight for every Node she will get to .
	 * @param src
	 */

	private void Dijkstras(int src) {
		node_data nowNode = graph.getNode(src);
		ArrayList<node_data> currentmin = new ArrayList<node_data>();
		currentmin.add(nowNode);
		while (!currentmin.isEmpty()) {
			nowNode = currentmin.get(0);
			currentmin.remove(0);
			if (nowNode.getTag() == 0) {
				nowNode.setTag(1);
				Collection <edge_data> Edges = this.graph.getE(nowNode.getKey());
				for (edge_data edge_data : Edges) {
					node_data nei_node = graph.getNode(edge_data.getDest());
					double weight_node = nowNode.getWeight();
					double weight_nie = nei_node.getWeight();
					double weight_edge = edge_data.getWeight();

					if (weight_node + weight_edge < weight_nie  ) {
						nei_node.setWeight(weight_node + weight_edge);
						nei_node.setInfo(nowNode.getKey()+"");
						if (nei_node.getTag() == 0) {
							int index = searchInsert(currentmin ,nei_node.getWeight());
							currentmin.add(index, nei_node);
						}
					}
				}
			}
		}

	}
	/**
	 * This function is made a search insert so we will know were we should put the Node
	 * @param arr
	 * @param target
	 * @return
	 */
	private static int searchInsert(ArrayList<node_data> arr, double target) {
		int hight = arr.size()-1;
		int low =0;
		int mid = low +((hight-low)/2);
		if (arr.size() == 0) {
			return 0;
		}
		else {
			while (low <= hight) {
				if (arr.get(mid).getWeight() == target ) {
					return mid;
				}
				if (arr.get(mid).getWeight() > target ) {
					hight=mid-1;
				}
				if (arr.get(mid).getWeight() < target  ) {
					low = mid+1;
				}
				mid = low +((hight-low)/2);
			}
		}
		return mid;
	}

	/**
	 * This function set the weight, and the tag to 0
	 * @param src
	 */

	private void setWeight(int src) {
		Collection <node_data> nodes = this.graph.getV();
		for (node_data Collection : nodes) {
			Collection.setWeight(999999999);
			Collection.setTag(0);
		}
		graph.getNode(src).setWeight(0);

	}


	@Override
	public List<node_data> shortestPath(int src, int dest) {
		try {
			connected(src,dest);
			ArrayList<node_data> shortPath = new ArrayList<node_data>();
			if (dest == src ) {
			shortPath.add(0,graph.getNode(src));
			return shortPath;
			}
			setWeight(src);
			Dijkstras(src);
			String info = graph.getNode(dest).getInfo();
			int key = Integer.parseInt(info);
			shortPath.add(0,graph.getNode(dest));
			shortPath.add(0,graph.getNode(key));
			while (!shortPath.contains(graph.getNode(src))) {	
				info = graph.getNode(key).getInfo();
				key = Integer.parseInt(info);
				shortPath.add(0,graph.getNode(key));
			}
			return shortPath;
		}
		catch(Exception e) {
			return null;
		}
		
	}



	/**
	 * this function check if the node that we entered there src and dest are connected
	 * if they aren't connect we return false
	 * @param src
	 * @param dest
	 * @return
	 */
	public boolean connected(int src, int dest) {
		double shortestPathDist=shortestPathDist (src, dest);
		if(shortestPathDist==9.99999999E8) {
			return false;
		}
		return true;
	}

	@Override
	public List<node_data> TSP(List<Integer> targets) {
		ArrayList<node_data> Path = new ArrayList<node_data>();
		ArrayList<node_data> temp = new ArrayList<node_data>();
		if ( targets.size() == 0) return null;
		try{if ( targets.size() == 1) {
			node_data node = graph.getNode(targets.get(0));
			Path.add(node);
			return Path;
		}
		for (int i = 0; i+1 < targets.size(); i++) {
			if (targets.get(i) ==  targets.get(i+1)) {
				targets.remove(i);
				i--;
			}
			else {
			temp = (ArrayList<node_data>) shortestPath(targets.get(i), targets.get(i+1));
			if (i > 0 ) {
				temp.remove(0);
			}
			Path.addAll(temp);
			}
		}
		return Path;
	}
		catch(Exception e) {
			return null;
		}
	}

	@Override
	public graph copy() {
		dGraph copy_Dgraph=new dGraph();
		Collection <node_data> Nodes = this.graph.getV();
		for(node_data node_data:Nodes) {
			//copy the node
			Nodes copy_node= new Nodes(node_data.getKey(),node_data.getLocation());
			copy_node.setInfo(node_data.getInfo());
			copy_node.setTag(node_data.getTag());
			copy_node.setWeight(node_data.getWeight());
			copy_Dgraph.addNode(copy_node);
			Collection<edge_data> Edges = this.graph.getE(node_data.getKey());
			for(edge_data edge_data: Edges ) {
				copy_Dgraph.connect(edge_data.getSrc(), edge_data.getDest(), edge_data.getWeight());
			}

		}
		Graph_Algo copy = new Graph_Algo();
		copy.init(copy_Dgraph);
		return copy.graph;

	}

}
