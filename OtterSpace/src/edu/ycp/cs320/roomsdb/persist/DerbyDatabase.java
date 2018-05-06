package edu.ycp.cs320.roomsdb.persist;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import edu.ycp.cs320.otterspace.controller.game.Item;
import edu.ycp.cs320.otterspace.controller.game.Pair;
import edu.ycp.cs320.otterspace.controller.game.Player;
import edu.ycp.cs320.otterspace.controller.game.Room;
import edu.ycp.cs320.otterspace.model.User;
import edu.ycp.cs320.sqldemo.DBUtil;

public class DerbyDatabase implements IDatabase {
	static {
		try {
			Class.forName("org.apache.derby.jdbc.EmbeddedDriver");
		} catch (Exception e) {
			throw new IllegalStateException("Could not load Derby driver");
		}
	}
	
	private interface Transaction<ResultType> {
		public ResultType execute(Connection conn) throws SQLException;
	}

	private static final int MAX_ATTEMPTS = 10;

	public<ResultType> ResultType executeTransaction(Transaction<ResultType> txn) {
		try {
			return doExecuteTransaction(txn);
		} catch (SQLException e) {
			throw new PersistenceException("Transaction failed", e);
		}
	}
	
	public<ResultType> ResultType doExecuteTransaction(Transaction<ResultType> txn) throws SQLException {
		Connection conn = connect();
		
		try {
			int numAttempts = 0;
			boolean success = false;
			ResultType result = null;
			
			while (!success && numAttempts < MAX_ATTEMPTS) {
				try {
					result = txn.execute(conn);
					conn.commit();
					success = true;
				} catch (SQLException e) {
					if (e.getSQLState() != null && e.getSQLState().equals("41000")) {
						// Deadlock: retry (unless max retry count has been reached)
						numAttempts++;
					} else {
						// Some other kind of SQLException
						throw e;
					}
				}
			}
			
			if (!success) {
				throw new SQLException("Transaction failed (too many retries)");
			}
			
			// Success!
			return result;
		} finally {
			DBUtil.closeQuietly(conn);
		}
	}

	private Connection connect() throws SQLException {
		Connection conn = DriverManager.getConnection("jdbc:derby:database.db;create=true");
		
		// Set autocommit to false to allow execution of
		// multiple queries/statements as part of the same transaction.
		conn.setAutoCommit(false);
		return conn;
	}
	
	// The main method creates the database tables and loads the initial data.
	public static void main(String[] args) throws IOException {
		System.out.println("Creating tables...");
		
		DerbyDatabase db = new DerbyDatabase();
		db.dropTables("new");
		db.createTables("new");
		
		System.out.println("Loading initial data...");
		db.loadInitialData("new");
		
		System.out.println("Success!");
	}
	
	private void loadConsoleData(String data, ResultSet resultSet, int index) throws SQLException {
		data = resultSet.getString(index++);
	}
	
	private void loadRoom(Room room, ResultSet resultSet, int index) throws SQLException {
		room.setRoomId(resultSet.getInt(index++));
		room.setTitle(resultSet.getString(index++));
		room.setDescription(resultSet.getString(index++));

		room.setRequirement(resultSet.getString(index++));
		room.setConnections();
		room.setConnections(resultSet.getString(index++), resultSet.getInt(index++));

	}
	
	private void loadItem(Item item, ResultSet resultSet, int index) throws SQLException {
		item.setItemId(resultSet.getInt(index++));
		item.setTitle(resultSet.getString(index++));
		item.setDescription(resultSet.getString(index++));
		item.setRoomLocat(resultSet.getInt(index++));
		item.setStatAffected(resultSet.getString(index++));
		item.setStatChangeVal(resultSet.getInt(index++));
	}
	
	private void loadUser(User user, ResultSet resultSet, int index) throws SQLException {
		index++;
		user.setEmail(resultSet.getString(index++));
		user.setPassword(resultSet.getString(index++));
		user.setFirstName(resultSet.getString(index++));
		user.setLastName(resultSet.getString(index++));
		user.setUsername(resultSet.getString(index++));
	}
	
	private void loadPlayer(Player player, ResultSet resultSet, int index) throws SQLException {
		player.setRoomId(resultSet.getInt(index++));
		player.setName(resultSet.getString(index++));
		player.setDescription(resultSet.getString(index++));
		player.setHealth(resultSet.getInt(index++));		
		player.setGold(resultSet.getInt(index++));	
		player.setScore(resultSet.getInt(index++));	
		player.setAttack(resultSet.getInt(index++));	
		player.setDefense(resultSet.getInt(index++));
		player.setHostility(resultSet.getBoolean(index++));
	}
	
