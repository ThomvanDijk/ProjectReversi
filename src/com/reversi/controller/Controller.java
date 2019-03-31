package com.reversi.controller;

import com.reversi.model.Model;

public abstract class Controller {
	
	private Model model; // Can and must only have one model
	
	/**
	 * Attach model to controller via constructor
	 * @param model
	 */
	public Controller(Model model) {
		this.model = model;
	}
	
	public abstract void notifyModel();
	
}
