package com.reversi.model;

public class Board {

	private final int boardSize;
	private int[][] board;
	private int placesOccupied;

	public Board(int size) {
		this.boardSize = size;
		board = new int[size][size];
		placesOccupied = 1;
	}
	
	public int[][] getBoard() {
		return board;
	}
	
	public int getPiece(int row, int col) {
		return board[row][col];
	}
	
	public void setPiece(int row, int col, int piece) throws Exception {
		System.out.println("Number of places occupied: " + placesOccupied);
		if(placesOccupied >= boardSize * boardSize) {
			throw new Exception("All places are occupied!");
		} else {
			board[row][col] = piece;
			placesOccupied++;
		}
	}
	
	public boolean emptyPlaces() {
		if(placesOccupied >= boardSize * boardSize) {
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
					System.out.print("x ");
				} else {
					System.out.print("o ");
				}

			}
			System.out.println();
		}
	}

}
