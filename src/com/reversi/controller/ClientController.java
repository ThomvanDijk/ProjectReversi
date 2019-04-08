package com.reversi.controller;

import com.reversi.model.GameModel;
import com.reversi.model.Game.GameMode;
import com.reversi.model.Game.GameType;

public class ClientController extends Controller {

	public ClientController(GameModel model) {
		super(model);
	}

	@Override
	public void notify(Notification notification, String argument) {
		switch (notification) {
		case START_REVERSI_MULTIPLAYER:
			model.startGame(GameMode.ONLINE, GameType.REVERSI);
			break;
		case START_TICTACTOE_MULTIPLAYER:
			model.startGame(GameMode.ONLINE, GameType.TICTACTOE);
			break;
		case END_REVERSI_MULTIPLAYER:
			break;
		case END_TICTACTOE_MULTIPLAYER:
			break;
		case LOG_OUT:
			break;
		case SET_MOVE_REVERSI:
			break;
		case SET_MOVE_TICTACTOE:
			// Argument has to be a valid number
			model.setMove(GameType.TICTACTOE, argument);
			break;
		default:
			throw new IllegalStateException();
		}
	}

}
