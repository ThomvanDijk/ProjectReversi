package com.reversi.model;

public abstract class Game {
	
	public enum GameType {
		REVERSI, TICTACTOE;
	}
	
	public enum GameMode {
		SINGLEPLAYER, MULTIPLAYER;
	}
	
	private final GameType gameType;
	protected Board board;
	protected GameMode gameMode;
	protected AI ai;
	
	public Game(GameType gameType, GameMode gameMode) {
		this.gameType = gameType;
		this.gameMode = gameMode;
		ai = new AI();
		
		createBoard();
	}
	
	private void createBoard() {
		switch(gameType) {
		case REVERSI:
			board = new Board(8);
			break;
		case TICTACTOE:
			board = new Board(3);
			break;
		default:
			throw new IllegalStateException();
		}
	}

}
