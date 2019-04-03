package com.reversi.model;

import java.util.Scanner;

import com.reversi.main.*;
import com.reversi.model.Player.PlayerType;

public class TicTacToe extends Game {

	private Player player1;
	private Player player2;
	private Scanner scanInput;

	public TicTacToe() {
		super(GameType.TICTACTOE, GameMode.SINGLEPLAYER);

		player1 = new Player(PlayerType.HUMAN, 1); // This is a human player or an AI
		player2 = new Player(PlayerType.AI, 2); // This is an AI or server

		player1.setTurn(true);
		player2.setTurn(false);

		scanInput = new Scanner(System.in);
		ai.setTicTacToe(this);
		
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
		if (board.getPiece(row, col) == 0) {
			board.setPiece(row, col, player);
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
			input = ai.calculateMove(board, player);
			
			player1.setTurn(true);
			player2.setTurn(false);
		}

		try {
			System.out.println(player.id + " plays: " + input);
			setMove(input, player.id);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// This input needs to come from the gui
	public void consoleInput() {
		board.debugBoard();

		while (scanInput.hasNextLine()) {
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

	public void update() {

	}

}
