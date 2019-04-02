package com.reversi.view;

import com.reversi.controller.UserController;

public abstract class View implements Runnable {
	
	private final UserController userController;
	
	public View(UserController userController) {
		this.userController = userController;
	}

}
