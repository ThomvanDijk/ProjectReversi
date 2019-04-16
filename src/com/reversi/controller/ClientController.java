package com.reversi.controller;

import com.reversi.model.GameModel;
import com.reversi.model.Game.GameMode;
import com.reversi.model.Game.GameType;

/**
 * The ClientController calls functions in GameModel to create a layer of
 * abstraction.
 * 
 * @author  Thom van Dijk
 * @version 1.0
 * @since   16-04-2019
 */
public class ClientController extends Controller {

	/**
	 * Passes a reference from GameModel to Controller where it will be set.
	 * 
	 * @param model The GameModel where functions need to be called.
	 */
	public ClientController(GameModel model) {
		super(model);
	}

	@Override
	public void notifyModel(int notificationID, String[] arguments) {
		GameModel gameModel = (GameModel) model;

		switch (notificationID) {
		case START_ONLINE_GAME:
			// Check the game type
			if (arguments[2].equals("Reversi")) {
				gameModel.startGame(GameMode.ONLINE, GameType.REVERSI, arguments);
			} else if (arguments[2].equals("Tic-tac-toe")) {
				gameModel.startGame(GameMode.ONLINE, GameType.TICTACTOE, arguments);
			} else {
				throw new IllegalArgumentException(arguments[2]);
			}
			break;
		case END_GAME:
			gameModel.endGame();
			break;
		case LOG_OUT:
			break;
		case SET_MOVE:
			// Argument should be a number
			gameModel.setMove(arguments[0]);
			break;
		case REQUEST_MOVE:
			gameModel.getMove();
			break;
		case PROCESS_NEW_CHALLENGE:
			gameModel.addChallenge(arguments);
			break;
		case CANCEL_CHALLENGE:
			gameModel.removeChallenge(Integer.parseInt(arguments[0]));
			break;
		default:
			throw new IllegalStateException();
		}
	}

}
