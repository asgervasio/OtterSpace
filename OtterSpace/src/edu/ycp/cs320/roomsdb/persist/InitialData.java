package edu.ycp.cs320.roomsdb.persist;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import edu.ycp.cs320.otterspace.controller.game.Item;
import edu.ycp.cs320.otterspace.controller.game.Pair;
import edu.ycp.cs320.otterspace.controller.game.Player;
import edu.ycp.cs320.otterspace.controller.game.Room;
import edu.ycp.cs320.otterspace.model.User;

public class InitialData {
	public static List<Room> getRooms() throws IOException {
		List<Room> roomList = new ArrayList<Room>();
		ReadCSV readRooms = new ReadCSV("rooms.csv");
		try {
			// auto-generated primary key for authors table
			Integer roomId = 1;
			while (true) {
				List<String> tuple = readRooms.next();
				if (tuple == null) {
					break;
				}
				Iterator<String> i = tuple.iterator();
				Room room = new Room();
				room.setRoomId(roomId++);				
				room.setTitle(i.next().toLowerCase());
				room.setDescription(i.next());
				room.setRequirement(stringToBoolean(i.next()));
				roomList.add(room);
			}
			return roomList;
		} finally {
			readRooms.close();
		}
	}

	public static List<User> getUsers() throws IOException {
		List<User> userList = new ArrayList<User>();
		ReadCSV readUsers = new ReadCSV("Users.csv");
		try {
			
			while (true) {
				List<String> tuple = readUsers.next();
				if (tuple == null) {
					break;
				}
				Iterator<String> i = tuple.iterator();
	
				User user = new User();
				user.setEmail(i.next());
				
				user.setFirstName(i.next());
				
				user.setLastName(i.next());
			
				user.setPassword(i.next());
				user.setUsername(i.next());
				userList.add(user);
				
			}

			return userList;
		} finally {
			readUsers.close();
		}
	}
		
	
	public static List<Item> getItems() throws IOException {
		List<Item> itemList = new ArrayList<Item>();
		ReadCSV readRooms = new ReadCSV("items.csv");
		try {
			// auto-generated primary key for authors table
			Integer itemId = 1;
			while (true) {
				List<String> tuple = readRooms.next();
				if (tuple == null) {
					break;
				}
				Iterator<String> i = tuple.iterator();
				Item item = new Item();
				item.setItemId(itemId++);				
				item.setTitle(i.next());
				item.setDescription(i.next());
				item.setRoomLocat(Integer.parseInt(i.next()));
				item.setStatAffected(i.next());
				item.setStatChangeVal(Integer.parseInt(i.next()));
				itemList.add(item);
			}
			return itemList;
		} finally {
			readRooms.close();
		}
	}
	  
	public static List<List<String>> getConnections() throws IOException {
	  List<List<String>> connectList = new ArrayList<List<String>>();
	  ReadCSV readConnection = new ReadCSV("connections.csv");
		  try {
			  while (true) {
				  List<String> tuple = readConnection.next();
				  if (tuple == null)
					  break;
				  
				  Iterator<String> i = tuple.iterator();
				  List<String> connect = new ArrayList<String>();
				  connect.add(i.next());
				  connect.add(i.next());
				  connect.add(i.next());
				  connectList.add(connect);
			  }
			  return connectList;
		  } finally {
			  readConnection.close();
		  }
	  }
	  
	public static List<Player> getPlayers() throws IOException {
		List<Player> playerList = new ArrayList<Player>();
		ReadCSV readPlayer = new ReadCSV("players.csv");
		try {
			while (true) {
				List<String> tuple = readPlayer.next();
				if (tuple == null)
					break;
				  
				Iterator<String> i = tuple.iterator();
				Player player = new Player();
				player.setName(i.next());
				player.setDescription(i.next());
				player.setHealth(Integer.parseInt(i.next()));
				player.setGold(Integer.parseInt(i.next()));
				player.setScore(Integer.parseInt(i.next()));
				player.setAttack(Integer.parseInt(i.next()));
				player.setDefense(Integer.parseInt(i.next()));
				player.setRoomLoc(Integer.parseInt(i.next()));
				player.setHostility(stringToBoolean(i.next()));
				playerList.add(player);  
			}
			return playerList;
		  } finally {
			  readPlayer.close();
		  }
	}
	
	public static List<Pair<Integer, Integer>> getRoomConnections() throws IOException {
		List<Pair<Integer, Integer>> roomConnectionList = new ArrayList<Pair<Integer, Integer>>();
		ReadCSV readRoomConnect = new ReadCSV("roomConnections.csv");
		try {
			while (true) {
				List<String> tuple = readRoomConnect.next();
				if(tuple == null)
					break;
				Iterator<String> i = tuple.iterator();
				Pair<Integer, Integer> roomConnection = new Pair();
				roomConnection.setLeft(Integer.parseInt(i.next()));
				roomConnection.setRight(Integer.parseInt(i.next()));
				roomConnectionList.add(roomConnection);
			}
			return roomConnectionList;
		} finally {
			readRoomConnect.close();
		}
	}
  
	private static boolean stringToBoolean(String x){
		  if(x.equals("true"))
			  return true;
		  else
			  return false;
	}
}