	public void dropTables(String username) {
		createTables(username);
		executeTransaction(new Transaction<Boolean>() {
			@Override
			public Boolean execute(Connection conn) throws SQLException {
				//DROP STATEMENTS
				PreparedStatement dropStmt1 = null;
				PreparedStatement dropStmt2 = null;
				PreparedStatement dropStmt3 = null;
				PreparedStatement dropStmt4 = null;
				PreparedStatement dropStmt6 = null;
				PreparedStatement dropStmt7 = null;
				String itemTableName = username + "items";
				String playerTableName = username + "player";
				String consoleTableName = username + "consolePersist";

				
				try {
					try {
						dropStmt6 = conn.prepareStatement(
							"DROP TABLE " + playerTableName + " " 

						);	
						dropStmt6.executeUpdate();
					} catch (SQLException e){
						if(!e.getSQLState().equals("X0Y32")){

							throw e;
						}
					}					
					try {
						dropStmt1 = conn.prepareStatement(
							"DROP TABLE rooms"

						);	
						dropStmt1.executeUpdate();
					} catch (SQLException e){
						if(!e.getSQLState().equals("X0Y32")){
							throw e;
						}
					}
					try {
						dropStmt2 = conn.prepareStatement(
							"DROP TABLE " + itemTableName + ""

						);	
						dropStmt2.executeUpdate();
					} catch (SQLException e){
						if(!e.getSQLState().equals("X0Y32")){
							throw e;
						}
					}
					
					try {
						dropStmt3 = conn.prepareStatement(
							"DROP TABLE users"

						);	
						dropStmt3.executeUpdate();
					} catch (SQLException e){
						if(!e.getSQLState().equals("X0Y32")){
							throw e;
						}
					}
					
					try {
						dropStmt4 = conn.prepareStatement(
							"DROP TABLE connections"

						);	
						dropStmt4.executeUpdate();
					} catch (SQLException e){
						if(!e.getSQLState().equals("X0Y32")){
							throw e;
						}
					}
					
					try {
						dropStmt7 = conn.prepareStatement(
							"DROP TABLE " + consoleTableName + " "

						);	
						dropStmt7.executeUpdate();
					} catch (SQLException e){
						if(!e.getSQLState().equals("X0Y32")){
							throw e;
						}
					}
					
					return true;
					
				} finally {
					DBUtil.closeQuietly(dropStmt1);
					DBUtil.closeQuietly(dropStmt2);
					DBUtil.closeQuietly(dropStmt3);
					DBUtil.closeQuietly(dropStmt4);
					DBUtil.closeQuietly(dropStmt6);
					DBUtil.closeQuietly(dropStmt7);
				}
			}
		});
	}

	public void createTables(String username) {
		executeTransaction(new Transaction<Boolean>() {
			@Override
			public Boolean execute(Connection conn) throws SQLException {
				PreparedStatement stmt1 = null;
				PreparedStatement stmt2 = null;
				PreparedStatement stmt3 = null;
				PreparedStatement stmt4 = null;
				PreparedStatement stmt6 = null;
				PreparedStatement stmt7 = null;

				String itemTableName = username + "items";
				String playerTableName = username + "player";
				String consoleTableName = username + "consolePersist";

				
				try {
					
				/**********************CREATE TABLES****************************************************/
					try {
					    DatabaseMetaData databaseMetadata = conn.getMetaData();
					    ResultSet resultSet = databaseMetadata.getTables(null, null, "rooms", null);
					    if (resultSet.next()) {
					       System.out.println("TABLE ALREADY EXISTS");
					    }
					    
						stmt1 = conn.prepareStatement(
							"create table rooms (" +
							"	room_id integer primary key " +
							"		generated always as identity (start with 1, increment by 1), " +									
							"	title varchar(40)," +
							"	description varchar(100)," +
							"	requirement varchar(20)" +
							")"
						);	
						stmt1.executeUpdate();
					} catch (SQLException e){
						if(!e.getSQLState().equals("X0Y32")){
							throw e;
						}
					}
					
					try {
					stmt2 = conn.prepareStatement(
							"create table " + itemTableName + " ( " +
							"   item_id integer primary key " +
							"		generated always as identity (start with 1, increment by 1), " +
							"	title varchar(40)," +
							"	description varchar(100)," +
							"	roomLocat integer,"	+
							"	statAff varchar(20)," +
							"	statChangeVal integer" +
							")"
					);
					stmt2.executeUpdate();
					} catch (SQLException e){
						if(!e.getSQLState().equals("X0Y32")){
							throw e;
						}						
					}
					
					try {
					stmt3 = conn.prepareStatement(
							"create table users (" +
							"   user_id integer primary key " +
							"       generated always as identity (start with 1, increment by 1), " +							
							"	emailAddress varchar(40)," +
							"	password varchar(40)," +
							"	firstname varchar(40)," +
							"	lastname varchar(40)," +
							"	Username varchar(40)" +
							")"
							);
					stmt3.executeUpdate();
					} catch (SQLException e){
						if(!e.getSQLState().equals("X0Y32")){
							throw e;
						}						
					}

					try {
					stmt4 = conn.prepareStatement(
							"create table connections (" +
							"   connection_id integer primary key " +
							"       generated always as identity (start with 1, increment by 1), " +
							"	roomStart integer, " +
							"   connectionDirection varchar(40)," +
							"   roomEnd integer" +
							")"
							);
					
					stmt4.executeUpdate();
					} catch (SQLException e){
						if(!e.getSQLState().equals("X0Y32")){
							throw e;
						}						
					}					
					
					try {
						stmt6 = conn.prepareStatement(
							"create table " + playerTableName + " (" +
							"	player integer primary key "+
							"		generated always as identity (start with 1, increment by 1), " +
							"	name varchar(40)," +
							"	description varchar(40)," +
							"	health integer," +
							"	gold integer," +
							"   score integer," +
							"	attack integer," +
							"	defense integer," +
							"	room integer," +
							"	hostility varchar(10)" +
							")"
							);
						stmt6.executeUpdate();
					} catch (SQLException e){
						if(!e.getSQLState().equals("X0Y32")){
							throw e;
						}												
					}
					try {
						stmt7 = conn.prepareStatement(
							"create table " + consoleTableName + " (" +
							"	data_id integer primary key " +
							"		generated always as identity (start with 1, increment by 1), " +									
							"	data varchar(1000)" +
							")"
						);	
						stmt7.executeUpdate();
					} catch (SQLException e){
						if(!e.getSQLState().equals("X0Y32")){
							throw e;
						}
					}


					return true;
					
				} finally {
					DBUtil.closeQuietly(stmt1);
					DBUtil.closeQuietly(stmt2);
					DBUtil.closeQuietly(stmt3);
					DBUtil.closeQuietly(stmt4);
					DBUtil.closeQuietly(stmt6);
					DBUtil.closeQuietly(stmt7);


				}
			}
		});
	}

