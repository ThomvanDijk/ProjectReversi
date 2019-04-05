package com.reversi.main;

import com.reversi.controller.*;
import com.reversi.model.*;
import com.reversi.server.Client;
import com.reversi.view.*;

public class Main {
	
	public static boolean running;

	public Main() {
		running = true;
		
		Client client = new Client();
		
		GameModel model = new GameModel();

		// Give model to controllers because they must have a model
		ServerController serverController = new ServerController(model);
		UserController userController = new UserController(model);
		
		// Make view and add a reference to controller
		GameView view = new GameView(userController);
		
		// Add the view and controllers references to the model
		model.setView(view);
		model.setServerController(serverController);
		model.setUserController(userController);
		
		Thread modelThread = new Thread(model);
		Thread viewThread = new Thread(view);
		
		modelThread.start();
		viewThread.start();
	}

	public static void main(String[] args) {
		new Main();
		running = false;
	}

}
