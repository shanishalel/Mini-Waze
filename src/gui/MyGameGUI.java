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
import gameClient.Fruit;
import gameClient.Robot;
import utils.Point3D;
import utils.StdDrawGame;

public class MyGameGUI extends Thread {
	public static graph graph;
	public static Fruit fruit;
	public static Robot robot;
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

	public MyGameGUI(graph g , Fruit f , Robot r)
	{
		this.graph=g;
		this.fruit=f;
		this.robot=r;
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
			String info = game.toString();
			// fruit 
			ArrayList<String> fruit =  (ArrayList<String>) game.getFruits();
			Fruit f = new Fruit(); 
			for (int i = 0; i < fruit.size(); i++) {
				f.init(fruit.get(i));
			}
			//robot
			Robot r=new Robot();
			r.init(game.toString());		
			Collection<node_data> node = graph.getV();
			Collection<Robot> rob = r.getR();
			int size = node.size();
			for (Robot robot : rob) {
				int rnd = 0;
				do {
				rnd = (int) (Math.random()*size);
				if (graph.getNode(rnd) != null) {
					Point3D po = graph.getNode(rnd).getLocation();
					robot.setPoint3D(po);
					game.addRobot(rnd);
				}
				} while (graph.getNode(rnd) == null);
			}
				MyGameGUI GG= new MyGameGUI(gg ,f,r);
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

		public void paintFruit() {
			if ( this.fruit != null ) {
				Collection <Fruit> Fruits = this.fruit.getF();
				for (Fruit fruit : Fruits) {
					Point3D p = fruit.getPoint3D();
					StdDrawGame.setPenColor(Color.RED);
					StdDrawGame.filledCircle(p.x(), p.y(), 0.0001); //nodes in orange
					StdDrawGame.setPenColor(Color.BLACK);

					StdDrawGame.text(p.x(), p.y()+(p.y()*0.000004) , (Integer.toString(fruit.getValue())));
				}


			}

		}

		public void paintRobot() {
			if(this.robot!=null) {
				Collection <Robot> robots = this.robot.getR();
				for (Robot robot : robots) {
					Point3D p = robot.getPoint3D();
					StdDrawGame.setPenColor(Color.GREEN);
					StdDrawGame.filledCircle(p.x(), p.y(), 0.0001); //nodes in orange
					StdDrawGame.setPenColor(Color.BLACK);

					StdDrawGame.text(p.x(), p.y()+(p.y()*0.000004) , (Integer.toString(robot.getValue())));
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
			paintFruit();
			paintRobot();
		}







	}
