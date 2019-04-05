package com.reversi.controller;

import com.reversi.model.GameModel;
import com.reversi.model.Game.GameMode;
import com.reversi.model.Game.GameType;

public class ServerController extends Controller implements Runnable {

	public ServerController(GameModel model) {
		super(model);
	}

	@Override
	public void run() {
	}

	@Override
	public void notify(Notification command, String argument) {
		switch (command) {
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
			model.setMove(GameType.TICTACTOE, argument);
			break;
		default:
			throw new IllegalStateException();
		}
	}

}
