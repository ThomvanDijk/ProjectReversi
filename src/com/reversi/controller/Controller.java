package com.reversi.controller;

import com.reversi.model.GameModel;

public abstract class Controller {
	
	public enum Notification {
		START_REVERSI_SINGLEPLAYER,
		START_TICTACTOE_SINGLEPLAYER,
		END_REVERSI_SINGLEPLAYER,
		END_TICTACTOE_SINGLEPLAYER,
		START_REVERSI_MULTIPLAYER,
		START_TICTACTOE_MULTIPLAYER,
		END_REVERSI_MULTIPLAYER,
		END_TICTACTOE_MULTIPLAYER,
		LOG_IN,
		LOG_OUT,
		CHALLENGE_PLAYER,
		SET_MOVE_REVERSI,
		SET_MOVE_TICTACTOE
	}
	
	protected GameModel model; // Can and must only have one model
	
	/**
	 * Attach model to controller via constructor
	 * @param model
	 */
	public Controller(GameModel model) {
		this.model = model;
	}
	
	public abstract void notify(Notification command, String argument);
	
}