	public void loadInitialData(String username) {
		executeTransaction(new Transaction<Boolean>() {
			@Override
			public Boolean execute(Connection conn) throws SQLException {
				List<Room> roomList;
				List<Item> itemList;
				List<User> userList;
				List<List<String>> connectionList;
				List<Player> playerList;
				String itemTableName = username + "items";
				String playerTableName = username + "player";

				
				try {
					roomList = InitialData.getRooms();
					itemList = InitialData.getItems();
					userList = InitialData.getUsers();
					connectionList = InitialData.getConnections();
					playerList = InitialData.getPlayers();
				} catch (IOException e) {
					throw new SQLException("Couldn't read initial data", e);
				}
				
				PreparedStatement insertRoom = null;
				PreparedStatement insertItem = null;
				PreparedStatement insertUser = null;
				PreparedStatement insertConnection = null;
				PreparedStatement insertPlayer = null;
				
				try {
					insertRoom = conn.prepareStatement("insert into rooms (title, description, requirement) values (?, ?, ?)");
					for (Room room : roomList) {
						insertRoom.setString(1, room.getTitle());
						insertRoom.setString(2, room.getDescription());
						insertRoom.setString(3, room.getRequirement().toString());
						insertRoom.addBatch();
					}
					insertRoom.executeBatch();
										
					insertItem = conn.prepareStatement("insert into " + itemTableName + " (title, description, roomLocat, statAff, statChangeVal) values (?, ?, ?, ?, ?)");
					for (Item item : itemList) {
						insertItem.setString(1, item.getTitle());
						insertItem.setString(2, item.getDescription());
						insertItem.setInt(3, item.getRoomLocat());
						insertItem.setString(4,  item.getStatAffected());
						insertItem.setInt(5, item.getStatChangeVal());
						insertItem.addBatch();
					}
					insertItem.executeBatch();				
					
					insertUser = conn.prepareStatement("insert into users (emailAddress, password, firstname, lastname, Username) values (?, ?, ?, ?, ?)");
					for (User user : userList) {
						insertUser.setString(1, user.getEmail());
						insertUser.setString(2, user.getPassword());
						insertUser.setString(3, user.getFirstName());
						insertUser.setString(4, user.getLastName());
						insertUser.setString(5, user.getUsername());
						insertUser.addBatch();
					}
					insertUser.executeBatch();				
					
					insertConnection = conn.prepareStatement("insert into connections (roomStart, connectionDirection, roomEnd) values (?, ?, ?)");
					for (List<String> connect : connectionList){
						insertConnection.setInt(1, Integer.parseInt(connect.get(0)));
						insertConnection.setString(2, connect.get(1));
						insertConnection.setInt(3, Integer.parseInt(connect.get(2)));
						insertConnection.addBatch();
					}
					insertConnection.executeBatch();
					
					insertPlayer = conn.prepareStatement("insert into " + playerTableName + " (name, description, health, gold, score, attack, defense, hostility, room) values (?, ?, ?, ?, ?, ?, ?, ?, ?)");
					for (Player player : playerList) {
						insertPlayer.setString(1, player.getName());
						insertPlayer.setString(2, player.getDescription());
						insertPlayer.setInt(3, player.getHealth());
						insertPlayer.setInt(4, player.getGold());
						insertPlayer.setInt(5, player.getScore());
						insertPlayer.setInt(6, player.getAttack());
						insertPlayer.setInt(7, player.getDefense());
						insertPlayer.setBoolean(8, player.getHostility());
						insertPlayer.setInt(9, player.getRoomId());
						insertPlayer.addBatch();
					}
					insertPlayer.executeBatch();					
					
					return true;
				} finally {
					DBUtil.closeQuietly(insertItem);
					DBUtil.closeQuietly(insertRoom);
					DBUtil.closeQuietly(insertUser);
					DBUtil.closeQuietly(insertConnection);
					DBUtil.closeQuietly(insertPlayer);
				}
			}
		});
	}
	

	

