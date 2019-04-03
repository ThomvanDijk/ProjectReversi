package com.reversi.model;

public class Board {

	private final int boardSize;
	private int[][] board;

	public Board(int size) {
		this.boardSize = size;
		board = new int[size][size];
	}
	
	public int[][] getBoard() {
		return board;
	}
	
	public int getBoardPiece(int row, int col) {
		return board[row][col];
	}
	
	public void setBoard(int row, int col, int input) {
		board[row][col] = input;
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
