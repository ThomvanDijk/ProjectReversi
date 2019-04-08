package com.reversi.model;

import com.reversi.controller.Controller;
import com.reversi.view.GameView;

public abstract class Model implements Runnable {
	
	protected GameView gameView;
	private Controller userController;
	private Controller serverController;
	
	public void setView(GameView view) {
		if (this.gameView!=null) {
			throw new IllegalStateException("View already set.");
		}
		this.gameView = view;
	}
	
	public void setUserController(Controller userController) {
		if (this.userController!=null) {
			throw new IllegalStateException("UserController already set.");
		}
		this.userController = userController;
	}
	
	public void setServerController(Controller serverController) {
		if (this.serverController!=null) {
			throw new IllegalStateException("ServerController already set.");
		}
		this.serverController = serverController;
	}

}
