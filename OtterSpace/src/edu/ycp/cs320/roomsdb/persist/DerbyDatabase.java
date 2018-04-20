package edu.ycp.cs320.roomsdb.persist;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import edu.ycp.cs320.otterspace.controller.game.Item;
import edu.ycp.cs320.otterspace.controller.game.Pair;
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
		Connection conn = DriverManager.getConnection("jdbc:derby:H:/mydatabase.db;create=true");
		
		// Set autocommit to false to allow execution of
		// multiple queries/statements as part of the same transaction.
		conn.setAutoCommit(false);
		
		return conn;
	}
	
	private void loadRoom(Room room, ResultSet resultSet, int index) throws SQLException {
		room.setRoomId(resultSet.getInt(index++));
		room.setTitle(resultSet.getString(index++));
		room.setDescription(resultSet.getString(index++));
		room.setRequirement(resultSet.getBoolean(index++));
	}
	
	private void loadItem(Item item, ResultSet resultSet, int index) throws SQLException {
		item.setItemId(resultSet.getInt(index++));
		item.setRoomLocat(resultSet.getInt(index++));
		item.setTitle(resultSet.getString(index++));
		item.setDescription(resultSet.getString(index++));
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

	public void createTables() {
		executeTransaction(new Transaction<Boolean>() {
			@Override
			public Boolean execute(Connection conn) throws SQLException {
				PreparedStatement stmt1 = null;
				PreparedStatement stmt2 = null;
				PreparedStatement stmt3 = null;
				PreparedStatement stmt4 = null;
				PreparedStatement stmt5 = null;
				
				try {
					try {
						stmt1 = conn.prepareStatement(
							"create table rooms (" +
							"	room_id integer primary key " +
							"		generated always as identity (start with 1, increment by 1), " +									
							"	title varchar(40)," +
							"	description varchar(100)," +
							"	requirement varchar(10)" +
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
							"create table items (" +
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
							"   connectionDirection varchar(40)," +
							"   roomID integer" +
							")"
							);
					
					stmt4.executeUpdate();
					} catch (SQLException e){
						if(!e.getSQLState().equals("X0Y32")){
							throw e;
						}						
					}					
					
					try {
					stmt5 = conn.prepareStatement(
							"create table roomConnections (" +
							"	room_id integer," +
							"	connection_id integer," +
							"   foreign key (room_id) references rooms(room_id), " +
							"   foreign key (connection_id) references connections(connection_id) " +
							")"
							);
					stmt5.executeUpdate();
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
					DBUtil.closeQuietly(stmt5);
				}
			}
		});
	}

	public void loadInitialData() {
		executeTransaction(new Transaction<Boolean>() {
			@Override
			public Boolean execute(Connection conn) throws SQLException {
				List<Room> roomList;
				List<Item> itemList;
				List<User> userList;
				List<Pair<String, Integer>> connectionList;
				
				try {
					roomList = InitialData.getRooms();
					itemList = InitialData.getItems();
					userList = InitialData.getUsers();
					connectionList = InitialData.getConnections();
				} catch (IOException e) {
					throw new SQLException("Couldn't read initial data", e);
				}

				PreparedStatement insertRoom = null;
				PreparedStatement insertItem = null;
				PreparedStatement insertUser = null;
				PreparedStatement insertConnection = null;

				try {
					insertRoom = conn.prepareStatement("insert into rooms (title, description, requirement) values (?, ?, ?)");
					for (Room room : roomList) {
						insertRoom.setString(1, room.getTitle());
						insertRoom.setString(2, room.getDescription());
						insertRoom.setString(3, room.getRequirement().toString());
						insertRoom.addBatch();
					}
					insertRoom.executeBatch();
					
					insertItem = conn.prepareStatement("insert into items (title, description, roomLocat, statAff, statChangeVal) values (?, ?, ?, ?, ?)");
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
					
					insertConnection = conn.prepareStatement("insert into connections (connectionDirection, roomID) values (?, ?)");
					for (Pair<String, Integer> connect : connectionList){
						insertConnection.setString(1, connect.getLeft());
						insertConnection.setInt(2, connect.getRight());
						insertConnection.addBatch();
					}
					insertConnection.executeBatch();
					return true;
				} finally {
					DBUtil.closeQuietly(insertItem);
					DBUtil.closeQuietly(insertRoom);
					DBUtil.closeQuietly(insertUser);
				}
			}
		});
	}
	
	// The main method creates the database tables and loads the initial data.
	public static void main(String[] args) throws IOException {
		System.out.println("Creating tables...");
		
		DerbyDatabase db = new DerbyDatabase();
		db.createTables();
		
		System.out.println("Loading initial data...");
		db.loadInitialData();
		
		System.out.println("Success!");
	}


	@Override
	public Room insertRoom(Room room) {
		return executeTransaction(new Transaction<Room>() {
			@Override
			public Room execute(Connection conn) throws SQLException {
				PreparedStatement stmt = null;
					// inserting the title description, and locked into the database
				try {
					stmt = conn.prepareStatement(
							"insert into rooms (title, description, requirement) "
							+ "values (?, ?, ?)"
							);
					// substitute the title entered by the user for the placeholder in the query
					stmt.setString(1, room.getTitle());
					stmt.setString(2, room.getDescription());
					stmt.setBoolean(3, room.getRequirement());
					

					// execute the query
					stmt.executeUpdate();
					
					System.out.println("Stored new room!!");
					
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
	public Room findRoomUsingTitle(String title) 
	{
		return executeTransaction(new Transaction<Room>() {
			@Override
			public Room execute(Connection conn) throws SQLException {
				PreparedStatement stmt = null;
				ResultSet resultSet = null;
				
				try {
					// retreive all attributes from both Books and Authors tables
					stmt = conn.prepareStatement(
							"select rooms.* " +
							"  from rooms " +
							" where  rooms.title = ?"
					);
					stmt.setString(1, title);
					
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
					
					// check if the title was found
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
	public Room findRoomUsingRoomId(int roomId) 
	{
		return executeTransaction(new Transaction<Room>() {
			@Override
			public Room execute(Connection conn) throws SQLException {
				PreparedStatement stmt = null;
				ResultSet resultSet = null;
				
				try {
					// retreive all attributes from rooms table
					stmt = conn.prepareStatement(
							"select rooms.* " +
							"  from rooms " +
							" where  rooms.id = ?"
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
	public Item insertItem(Item item) 
	{
		return executeTransaction(new Transaction<Item>() {
			@Override
			public Item execute(Connection conn) throws SQLException {
				PreparedStatement stmt = null;
					// inserting the title, description, statAffected, statChangeVal, and roomLocat into database
				try {
					stmt = conn.prepareStatement(
							"insert into items(title, description, roomLocat, statAff, statChangeVal) "
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
	public Item findItemUsingTitle(String title) 
	{
		return executeTransaction(new Transaction<Item>() {
			@Override
			public Item execute(Connection conn) throws SQLException {
				PreparedStatement stmt = null;
				ResultSet resultSet = null;
				
				try {
					// retreive all attributes from items table
					stmt = conn.prepareStatement(
							"select items.* " +
							"  from items " +
							" where  items.title = ?"
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
	public Item findItemUsingItemId(int itemId) 
	{
		return executeTransaction(new Transaction<Item>() {
			@Override
			public Item execute(Connection conn) throws SQLException {
				PreparedStatement stmt = null;
				ResultSet resultSet = null;
				
				try {
					// retreive all attributes from items tables
					stmt = conn.prepareStatement(
							"select items.* " +
							"  from items " +
							" where  items.id = ?"
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
	public List<Item> findItemsUsingLocation(int locationId) 
	{
		return executeTransaction(new Transaction<List<Item>>() {
			@Override
			public List<Item> execute(Connection conn) throws SQLException {
				PreparedStatement stmt = null;
				ResultSet resultSet = null;
				
				try {
					// retreive all attributes from items tables
					stmt = conn.prepareStatement(
							"select items.* " +
							"  from items " +
							" where  items.location = ?"
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
							" where  username = ?"
					);
					stmt.setString(1, username);
					
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
	public List<User> addUserToDatabase(String username, String pass, String email, String first, String last) {
		{
			return executeTransaction(new Transaction<List<User>>() {
				@Override
				public List<User> execute(Connection conn) throws SQLException {
					PreparedStatement stmt = null;
						// user into data base
					try {
						stmt = conn.prepareStatement(
								"insert into user(emailAddress, password, firstname, lastname, Username) "
								+ "values (?, ?, ?, ?, ?)"
								);
						// substitute the title entered by the user for the placeholder in the query
						
						stmt.setString(1, username);
						stmt.setString(2, pass);
						stmt.setString(3, email);
						stmt.setString(4, first);
						stmt.setString(5, last);
						
						// execute the query
						stmt.executeUpdate();
						
						System.out.println("New user <"+username+">!");
						
						return null;
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
	public List<User> changePassword(String name, String pswd, String newPassword) {
		// TODO Auto-generated method stub
		return null;
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
						
						// create new Room object
						// retrieve attributes from resultSet starting with index 1
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
								"  from users " +
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
		// TODO Auto-generated method stub
		return null;
	}

}