	public Player insertPlayer(Player player, String username) {
		return executeTransaction(new Transaction<Player>() {
			@Override
			public Player execute(Connection conn) throws SQLException {
				PreparedStatement stmt = null;
				String playerTableName = username + "player";
				
				try {
					stmt = conn.prepareStatement(
							"insert into " + playerTableName + " (name, description, health, gold, score, attack, defense, hostility, room) "
							+ "values (?, ?, ?, ?, ?, ?, ?, ?, ?)"
							);
					stmt.setString(1, player.getName());
					stmt.setString(2, player.getDescription());
					stmt.setInt(3, player.getHealth());
					stmt.setInt(4, player.getGold());
					stmt.setInt(5, player.getScore());
					stmt.setInt(6, player.getAttack());
					stmt.setInt(7, player.getDefense());
					stmt.setBoolean(8, player.getHostility());
					stmt.setInt(9, player.getCurrentRoom().getRoomId());

					// execute the query
					stmt.executeUpdate();
					
					System.out.println("Stored new player!!");
					
					return null;
				} finally {
					
					DBUtil.closeQuietly(stmt);
					
					DBUtil.closeQuietly(conn);

				}
			}
		});	
	}
	@Override
	public Player UpdatePlayer(Player player, String username) {
		return executeTransaction(new Transaction<Player>() {
			@Override
			public Player execute(Connection conn) throws SQLException {
				PreparedStatement stmt = null;
				String playerTableName = username + "player";
				
				try {
					stmt = conn.prepareStatement(
							"update " + playerTableName 
							+ " set name = ?, description = ?, health = ?, gold = ?, score = ?, attack = ?, defense = ?, hostility = ?, room = ?"
							+ " where name = ?"
							);
					stmt.setString(1, player.getName());
					stmt.setString(2, player.getDescription());
					stmt.setInt(3, player.getHealth());
					stmt.setInt(4, player.getGold());
					stmt.setInt(5, player.getScore());
					stmt.setInt(6, player.getAttack());
					stmt.setInt(7, player.getDefense());
					stmt.setBoolean(8, player.getHostility());
					stmt.setInt(9, player.getCurrentRoom().getRoomId());
					stmt.setString(10, player.getName());

					// execute the query
					stmt.executeUpdate();
					
					System.out.println("UPDATED player!!");
					
					return null;
				} finally {
					
					DBUtil.closeQuietly(stmt);
					
					DBUtil.closeQuietly(conn);

				}
			}
		});	
	}
	
	public Player findPlayerUsingName(String name, String username){
		return executeTransaction(new Transaction<Player>() {
			@Override
			public Player execute(Connection conn) throws SQLException {
				PreparedStatement stmt = null;
				ResultSet resultSet = null;
				String playerTableName = username + "player";
				
				try {
					// retreive all attributes from both Books and Authors tables
					stmt = conn.prepareStatement(
							"select "+ playerTableName + ".* " +
							"  from "+ playerTableName + " " +
							" where "+ playerTableName +".name = ?"
					);
					stmt.setString(1, name);
					
					Player result = new Player();
					
					resultSet = stmt.executeQuery();
					
					// for testing that a result was returned
					Boolean found = false;
					
					while (resultSet.next()) {
						found = true;
						
						// create new Room object
						// retrieve attributes from resultSet starting with index 1
						Player player = new Player();
						loadPlayer(player, resultSet, 1);
						
						
						result = player;
					}
					
					// check if the title was found
					if (!found) {
						System.out.println("<" + name + "> was not found in the Room table");
					}
					
					return result;
				} finally {
					DBUtil.closeQuietly(resultSet);
					DBUtil.closeQuietly(stmt);
				}
			}
		});		
	}
	
	public List<Player> findPlayersUsingLocation(Room roomLoc, String username){
		return executeTransaction(new Transaction<List<Player>>() {
			@Override
			public List<Player> execute(Connection conn) throws SQLException {
				PreparedStatement stmt = null;
				ResultSet resultSet = null;
				List<Player> result = new ArrayList<Player>();
				
				String playerTableName = username + "player";
				try {
					// retreive all attributes from rooms table
					stmt = conn.prepareStatement(
							"select "+ playerTableName + ".* " +
							"  from "+ playerTableName + " " +
							" where "+ playerTableName + ".room = ?"
					);
					stmt.setInt(1, roomLoc.getRoomId());
					
					System.out.println("Prepared Statement");
					
					
					resultSet = stmt.executeQuery();
					
					System.out.println("Executed Query");
					
					// for testing that a result was returned
					Boolean found = false;
					
					while (resultSet.next()) {
						found = true;
						
						// create new item object
						// retrieve attributes from resultSet starting with index 1
						Player player = new Player();
						loadPlayer(player, resultSet, 1);
						
						
						result.add(player);
					}
					
					// check if the id was found
					if (!found) {
						System.out.println("No players found in <" + roomLoc + "> ");
					}
					
					return result;
				} finally {
					DBUtil.closeQuietly(resultSet);
					DBUtil.closeQuietly(stmt);
				}
			}
		});		
	}
	
