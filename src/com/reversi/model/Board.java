package com.reversi.model;

import com.reversi.model.Game.GameType;

/**
 * This class will save everything that has something to do with the board.
 * 
 * @author  Thom van Dijk
 * @author  Sebastiaan van Vliet
 * @version 1.0
 * @since   1.0
 */
public class Board{

	private final int boardSize;
	private int[][] board;
	private int placesOccupied;
	private GameType gameType;

	/**
	 * De constructor van het bord wilt weten hoe groot het bord moet worden en
	 * wat voor een spel er op gespeeld gaat worden.
	 * 
	 * @param size		De grootte van het bord (Bijv. 8 = een veld van 8 bij 8)
	 * @param gameType	Het type van het spel dat gespeeld gaat worden op het bord.
	 */
	public Board(int size, GameType gameType) {
		this.boardSize = size;
		board = new int[size][size];
		placesOccupied = 1;
		this.gameType = gameType;
	}

	/**
	 * @return 	Stuur terug de array met hoe het veld ervoor staat
	 * 			(0 = leeg, 1 = zwart, 2 = wit)
	 */
	public int[][] getBoard() {
		return board;
	}

	/**
	 * @param row	Rij nummer
	 * @param col	Colom nummer
	 * @return		Krijg de status van het veld van de opgegeven coordinaten
	 */
	public int getPiece(int row, int col) {
		return board[row][col];
	}

	/**
	 * @param row			Rij nummer
	 * @param col			Colom nummer
	 * @param piece			Het stuk dat geplaatst moet worden
	 * @throws Exception	Als alle plekken bezet zijn, geef exception
	 */
	public void setPiece(int row, int col, int piece) throws Exception {
		if (placesOccupied >= boardSize * boardSize) {
			//throw new Exception("All places are occupied!");
			board[row][col] = piece;
			placesOccupied++;
		} else {
			board[row][col] = piece;
			placesOccupied++;
		}
	}

	/**
	 * @return Geef het aantal lege plekken terug
	 */
	public boolean emptyPlaces() {
		if (placesOccupied >= boardSize * boardSize) {
			return false;
		} else {
			return true;
		}
	}

	/**
	 * @return Geef de grootte van het bord terug
	 */
	public int getBoardSize() {
		return boardSize;
	}
	
	/**
	 * Deze functie zorgt ervoor dat het huidige bord word overschreven
	 * door de parameter die wordt meegegeven. Dit wordt gebruikt door het
	 * minimax algoritme.
	 * 
	 * @param newBoard  Een array met een board status.
	 */
	public void setBoard(int[][] newBoard) {
		board = newBoard;
	}

	/**
	 * Deze functie werd gebruikt om het bord in console te printen.
	 */
	public void debugBoard() {
		for (int row = 0; row < boardSize; row++) {
			for (int col = 0; col < boardSize; col++) {
				if (board[row][col] == 0) {
					System.out.print(". ");
				} else if (board[row][col] == 1) {
					if (gameType.equals(GameType.REVERSI)) {
						System.out.print("b "); // black
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