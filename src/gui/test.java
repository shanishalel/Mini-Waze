package gui;

import MYdataStructure.DGraph;
import MYdataStructure.Nodes;
import utils.Point3D;

public class test {

	public static void main(String[] args) {
		DGraph d = new DGraph();
		d.addNode(new Nodes(1, new Point3D(130, 130)));
		d.addNode(new Nodes(2, new Point3D(200, 140)));
		d.addNode(new Nodes(3, new Point3D(600, 600)));
		d.connect(1, 2, 3);
		d.connect(2, 3, 4);
		d.connect(3, 1, 1);
		Gui_Graph1 graph = new Gui_Graph1(d);
		
	}

}
