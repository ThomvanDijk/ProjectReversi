package com.reversi.model;

import java.util.Scanner;

import com.reversi.main.*;

public class TicTacToe extends Game {

	private Player player1;
	private Player player2;
	private Scanner scanInput;

	public TicTacToe() {
		super(GameType.TICTACTOE, GameMode.SINGLEPLAYER);

		player1 = new Player(); // This is a human player or an AI
		player2 = new Player(); // This is an AI or server

		player1.setTurn(true);
		player2.setTurn(false);

		scanInput = new Scanner(System.in);
		
		consoleInput();
	}

	// Check if the move is valid
	public boolean isValidMove(int input, int player) {
		int row = 0;
		int col = 0;
		int boardSize = board.getBoardSize();

		if ((input % boardSize) > 0) {
			row = input / boardSize;
			col = input % boardSize;
		} else {
			row = input / boardSize;
			col = 0;
		}

		// Check if there isn't already a piece
		if (board.getBoardPiece(row, col) == 0) {
			return true;
		} else {
			return false;
		}

	}

	// Set the move only if it is a valid move
	public void setMove(int input, int player) throws Exception {
		int row = 0;
		int col = 0;
		int boardSize = board.getBoardSize();

		if ((input % boardSize) > 0) {
			row = input / boardSize;
			col = input % boardSize;
		} else {
			row = input / boardSize;
			col = 0;
		}

		// Check if there isn't already a piece
		if (board.getBoardPiece(row, col) == 0) {
			board.setBoard(row, col, player);
		} else {
			throw new Exception("The move is not valid!");
		}

		// Show the board in the console
		board.debugBoard();
	}

	public void consoleInput() {
		System.out.println("--------------");
		System.out.println("x has the turn");

		while (scanInput.hasNextLine()) {
			// System.out.println(scanInput.nextLine());
			// int place = scanInput.nextInt();

			if (player1.hasTurn()) {
				try {
					setMove(scanInput.nextInt(), 1);
				} catch (Exception e) {
					e.printStackTrace();
				}
				player1.setTurn(false);
				player2.setTurn(true);

				System.out.println("--------------");
				System.out.println("o has the turn");
			} else {
				try {
					setMove(scanInput.nextInt(), 2);
				} catch (Exception e) {
					e.printStackTrace();
				}
				player1.setTurn(true);
				player2.setTurn(false);

				System.out.println("--------------");
				System.out.println("x has the turn");
			}

		}

		if (!Main.running) {
			scanInput.close();
		}
	}

	public void update() {

	}

}
