package com.reversi.model;

import com.reversi.model.Game.GameType;

public class Board {

	private final int boardSize;
	private int[][] board;
	private int placesOccupied;
	private GameType gameType;

	public Board(int size, GameType gameType) {
		this.boardSize = size;
		board = new int[size][size];
		placesOccupied = 1;
		this.gameType = gameType;
	}

	public int[][] getBoard() {
		return board;
	}

	public int getPiece(int row, int col) {
		return board[row][col];
	}

	public void setPiece(int row, int col, int piece) throws Exception {
		if (placesOccupied >= boardSize * boardSize) {
			throw new Exception("All places are occupied!");
		} else {
			board[row][col] = piece;
			placesOccupied++;
		}
	}

	public boolean emptyPlaces() {
		if (placesOccupied >= boardSize * boardSize) {
			return false;
		} else {
			return true;
		}
	}

	public int getBoardSize() {
		return boardSize;
	}

	public void debugBoard() {
		for (int row = 0; row < boardSize; row++) {
			for (int col = 0; col < boardSize; col++) {
				if (board[row][col] == 0) {
					System.out.print(". ");
				} else if (board[row][col] == 1) {
					if (gameType.equals(GameType.REVERSI)) {
						System.out.print("z ");
					} else {
						System.out.print("x ");
					}
				} else {
					if (gameType.equals(GameType.REVERSI)) {
						System.out.print("w ");
					} else {
						System.out.print("o ");
					}
				}

			}
			System.out.println();
		}
	}

}
