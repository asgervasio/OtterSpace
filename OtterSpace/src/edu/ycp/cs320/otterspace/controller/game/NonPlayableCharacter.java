package edu.ycp.cs320.otterspace.controller.game;

import java.util.ArrayList;
import java.util.List;

public class NonPlayableCharacter extends Player{
	
	Room currentRoom;
	List<Item> inventory = new ArrayList<Item>();
	private int health, gold, score, attack, defense;
	
	public void setHealth(int health){
		this.health = health;
	}
	
	public int getHealth(){
		return health;
	}
	
	public void setGold(int gold){
		this.gold = gold;
	}
	
	public int getGold(){
		return gold;
	}
	
	public void setScore(int score){
		this.score = score;
	}
	
	public int getScore(){
		return score;
	}
	
	public void setAttack(int attack){
		this.attack = attack;
	}
	
	public int getAttack(){
		return attack;
	}
	
	public void setDefense(int defense){
		this.defense = defense;
	}
	
	public int getDefense(){
		return defense;
	}
	
	public void attackEnemy(Player player){
		
	}
	
	

}
