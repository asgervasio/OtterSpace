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
import edu.ycp.cs320.otterspace.controller.game.Room;
import edu.ycp.cs320.otterspace.model.User;
import edu.ycp.cs320.roomsdb.model.Pair;
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
/*
	@Override
	public List<Pair<Author, Book>> insertBookWithAuthor(String lastname, String firstname, String title, String ISBN, int year, String YorN) {
		return executeTransaction(new Transaction<List<Pair<Author,Book>>>() {
			@Override
			public List<Pair<Author, Book>> execute(Connection conn) throws SQLException {
				PreparedStatement stmt = null;
				PreparedStatement Insertstmt = null;
				PreparedStatement IDstmt = null;
				ResultSet IDresultSet = null;
				String author_id = null;
				
				try {
					// searches for existing author 
					if(YorN.equals("n")){
						// finding the author ID based on the lastname and firstname
						IDstmt = conn.prepareStatement(
								"select author_id "
								+ "from authors "
								+ "where authors.lastname = ? and authors.firstname = ?"
								);
						// substitute the title entered by the user for the placeholder in the query
						IDstmt.setString(1, lastname);
						IDstmt.setString(2, firstname);
			
						// execute the query
						IDresultSet = IDstmt.executeQuery();
			
						if(IDresultSet.next()){
							author_id = IDresultSet.getString(1);
							System.out.println("Got the authorId!!");
						} else {
							System.out.println("Data not recieved!!");
						}
						
					// inserts new author into database and collects its author_ID
					}else if (YorN.equals("y")){
						Insertstmt = conn.prepareStatement(
								"insert into authors (lastname, firstname) "
								+ "values (?, ?)"
								);
						
						Insertstmt.setString(1, lastname);
						Insertstmt.setString(2, firstname);

						Insertstmt.executeUpdate();
						System.out.println("Added in the new Author");
						
						// finding the author ID based on the lastname and firstname
						IDstmt = conn.prepareStatement(
								"select author_id "
								+ "from authors "
								+ "where authors.lastname = ? and authors.firstname = ?"
								);
						// substitute the title entered by the user for the placeholder in the query
						IDstmt.setString(1, lastname);
						IDstmt.setString(2, firstname);
			
						// execute the query
						IDresultSet = IDstmt.executeQuery();
			
						if(IDresultSet.next()){
							author_id = IDresultSet.getString(1);
							System.out.println("Got authorId for new author!!");
						} else {
							System.out.println("Data not recieved!!");
						}
					}else{
						System.out.println("Please enter in a proper answer next time");
					}// end of new or old author check
					
					
					// inserting the author_id, title, ISBN, published into database
					stmt = conn.prepareStatement(
							"insert into books (author_id, title, ISBN, published) "
							+ "values (?, ?, ?, ?)"
							);
					// substitute the title entered by the user for the placeholder in the query
					stmt.setString(1, author_id);
					stmt.setString(2, title);
					stmt.setString(3, ISBN);
					stmt.setInt(4,  year);
					

					// execute the query
					stmt.executeUpdate();
					
					System.out.println("Stored new book!!");
					
					return null;
				} finally {
					DBUtil.closeQuietly(IDresultSet);

					DBUtil.closeQuietly(IDstmt);					
					DBUtil.closeQuietly(stmt);
					DBUtil.closeQuietly(Insertstmt);
					
					DBUtil.closeQuietly(conn);

				}
			}
		});
	}

	
	@Override
	public List<Pair<Author, Book>> findAuthorAndBookByAuthorLastName(String lastname) throws UnsupportedOperationException{
		return executeTransaction(new Transaction<List<Pair<Author,Book>>>() {
			@Override
			public List<Pair<Author, Book>> execute(Connection conn) throws SQLException {
				PreparedStatement stmt = null;
				ResultSet resultSet = null;
				
				try {
					// retreive all attributes from both Books and Authors tables
					stmt = conn.prepareStatement(
							"select authors.*, books.* "
									+ "  from authors, books "
									+ "  where authors.author_id = books.author_id "
									+ "        and authors.lastname = ?"
									+ "  order by books.title ASC"
					);
					
					stmt.setString(1, lastname);
					
					List<Pair<Author, Book>> result = new ArrayList<Pair<Author,Book>>();
					
					resultSet = stmt.executeQuery();
					
					// for testing that a result was returned
					Boolean found = false;
					
					while (resultSet.next()) {
						found = true;
						
						// create new Author object
						// retrieve attributes from resultSet starting with index 1
						Author author = new Author();
						loadAuthor(author, resultSet, 1);
						
						// create new Book object
						// retrieve attributes from resultSet starting at index 4
						Book book = new Book();
						loadBook(book, resultSet, 4);
						
						result.add(new Pair<Author, Book>(author, book));
					}
					
					// check if the title was found
					if (!found) {
						System.out.println("<" + lastname + "> was not found in the books table");
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
	public List<Pair<Author, Book>> findAuthorAndBookByTitle(final String title) {
		return executeTransaction(new Transaction<List<Pair<Author,Book>>>() {
			@Override
			public List<Pair<Author, Book>> execute(Connection conn) throws SQLException {
				PreparedStatement stmt = null;
				ResultSet resultSet = null;
				
				try {
					// retreive all attributes from both Books and Authors tables
					stmt = conn.prepareStatement(
							"select authors.*, books.* " +
							"  from authors, books " +
							" where authors.author_id = books.author_id " +
							"   and books.title = ?"
					);
					stmt.setString(1, title);
					
					List<Pair<Author, Book>> result = new ArrayList<Pair<Author,Book>>();
					
					resultSet = stmt.executeQuery();
					
					// for testing that a result was returned
					Boolean found = false;
					
					while (resultSet.next()) {
						found = true;
						
						// create new Author object
						// retrieve attributes from resultSet starting with index 1
						Author author = new Author();
						loadAuthor(author, resultSet, 1);
						
						// create new Book object
						// retrieve attributes from resultSet starting at index 4
						Book book = new Book();
						loadBook(book, resultSet, 4);
						
						result.add(new Pair<Author, Book>(author, book));
					}
					
					// check if the title was found
					if (!found) {
						System.out.println("<" + title + "> was not found in the books table");
					}
					
					return result;
				} finally {
					DBUtil.closeQuietly(resultSet);
					DBUtil.closeQuietly(stmt);
				}
			}
		});
	}
*/	
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
		Connection conn = DriverManager.getConnection("jdbc:derby:test.db;create=true");
		
		// Set autocommit to false to allow execution of
		// multiple queries/statements as part of the same transaction.
		conn.setAutoCommit(false);
		
		return conn;
	}
	
	private void loadRoom(Room room, ResultSet resultSet, int index) throws SQLException {
		room.setRoomId(resultSet.getInt(index++));
		room.setTitle(resultSet.getString(index++));
		room.setDescription(resultSet.getString(index++));
		room.setRequirement(resultSet.getString(index++));
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

	public void createTables() {
		executeTransaction(new Transaction<Boolean>() {
			@Override
			public Boolean execute(Connection conn) throws SQLException {
				PreparedStatement stmt1 = null;
				PreparedStatement stmt2 = null;
				PreparedStatement stmt3 = null;
				
				try {
					stmt1 = conn.prepareStatement(
						"create table rooms (" +
						"	room_id integer primary key " +
						"		generated always as identity (start with 1, increment by 1), " +									
						"	title varchar(40)," +
						"	description varchar(100)" +
						"	requirement varchar(10)" +
						")"
					);	
					stmt1.executeUpdate();
					
					stmt2 = conn.prepareStatement(
							"create table items (" +
							"	item_id integer primary key " +
							"		generated always as identity (start with 1, increment by 1), " +
							"	roomLocat integer constraint roomLocat references room, " +
							"	title varchar(40)," +
							"	description varchar(100)," +
							"   roomLocat integer " +
							"	statAff varchar(20)," +
							"	statChangeVal integer," +
							")"
					);
					stmt2.executeUpdate();
					
					stmt3 = conn.prepareStatement(
							"create table users (" +
							"   user_id integer primary key " +
							"       generated always as identity (start with 1, increment by 1), " +							
							"	emailAddress varchar(40)," +
							"	password varchar(40)," +
							"	firstname varchar(40)," +
							"	lastname varchar(40)," +
							"	Username varchar(40)," +
							")"
							);
					stmt3.executeUpdate();
					
					return true;
				} finally {
					DBUtil.closeQuietly(stmt1);
					DBUtil.closeQuietly(stmt2);
					DBUtil.closeQuietly(stmt3);
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
				
				try {
					roomList = InitialData.getRooms();
					itemList = InitialData.getItems();
					userList = InitialData.getUsers();
				} catch (IOException e) {
					throw new SQLException("Couldn't read initial data", e);
				}

				PreparedStatement insertRoom = null;
				PreparedStatement insertItem = null;
				PreparedStatement insertUser = null;

				try {
					// populate authors table (do authors first, since author_id is foreign key in books table)
					insertRoom = conn.prepareStatement("insert into room (title, description, locked) values (?, ?, ?)");
					for (Room room : roomList) {
//						insertAuthor.setInt(1, author.getAuthorId());	// auto-generated primary key, don't insert this
						insertRoom.setString(1, room.getTitle());
						insertRoom.setString(2, room.getDescription());
						insertRoom.setString(3, room.getRequirement());
						insertRoom.addBatch();
					}
					insertRoom.executeBatch();
					
					// populate books table (do this after authors table,
					// since author_id must exist in authors table before inserting book)
					insertItem = conn.prepareStatement("insert into item (title, description, roomLocat, statAff, statChange) values (?, ?, ?, ?, ?)");
					for (Item item : itemList) {
//						insertBook.setInt(1, book.getBookId());		// auto-generated primary key, don't insert this
						insertItem.setString(1, item.getTitle());
						insertItem.setString(2, item.getDescription());
						insertItem.setInt(3, item.getRoomLocat());
						insertItem.setString(4,  item.getStatAffected());
						insertItem.setInt(5, item.getStatChangeVal());
						insertItem.addBatch();
					}
					insertItem.executeBatch();
					
					insertUser = conn.prepareStatement("insert into user (emailAddress, password, firstname, lastname, Username) values (?, ?, ?, ?, ?)");
					for (User user : userList) {
						insertUser.setString(1, user.getEmail());
						insertUser.setString(2, user.getPassword());
						insertUser.setString(3, user.getFirstName());
						insertUser.setString(4, user.getLastName());
						insertUser.setString(5, user.getUsername());
						insertUser.addBatch();
					}
					insertUser.executeBatch();
					
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
	public void insertRoom(Room room) {
		// TODO Auto-generated method stub
	}

	@Override
	public Room findRoomUsingTitle(String title) {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	//TODO: Delete this method
	public Room findRoomUsingLocation(String location) {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public Room findRoomUsingRoomId(int roomId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void insertItem(Item item) {

	}

	@Override
	public Item findItemUsingTitle(String title) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Item findItemUsingItemId(int itemId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Item findItemUsingLocation(int location) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<User> getAccountInfo(String name) {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public List<User> addUserToDatabase(String name, String pass, String email, String first, String last) {
		// TODO Auto-generated method stub
		return null;
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
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<User> findUserByLastName(String lastname) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<User> matchUsernameWithPassword(String Username, String pass) {
		// TODO Auto-generated method stub
		return null;
	}

}
