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

import MYdataStructure.DGraph;
import MYdataStructure.edge_data;
import MYdataStructure.graph;
import MYdataStructure.node_data;
import Server.Game_Server;
import Server.game_service;
import algorithms.Graph_Algo;
import utils.Point3D;
import utils.StdDrawGame;

public class MyGameGUI extends Thread {
	public static graph graph;
	private static final long serialVersionUID = 6128157318970002904L;
	LinkedList<Point3D> points = new LinkedList<Point3D>();
	double X_min = Integer.MAX_VALUE;
	double X_max = Integer.MIN_VALUE;
	double Y_min = Integer.MAX_VALUE;
	double Y_max = Integer.MIN_VALUE;

	public MyGameGUI(){
		this.graph =null;
		initGUI();

	}


	public MyGameGUI(graph g)
	{
		this.graph=g;
		initGUI();
	
	}
	
	public void Scenario() {
		JFrame input = new JFrame();
		String s ="";
		s = JOptionPane.showInputDialog(
				null, "Please enter a Scenario number between 0-23");
		int sen=Integer.parseInt(s);
		if(sen<0 || sen>23) {
			JOptionPane.showMessageDialog(input, "The number that you entered isn't a Scenario number " );

		}
		else {
			game_service game = Game_Server.getServer(sen); // you have [0,23] games
			String g = game.getGraph();
			DGraph gg = new DGraph();
			gg.init(g);
			MyGameGUI graph = new MyGameGUI(gg);
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
				StdDrawGame.setPenColor(Color.ORANGE);
				StdDrawGame.filledCircle(p.x(), p.y(), 0.0001); //nodes in orange
				StdDrawGame.setPenColor(Color.BLACK);
				
				StdDrawGame.text(p.x(), p.y()+(p.y()*0.000004) , (Integer.toString(node_data.getKey())));
				Collection<edge_data> Edge = this.graph.getE(node_data.getKey());
				for (edge_data edge_data : Edge) {
					if (edge_data.getTag() ==100) {
						edge_data.setTag(0);
						StdDrawGame.setPenColor(Color.RED); 
					}
					else {
						StdDrawGame.setPenColor(Color.BLUE);

					}
					node_data dest = graph.getNode(edge_data.getDest());
					Point3D p2 = dest.getLocation();
					if (p2 != null) {
						StdDrawGame.line(p.x(), p.y(), p2.x(), p2.y());
						StdDrawGame.setPenColor(Color.MAGENTA);
						double x_place =((((((p.x()+p2.x())/2)+p2.x())/2)+p2.x())/2);
						double y_place = ((((((p.y()+p2.y())/2)+p2.y())/2)+p2.y())/2);
						StdDrawGame.filledCircle(x_place, y_place, 0.0001);
						StdDrawGame.setPenColor(Color.BLUE);
						//cut the number to only 1 digit after the point
						String toShort=Double.toString(edge_data.getWeight());
						int i=0;
						while(i<toShort.length()) {
						if(toShort.charAt(i)=='.') {
							toShort=toShort.substring(0, i+2);
						}
						i++;
					}
						StdDrawGame.text(x_place, y_place-(y_place*0.000004),toShort );

					}	

				}
			}
		}
	}

	public void initGUI() {
		StdDrawGame.setCanvasSize(600, 600);
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
		StdDrawGame.setXscale(X_min, X_max);
		StdDrawGame.setYscale(Y_min, Y_max);
		StdDrawGame.setGuiGraph(this);
		paint();
	}


	




}
