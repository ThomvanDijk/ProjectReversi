package com.reversi.main;

import com.reversi.client.Client;
import com.reversi.controller.*;
import com.reversi.model.*;
import com.reversi.view.*;

public class Main {
	
	public static boolean running;

	// Pass args for javaFX
	public Main(String[] args) {
		GameModel model = new GameModel();

		// Give model to controllers because they must have a model
		ClientController clientController = new ClientController(model);
		UserController userController = new UserController(model);
		
		// Make a client that connects to the server
		Client client = new Client(clientController);
		
		// Make view and add a reference to controller 
		// also pass args for javaFX
		GameView view = new GameView(userController, args);
		
		// Add the view references to the model
		model.setView(view);
		model.setClient(client);
		
		Thread modelThread = new Thread(model);
		Thread viewThread = new Thread(view);
		
		modelThread.start();
		viewThread.start();
	}

	public static void main(String[] args) {
		new Main(args);
	}

}
