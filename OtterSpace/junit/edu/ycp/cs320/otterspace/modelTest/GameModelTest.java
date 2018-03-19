package edu.ycp.cs320.otterspace.modelTest;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

import edu.ycp.cs320.otterspace.controller.GameController;
import edu.ycp.cs320.otterspace.model.EditorRoomModel;
import edu.ycp.cs320.otterspace.model.GameModel;

public class GameModelTest 
{
	private GameModel model;
	
	@Before
	public void setUp()
	{
		model = new GameModel();		
	}
	
	@Test
	public void testSetCommand()
	{
		String cmd = "test command";
		model.setCommand(cmd);

		assertEquals(cmd, model.getCommand());
	}

}
