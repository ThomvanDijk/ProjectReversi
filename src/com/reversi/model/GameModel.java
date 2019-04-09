package com.reversi.model;

import java.util.ArrayList;
import java.util.List;

import com.reversi.controller.*;
import com.reversi.model.Game.GameMode;
import com.reversi.model.Game.GameType;
import com.reversi.view.*;

public class GameModel extends Model {

	private boolean running;
	private TicTacToe ticTacToe;
	private Reversi reversi;

	private GameType currentGameType;
	private GameMode currentGameMode;

	public GameModel() {
		// reversi = new Reversi(GameMode.SINGLEPLAYER);
		currentGameType = GameType.NOGAME;
	}

	// Send a notify to do something here
	public void setState() {

	}

	public void update(int serverMove, int enemyScore, int playerScore) {
		// TODO implement
	}

	public void startGame(GameMode gameMode, GameType gameType) {
		currentGameMode = gameMode;
		currentGameType = gameType;

		switch (gameType) {
		case REVERSI:
			if (gameMode.equals(GameMode.SINGLEPLAYER)) {
				reversi = new Reversi(GameMode.SINGLEPLAYER);
				break;
			} else {
				reversi = new Reversi(GameMode.ONLINE);
				break;
			}
		case TICTACTOE:
			if (gameMode.equals(GameMode.SINGLEPLAYER)) {
				ticTacToe = new TicTacToe(GameMode.SINGLEPLAYER);
				break;
			} else {
				ticTacToe = new TicTacToe(GameMode.ONLINE);
				break;
			}
		default:
			throw new IllegalStateException();
		}

		notifyView();
	}

	public void setMove(String move, int playerID) {
		int intMove = Integer.valueOf(move);
		switch (currentGameType) {
		case REVERSI:
			try {
				int boardsize = reversi.board.getBoardSize();
				reversi.setMove(intMove, reversi.getValidMoves(reversi.board, playerID), playerID, reversi.board);
			} catch (Exception e) {
				e.printStackTrace();
			}
			break;
		case TICTACTOE:
			try {
				ticTacToe.setMove(intMove, playerID);
			} catch (Exception e) {
				e.printStackTrace();
			}
			break;
		default:
			throw new IllegalStateException();
		}

		notifyView();
	}

	public void login(String[] arguments) {
		client.login(arguments);
	}


	public int[][] getBoard() {
		switch (currentGameType) {
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
		switch (currentGameType) {
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

//		login(new String[] {"Naam", "localhost"});
//		
//		while (true) {
//			
//		}
	}

}
