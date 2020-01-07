package gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.LinkedList;

import javax.swing.JMenu;
import javax.swing.JMenuBar;

import MYdataStructure.graph;
import utils.StdDraw;
import utils.Point3D;

public class g_StdDraw implements ActionListener {
	private graph graph;
	private static final long serialVersionUID = 6128157318970002904L;
	LinkedList<Point3D> points = new LinkedList<Point3D>();
	
	public g_StdDraw(){
		this.graph =null;
		drawFunctions();
	}

	public g_StdDraw(graph g)
	{
		this.graph=g;
		drawFunctions();
	}
	
	public void drawFunctions() {
		StdDraw.setCanvasSize(1000, 1000);
		int X_min = Integer.MIN_VALUE;
		int X_max = Integer.MAX_VALUE;
		int Y_min = Integer.MIN_VALUE;
		int Y_max = Integer.MAX_VALUE;
		
		// rescale the coordinate system
		StdDraw.setXscale(  X_min, X_max);
		StdDraw.setYscale(Y_min, Y_max);
		JMenuBar menuBar = new JMenuBar();
		JMenu menu = new JMenu("Menu");
		menuBar.add(menu);
		
		
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		
	}
	
	
	
}
