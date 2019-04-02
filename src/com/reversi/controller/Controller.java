package com.reversi.controller;

import com.reversi.model.GameModel;

public abstract class Controller {
	
	protected GameModel model; // Can and must only have one model
	
	/**
	 * Attach model to controller via constructor
	 * @param model
	 */
	public Controller(GameModel model) {
		this.model = model;
	}
	
	public abstract void notifyModel();
	
}