	@Override
	public Room insertRoom(Room room) {
		return executeTransaction(new Transaction<Room>() {
			@Override
			public Room execute(Connection conn) throws SQLException {
				PreparedStatement stmt1 = null;
				PreparedStatement stmt2 = null;
				PreparedStatement stmt3 = null;
				PreparedStatement stmt4 = null;
				PreparedStatement stmt5 = null;
				
				ResultSet resultSetBefore = null;
				ResultSet resultSetAfter  = null;
				ResultSet resultSetRoomID = null;  
				int before, after, roomID;

					// inserting the title description, and locked into the database
				try {
					stmt1 = conn.prepareStatement(
						"insert into rooms (title, description, requirement) "
						+ "values (?, ?, ?)"
						);
					// substitute the title entered by the user for the placeholder in the query
					stmt1.setString(1, room.getTitle());
					stmt1.setString(2, room.getDescription());
					stmt1.setString(3, room.getRequirement());
					
					// execute the query
					stmt1.executeUpdate();
					
					stmt3 = conn.prepareStatement(
							"select MAX(connections.connection_id)" +
							"	from connections"
							);
					
					resultSetBefore = stmt3.executeQuery();
					before = 0;
					
					HashMap<String, Integer> map = room.getTrueConnections();
					stmt2 = conn.prepareStatement(
						"insert into connections (connectionDirection, roomID)"
						+ "values (?, ?)"
						);
					Set<String> keySet = map.keySet();
					Iterator<String> iter = keySet.iterator();
					while(iter.hasNext()){
						String direction = iter.next();
						stmt2.setString(1, direction);
						stmt2.setInt(2, map.get(direction));
						stmt2.addBatch();
					}
					stmt2.executeBatch();
					
					resultSetAfter = stmt3.executeQuery();
					after = 2;
					
//					stmt4 = conn.prepareStatement(
//							"select max(rooms.room_id) as largestID" +
//							"	from rooms"
//							);
//					resultSetRoomID = stmt4.executeQuery();
//					roomID = resultSetRoomID.getInt(1);
					
					for(int i = before; i <= after; i++){
						stmt5 = conn.prepareStatement(
								"insert into roomConnections(room, connectID) "
								+ "values (?, ?)"
								);
						stmt5.setInt(1, room.getRoomId());
						stmt5.setInt(2, i);
						stmt5.addBatch();
					}
					stmt5.executeBatch();
					
					System.out.println("Stored new room!!");
					
					return null;
				} finally {
					
					DBUtil.closeQuietly(stmt1);
					DBUtil.closeQuietly(stmt2);
					DBUtil.closeQuietly(conn);

				}
			}
		});	

	}

		
	
	@Override
	public String insertConsole(String data, String username) {
		return executeTransaction(new Transaction<String>() {
			@Override
			public String execute(Connection conn) throws SQLException {
				PreparedStatement stmt = null;
				String consoleTableName = username + "consolePersist";

					// insert the command into the console table
				try {
					stmt = conn.prepareStatement(
							"insert into " + consoleTableName + " (data) "
							+ "values (?)"
							);
					// substitute the title entered by the user for the placeholder in the query
					stmt.setString(1, data);
					
					// execute the query
					stmt.executeUpdate();
					
					System.out.println("Console Logged!!" + data);
					
					return null;
					
				} finally {
	
					DBUtil.closeQuietly(stmt);
					
					DBUtil.closeQuietly(conn);

				}

			}
		});	
		}
	
	
	@Override
	public List<String> loadConsole(String username) 
	{
		return executeTransaction(new Transaction<List<String>>() {
			@Override
			public List<String> execute(Connection conn) throws SQLException {
				PreparedStatement stmt = null;
				ResultSet resultSet = null;
				String consoleTableName = username + "consolePersist";

				try {
					// retreive all attributes from items tables
					stmt = conn.prepareStatement(
							"select " + consoleTableName + ".data " +
							"  from " + consoleTableName + " "
					);


					List<String> result = new ArrayList<String>();
					String data = "";

					
					resultSet = stmt.executeQuery();
					
					// for testing that a result was returned
					Boolean found = false;
					int index = resultSet.getMetaData().getColumnCount();
					while (resultSet.next()) 
					{
						found = true;
				        for (int i = 1; i <= index; i++) 
				        {
							result.add(resultSet.getString(i));
				        }

					}
					
					// check if the id was found
					if (!found) {
						System.out.println("No previous data found");

					}
					
					return result;
				} finally {
					DBUtil.closeQuietly(resultSet);
					DBUtil.closeQuietly(stmt);
				}
			}
		});


	}
	
	
	@Override
	public Room findRoomUsingTitle(String title) 
	{
		return executeTransaction(new Transaction<Room>() {
			@Override
			public Room execute(Connection conn) throws SQLException {
				PreparedStatement stmt = null;
				PreparedStatement stmt2 = null;
				ResultSet resultSet = null;
				ResultSet resultSetCon = null;
				
				try {
					// retrieve all info from room matching title
					stmt = conn.prepareStatement(
							"select rooms.* " +
							"  from rooms " +
							" where  rooms.title = ?"
					);
					stmt.setString(1, title);
					
					Room result = new Room();
					
					resultSet = stmt.executeQuery();
					
					stmt2 = conn.prepareStatement(
							"select connections.* " +
							"	from connections, rooms, roomConnections " +
							"	where rooms.room_id = roomConnections.room" +
							"	and connections.connection_id = roomConnections.connectID" +
							"	and rooms.title = ?"
							);
					
					stmt2.setString(1, title);
					
					resultSetCon = stmt2.executeQuery();
					
					System.out.println(resultSetCon.getFetchSize());

					// for testing that a result was returned
					Boolean found = false;
					
					while (resultSet.next()) {
						found = true;
						
						// create new Room object
						// retrieve attributes from resultSet starting with index 1
						Room room = new Room();
						loadRoom(room, resultSet, 1);
						
						
						result = room;
					}
					
					// output if title was not found
					if (!found) {
						System.out.println("<" + title + "> was not found in the Room table");
					}
					
					return result;
				} finally {
					DBUtil.closeQuietly(resultSet);
					DBUtil.closeQuietly(stmt);
				}
			}
		});
	}
	
