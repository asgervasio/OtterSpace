package edu.ycp.cs320.otterspace.controller.game;

import java.util.ArrayList;
import java.util.List;

import edu.ycp.cs320.roomsdb.persist.DatabaseProvider;
import edu.ycp.cs320.roomsdb.persist.IDatabase;

public class Player 
{
	private Room currentRoom;
	private List<Item> inventory = new ArrayList<Item>();
	private int health, gold, score, attack, defense, roomId;
	public boolean hostility;
	private String name, description;
	
	public void setName(String name){
		this.name = name;
	}
	
	public String getName(){
		return name;
	}
	
	public void setDescription(String description){
		this.description = description;
	}
	
	public String getDescription(){
		return description;
	}
	
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
	
	public void setHostility(boolean hostile)
	{
		System.out.println("HBOOLEAN GOTTEN: " + name + hostile);
		this.hostility = hostile;
	}
	
	public boolean getHostility(){
		return hostility;
	}
	
	public void setCurrentRoom(Room room)
	{
		currentRoom = room;
		roomId = room.getRoomId();
	}
	
	public Room getCurrentRoom()
	{
		return currentRoom;
	}
	
	public List<Item> getInventory()
	{
		return inventory;
	}
	
	public void setRoomId(int id)
	{
		roomId = id;
	}
	
	public int getRoomId()
	{
		return roomId;
	}
	
	public void addItem(Item item)
	{
		inventory.add(item);
		System.out.println(inventory.size());
	}
	
	public void removeItem(Item item)
	{
		inventory.remove(item);
	}
	public boolean inventoryContains(Item item)
	{
		if(inventory.contains(item))
		{
			return true;
		}
		else
		{
			return false;
		}
	}
	
	public void attackEnemy(Player player){
		int damage = attack - player.getDefense();
		if(damage < 0)
		{
			damage = 0;
		}
		player.setHealth(player.getHealth() - damage);
		System.out.println("ATTACK " + attack);
		System.out.println("ENEMY DEFENSE " + player.getDefense());
		System.out.println("DAMAGE "+ damage);
	}
	
	public void dropItems(){
		for(int i = 0; i < inventory.size(); i++){
			inventory.get(i).setRoomLocat(currentRoom.getRoomId());
		}
	}

}
