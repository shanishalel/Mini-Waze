package gui;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.MouseListener;
import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

import org.json.JSONException;
import org.json.JSONObject;

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

/**
 * This class represents the gui graph that we initalize , every MyGameGui have :
    public  graph graph; - graph object
	Hashtable<Point3D, Fruit> fruits; - hashtable for the fruits
	Hashtable<Integer, Robot> robots; - hashtable for the robots
	LinkedList<Point3D> points = new LinkedList<Point3D>(); - linked list of points
	double X_min = Integer.MAX_VALUE; - the x min value in the frame
	double X_max = Integer.MIN_VALUE; - the x max value in the frame
	double Y_min = Integer.MAX_VALUE; - the y min value in the frame
	double Y_max = Integer.MIN_VALUE; - the y max value in the frame
 * 
 * @author USER
 *
 */
public class MyGameGUI  {
	public  graph graph;
	Hashtable<Point3D, Fruit> fruits;
	Hashtable<Integer, Robot> robots;
	private static final long serialVersionUID = 6128157318970002904L;
	LinkedList<Point3D> points = new LinkedList<Point3D>();
	double X_min = Integer.MAX_VALUE;
	double X_max = Integer.MIN_VALUE;
	double Y_min = Integer.MAX_VALUE;
	double Y_max = Integer.MIN_VALUE;
	double x;
	double y;
	boolean isRobot = false;

	/**
	 * This function is a default constructor
	 */
	public MyGameGUI(){
		this.graph =null;
		initGUI();

	}

	/**
	 * This function is constructor that get graph g and init int it to gui
	 * @param g
	 */
	public MyGameGUI(graph g)
	{
		this.graph=g;
		initGUI();
	}

	/**
	 * This function is constructor that gets graph g, fruit f, roobot r 
	 * @param g
	 * @param f
	 * @param r
	 */
	public MyGameGUI(graph g , Fruit f , Robot r)
	{
		this.graph=g;
		initGUI();
	}

