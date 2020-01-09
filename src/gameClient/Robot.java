package gameClient;

import java.awt.Point;
import java.util.Collection;
import java.util.Hashtable;

import org.json.JSONObject;

import utils.Point3D;

public class Robot {
	int VALUE;
	int ID;
	int SRC;
	int DEST;
	int SPEED;
	Point3D POINT;


	public Robot() {
		this.VALUE=0;
		this.ID=ID;
		this.SRC = SRC;
		this.DEST=DEST;
		this.SPEED=SPEED;
		this.POINT=POINT;
	}
	
	public Robot(Point3D p,int VALUE,int ID,int SRC,int DEST,int SPEED) {
		this.VALUE=0;
		this.ID=ID;
		this.SRC = SRC;
		this.DEST=DEST;
		this.SPEED=SPEED;
		this.POINT=POINT;
	}

	public Robot (int type , int value , Point3D p) {
		this.VALUE=value;
		this.POINT=p;
	}

	public int getID () {
		return this.ID;
	}
	
	public void setID(int ID) {
		this.ID=ID;
	}
	
	public int getSpedd () {
		return this.SPEED;
	}
	
	public void setSpeed(int SPEED) {
		this.SPEED=SPEED;
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
			double x=0,y=0,z=0,counter=0;
			String k = "";
			JSONObject obj = new JSONObject(s);
			JSONObject Robots =obj.getJSONObject("Robot");
				String point=(String) Robots.get("pos");
				for (int j = 0; j < point.length(); j++) {
					if (point.charAt(j) != ',') {
						k+=point.charAt(j); 
						 if (counter==2 && j == point.length()-1) {
								z= Double.parseDouble(k);
								counter=0;
								k="";
							}
					}
					else {
						if (counter==0) {
							x= Double.parseDouble(k);
							counter++;
							k="";
						}
						else if (counter==1) {
							y= Double.parseDouble(k);
							counter++;
							k="";
						}
					}
				}
				Point3D p = new Point3D(x,y,z);
				int value = Robots.getInt("value");
				int id =  Robots.getInt("id");
				int src = Robots.getInt("src");
				int dest = Robots.getInt("dest");
				int speed = Robots.getInt("speed");
				Robot r = new Robot(p, value,id , src ,dest,speed );
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		
		
		
		
		//		try {
//			Robot temp = new Robot();
//			JSONObject obj = new JSONObject(s);
//			JSONObject Robot =obj.getJSONObject("GameServer");
//			int robot = Robot.getInt("robots");
//			for (int i = 0; i < robot; i++) {
//				Robots.put(i, temp);
//			}
//		}
//		catch (Exception e) {
//			e.printStackTrace();
//		}
	}




}
