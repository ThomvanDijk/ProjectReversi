package com.reversi.model;

import java.util.Scanner;

import com.reversi.main.*;
import com.reversi.model.Player.PlayerType;

public class TicTacToe extends Game {

	private Scanner scanInput;

	public TicTacToe(GameMode gameMode) {
		super(GameType.TICTACTOE, gameMode);

		scanInput = new Scanner(System.in);
		
		if(player1.type.equals(PlayerType.AI)) {
			player1.ai.setTicTacToe(this);
		}
		
		if(player2.type.equals(PlayerType.AI)) {
			player2.ai.setTicTacToe(this);
		}
		
		if(gameMode.equals(GameMode.SINGLEPLAYER)) {
			consoleInput();
		} else {
			
		}
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
		if (board.getPiece(row, col) == 0) {
			return true;
		} else {
			return false;
		}
	}

	// Set the move only if it is a valid move
	public void setMove(int input, int playerID) throws Exception {
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
		if (board.getPiece(row, col) == 0) {
			board.setPiece(row, col, playerID);
		} else {
			throw new Exception("The move is not valid!");
		}

		// Show the board in the console
		board.debugBoard();
		System.out.println();
	}

	public void makeMove(Player player) {
		int input = 0;

		if (player.id == 1 && player.type.equals(PlayerType.HUMAN)) {
			input = scanInput.nextInt();
			
			player1.setTurn(false);
			player2.setTurn(true);
		} else {
			// AI has to make a move
			input = player.ai.calculateMove(board, player);
			
			player1.setTurn(true);
			player2.setTurn(false);
		}

		try {
			System.out.println("Player " + player.id + " plays: " + input);
			setMove(input, player.id);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		if(playerWon(player)) {
			System.out.println("Player " + player.id + " won!");
			noWinner = false;
		}
	}
	
	// Based on: https://www.coderslexicon.com/code/15/
	// Check board for a win by looping through rows, columns and checking diagonals.
	// If any of them are true, then there is a winning condition.
	public boolean playerWon(Player player) {
		int[][] boardArray = board.getBoard();
		
		// Loop through the rows
		for (int i = 0; i < 3; i++) {
			if (checkRow(boardArray[i][0], boardArray[i][1], boardArray[i][2], player)) {
				return true;
			}
		}

		// Loop through the columns
		for (int i = 0; i < 3; i++) {
			if (checkRow(boardArray[0][i], boardArray[1][i], boardArray[2][i], player)) {
				return true;
			}
		}

		// Check diagonals
		if (checkRow(boardArray[0][0], boardArray[1][1], boardArray[2][2], player)) {
			return true;
		}

		if (checkRow(boardArray[0][2], boardArray[1][1], boardArray[2][0], player)) {
			return true;
		}

		return false;
	}
	
	// Check three values to see if they are the same. If so, we have a winner.
	public boolean checkRow(int pos0, int pos1, int pos2, Player player) {
		if ((pos0 == pos1) && (pos0 == pos2)) {
			if(player.id == pos0) {
				return true;
			}
		}
		return false;
	}

	// This input needs to come from the GUI
	public void consoleInput() {
		board.debugBoard();

		while (scanInput.hasNextLine() && noWinner) {
			if (player1.hasTurn() && player1.type.equals(PlayerType.HUMAN)) {
				makeMove(player1);
			} else {
				makeMove(player2);
			}
		}

		if (!Main.running) {
			scanInput.close();
		}
	}

}
