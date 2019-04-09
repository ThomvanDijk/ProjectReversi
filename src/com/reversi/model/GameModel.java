package com.reversi.model;

import com.reversi.model.Game.GameMode;
import com.reversi.model.Game.GameType;

public class GameModel extends Model {

	private TicTacToe ticTacToe;
	private Reversi reversi;
	private GameType currentGame;

	public GameModel() {
		// reversi = new Reversi(GameMode.SINGLEPLAYER);
	}

	public void startGame(GameMode gameMode, GameType gameType) {
		switch (gameType) {
		case REVERSI:
			currentGame = gameType;
			if (gameMode.equals(GameMode.SINGLEPLAYER)) {
				ticTacToe = new TicTacToe(GameMode.SINGLEPLAYER);
				notifyView();
				break;
			} else {
				ticTacToe = new TicTacToe(GameMode.ONLINE);
				notifyView();
				break;
			}
		case TICTACTOE:
			currentGame = gameType;
			if (gameMode.equals(GameMode.SINGLEPLAYER)) {
				break;
			} else {
				break;
			}
		default:
			throw new IllegalStateException();
		}
	}

	public void setMove(GameType gameType, String argument) {
		// Check if the move is for the current game
		if (gameType.equals(currentGame)) {
			switch (gameType) {
			case REVERSI:
				//reversi.setMove(input, validMoves, playerID);
				break;
			case TICTACTOE:
				int move = Integer.getInteger(argument);
				try {
					ticTacToe.setMove(move, ticTacToe.getHumanPlayer().id);
				} catch (Exception e) {
					e.printStackTrace();
				}
				break;
			default:
				throw new IllegalStateException();
			}
		} else {
			throw new IllegalArgumentException("Game: " + gameType + " is not being played!");
		}
	}

	// Send a notify to do something here
	public void setState() {

	}

	public void update(int serverMove, int enemyScore, int playerScore) {
		// TODO implement
	}

	public int[][] getBoard() {
		switch (currentGame) {
		case REVERSI:
			return reversi.board.getBoard();
		case TICTACTOE:
			return ticTacToe.board.getBoard();
		default:
			return null;
		}
	}
	
	public Player[] getPlayer() {
		Player[] players = new Player[2];
		switch (currentGame) {
		case REVERSI:
			players[0] = reversi.player1;
			players[1] = reversi.player2;
			return players;
		case TICTACTOE:
			players[0] = ticTacToe.player1;
			players[1] = ticTacToe.player2;
			return players;
		default:
			return null;
		}
	}

	@Override
	public void run() {

	}

}