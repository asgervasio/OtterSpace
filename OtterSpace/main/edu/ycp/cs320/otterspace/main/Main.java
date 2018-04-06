package edu.ycp.cs320.otterspace.main;

import java.io.File;

import org.eclipse.jetty.server.Server;

import ecu.ycp.cs320.roomsdb.InitDatabase;
import edu.ycp.cs320.otterspace.controller.game.Player;
import edu.ycp.cs320.roomsdb.persist.DatabaseProvider;
import edu.ycp.cs320.roomsdb.persist.IDatabase;

public class Main {
	public static void main(String[] args) throws Exception {
		String webappCodeBase = "./war";
		File warFile = new File(webappCodeBase);
		Launcher launcher = new Launcher();
		InitDatabase.init();
		Player player = new Player();
		IDatabase db = DatabaseProvider.getInstance();
		
		// get a server for port 8081
		System.out.println("CREATING: web server on port 8081");
		Server server = launcher.launch(true, 8081, warFile.getAbsolutePath(), "/otterspace");

        // Start things up!		
		System.out.println("STARTING: web server on port 8081");
		server.start();
		
		// dump the console output - this will produce a lot of red text - no worries, that is normal
		server.dumpStdErr();
		
		// Inform user that server is running
		System.out.println("RUNNING: web server on port 8081");
		
        // The use of server.join() the will make the current thread join and
        // wait until the server is done executing.
        // See http://docs.oracle.com/javase/7/docs/api/java/lang/Thread.html#join()
		server.join();
		player.setCurrentRoom(db.findRoomUsingRoomId(1));
	}
}
