package com.reversi.model;

import com.reversi.model.Player.PlayerType;

public abstract class Game {

	public enum GameType {
		NOGAME, REVERSI, TICTACTOE;
	}

	public enum GameMode {
		SINGLEPLAYER, ONLINE;
	}

	private final GameType gameType;
	protected Board board;
	protected GameMode gameMode;
	protected boolean noWinner;
	protected int noWinnerCount;
	protected Player player1;
	protected Player player2;
	protected int turn;

	public Game(GameType gameType, GameMode gameMode) {
		this.gameType = gameType;
		this.gameMode = gameMode;

		noWinner = true;

		if (gameMode.equals(GameMode.SINGLEPLAYER)) {
			player1 = new Player(PlayerType.HUMAN, 1);
			player2 = new Player(PlayerType.AI, 2);
		} else {
			player1 = new Player(PlayerType.AI, 1);
			player2 = new Player(PlayerType.SERVER, 2);
		}

		player1.setTurn(true);
		player2.setTurn(false);

		createBoard();
	}

	private void createBoard() {
		switch (gameType) {
		case REVERSI:
			board = new Board(8, gameType);
			break;
		case TICTACTOE:
			board = new Board(3, gameType);
			break;
		default:
			throw new IllegalStateException();
		}
	}

}
