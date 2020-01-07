package gui;

import java.awt.Color;
import java.awt.Graphics;
import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedList;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

import MYdataStructure.edge_data;
import MYdataStructure.graph;
import MYdataStructure.node_data;
import algorithms.Graph_Algo;
import utils.Point3D;
import utils.StdDraw;

public class Gui_Graph1 {
	public static graph graph;
	private static final long serialVersionUID = 6128157318970002904L;
	LinkedList<Point3D> points = new LinkedList<Point3D>();
	double X_min = Integer.MAX_VALUE;
	double X_max = Integer.MIN_VALUE;
	double Y_min = Integer.MAX_VALUE;
	double Y_max = Integer.MIN_VALUE;

	public Gui_Graph1(){
		this.graph =null;
		initGUI();

	}


	public Gui_Graph1(graph g)
	{
		this.graph=g;
		initGUI();
	
	}

	/**
	 * this function will save a graph to file
	 */
	public void Savegraph() {
		Graph_Algo gg = new Graph_Algo();
		gg.init(this.graph);
		//		 parent component of the dialog
		JFrame parentFrame = new JFrame();
		JFileChooser fileChooser = new JFileChooser();
		fileChooser.setDialogTitle("Specify a file to save");   
		int userSelection = fileChooser.showSaveDialog(parentFrame);

		if (userSelection == JFileChooser.APPROVE_OPTION) {
			File fileToSave = fileChooser.getSelectedFile();
			String file= fileToSave.getAbsolutePath();
			gg.save(file);		
			System.out.println("Save as file: " + fileToSave.getAbsolutePath());
		}

	}

	/**
	 * this function will paint the graph
	 */
	public void paint() {
		if(this.graph !=null) {
			Collection <node_data> Nodes = this.graph.getV();
			for (node_data node_data : Nodes) {
				Point3D p = node_data.getLocation();
				StdDraw.setPenColor(Color.ORANGE);
				StdDraw.filledCircle(p.x(), p.y(), 0.0001); //nodes in orange
				StdDraw.setPenColor(Color.BLACK);
				
				StdDraw.text(p.x()+p.y(), p.y()*0.01 , (Integer.toString(node_data.getKey())));
				Collection<edge_data> Edge = this.graph.getE(node_data.getKey());
				for (edge_data edge_data : Edge) {
					if (edge_data.getTag() ==100) {
						edge_data.setTag(0);
						StdDraw.setPenColor(Color.RED); 
					}
					else {
						StdDraw.setPenColor(Color.BLUE);

					}
					node_data dest = graph.getNode(edge_data.getDest());
					Point3D p2 = dest.getLocation();
					if (p2 != null) {
						StdDraw.line(p.x(), p.y(), p2.x(), p2.y());
						StdDraw.setPenColor(Color.MAGENTA);
						double x_place =((((((p.x()+p2.x())/2)+p2.x())/2)+p2.x())/2);
						double y_place = ((((((p.y()+p2.y())/2)+p2.y())/2)+p2.y())/2);
						StdDraw.filledCircle(x_place, y_place, 0.0001);
						StdDraw.setPenColor(Color.BLUE);
						//cut the number to only 1 digit after the point
						String toShort=Double.toString(edge_data.getWeight());
						int i=0;
						while(i<toShort.length()) {
						if(toShort.charAt(i)=='.') {
							toShort=toShort.substring(0, i+2);
						}
						i++;
					}
						System.out.println(toShort);
						StdDraw.text(x_place, y_place,toShort );

					}	

				}
			}
		}
	}

	/**
	 * this function will load a graph and paint it 
	 */
	public void Loadgraph() {
		Graph_Algo gg = new Graph_Algo();
		JFrame parentFrame = new JFrame();
		JFileChooser fileChooser = new JFileChooser();
		fileChooser.setDialogTitle("Specify a file to load");   
		int userSelection = fileChooser.showOpenDialog(parentFrame);

		if (userSelection == JFileChooser.APPROVE_OPTION) {
			File fileToLoad = fileChooser.getSelectedFile();
			String file= fileToLoad.getAbsolutePath();
			gg.init(file);
			paint();
			System.out.println("Load from file: " + fileToLoad.getAbsolutePath());
		}
	}

