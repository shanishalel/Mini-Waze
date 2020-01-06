package dataStructureTesting;

import static org.junit.jupiter.api.Assertions.*;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.junit.jupiter.api.Test;

import algorithms.Graph_Algo;
import algorithms.graph_algorithms;
import dataStructure.DGraph;
import dataStructure.Nodes;
import dataStructure.graph;
import dataStructure.node_data;
import utils.Point3D;

class Graph_AlgoTest implements java.io.Serializable {

	@Test
	void testInitGraph() {
		DGraph d = new DGraph();
		int j=1;
		for (int i =1 ; i<10 ;i++){
			Point3D p = new Point3D(j, j, j);
			Nodes n = new Nodes(i , p);
			d.addNode(n);
		}
		for(int i =1 ; i <d.nodeSize() ;i++) {
			d.connect(1, i, i);
		}
		//1->2, 1->3, 1->4, 1->5, 1->6, 1->7, 1->8, 1->9
		Graph_Algo graph = new Graph_Algo();
		graph.init(d);
		
	}

	@Test
	void testInitString() {
		DGraph d = new DGraph();
		int j=1;
		for (int i =1 ; i<10 ;i++){
			Point3D p = new Point3D(j, j, j);
			Nodes n = new Nodes(i , p);
			d.addNode(n);
		}
		for(int i =1 ; i <d.nodeSize() ;i++) {
			d.connect(1, i, i);
		}
		//1->2, 1->3, 1->4, 1->5, 1->6, 1->7, 1->8, 1->9
		Graph_Algo graph = new Graph_Algo();
		graph.init(d);
		graph.save("test_save1.txt");
		Graph_Algo graph1 = new Graph_Algo();
		graph1.init("test_save1.txt");
		
		
	}

	@Test
	void testSave() {
		DGraph d = new DGraph();
		int j=1;
		for (int i =1 ; i<10 ;i++){
			Point3D p = new Point3D(j, j, j);
			Nodes n = new Nodes(i , p);
			d.addNode(n);
		}
		for(int i =1 ; i <d.nodeSize() ;i++) {
			d.connect(1, i, i);
		}
		//1->2, 1->3, 1->4, 1->5, 1->6, 1->7, 1->8, 1->9
		Graph_Algo graph = new Graph_Algo();
		graph.init(d);
		graph.save("test_save.txt");

	}

	@Test
	void testIsConnected() {
		DGraph d = new DGraph();
		/* create d graph that is connect graph  */
		int j=1;
		for (int i =1 ; i<=5 ;i++){
			Point3D p = new Point3D(j, j, j);
			Nodes n = new Nodes(i , p);
			d.addNode(n);
		}
		d.connect(1, 2, 3);
		d.connect(2, 5, 1);
		d.connect(2, 3, 7);
		d.connect(5, 1, 2);
		d.connect(3, 4, 5);
		d.connect(4, 1, 1);
		Graph_Algo graph = new Graph_Algo();
		graph.init(d);
		if(!graph.isConnected()) {
			fail();
		}

	}

	@Test
	void testShortestPathDist() {
		DGraph d = new DGraph();
		int j=1;
		for (int i =1 ; i<=6 ;i++){
			Point3D p = new Point3D(j, j, j);
			Nodes n = new Nodes(i , p);
			d.addNode(n);
		}
		d.connect(1, 2, 3);
		d.connect(1, 3, 1);
		d.connect(2, 3, 7);
		d.connect(3, 4, 2);
		d.connect(2, 4, 5);
		d.connect(2, 6, 1);
		d.connect(4, 6 ,7);
		Graph_Algo graph = new Graph_Algo();
		graph.init(d);
		System.out.println(graph.shortestPath(1, 6));
	}

	@Test
	void testShortestPath() {
		DGraph d = new DGraph();
		d.addNode(new Nodes(1, new Point3D(130, 130)));
		d.addNode(new Nodes(2, new Point3D(200, 140)));
		d.addNode(new Nodes(3, new Point3D(600, 600)));
		d.addNode(new Nodes(4, new Point3D(100, 400)));
		d.addNode(new Nodes(5, new Point3D(120, 300)));
		d.connect(1, 2, 3);
		d.connect(2, 5, 3);
		d.connect(3, 1, 4);
		d.connect(5, 3, 5);
		d.connect(2, 3, 9);
		d.connect(4, 5, 1);
		Graph_Algo gg = new Graph_Algo();
		gg.init(d);
		ArrayList<node_data> ans = new ArrayList<node_data>();
		ans = (ArrayList<node_data>) gg.shortestPath(1, 3);
		String s="";
		for (int i = 0; i+1 < ans.size(); i++) {
			s+= ans.get(i).getKey() + "-->";
		}
		s+= ans.get(ans.size()-1).getKey();
		assertEquals(s, "1-->2-->5-->3");
		
	}
	

	@Test
	void testTSP() {
		DGraph d = new DGraph();
		d.addNode(new Nodes(1, new Point3D(130, 130)));
		d.addNode(new Nodes(2, new Point3D(200, 140)));
		d.addNode(new Nodes(3, new Point3D(600, 600)));
		d.addNode(new Nodes(4, new Point3D(100, 400)));
		d.addNode(new Nodes(5, new Point3D(120, 300)));
		d.connect(1, 2, 3);
		d.connect(2, 5, 3);
		d.connect(3, 1, 4);
		d.connect(5, 3, 5);
		d.connect(2, 3, 9);
		d.connect(4, 5, 1);
		ArrayList<Integer> targets = new ArrayList<Integer>();
		targets.add(1);
		targets.add(1);
		targets.add(2);
		targets.add(5);
		targets.add(5);
		targets.add(1);
		Graph_Algo gg = new Graph_Algo();
		gg.init(d);
		ArrayList<node_data> ans = new ArrayList<node_data>();
		ans = (ArrayList<node_data>) gg.TSP(targets);
		String s="";
		for (int i = 0; i+1 < ans.size(); i++) {
			s+= ans.get(i).getKey() + "-->";
		}
		s+= ans.get(ans.size()-1).getKey();
		assertEquals(s, "1-->2-->5-->3-->1");
	}

	@Test
	void testCopy() {
		DGraph d = new DGraph();
		int j=1;
		for (int i =1 ; i<10 ;i++){
			Point3D p = new Point3D(j, j, j);
			Nodes n = new Nodes(i , p);
			d.addNode(n);
		}
		for(int i =1 ; i <d.nodeSize() ;i++) {
			d.connect(1, i, i);
		}
		//1->2, 1->3, 1->4, 1->5, 1->6, 1->7, 1->8, 1->9
		Graph_Algo graph = new Graph_Algo();
		graph.init(d);
		//for deep copy the location in the memory shouldn't be the same
		if(graph.equals(graph.copy())) {
			fail();
		}
		
		
		
	}

}
