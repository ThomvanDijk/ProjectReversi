package com.reversi.controller;

import com.reversi.model.Model;

public class ServerController extends Controller implements Runnable {

	public ServerController(Model model) {
		super(model);
	}

	@Override
	public void notifyModel() {
	}

	@Override
	public void run() {
	}

}
