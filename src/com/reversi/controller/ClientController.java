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
		case START_ONLINE_GAME:
			if(arguments[0].equals("reversi")) {
				gameModel.startGame(GameMode.ONLINE, GameType.REVERSI);
			} 
			if(arguments[0].equals("tictactoe")) {
				gameModel.startGame(GameMode.ONLINE, GameType.TICTACTOE);
			}
			break;
		case END_REVERSI_MULTIPLAYER:
			break;
		case END_TICTACTOE_MULTIPLAYER:
			break;
		case LOG_OUT:
			break;
		case SET_MOVE:
			// Argument has to be a valid number
			gameModel.setMove(arguments[0], 2);
			break;
		default:
			throw new IllegalStateException();
		}
	}

}
