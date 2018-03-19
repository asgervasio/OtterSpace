package edu.ycp.cs320.otterspace.controller;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import edu.ycp.cs320.otterspace.model.EditorRoomModel;



public class EditorRoomControllerTest {
	private EditorRoomModel model;
	private EditorRoomController controller;
	
	// Set-up for the upcoming tests
	@Before
	public void setUp() {
		model = new EditorRoomModel();
		controller = new EditorRoomController();
		
		controller.setModel(model);
	}
	
	
}// end of class
