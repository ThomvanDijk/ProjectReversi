package com.reversi.model;

import java.util.ArrayList;

public abstract class Game {
	
	private final GameType gameType;
	private Board board;
	
	public Game(final GameType gameType) {
		this.gameType = gameType;
		
		createBoard();
	}

	public boolean checkMove(int row, int col) {
		switch(gameType) {
		case REVERSI:
			break;
		case TICTACTOE:
			break;
		default:
			throw new IllegalStateException();
		}
		return false;
	}
	
	private void createBoard() {
		switch(gameType) {
		case REVERSI:
			board = new Board();
			break;
		case TICTACTOE:
			board = new Board();
			break;
		default:
			throw new IllegalStateException();
		}
	}

}
