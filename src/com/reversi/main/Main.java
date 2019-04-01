package com.reversi.main;

import com.reversi.controller.*;
import com.reversi.model.*;
import com.reversi.view.*;

public class Main {

	public Main() {
		Model model = new Model();

		// Give model to controllers because they must have a model
		ServerController serverController = new ServerController(model);
		UserController userController = new UserController(model);
		
		// Make view and add a reference to controller
		View view = new View();
		view.addUserController(userController);
		
		// Add the view and controllers references to the model
		model.setView(view);
		model.setServerController(serverController);
		model.setUserController(userController);
		
		Thread modelThread = new Thread(model);
		Thread viewThread = new Thread(view);
		Thread serverControllerThread = new Thread(serverController);
		
		modelThread.start();
		viewThread.start();
		serverControllerThread.start();
	}

	public static void main(String[] args) {
		new Main();
	}

}
