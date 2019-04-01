package com.reversi.controller;

import com.reversi.model.Model;

public class ServerController extends Controller implements Runnable {

	public ServerController(Model model) {
		super(model);
	}

	@Override
	public void notifyModel() {
		// Give the model the new move from the server and the sores
		model.update(1, 1, 1);
	}

	@Override
	public void run() {
	}

}