	/**
	 * the function will operate the shortest path dist and return the distance 
	 */
	public void shortestPathDist() {
		JFrame input = new JFrame();
		String src = JOptionPane.showInputDialog(
				null, "what is the key for src?");
		String dest = JOptionPane.showInputDialog(
				null, "what is the key for dest?");
		try {
			Graph_Algo gg = new Graph_Algo();
			gg.init(this.graph);
			double ans = gg.shortestPathDist(Integer.parseInt(src), Integer.parseInt(dest));
			String s = Double.toString(ans);	
			JOptionPane.showMessageDialog(input, "the shortest distance is: " + s);
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * the function will operate the shortest path and return the shortest path to the user 
	 * and paint the path in red color
	 */
	public void shortestPath() {
		JFrame input = new JFrame();
		String s = "";
		String src = JOptionPane.showInputDialog(
				null, "what is the key for src?");
		String dest = JOptionPane.showInputDialog(
				null, "what is the key for dest?");
		if (!src.equals(dest)) {
			try {
				Graph_Algo gg = new Graph_Algo();
				gg.init(this.graph);
				ArrayList<node_data> shortPath = new ArrayList<node_data>();
				shortPath = (ArrayList<node_data>) gg.shortestPath(Integer.parseInt(src), Integer.parseInt(dest));
				for (int i =0 ; i+1 < shortPath.size() ; i++) {
					this.graph.getEdge(shortPath.get(i).getKey(), shortPath.get(i+1).getKey()).setTag(100);
					s+= shortPath.get(i).getKey() + "--> ";
				}
				s+= shortPath.get(shortPath.size()-1).getKey();
				paint();
				JOptionPane.showMessageDialog(input, "the shortest path is: " +s);

			}
			catch (Exception e) {
				JOptionPane.showMessageDialog(input, "the shortest path is: null" );
			}
		}
	}
	/**
	 * the function will operate the tsp function, return the user the shortest path and paint 
	 * the path in red color
	 */

	public void TSP() {
		JFrame input = new JFrame();
		Graph_Algo gr = new Graph_Algo();
		gr.init(this.graph);
		ArrayList<Integer> targets = new ArrayList<Integer>();
		String src = "";
		String s = "";
		do {
			src = JOptionPane.showInputDialog(
					null, "get a key if you want to Exit get in Exit");
			if ((!src.equals("Exit"))) {
				targets.add(Integer.parseInt(src));
			}
		}while(!src.equals("Exit"));
		ArrayList<node_data> shortPath = new ArrayList<node_data>();
		shortPath = (ArrayList<node_data>) gr.TSP(targets);
		if (shortPath != null ) {
			for (int i =0 ; i+1 < shortPath.size() ; i++) {
				this.graph.getEdge(shortPath.get(i).getKey(), shortPath.get(i+1).getKey()).setTag(100);
				s+= shortPath.get(i).getKey() + "--> ";
			}
			s+= shortPath.get(shortPath.size()-1).getKey();
			paint();
			JOptionPane.showMessageDialog(input, "the shortest path is: " +s);
		}
		if(shortPath==null) {
			JOptionPane.showMessageDialog(input, "the shortest path is: null ");
		}
	}

	public void isConnected() {
		JFrame input = new JFrame();
		Graph_Algo gr = new Graph_Algo();
		gr.init(this.graph);
		boolean ans = gr.isConnected();
		if (ans == true) {
			JOptionPane.showMessageDialog(input, "this graph is connected");
		}
		else {
			JOptionPane.showMessageDialog(input, "the graph is not connected");
		}

	}


	public void initGUI() {
		StdDraw.setCanvasSize(600, 600);
		double X_min = Integer.MAX_VALUE;
		double X_max = Integer.MIN_VALUE;
		double Y_min = Integer.MAX_VALUE;
		double Y_max = Integer.MIN_VALUE;
		
		// rescale the coordinate system
		
		Collection<node_data> nodes=graph.getV();   
		for(node_data node:nodes) {
		if(node.getLocation().x()>X_max) {
			X_max=(node.getLocation().x());
		}
		if(node.getLocation().x()<X_min) {
			X_min=(node.getLocation().x());
		}
		if(node.getLocation().y()>Y_max) {
			Y_max=(node.getLocation().y());
		}
		if(node.getLocation().y()<Y_min) {
			Y_min=(node.getLocation().y());
		}

		}	
		StdDraw.setXscale(X_min, X_max);
		StdDraw.setYscale(Y_min, Y_max);
		StdDraw.setGuiGraph(this);
		paint();
	}


	




}
