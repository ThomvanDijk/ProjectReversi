package com.reversi.model;

import com.reversi.controller.Controller;
import com.reversi.view.View;

public class Model implements Runnable {
	
	private View mainView;
	private Controller userController;
	private Controller serverController;

	public Model() {
	}
	
	public void setView(View view) {
		if (this.mainView!=null) {
			throw new IllegalStateException("View already set.");
		}
		this.mainView = view;
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

	@Override
	public void run() {
		
	}

}
