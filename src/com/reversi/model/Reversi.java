package com.reversi.model;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.Scanner;

import com.reversi.main.Main;
import com.reversi.model.Player.PlayerType;

public class Reversi extends Game {

	private Scanner scanInput;

	public Reversi(GameMode gameMode) {
		super(GameType.REVERSI, gameMode);

		scanInput = new Scanner(System.in);

		if (player1.type.equals(PlayerType.AI)) {
			player1.ai.setReversi(this);
		}

		if (player2.type.equals(PlayerType.AI)) {
			player2.ai.setReversi(this);
		}

		// Set the first pieces
		try {
			board.setPiece(3, 3, 1);
			board.setPiece(3, 4, 2);
			board.setPiece(4, 3, 2);
			board.setPiece(4, 4, 1);
		} catch (Exception e) {
			e.printStackTrace();
		}

		if (gameMode.equals(GameMode.SINGLEPLAYER)) {
			consoleInput();
		} else {

		}
	}

//	public static void printBoard(int boardsize, ArrayList<ArrayList> board) {
//		System.out.println("  1 2 3 4 5 6 7 8");
//		for (int i = 0; i < boardsize; i++) {
//			ArrayList<String> temp = board.get(i);
//			System.out.printf(i + 1 + " ");
//			for (int j = 0; j < boardsize; j++) {
//				System.out.printf(temp.get(j) + " ");
//			}
//			System.out.println();
//		}
//		System.out.println();
//	}

	public ArrayList<ArrayList> getValidMoves(int boardsize, Player player) {
		// Array met mogelijke zetten
		ArrayList<ArrayList> validMoves = new ArrayList<ArrayList>();

		// alle mogelijke zetten opslaan in arraylist
		for (int x = 0; x < boardsize; x++) {
			for (int y = 0; y < boardsize; y++) {

				// check of het veld leeg is (volle vakken zijn nooit geldig)
				if (board.getPiece(x, y) == 0) {
					ArrayList<String> tempList = new ArrayList<String>();
					ArrayList<String> tempAdd = new ArrayList<String>();
					// check in elke richting of de zet geldig is:
					// noordwest
					tempAdd = checkDirection(x, y, -1, -1, player);
					if (tempAdd.isEmpty() == false) {
						tempList.addAll(tempAdd);
					}
					// noord
					tempAdd = checkDirection(x, y, 0, -1, player);
					if (tempAdd.isEmpty() == false) {
						tempList.addAll(tempAdd);
					}
					// noordoost
					tempAdd = checkDirection(x, y, 1, -1, player);
					if (tempAdd.isEmpty() == false) {
						tempList.addAll(tempAdd);
					}
					// oost
					tempAdd = checkDirection(x, y, 1, 0, player);
					if (tempAdd.isEmpty() == false) {
						tempList.addAll(tempAdd);
					}
					// zuidoost
					tempAdd = checkDirection(x, y, 1, 1, player);
					if (tempAdd.isEmpty() == false) {
						tempList.addAll(tempAdd);
					}
					// zuid
					tempAdd = checkDirection(x, y, 0, 1, player);
					if (tempAdd.isEmpty() == false) {
						tempList.addAll(tempAdd);
					}
					// zuidwest
					tempAdd = checkDirection(x, y, -1, 1, player);
					if (tempAdd.isEmpty() == false) {
						tempList.addAll(tempAdd);
					}
					// west
					tempAdd = checkDirection(x, y, -1, 0, player);
					if (tempAdd.isEmpty() == false) {
						tempList.addAll(tempAdd);
					}

					if (tempList.isEmpty() == false) {
						LinkedHashSet<String> set = new LinkedHashSet<>(tempList);
						tempList.clear();
						tempList.addAll(set);
						// System.out.println(tempList);
						validMoves.add(tempList);
					}
				}
			}
		}
		return validMoves;
	}

