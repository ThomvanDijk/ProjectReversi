package com.reversi.view;

import com.reversi.controller.UserController;

public class GameView extends View {


	public GameView(UserController userController, String[] args) {
		super(userController);

		//userController.GiveUserCommando("commando")

		Window window = new Window();
		window.rmain(args);
	}

		@Override
	public void run() {

	}


}
