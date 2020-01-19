package gameClient;

import java.util.Collection;
import java.util.Hashtable;
import java.util.List;
import java.util.Set;
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
import gui.MyGameGUI;
import utils.Point3D;

public class Manual {
	MyGameGUI gameGui;
	
	public Manual(MyGameGUI gameGui) {
		this.gameGui=gameGui;
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
	
	public void moveManual(game_service game) {
		game.startGame();
		ThreadGame.timeRun(game);
		try {
			Set <Integer> roboLoc = gameGui.robots.keySet();
			Robot r = new Robot();
			Long timeB = game.timeToEnd();
			while (game.isRunning()) {
				if(timeB-game.timeToEnd() > 70)
				{
					game.move();
					timeB = game.timeToEnd();
				}
				//re build the robots
				reRobot(game, gameGui.graph);
				// fruit move if its eaten
				reFruit(game, gameGui.graph);
				long t = game.timeToEnd();
				
				if (gameGui.isRobot == false ) {
					for (Integer roboL : roboLoc) {
						r = gameGui.robots.get(roboL);
						Point3D p = r.getPoint3D();
						double disX=Math.pow((p.x()-gameGui.x), 2);
						double disY=Math.pow((p.y()-gameGui.y), 2);
						if(Math.sqrt(disY+disX)<=0.00025) {	
							gameGui.isRobot =true;
							gameGui.x=0;
							gameGui.y=0;
							break;
						}
					}
				}
				else {	
					node_data node = gameGui.graph.getNode(r.getSrc());
					Collection <edge_data> edges = gameGui.graph.getE(node.getKey());
					for (edge_data edge_data : edges) {
						node_data node_edge = gameGui.graph.getNode(edge_data.getDest());
						Point3D po = node_edge.getLocation();
						double disX=Math.pow((po.x()-gameGui.x), 2);
						double disY=Math.pow((po.y()-gameGui.y), 2);
						if(Math.sqrt(disY+disX)<=0.00025) {
							r.setPoint3D(po);
							r.setDest(node_edge.getKey());
							game.chooseNextEdge(r.getID(), node_edge.getKey());
							gameGui.isRobot =false;
							gameGui.x=0;
							gameGui.y=0;
							break;
						}
					}
				}
				gameGui.paint();
			}
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		try {
			JFrame input = new JFrame();
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
	 * This function is play the manual choice and send it to move manual
	 */
	public void PlayManual(game_service game, int serv) {
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
				//robot
				Robot r=new Robot();
				JSONObject obj = null;
				try {
					obj = new JSONObject(info);
				} catch (JSONException e2) {
					// TODO Auto-generated catch block
					e2.printStackTrace();
				}
				JSONObject Robot = null;
				try {
					Robot = obj.getJSONObject("GameServer");
				} catch (JSONException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
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
				moveManual(game);
			}
		
				
	}





