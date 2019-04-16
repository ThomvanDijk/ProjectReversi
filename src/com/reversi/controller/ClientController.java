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
		GameModel gameModel = (GameModel) model;
		
		switch (notification_id) {
		case START_ONLINE_GAME:	
			// Check the game type
			if(arguments[2].equals("Reversi")) {
				gameModel.startGame(GameMode.ONLINE, GameType.REVERSI, arguments);
			} else if(arguments[2].equals("Tic-tac-toe")) {
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
