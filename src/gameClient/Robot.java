package gameClient;


import java.awt.Point;
import java.util.Collection;
import java.util.Hashtable;

import org.json.JSONObject;

import utils.Point3D;

/**
 *  This represents Robot that eat all the fruit in the graph, 
 * every robot have:
	int VALUE;
	int ID;
	int SRC;
	int DEST;
	int SPEED;
	Point3D POINT;
	
	every robot have the following function by there define 

 * @author USER
 *
 */
public class Robot {
	int VALUE;
	int ID;
	int SRC;
	int DEST;
	int SPEED;
	Point3D POINT;


	/**
	 * This function is the default counstrctor of the robot struct 
	 */
	public Robot() {
		this.VALUE=0;
		this.ID=ID;
		this.SRC = SRC;
		this.DEST=DEST;
		this.SPEED=SPEED;
		this.POINT=POINT;
	}
	
	/**
	 * this function is the counstrctor of the struct robot
	 * @param p - point that represent the location of the robot
	 * @param VALUE- value that represente the point the robot get to by eating fruit
	 * @param ID - id that represent the key of the node 
	 * @param SRC - src that represent the src that the robot stand on 
	 * @param DEST - dest that represent the dest that the robot go to 
	 * @param SPEED - the speed of the robot
	 */
	public Robot(Point3D p,int VALUE,int ID,int SRC,int DEST,int SPEED) {
		this.VALUE=0;
		this.ID=ID;
		this.SRC = SRC;
		this.DEST=DEST;
		this.SPEED=SPEED;
		this.POINT=POINT;
	}
	
	
	/**
	 * This function gets the type, value and point and entered them to the robot
	 * @param type - represent by -1 from low to hight and 1 from hight to low
	 * @param value 
	 * @param p - point of the location
	 */
	public Robot (int type , int value , Point3D p) {
		this.VALUE=value;
		this.POINT=p;
	}

	/**
	 * gets the id
	 * @return
	 */
	public int getID () {
		return this.ID;
	}
	
	/**
	 * set thr id
	 * @param ID
	 */
	public void setID(int ID) {
		this.ID=ID;
	}
	
	/**
	 * get the speed
	 * @return
	 */
	public int getSpeed () {
		return this.SPEED;
	}
	
	
	/**
	 * set the speed
	 * @param SPEED
	 */
	public void setSpeed(int SPEED) {
		this.SPEED=SPEED;
	}
	
	/**
	 * get the point of the location
	 * @return
	 */
	public Point3D getPoint3D () {
		return this.POINT;
	}
	
	/**
	 * set the point 3d
	 * @param p
	 */
	public void setPoint3D (Point3D p) {
		 this.POINT =p;
	}
	
	/**
	 * get the value
	 * @return
	 */
	public int getValue () {
		return this.VALUE;
	}
	
	/**
	 * get the src
	 * @return
	 */
	public int getSrc() {
		return this.SRC;
	}
	
	/**
	 * set the src
	 * @param Src
	 */
	public void setSrc(int Src) {
		this.SRC = Src;
	}
	
	/**
	 * get the dest
	 * @return
	 */
	public int getDest() {
		return this.DEST;
	}
	
	
	/**
	 * set the dest
	 * @param Dest
	 */
	public void setDest(int Dest) {
		this.DEST = Dest;
	}
	
	
	/**
	 * This function gets string s and init from her the robot all the information she needs, 
	   by reading from the json. 
	 * @param s
	 */
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
				this.POINT= new Point3D(x,y,z);
				this.VALUE = Robots.getInt("value");
				this.ID =  Robots.getInt("id");
				this.SRC = Robots.getInt("src");
				this.DEST = Robots.getInt("dest");
				this.SPEED = Robots.getInt("speed");
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		
		
		
		

	}




}
