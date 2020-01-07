package gameClient;

import org.json.JSONArray;
import org.json.JSONObject;
import utils.Point3D;

public class Fruit {

	public void init(String s) {
		try {
			double x=0,y=0,z=0,counter=0;
			JSONObject obj = new JSONObject(s);
			JSONArray Fruits =obj.getJSONArray("Fruit");
			for (int i = 0; i < Fruits.length(); i++) {
				JSONObject Fruit =(JSONObject)Fruits.get(i);
				String point=(String) Fruit.get("pos");
				for (int j = 0; j < point.length(); j++) {
					if (point.charAt(j) != ',') {
						s+=point.charAt(j); 
						 if (counter==2 && j == point.length()-1) {
								z= Double.parseDouble(s);
								counter=0;
								s="";
							}
					}
					else {
						if (counter==0) {
							x= Double.parseDouble(s);
							counter++;
							s="";
						}
						else if (counter==1) {
							y= Double.parseDouble(s);
							counter++;
							s="";
						}
					}
				}
				Point3D p = new Point3D(x,y,z);
				int value = Fruit.getInt("value");
				int type =  Fruit.getInt("type");
			}
		}
		catch(Exception e) {
			e.printStackTrace();
		}

	}

}




