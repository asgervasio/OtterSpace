package edu.ycp.cs320.otterspace.controller;

import org.junit.Before;

import edu.ycp.cs320.otterspace.model.GameModel;

public class GameControllerTest 
{
	private GameModel model;
	private GameController controller;
	
	@Before
	public void setUp() 
	{
		model = new GameModel();
		controller = new GameController();
		controller.setModel(model);
	}

}
