package com.reversi.view;

import com.reversi.controller.UserController;

public class View {
	
	private UserController userController;

	public View() {
	}
	
	public void addUserController(UserController userController) {
		this.userController = userController;
	}

}
