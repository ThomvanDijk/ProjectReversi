package com.reversi.main;

import com.reversi.controller.ServerController;
import com.reversi.controller.UserController;
import com.reversi.model.Model;
import com.reversi.view.View;

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
		model.setController(serverController);
		model.setController(userController);
	}

	public static void main(String[] args) {
		new Main();
		System.out.println("Fuck you");
	}

}
