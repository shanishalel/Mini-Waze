package gameClient;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Hashtable;
import java.util.List;
import java.util.Set;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import org.json.JSONException;
import org.json.JSONObject;

import MYdataStructure.DGraph;
import MYdataStructure.graph;
import MYdataStructure.node_data;
import Server.Game_Server;
import Server.game_service;
import algorithms.Graph_Algo;
import gui.MyGameGUI;
import utils.Point3D;

/**
 * This class include all the automatic algorithms the we used in the playautomatic function. 
 * This Automatic class have MyGameGui field that allow as to build the gameGui according to the MyGameGui.
 * 
 * @author USER
 *
 */






public class Automatic {

	MyGameGUI gameGui;
	
	
	/**
	 * construct that get the gui game and init him to gamegui
	 * @param gameGui
	 */
	public Automatic(MyGameGUI gameGui) {
		this.gameGui=gameGui;
	}


	/**
	 * This function get the game and num of sceneario,
	 * the function locate the robots by run on the fruit list (reading it from the json) and check for every fruit what is the closest node 
	 * and then locate the robot on this node. the function called helper function startGameNow.
	 * 
	 */
	public void Playautomatic(game_service game, int serv ) {
		String g = game.getGraph();
		DGraph gg = new DGraph();
		gg.init(g);
		gameGui.graph =gg;
		String info = game.toString();
		// fruit
		if (gameGui.fruits == null ) {
			gameGui.fruits= new Hashtable<Point3D, Fruit>();
		}
		for (String  fruit : game.getFruits()) {
			Fruit f = new Fruit();
			f.init(fruit);
			Point3D p_f	=f.getPoint3D();
			f.findFruitPlace(gg , f);
			gameGui.fruits.put(p_f, f);
		}
		
		Robot r=new Robot();
		JSONObject obj = null;
		try {
			obj = new JSONObject(info);
		} catch (JSONException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		JSONObject Robot = null;
		try {
			Robot = obj.getJSONObject("GameServer");
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		int amountRobot = 0;
		try {
			amountRobot = Robot.getInt("robots");
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Collection<node_data> node = gameGui.graph.getV();
		int size = node.size();
		int rnd = 0;
		if (gameGui.robots == null ) {
			gameGui.robots = new Hashtable<Integer, Robot>();
		}
		int counter=0;
		Set <Point3D> allFruits = gameGui.fruits.keySet();
		for (Point3D Point : allFruits) {
			if (counter < gameGui.fruits.size()) {
				if (counter >= amountRobot ) {
					break;
				}
				Fruit placeFruit = gameGui.fruits.get(Point);
				r.setSrc(placeFruit.getSrc());
				game.addRobot(placeFruit.getSrc());
				counter++;
			}
			else {
				while (counter < amountRobot) {
					rnd = (int) (Math.random()*size);
					if (gameGui.graph.getNode(rnd) != null) {
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
			gameGui.robots.put(id, ro);
		}
		gameGui.initGUI();
		startGameNow(game, gg , serv);



					
	}


	


	/**
	 * This function get the game, graph , num of scenario and start the game, start the kml file and all our threads. 
	 * while the game is running the function call help function - the smartmove.  
	 * @param game
	 * @param gg
	 */
	public void startGameNow(game_service game ,graph gg , int sen) {
		JFrame input = new JFrame();
		game.startGame();
		gameGui.KML.setGame(game);
		ThreadGame.moveKml(game, gameGui.KML);
		ThreadGame.timeRun(game);
		Long timeB = game.timeToEnd();
		while(game.isRunning()) {
			if(timeB-game.timeToEnd() > 70)
			{
				game.move();
				timeB = game.timeToEnd();
			}
			smartMove(game , gg);
		}
		try {
			gameGui.KML.save(sen + ".kml");
			String info = game.toString();
			JSONObject obj = new JSONObject(info);
			JSONObject GameServer =obj.getJSONObject("GameServer");
			int grade = GameServer.getInt("grade");
			JOptionPane.showMessageDialog(input, "the game is finished! \n"+ "your score is: " + grade);

		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}





	/**
	 * This function is the automatic play moves that we create, this function goal is to create the ultimate path for all the robots in the game that we will 
	 * get the best score and win the game ! 
	 * this function idea is checking for every robot which fruit is the closest to him by using the shortestpathdist (graph_algo) and save the min path by list of nodes
	 * , then she will determined the path for the node by the helper function SetPath, and move the robots by the helper function MoveRobot.
	 * 
	 * @param game
	 * @param gg
	 */
	private void smartMove(game_service game, graph gg) {
		List<node_data> ListGr = new ArrayList<node_data>();
		Graph_Algo graphA = new Graph_Algo(gg);
		try {
			String info = game.toString();
			JSONObject obj = new JSONObject(info);
			JSONObject Robot =obj.getJSONObject("GameServer");
			int amountRobot = Robot.getInt("robots");
			int counter=0;
			while ( counter < amountRobot) {
				Robot ro = gameGui.robots.get(counter);
				ListGr=SetPath ( game,  gg, ro,  graphA);
				MoveRobot( game,  gg,  ro,  graphA, ListGr);
				counter++;
			}
		} 
		catch (JSONException e) {e.printStackTrace();}
		// robot move 
		reRobot(game , gg);	
		// fruit move if its eaten
		reFruit( game, gg);
		gameGui.paint();
	}

	/**
	 * This function determined the path for the robot by pass all the fruit and check the 
	 * @param game
	 * @param gg
	 * @param dest
	 * @param ro
	 * @param graphA
	 * @return
	 */
	private List<node_data> SetPath (game_service game, MYdataStructure.graph gg, Robot ro, Graph_Algo graphA) {
		double min =Integer.MAX_VALUE , temp=0;
		List<node_data> ListGr = new ArrayList<node_data>();
		Set <Point3D> allFruits = gameGui.fruits.keySet();
		Fruit temp_f = new Fruit();
		for (Point3D point3d : allFruits) {
			Fruit fo = gameGui.fruits.get(point3d);
			temp = graphA.shortestPathDist(ro.getSrc(), fo.getSrc());
			if ( temp < min && fo.getVisited() == false) {
				temp_f = fo;
				min = temp;
				fo.setVisited(true);//mark as visited 
				ListGr = graphA.shortestPath(ro.getSrc(), fo.getSrc());
				ListGr.add(gg.getNode(fo.getDest()));
			}


		}
		return ListGr;

	}

	/**
	 * this function will move the robots by the path she gets
	 * @param game
	 * @param gg
	 * @param ro
	 * @param graphA
	 * @param ListGr
	 */
	private void MoveRobot(game_service game, MYdataStructure.graph gg, Robot ro, Graph_Algo graphA, List<node_data> ListGr) {
		Fruit temp_f = new Fruit();
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
		}
	}

	private void reRobot(game_service game, MYdataStructure.graph gg) {
		gameGui.robots.clear();
		for (String robo : game.getRobots()) {
			Robot r=new Robot();
			r.init(robo);
			int id = r.getID();
			gameGui.robots.put(id, r);
		}
	}

	private void reFruit(game_service game, MYdataStructure.graph gg) {
		gameGui.fruits.clear();
		for (String  fruit : game.getFruits()) {
			Fruit f = new Fruit();
			f.init(fruit);
			Point3D p_f	=f.getPoint3D();
			f.setVisited(false);
			f.findFruitPlace(gg , f);
			gameGui.fruits.put(p_f, f);
		}
	}


}