	@Override
	public Integer findRoomIdFromConnection(String connection) 
	{
		return executeTransaction(new Transaction<Integer>() {
			@Override
			public Integer execute(Connection conn) throws SQLException {
				PreparedStatement stmt = null;
				ResultSet resultSet = null;
				
				try {
					// retreive all attributes from rooms table
					stmt = conn.prepareStatement(
							"select connections.roomEnd " +
							"  from connections " +
							" where  connections.connectionDirection = ?"
					);
					stmt.setString(1, connection);
					
					Integer result = 0;
					
					resultSet = stmt.executeQuery();
					
					// for testing that a result was returned
					Boolean found = false;
					
					while (resultSet.next()) {
						found = true;
						
						// create new Room object
						// retrieve attributes from resultSet starting with index 1
						result = resultSet.getInt(1);

					}
					
					// check if the id was found
					if (!found) {
						System.out.println("<" + connection + "> was not found in the connections table");
					}
					
					return result;
				} finally {
					DBUtil.closeQuietly(resultSet);
					DBUtil.closeQuietly(stmt);
				}
			}
		});
	}


	@Override
	public Room findRoomUsingRoomId(int roomId) 
	{
		return executeTransaction(new Transaction<Room>() {
			@Override
			public Room execute(Connection conn) throws SQLException {
				PreparedStatement stmt = null;
				PreparedStatement stmt2 = null;
				ResultSet resultSet = null;
				ResultSet resultSetCon = null;
				
				try {
					// retreive all attributes from rooms table
					stmt = conn.prepareStatement(
							"select rooms.*, connections.connectionDirection, connections.roomEnd" +
							" 	from rooms, connections " +
							"	where  rooms.room_id = connections.roomStart " +
							"	and rooms.room_id = ?"
					);
					stmt.setInt(1, roomId);
					
					Room result = new Room();
					
					resultSet = stmt.executeQuery();
					
					// for testing that a result was returned
					Boolean found = false;
					
					while (resultSet.next()) {
						found = true;
						
						// create new Room object
						// retrieve attributes from resultSet starting with index 1
						Room room = new Room();
						loadRoom(room, resultSet, 1);
						
						
						result = room;
					}
					
					// check if the id was found
					if (!found) {
						System.out.println("<" + roomId + "> was not found in the Room table");
					}
					
					return result;
				} finally {
					DBUtil.closeQuietly(resultSet);
					DBUtil.closeQuietly(stmt);
				}
			}
		});
	}

	@Override
	public Item insertItem(Item item, String username) 
	{
		return executeTransaction(new Transaction<Item>() {
			@Override
			public Item execute(Connection conn) throws SQLException {
				PreparedStatement stmt = null;
				String itemTableName = username + "items";
					// inserting the title, description, statAffected, statChangeVal, and roomLocat into database
				try {
					stmt = conn.prepareStatement(
							"insert into " + itemTableName + "(title, description, roomLocat, statAff, statChangeVal) "
							+ "values (?, ?, ?, ?, ?)"
							);
					// substitute the title entered by the user for the placeholder in the query
					stmt.setString(1, item.getTitle());
					stmt.setString(2, item.getDescription());
					stmt.setInt(3, item.getRoomLocat());
					stmt.setString(4, item.getStatAffected());
					stmt.setInt(5, item.getStatChangeVal());
					
					// execute the query
					stmt.executeUpdate();
					
					System.out.println("Stored new item!!");
					
					return null;
				} finally {
;					
					DBUtil.closeQuietly(stmt);
					
					DBUtil.closeQuietly(conn);

				}
			}
		});	
	}
	
	@Override
	public Item UpdateItem(Item item, String username) 
	{
		return executeTransaction(new Transaction<Item>() {
			@Override
			public Item execute(Connection conn) throws SQLException {
				PreparedStatement stmt = null;
				String itemTableName = username + "items";
					// inserting the title, description, statAffected, statChangeVal, and roomLocat into database
				try {
					stmt = conn.prepareStatement(
							"update " + itemTableName
							+ " set title = ?, description = ?, roomLocat = ?, statAff = ?, statChangeVal = ?"
							+ " where title = ?"
							);
					// substitute the title entered by the user for the placeholder in the query
					stmt.setString(1, item.getTitle());
					stmt.setString(2, item.getDescription());
					stmt.setInt(3, item.getRoomLocat());
					stmt.setString(4, item.getStatAffected());
					stmt.setInt(5, item.getStatChangeVal());
					stmt.setString(6, item.getTitle());
					
					// execute the query
					stmt.executeUpdate();
					
					System.out.println("Updated item!!");
					
					return null;
				} finally {
				
					DBUtil.closeQuietly(stmt);
					
					DBUtil.closeQuietly(conn);

				}
			}
		});	
	}

