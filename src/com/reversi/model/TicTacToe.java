package com.reversi.model;

import com.reversi.model.Player.PlayerType;
import com.reversi.view.Main;

import java.util.Scanner;

/**
 * The TicTacToe class contains all the rules of TicTacToe and handles some game
 * specific moves.
 * 
 * @author  Thom van Dijk
 * @version 1.0
 * @since   1.0
 */
public class TicTacToe extends Game {

	private Scanner scanInput;
	/**
	 * De constructor vraagt de game modus op.
	 * In deze klasse word het gametype altijd overschreven met TicTacToe.
	 * 
	 * @param gameMode		De game modus van het spel.
	 */
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

	/**
	 * Controleren of de zet een valide zet is
	 *
	 * @param  input		De zet die gechecked moet worden.
	 * @param  player 		De speler die de zet wilt doen.
	 */
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

	/**
	 * De zet uitvoeren die uit de input komt, zolang
	 * de zet een valide zet is.
	 *
	 * @param  input		De zet die de speler wilt uitvoeren.
	 * @param  playerID 	Het ID van de huidige speler.
	 */
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

	/**
	 * De zet voorbereiden die uit de input komt.
	 *
	 * @param  player 	De huidige speler.
	 */
	public void makeMove(Player player) {
		int input = 0;

		if (player.color == 1 && player.type.equals(PlayerType.HUMAN)) {
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
			System.out.println("Player " + player.color + " plays: " + input);
			setMove(input, player.color);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		if(playerWon(player)) {
			System.out.println("Player " + player.color + " won!");
			noWinner = false;
		}
	}
	

	/**
	 * Based on: https://www.coderslexicon.com/code/15/
	 * Check board for a win by looping through rows, columns and checking diagonals.
	 * If any of them are true, then there is a winning condition.
	 *
	 * @param  player 	The current player
	 * @return 			Return if the player has won, or not (true = win, false = no win)
	 */
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
	
	/**
	 * Deze functie kijkt voor een rij of er 3 dezelfde stukken zijn. Zo ja,
	 * dan is er een winnaar.
	 *
	 * @param  pos0 	Positie 1 die gechecked moet worden
	 * @param  pos1		Positie 2 die gechecked moet worden
	 * @param  pos2 	Positie 3 die gechecked moet worden
	 * @param  player 	De huidige speler
	 * @return 			Stuur terug false, als de speler niet heeft gewonnen in deze
	 * 					rij. Stuur true, als hij wel in deze rij heeft gewonnen.
	 */
	public boolean checkRow(int pos0, int pos1, int pos2, Player player) {
		if ((pos0 == pos1) && (pos0 == pos2)) {
			if(player.color == pos0) {
				return true;
			}
		}
		return false;
	}

	/**
	 * Oude functie die de input uit de console haalde.
	 */
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
