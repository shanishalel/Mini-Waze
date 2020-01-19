package JuintTesting;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import Server.Game_Server;
import Server.game_service;
import gameClient.Automatic;
import gui.MyGameGUI;

class AutomaticTest {

	
	@Test
	void testAutomatic() {
		 MyGameGUI game= new MyGameGUI(); 
		  Automatic game1= new Automatic(game);
		  if(!game.equals(game1.gameGui)) {
			  fail();
			  
		  }
	}

	@Test
	void testPlayautomatic() {
		
		
		
		
	}

	@Test
	void testStartGameNow() {
		
	}

}
