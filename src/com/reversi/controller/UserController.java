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
	public void notifyModel(int notification_id, String[] arguments) {
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
		case START_ONLINE_GAME: // subscribe <gametype>
			if(arguments[0].equals("reversi")) {
				gameModel.subscribeToGame(GameType.REVERSI);
			} 
			if(arguments[0].equals("tictactoe")) {
				gameModel.subscribeToGame(GameType.TICTACTOE);
			}
			break;
		case END_ONLINE_GAME:
			gameModel.endGame(null);
			break;
		case LOG_IN:
			// Argument represents a name
			gameModel.login(arguments);
			break;
		case LOG_OUT:
			break;
		case CHALLENGE_PLAYER: // challenge <player name> followed by tictactoe or reversi
			gameModel.challengePlayer(arguments);
			break;
		case SERVER_DID_MOVE:
			// Argument should be a valid number from 0 to boardsize^2
			gameModel.setMove(arguments[0]);
			break;
		case ACCEPT_CHALLENGE:
			gameModel.acceptChallenge(Integer.parseInt(arguments[0]));
			break;
		default:
			throw new IllegalStateException();
		}
	}

}
