package dataStructureTesting;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Random;

import MYdataStructure.DGraph;
import MYdataStructure.Nodes;
import MYdataStructure.graph;
import MYdataStructure.node_data;
import algorithms.Graph_Algo;
import algorithms.graph_algorithms;
import gui.Gui_Graph;
import utils.Point3D;


public class Testing_gui {

	public static void main(String[] args) {
//		testmaybe();
//			test();
//			test2();
//			test3();
			test4();
//			test5();
//		test6();
	}
	
	

	private static void test6() {
		DGraph d = new DGraph();
		d.addNode(new Nodes(1, new Point3D(130, 130)));
		d.addNode(new Nodes(2, new Point3D(200, 140)));
		d.addNode(new Nodes(3, new Point3D(600, 600)));
		d.connect(1, 2, 3);
		d.connect(2, 3, 4);
		d.connect(3, 1, 1);
		Gui_Graph graph = new Gui_Graph(d);
		
	}



	private static void test5() {
		DGraph d = new DGraph();
		d.addNode(new Nodes(1, new Point3D(130, 130)));
		d.addNode(new Nodes(2, new Point3D(200, 140)));
		d.addNode(new Nodes(3, new Point3D(600, 600)));
		d.addNode(new Nodes(4, new Point3D(100, 400)));
		d.connect(1, 2, 3);
		d.connect(2, 3, 4);
		d.connect(4, 2, 1);
		Gui_Graph graph = new Gui_Graph(d);
		ArrayList<Integer> targets = new ArrayList<Integer>();
		targets.add(1);
		targets.add(4);
		Graph_Algo gg = new Graph_Algo();
		gg.init(d);
		gg.TSP(targets);
		
	}

	private static void test4() {
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
		Gui_Graph graph = new Gui_Graph(d);
//		ArrayList<Integer> targets = new ArrayList<Integer>();
//		targets.add(1);
//		targets.add(5);
//		targets.add(1);
//		Graph_Algo gg = new Graph_Algo();
//		gg.init(d);
//		gg.TSP(targets);
		
	}

	private static void test3() {
		DGraph d = new DGraph();
		for (int i = 0 ; i< 1000000 ; i++) {
			int x = (int) ((Math.random()*700)+100);
			int y = (int) ((Math.random()*700)+100);
			d.addNode(new Nodes(i, new Point3D(x, y)));
		}
		for (int i =0 ; i <10000 ; i++) {
			int w = (int) ((Math.random()*20)+1);
			int src =(int) ((Math.random()*100));
			int dest =(int) ((Math.random()*100));
			d.connect(src,dest , w);
		}
		
		Gui_Graph graph = new Gui_Graph(d);
		
	}

	private static void test2() {
		DGraph d = new DGraph();
		d.addNode(new Nodes(1, new Point3D(130, 130)));
		d.addNode(new Nodes(2, new Point3D(200, 140)));
		d.addNode(new Nodes(3, new Point3D(600, 600)));
		d.addNode(new Nodes(4, new Point3D(100, 400)));
		d.addNode(new Nodes(5, new Point3D(120, 300)));
		d.addNode(new Nodes(6, new Point3D(200, 123)));
		d.addNode(new Nodes(7, new Point3D(250, 250)));
		d.addNode(new Nodes(8, new Point3D(249, 400)));
		d.addNode(new Nodes(9, new Point3D(430, 145)));
		d.addNode(new Nodes(10, new Point3D(620, 150)));
		d.connect(1, 2, 10);
		d.connect(1, 3, 4);
		d.connect(2, 4, 5);
		d.connect(3, 2, 2);
		d.connect(5, 6, 7);
		d.connect(1, 5, 3);
		d.connect(10, 7, 2);
		d.connect(10, 1, 1);
		d.connect(1, 7, 6);
		d.connect(9, 3, 2);
		d.connect(3, 10, 10);
		d.connect(8, 3, 4);
		Gui_Graph graph = new Gui_Graph(d);
		
	}

	public static void test() {
		DGraph d = new DGraph();
		d.addNode(new Nodes(1, new Point3D(130, 130)));
		d.addNode(new Nodes(2, new Point3D(200, 140)));
		d.addNode(new Nodes(3, new Point3D(600, 600)));
		d.addNode(new Nodes(4, new Point3D(100, 400)));
		d.addNode(new Nodes(5, new Point3D(120, 300)));
		d.addNode(new Nodes(6, new Point3D(200, 123)));
		d.addNode(new Nodes(7, new Point3D(250, 250)));
		d.addNode(new Nodes(8, new Point3D(20, 400)));
		d.addNode(new Nodes(9, new Point3D(330, 145)));
		d.addNode(new Nodes(10, new Point3D(155, 155)));
		d.connect(1, 2, 10);
		d.connect(1, 3, 4);
		d.connect(2, 4, 5);
		d.connect(3, 2, 2);
		d.connect(5, 6, 7);
		d.connect(1, 5, 3);
		d.connect(10, 7, 2);
		d.connect(10, 1, 1);
		d.connect(1, 7, 6);
		d.connect(9, 3, 2);
		d.connect(3, 10, 10);
		d.connect(8, 3, 4);
		Gui_Graph graph = new Gui_Graph(d);
		Graph_Algo gg = new Graph_Algo();
		gg.init(d);
		System.out.println(gg.shortestPathDist(1, 6));
		
	}
}
