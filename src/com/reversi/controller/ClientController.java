package com.reversi.controller;

import com.reversi.client.Parser.ArgumentKey;
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
			if(arguments[0].equals("Reversi")) {
				gameModel.startGame(GameMode.ONLINE, GameType.REVERSI);
			} 
			if(arguments[0].equals("Tic-tac-toe")) {
				gameModel.startGame(GameMode.ONLINE, GameType.TICTACTOE);
			}
			break;
		case END_ONLINE_GAME:
			gameModel.endGame(arguments);
			break;
		case LOG_OUT:
			break;
		case SERVER_DID_MOVE: // Tell model that server did a move
			// Argument has to be a valid number
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
