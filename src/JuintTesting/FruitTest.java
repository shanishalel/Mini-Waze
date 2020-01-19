package JuintTesting;

import static org.junit.jupiter.api.Assertions.*;

import org.json.JSONObject;
import org.junit.jupiter.api.Test;

import MYdataStructure.DGraph;
import MYdataStructure.graph;
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
	}

	@Test
	void testFindFruitPlace() {
		fail("Not yet implemented");
	}

}
