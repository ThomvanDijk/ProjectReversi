package com.reversi.model;

import java.util.Scanner;

import com.reversi.main.*;

public class Board {

	private final int boardSize;
	// private ArrayList<ArrayList> board;
	private int[][] board;

	public Board(int size) {
		this.boardSize = size;
		board = new int[size][size];

		//debugBoard();
	}

	public void setMove(int input, int player) {
		int row = 0;
		int col = 0;

		if ((input % boardSize) > 0) {
			row = input / boardSize;
			col = input % boardSize;
		} else {
			row = input / boardSize;
			col = 0;
		}

		//System.out.println(row + " " + col);

		board[row][col] = player;
		debugBoard();
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
