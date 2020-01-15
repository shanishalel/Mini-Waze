package gameClient;

import java.awt.Color;

import Server.game_service;
import utils.StdDraw;
import utils.StdDrawGame;

public class ThreadGame {

	static Thread time;
	public static void timeRun(game_service game) {
		time = new Thread(new Runnable() {
			@Override
			public void run() {
				// TODO Auto-generated method stub
				while(game.isRunning()) {
					System.out.println(game.timeToEnd()/1000);
					try{
						Thread.sleep(1000);
					}
					catch (Exception e) {
						e.printStackTrace();
					}
				}
			}
		});
		time.start();
	}
	
	static Thread moveTime;
	public static void moveTime(game_service game) {
		moveTime= new Thread(new Runnable() {
			
			@Override
			public void run() {
				while (game.isRunning()) {
					game.move();
					try{
						Thread.sleep(60);
					}
					catch (Exception e) {
						e.printStackTrace();
					}
				}
				
			}
		});
		moveTime.start();
	}
	
}