	@Override
	public Item findItemUsingTitle(String title, String username) 
	{
		return executeTransaction(new Transaction<Item>() {
			@Override
			public Item execute(Connection conn) throws SQLException {
				PreparedStatement stmt = null;
				ResultSet resultSet = null;
				String itemTableName = username + "items";
				
				try {
					// retreive all attributes from items table
					stmt = conn.prepareStatement(
							"select " + itemTableName + ".* " +
							"  from " + itemTableName +
							" where  " + itemTableName + ".title = ?"
					);
					stmt.setString(1, title);
					
					Item result = new Item();
					
					resultSet = stmt.executeQuery();
					
					// for testing that a result was returned
					Boolean found = false;
					
					while (resultSet.next()) {
						found = true;
						
						// create new item object
						// retrieve attributes from resultSet starting with index 1
						Item item = new Item();
						loadItem(item, resultSet, 1);
						
						result = item;
					}
					
					// check if the title was found
					if (!found) {
						System.out.println("<" + title + "> was not found in the Item table");
					}
					
					return result;
				} finally {
					DBUtil.closeQuietly(resultSet);
					DBUtil.closeQuietly(stmt);
				}
			}
		});
	}
	

	@Override
	public Item findItemUsingItemId(int itemId, String username) 
	{
		return executeTransaction(new Transaction<Item>() {
			@Override
			public Item execute(Connection conn) throws SQLException {
				PreparedStatement stmt = null;
				ResultSet resultSet = null;
				String itemTableName = username + "items";
				
				try {
					// retreive all attributes from items tables
					stmt = conn.prepareStatement(
							"select " + itemTableName + ".* " +
							"  from " + itemTableName +
							" where  " + itemTableName + ".item_id = ?"
					);
					stmt.setInt(1, itemId);
					
					Item result = new Item();
					
					resultSet = stmt.executeQuery();
					
					// for testing that a result was returned
					Boolean found = false;
					
					while (resultSet.next()) {
						found = true;
						
						// create new item object
						// retrieve attributes from resultSet starting with index 1
						Item item = new Item();
						loadItem(item, resultSet, 1);
						
						
						result = item;
					}
					
					// check if the id was found
					if (!found) {
						System.out.println("<" + itemId + "> was not found in the item table");
					}
					
					return result;
				} finally {
					DBUtil.closeQuietly(resultSet);
					DBUtil.closeQuietly(stmt);
				}
			}
		});
	}
	

	@Override
	public List<Item> findItemsUsingLocation(int locationId, String username) 
	{
		return executeTransaction(new Transaction<List<Item>>() {
			@Override
			public List<Item> execute(Connection conn) throws SQLException {
				PreparedStatement stmt = null;
				ResultSet resultSet = null;
				String itemTableName = username + "items";
				
				try {
					// retreive all attributes from items tables
					stmt = conn.prepareStatement(
							"select " + itemTableName + ".* " +
							"  from " + itemTableName +
							" where " + itemTableName + ".roomLocat = ?"
					);
					stmt.setInt(1, locationId);
					
					List<Item> result = new ArrayList<Item>();

					
					resultSet = stmt.executeQuery();
					
					// for testing that a result was returned
					Boolean found = false;
					
					while (resultSet.next()) {
						found = true;
						
						// create new item object
						// retrieve attributes from resultSet starting with index 1
						Item item = new Item();
						loadItem(item, resultSet, 1);
						
						
						result.add(item);
					}
					
					// check if the id was found
					if (!found) {
						System.out.println("<" + locationId + "> was not found in the item table");

					}
					
					return result;
				} finally {
					DBUtil.closeQuietly(resultSet);
					DBUtil.closeQuietly(stmt);
				}
			}
		});

	}
	

	@Override
	public List<User> getAccountInfo(String username) {
		return executeTransaction(new Transaction< List<User>>() {
			@Override
			public  List<User> execute(Connection conn) throws SQLException {
				PreparedStatement stmt = null;
				ResultSet resultSet = null;
				
				try {
					// retreive all attributes from rooms table
					stmt = conn.prepareStatement(
							"select * " +
							"  from users " +
							" where  Username = ?"
					);
					stmt.setString(1, username);
					
					 List<User> result = new ArrayList<User>();
					
					resultSet = stmt.executeQuery();
					
					// for testing that a result was returned
					Boolean found = false;
					
					while (resultSet.next()) {
						found = true;
						
						// create new User object
						// retrieve attributes from resultSet starting with index 1
						User u = new User();
						loadUser(u, resultSet, 1);
					
						result.add(u);
					}
					
					// check if the id was found
					if (!found) {
						System.out.println("<" + username + "> was not found in the user table");
					}
					
					return result;
				} finally {
					DBUtil.closeQuietly(resultSet);
					DBUtil.closeQuietly(stmt);
				}
			}
		});
	}


