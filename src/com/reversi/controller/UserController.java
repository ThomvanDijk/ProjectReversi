package com.reversi.controller;

import com.reversi.model.Game.GameMode;
import com.reversi.model.Game.GameType;
import com.reversi.model.GameModel;

public class UserController extends Controller {

	public UserController(GameModel model) {
		super(model);
	}

	// From GameView use this notify function to send a notification to model. Arguments can be null...
	@Override
	public void notifyModel(int notification_id, String argument) {
		GameModel gameModel = (GameModel) model; // cast
		
		switch (notification_id) {
		case START_REVERSI_SINGLEPLAYER:
			gameModel.startGame(GameMode.SINGLEPLAYER, GameType.REVERSI);
			break;
		case START_TICTACTOE_SINGLEPLAYER:
			gameModel.startGame(GameMode.SINGLEPLAYER, GameType.TICTACTOE);
			break;
		case END_REVERSI_SINGLEPLAYER:
			break;
		case END_TICTACTOE_SINGLEPLAYER:
			break;
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
		case LOG_IN:
			break;
		case LOG_OUT:
			break;
		case CHALLENGE_PLAYER:
			break;
		case SET_MOVE_REVERSI:
			break;
		case SET_MOVE_TICTACTOE:
			// Argument should be a valid number
			gameModel.setMove(GameType.TICTACTOE, argument);
			break;
		default:
			throw new IllegalStateException();
		}
	}

}
