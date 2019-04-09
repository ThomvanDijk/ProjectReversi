package com.reversi.controller;

import com.reversi.model.GameModel;
import com.reversi.model.Game.GameMode;
import com.reversi.model.Game.GameType;

public class ClientController extends Controller {

	public ClientController(GameModel model) {
		super(model);
	}

	@Override
	public void notifyModel(int notification_id, String[] arguments) {
		GameModel gameModel = (GameModel) model; // cast
		
		switch (notification_id) {
		case START_REVERSI_MULTIPLAYER:
			gameModel.startGame(GameMode.ONLINE, GameType.REVERSI);
			break;
		case START_TICTACTOE_MULTIPLAYER:
			gameModel.startGame(GameMode.ONLINE, GameType.TICTACTOE);
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
			gameModel.setMove(GameType.TICTACTOE, arguments[0]);
			break;
		default:
			throw new IllegalStateException();
		}
	}

}