	@Override
	public Boolean addUserToDatabase(String username, String pass, String email, String first, String last) {
		{
			return executeTransaction(new Transaction<Boolean>() {
				@Override
				public Boolean execute(Connection conn) throws SQLException {
					PreparedStatement stmt = null;
						// user into data base
					try {
						stmt = conn.prepareStatement(
								"insert into users (emailAddress, password, firstname, lastname, Username) "
								+ "values (?, ?, ?, ?, ?)"
								);
						// substitute the title entered by the user for the placeholder in the query
						

						stmt.setString(1, email);
						stmt.setString(2, pass);
						stmt.setString(3, first);
						stmt.setString(4, last);
						stmt.setString(5, username);
						// execute the query
						stmt.executeUpdate();
						
						System.out.println("New user <"+username+">!");
						
						return true;
					} finally {
						
						DBUtil.closeQuietly(stmt);
						
						DBUtil.closeQuietly(conn);

					}
				}
			});	
	}
}

	@Override
	public List<User> DeleteUserFromDatabase(String name, String pswd) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String changeInfo(String newEmail, String username, String pswd, String newPassword) {
		return executeTransaction(new Transaction<String>() {
			@Override
			public String execute(Connection conn) throws SQLException {
				
				PreparedStatement stmt = null;
				
				//ensuring that the user is valid
				if(!matchUsernameWithPassword(username, pswd).isEmpty()){
					return "User not Valid";
				}
				try{
					stmt = conn.prepareStatement(
							"update users"
							+ "set emailAddress = ?, password= ?"
							+ "where Username = ?"
					);
					stmt.setString(1, newEmail);
					stmt.setString(2, newPassword);
					stmt.setString(3, username);
					
					stmt.executeUpdate();
					
					
					System.out.println("Password Changed");
					return "Password Changed";
					
				} finally {
					
					DBUtil.closeQuietly(stmt);
				}
			}
		});
	}

	@Override
	public List<User> findAllUsers() {
		return executeTransaction(new Transaction< List<User>>() {
			@Override
			public  List<User> execute(Connection conn) throws SQLException {
				PreparedStatement stmt = null;
				ResultSet resultSet = null;
				
				try {
					// retreive all attributes from rooms table
					stmt = conn.prepareStatement(
							"select * " +
							"  from users "
					);
					
					 List<User> result = new ArrayList<User>();
					
					resultSet = stmt.executeQuery();
					
					// for testing that a result was returned
					Boolean found = false;
					
					while (resultSet.next()) {
						found = true;
						
						User u = new User();
						loadUser(u, resultSet, 1);
						
						
						result.add(u);
					}
					
					// check if the id was found
					if (!found) {
						System.out.println("No users were found in the user table");
					}
					
					return result;
				} finally {
					DBUtil.closeQuietly(resultSet);
					DBUtil.closeQuietly(stmt);
				}
			}
		});
	}

	@Override
	public List<User> findUserByLastName(String lastname) {
			return executeTransaction(new Transaction< List<User>>() {
				@Override
				public  List<User> execute(Connection conn) throws SQLException {
					PreparedStatement stmt = null;
					ResultSet resultSet = null;
					
					try {
						// retreive all attributes from rooms table
						stmt = conn.prepareStatement(
								"select * " +
								"from users " +
								" where  lastname = ?"
						);
						stmt.setString(1, lastname);
						
						 List<User> result = new ArrayList<User>();
						
						resultSet = stmt.executeQuery();
						
						// for testing that a result was returned
						Boolean found = false;
						
						while (resultSet.next()) {
							found = true;
							
							// create new Room object
							// retrieve attributes from resultSet starting with index 1
							User u = new User();
							loadUser(u, resultSet, 1);
							
							
							result.add(u);
						}
						
						// check if the id was found
						if (!found) {
							System.out.println("<" + lastname + "> was not found in the user table");
						}
						
						return result;
					} finally {
						DBUtil.closeQuietly(resultSet);
						DBUtil.closeQuietly(stmt);
					}
				}
			});
		}

	@Override
	public List<User> matchUsernameWithPassword(String Username, String pass) {
		return executeTransaction(new Transaction< List<User>>() {
			@Override
			public  List<User> execute(Connection conn) throws SQLException {
				PreparedStatement stmt = null;
				ResultSet resultSet = null;
				
				try {
					// retreive all attributes from rooms table
					stmt = conn.prepareStatement(
							"select * " +
							"  from users " +
							" where  username = ? and password = ?"
					);
					stmt.setString(1, Username);
					stmt.setString(2, pass);
					 List<User> result = new ArrayList<User>();
					
					resultSet = stmt.executeQuery();
					
					// for testing that a result was returned
					Boolean found = false;
					
					while (resultSet.next()) {
						found = true;
						
						// create new Room object
						// retrieve attributes from resultSet starting with index 1
						User u = new User();
						loadUser(u, resultSet, 1);
						
						
						result.add(u);
					}
					
					// check if the id was found
					if (!found) {
						System.out.println("<" + Username + "> was not found in the user table");
					}
					
					return result;
				} finally {
					DBUtil.closeQuietly(resultSet);
					DBUtil.closeQuietly(stmt);
				}
			}
		});
	}

}