	/**
	 * This function is play the manual choice and send it to move manual
	 */
	public void PlayManual() {
		try {
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
				this.graph =gg;
				String info = game.toString();
				// fruit
				if (fruits == null ) {
					fruits= new Hashtable<Point3D, Fruit>();
				}
				for (String  fruit : game.getFruits()) {
					Fruit f = new Fruit();
					f.init(fruit);
					Point3D p_f	=f.getPoint3D();
					f.findFruitPlace(gg , f);
					fruits.put(p_f, f);
				}
				//robot
				Robot r=new Robot();
				JSONObject obj = new JSONObject(info);
				JSONObject Robot =obj.getJSONObject("GameServer");
				int amountRobot = Robot.getInt("robots");
				Collection<node_data> node = graph.getV();
				int size = node.size();
				int rnd = 0;
				if (robots == null ) {
					robots = new Hashtable<Integer, Robot>();
				}
				int counter=0;
				Set <Point3D> allFruits = fruits.keySet();
				for (Point3D Point : allFruits) {
					if (counter < fruits.size()) {
						if (counter >= amountRobot ) {
							break;
						}
						Fruit placeFruit = fruits.get(Point);
						game.addRobot(placeFruit.getSrc());
						counter++;
					}
					else {
						while (counter < amountRobot) {
							rnd = (int) (Math.random()*size);
							if (graph.getNode(rnd) != null) {
								game.addRobot(rnd);
								counter++;
							}
						}
					}
				}
				for (String robo : game.getRobots()) {
					Robot ro = new Robot();
					ro.init(robo);
					int id = ro.getID();
					robots.put(id, ro);
				}
				initGUI();
				moveManual(game);
			}
		}
		catch (Exception e) {
			e.printStackTrace();
		}			
	}


	/**
	 * This function is playing the automatic choice, and sent it to moveautomatic
	 */
	public void Playautomatic() {
		try {
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
				this.graph =gg;
				String info = game.toString();
				// fruit
				if (fruits == null ) {
					fruits= new Hashtable<Point3D, Fruit>();
				}
				for (String  fruit : game.getFruits()) {
					Fruit f = new Fruit();
					f.init(fruit);
					Point3D p_f	=f.getPoint3D();
					f.findFruitPlace(gg , f);
					fruits.put(p_f, f);
				}
				//robot
				Robot r=new Robot();
				JSONObject obj = new JSONObject(info);
				JSONObject Robot =obj.getJSONObject("GameServer");
				int amountRobot = Robot.getInt("robots");
				Collection<node_data> node = graph.getV();
				int size = node.size();
				int rnd = 0;
				if (robots == null ) {
					robots = new Hashtable<Integer, Robot>();
				}
				int counter=0;
				Set <Point3D> allFruits = fruits.keySet();
				for (Point3D Point : allFruits) {
					if (counter < fruits.size()) {
						if (counter >= amountRobot ) {
							break;
						}
						Fruit placeFruit = fruits.get(Point);
						r.setSrc(placeFruit.getSrc());
						game.addRobot(placeFruit.getSrc());
						counter++;
					}
					else {
						while (counter < amountRobot) {
							rnd = (int) (Math.random()*size);
							if (graph.getNode(rnd) != null) {
								game.addRobot(rnd);
								counter++;
							}
						} 
					}
				}
				for (String robo : game.getRobots()) {
					Robot ro = new Robot();
					ro.init(robo);
					int id = ro.getID();
					robots.put(id, ro);
				}
				initGUI();
				startGameNow(game, gg);
			}

		}
		catch (Exception e) {
			e.printStackTrace();
		}			
	}


	/**
	 * This function gets game and graph start the game and while the game run 
	 * she send the parameters to move robots
	 * @param game
	 * @param gg
	 */
	public void startGameNow(game_service game ,graph gg) {
		game.startGame();
		while(game.isRunning()) {
			smartMove(game , gg);
		}
		System.out.println(game.toString());
	}

	/**
	 * Ths function is the smart move
	 * @param game
	 * @param gg
	 */
	private void smartMove(game_service game, graph gg) {
		double min =Integer.MAX_VALUE , temp=0;
		List<node_data> ListGr = new ArrayList<node_data>();
		Graph_Algo graphA = new Graph_Algo(gg);
		List<String> log = game.move();
		if(log!=null) {
			try {
				String info = game.toString();
				JSONObject obj = new JSONObject(info);
				JSONObject Robot =obj.getJSONObject("GameServer");
				int amountRobot = Robot.getInt("robots");
				int counter=0;
				Set <Point3D> allFruits = fruits.keySet();
				Fruit temp_f = new Fruit();
				while ( counter < amountRobot) {
					Robot ro = robots.get(counter);
					for (Point3D point3d : allFruits) {
						Fruit fo = fruits.get(point3d);
						temp = graphA.shortestPathDist(ro.getSrc(), fo.getSrc());
						if ( temp < min && fo.getVisited() == false) {
							temp_f = fo;
							min = temp;
							ListGr = graphA.shortestPath(ro.getSrc(), fo.getSrc());
							ListGr.add(gg.getNode(fo.getDest()));
						}
					}
					temp_f.setVisited(true);
					if (ListGr.size() != 0 ) {
					ListGr.remove(0);
					}
					for (int j = 0; j < ListGr.size(); j++) {
						node_data temp_node = ListGr.get(j);
						int destGo = temp_node.getKey();
						Point3D po = temp_node.getLocation();
						ro.setPoint3D(po);
						ro.setSrc( temp_node.getKey());
						game.chooseNextEdge(ro.getID(), destGo);
						System.out.println(ro.getID()+" "+destGo);
						
//						System.out.println(game.timeToEnd()/1000);
					}
					counter++;
				}
				//counter=0;
			} 
			catch (JSONException e) {e.printStackTrace();}
		}
		// robot move 
		reRobot(game , gg);	
		// fruit move if its eaten
		reFruit( game, gg);
		game.move();
		paint();
	}
	
	
	private void reRobot(game_service game, MYdataStructure.graph gg) {
		robots.clear();
		for (String robo : game.getRobots()) {
			Robot r=new Robot();
			r.init(robo);
			int id = r.getID();
			robots.put(id, r);
		}
		
	}

	private void reFruit(game_service game, MYdataStructure.graph gg) {
		fruits.clear();
		for (String  fruit : game.getFruits()) {
			Fruit f = new Fruit();
			f.init(fruit);
			Point3D p_f	=f.getPoint3D();
			f.setVisited(false);
			f.findFruitPlace(gg , f);
			fruits.put(p_f, f);
		}
	}

	/**
	 * This function gets the point that the user click on her in the screen
	 * @param x
	 * @param y
	 */
	public void setXY(double x , double y) {
		this.x=x;
		this.y=y;
	}

	/**
	 * This function is the thread that repaint the frame every 0.01 secound
	 * @param game
	 */

	Thread thread=new Thread();
	public void run(game_service game) {
		while(game.isRunning()) {
			try {
				Thread.sleep(100);
				Fruit f = new Fruit();
				fruits.clear();
				for (String  fruit : game.getFruits()) {
					f.init(fruit);
					Point3D p_f	=f.getPoint3D();
					fruits.put(p_f, f);
				}

				Set <Integer> roboLoc = robots.keySet();
				Robot r = new Robot();
				while (game.isRunning()) {
					robots.clear();
					for (String robo : game.getRobots()) {
						r.init(robo);
						int id = r.getID();
						robots.put(id, r);
					}


				}
			}
			catch(Exception e) {
				e.printStackTrace();
			}
		}
	}



	/**
	 * This function moving the robots manualy by the user choice 
	 * while the game is running she will change the robots ocation by the location the user entered 
	 * and se will print the changes on the screen
	 * @param game
	 */

	public void moveManual(game_service game) {
		game.startGame();
		thread.start();
		try {
			List<String> log = game.move();
			Set <Integer> roboLoc = robots.keySet();
			Robot r = new Robot();
			while (game.isRunning()) {
				//re build the robots
				reRobot(game, graph);
				// fruit move if its eaten
				reFruit(game, graph);
				long t = game.timeToEnd();
				if (isRobot == false ) {
					for (Integer roboL : roboLoc) {
						r = robots.get(roboL);
						Point3D p = r.getPoint3D();
						if ( (p.x()+0.0001)-(this.x+0.0001)<= 0.0001 && (p.y()+0.00001)-(this.y+0.0001)<= 0.00001 ){
							isRobot =true;
							this.x=0;
							this.y=0;
							break;
						}
					}
				}
				else {	
					node_data node = graph.getNode(r.getSrc());
					Collection <edge_data> edges = graph.getE(node.getKey());
					for (edge_data edge_data : edges) {
						node_data node_edge = graph.getNode(edge_data.getDest());
						Point3D po = node_edge.getLocation();
						if ( ((po.x()+0.0001)-(this.x+0.0001)<= 0.0001) && ((po.y()+0.00001)-(this.y+0.0001)<= 0.00001 ) ){
							r.setPoint3D(po);
							r.setDest(node_edge.getKey());
							game.chooseNextEdge(r.getID(), node_edge.getKey());
							isRobot =false;
							this.x=0;
							this.y=0;
							String robot_json = log.get(r.getID());
							JSONObject line = new JSONObject(robot_json);
							JSONObject ttt = line.getJSONObject("Robot");
							System.out.println("Turn to node: "+node_edge.getKey()+"  time to end:"+(t/1000));
							System.out.println(ttt);
							break;
						}
					}
				}
//				paintFruit();
				game.move();
				paint();
			}
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}


	/**
	 * this function will paint the graph 
	 */
	public void paint() {
		StdDrawGame.clear();
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
			paintFruit();
			paintRobot();
			StdDrawGame.show();
		}
	}

	/**
	 * This function will paint the fruit on the screen by pass over the 
	 * fruit location .
	 */
	public void paintFruit() {
		if ( this.fruits != null ) {
			Set <Point3D> set = fruits.keySet();
			for (Point3D p3 : set) {
				Fruit fru = fruits.get(p3);
				if (fru.getType() == -1) {
					StdDrawGame.setPenColor(Color.RED);
					StdDrawGame.filledCircle(p3.x(), p3.y(), 0.0001);
				}
				else {
					StdDrawGame.setPenColor(Color.CYAN);
					StdDrawGame.filledCircle(p3.x(), p3.y(), 0.0001);
				}
			}
		}
	}



	public void moveRobot(game_service game ) {
		Thread t= new Thread(new Runnable() {

			@Override
			public void run() {
				while(game.isRunning()) {

				}
			}
		});

	}

	/**
	 * This function paint the robots by 
	 */
	public void paintRobot() {
		if(this.robots!=null) {
			Set <Integer> set = robots.keySet();
			for (Integer robot : set) {
				Robot robo = robots.get(robot);
				Point3D p = robo.getPoint3D();
				StdDrawGame.setPenColor(Color.GREEN);
				StdDrawGame.filledCircle(p.x(), p.y(), 0.0001); //nodes in orange
				//				StdDrawGame.setPenColor(Color.BLACK);
				//				StdDrawGame.text(p.x(), p.y()+(p.y()*0.000004) , (Integer.toString(robo.getValue())));
			}

		}
	}

	public void initGUI() {
		StdDrawGame.setCanvasSize(800, 600);
		StdDrawGame.enableDoubleBuffering();
		double X_min = Integer.MAX_VALUE;
		double X_max = Integer.MIN_VALUE;
		double Y_min = Integer.MAX_VALUE;
		double Y_max = Integer.MIN_VALUE;

		// rescale the coordinate system
		if (graph != null) {
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
		}
		StdDrawGame.setXscale(X_min, X_max);
		StdDrawGame.setYscale(Y_min, Y_max);
		StdDrawGame.setGuiGraph(this);
		paint();
	}







}
