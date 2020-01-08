package gameClient;

import java.awt.Point;
import java.util.Collection;
import java.util.Hashtable;

import org.json.JSONObject;

import utils.Point3D;

public class Robot {
	Hashtable<Integer, Robot> Robots = new Hashtable<Integer, Robot>();
	int VALUE;
	Point3D POINT;


	public Robot() {
		this.VALUE=0;
		this.POINT=POINT;
	}
	
	public Robot(Point3D p) {
		this.VALUE=0;
		this.POINT=p;
	}

	public Collection<Robot> getR() {
		return Robots.values();
	}

	public Robot (int type , int value , Point3D p) {
		this.VALUE=value;
		this.POINT=p;
	}

	public Point3D getPoint3D () {
		return this.POINT;
	}
	
	public void setPoint3D (Point3D p) {
		 this.POINT =p;
	}

	public int getValue () {
		return this.VALUE;
	}

	public void init (String s) {
		try {
			Robot temp = new Robot();
			JSONObject obj = new JSONObject(s);
			JSONObject Robot =obj.getJSONObject("GameServer");
			int robot = Robot.getInt("robots");
			for (int i = 0; i < robot; i++) {
				Robots.put(i, temp);
			}
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}




}
