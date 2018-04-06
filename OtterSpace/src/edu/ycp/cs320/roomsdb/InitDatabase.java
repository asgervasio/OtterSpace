package edu.ycp.cs320.roomsdb;

import java.util.Scanner;

import edu.ycp.cs320.roomsdb.persist.DatabaseProvider;
import edu.ycp.cs320.roomsdb.persist.FakeDatabase;
import edu.ycp.cs320.roomsdb.persist.DerbyDatabase;

public class InitDatabase 
{
	public static void init() 
	{
		DatabaseProvider.setInstance(new FakeDatabase());
		
	}
}