	public ArrayList<String> checkDirection(int x, int y, int intX, int intY, Player player) {
		ArrayList<String> fields = new ArrayList<String>();

		// Richting increment
		int yd = intY;
		int xd = intX;
		int i = 0;
		boolean loop = true;

		// out of bounds check
		if ((y + intY < 0 || x + intX < 0 || y + intY > board.getBoardSize() - 1
				|| x + intX > board.getBoardSize() - 1) == false) {
			// check of er een tegenstander aan het veld is
			if (board.getPiece(y + intY, x + intX) != player.id) {
				// increment de richting
				intY = intY + yd;
				intX = intX + xd;
				if ((y + intY < 0 || x + intX < 0 || y + intY > board.getBoardSize() - 1
						|| x + intX > board.getBoardSize() - 1) == false) {

					// loop totdat er geen tegenstander stukken meer zijn in deze richting
					while (loop == true) {

						// als er een leeg vakje gevonden is, is de richting niet geldig voor deze zet.
						if (board.getPiece(y + intY, x + intX) == 0) {
							loop = false;
						}
						// als er een vakje met een eigen steen gevonden wordt, is het wel een geldige
						// zet.
						else if (board.getPiece(y + intY, x + intX) == player.id) {
							for (int j = 0; j <= i + 1; j++) {
								int newX = x + (j * xd);
								int newY = y + (j * yd);
								fields.add(newX + "" + newY);
							}
							loop = false;
						}
						// increment de richting
						intY = intY + yd;
						intX = intX + xd;

						// als er out of bounds gegaan wordt, is de richting niet geldig voor deze zet.
						if (y + intY < 0 || x + intX < 0 || y + intY > board.getBoardSize() - 1
								|| x + intX > board.getBoardSize() - 1) {
							loop = false;
						}
						i++;
					}
				}

			}
		}
		return fields;

	}

	public void makeMove(Player player) {
		int input = 0;

		// haal geldige zetten op
		ArrayList<ArrayList> validMoves = getValidMoves(board.getBoardSize(), player);

		// kijk of er zetten mogelijk zijn
		if (!validMoves.isEmpty()) {

			// Voer coordinaat in en druk op enter (bijv: 20 of 27)
			System.out.println("Player: " + player.id + ", voer een coordinaat in:");

			input = scanInput.nextInt();

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

			String currentCoordinate = (row + "" + col);

			// check of zet geldig is
			boolean geldig = false;
			for (int check = 0; check < validMoves.size(); check++) {
				if (validMoves.get(check).get(0).equals(currentCoordinate)) {
					geldig = true;
					for (int check2 = 0; check2 < validMoves.get(check).size(); check2++) {
						
						// score update
						if (player.id == 1) { // If player == 1 or black
							if (check2 != 0) {
								player1.decrementScore();
							}
							player2.incrementScore();
						} else {
							if (check2 != 0) {
								player2.decrementScore();
							}
							player1.incrementScore();
						}
					}
				}
			}

			if (!geldig) {
				System.out.println("Geen geldige zet");
			} else {
				// update score
				System.out.println("Zwart: " + player1.getScore() + "          Wit: " + player2.getScore());

				// update board
				board.debugBoard();
			}
		} else {
			noWinner = false;
			if (player2.getScore() > player1.getScore()) {
				System.out.println("Wit wint!");
			} else if (player2.getScore() < player1.getScore()) {
				System.out.println("Zwart wint!");
			} else {
				System.out.println("Gelijkspel!");
			}
		}

		if (player.id == 1 && player.type.equals(PlayerType.HUMAN)) {
			input = scanInput.nextInt();

			player1.setTurn(false);
			player2.setTurn(true);
		} else {
			// AI has to make a move
			// input = player.ai.calculateMove(board, player);

			player1.setTurn(true);
			player2.setTurn(false);
		}

//		try {
//			System.out.println("Player " + player.id + " plays: " + input);
//			setMove(input, player.id);
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
	}

	// This input needs to come from the GUI
	public void consoleInput() {
		board.debugBoard();

		player1.setScore(2); // black
		player2.setScore(2); // white

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
