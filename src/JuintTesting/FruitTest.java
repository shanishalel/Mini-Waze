package JuintTesting;

import static org.junit.jupiter.api.Assertions.*;

import org.json.JSONObject;
import org.junit.jupiter.api.Test;

import MYdataStructure.DGraph;
import MYdataStructure.graph;
import MYdataStructure.node_data;
import Server.Game_Server;
import Server.game_service;
import gameClient.Fruit;
import utils.Point3D;

class FruitTest {

	@Test
	void Fruit() {
		 Point3D point1= new Point3D(1,1,1);
		 int Type= 1;
		 int value =1;
		 graph g = null ;
		 Fruit test1= new Fruit(Type, value, point1,g);
		 if(test1.getType()!=Type) {
			 fail();
		 }
		 if(test1.getValue()!=value) {
			 fail();
		 }
		 
		 
	}
	@Test
	void testInit() {
		String test = "{\"Fruit\":{\"value\":8.0,\"type\":1,\"pos\":\"35.18752225008876,32.10379910146876,0.0\"}}";
		Fruit f= new Fruit ();
		f.init(test);
		if (f.getValue()!=8.0) {
			fail();
		}
	}

	@Test
	void testFindFruitPlace() {
		String test = "{\"Fruit\":{\"value\":5.0,\"type\":-1,\"pos\":\"35.197656770719604,32.10191878639921,0.0\"}}";
		Fruit f= new Fruit ();
		f.init(test);
		DGraph g= new DGraph();
		game_service game=Game_Server.getServer(2);
		game.getGraph();
		g.init(game.getGraph());
		f.findFruitPlace(g, f);
		System.out.println(f.getSrc());
		if(f.getDest()!=9) {
			fail();
		}
		if(f.getSrc()!=8) {
			fail();
		}
		
			
	}

}
