package com.reversi.controller;

import com.reversi.model.Game.GameMode;
import com.reversi.model.Game.GameType;
import com.reversi.model.GameModel;

/**
 * The ViewController calls functions in GameModel to create a layer of
 * abstraction.
 * 
 * @author  Thom van Dijk
 * @version 1.0
 * @since   16-04-2019
 */
public class ViewController extends Controller {

	/**
	 * Passes a reference from GameModel to Controller where it will be set.
	 * 
	 * @param model The GameModel where functions need to be called.
	 */
	public ViewController(GameModel model) {
		super(model);
	}

	@Override
	public void notifyModel(int notification_id, String[] arguments) {
		GameModel gameModel = (GameModel) model;

		switch (notification_id) {
		case START_REVERSI_SINGLEPLAYER:
			gameModel.startGame(GameMode.SINGLEPLAYER, GameType.REVERSI, null);
			break;
		case START_TICTACTOE_SINGLEPLAYER:
			gameModel.startGame(GameMode.SINGLEPLAYER, GameType.TICTACTOE, null);
			break;
		case SUBSCRIBE_TO_REVERSI:
			gameModel.subscribeToGame(GameType.REVERSI);
			break;
		case SUBSCRIBE_TO_TICTACTOE:
			gameModel.subscribeToGame(GameType.TICTACTOE);
			break;
		case END_GAME:
			gameModel.endGame();
			break;
		case LOG_IN:
			// Argument represents a name
			gameModel.login(arguments); 
			break;
		case LOG_OUT:
			break;
		case CHALLENGE_PLAYER: 
			// Arguments should be player name followed by tictactoe or reversi
			gameModel.challengePlayer(arguments);
			break;
		case SET_MOVE:
			// Argument should be a valid number
			gameModel.setMove(arguments[0]); 
			break;
		case ACCEPT_CHALLENGE:
			gameModel.acceptChallenge(Integer.parseInt(arguments[0]));
			break;
		case REQUEST_PLAYERLIST:
			gameModel.requestPlayerlist();
			break;
		default:
			throw new IllegalStateException();
		}
	}

}
