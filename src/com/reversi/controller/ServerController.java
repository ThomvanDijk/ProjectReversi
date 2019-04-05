package com.reversi.controller;

import com.reversi.model.GameModel;

public class ServerController extends Controller implements Runnable {

	public ServerController(GameModel model) {
		super(model);
	}

	@Override
	public void run() {
	}

	@Override
	public void request(Command command, String arguments) {
		
	}

}
