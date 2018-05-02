package edu.ycp.cs320.otterspace.controller;

import edu.ycp.cs320.otterspace.controller.game.Player;
import edu.ycp.cs320.otterspace.controller.game.Room;

public class PlayerController {
	
	public Player createPlayer(String name, String descrip, int health, int gold, int score, 
			int attack, int defense, boolean hostile, Room roomLoc){
		Player player = new Player();
		player.setName(name);
		player.setDescription(descrip);
		player.setHealth(health);
		player.setGold(gold);
		player.setScore(score);
		player.setAttack(attack);
		player.setDefense(defense);
		player.setHostility(hostile);
		player.setCurrentRoom(roomLoc);
		
		return player;
	}
	
}
