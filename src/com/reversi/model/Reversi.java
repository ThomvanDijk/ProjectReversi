package com.reversi.model;

import java.util.ArrayList;
import java.util.List;
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

		// Set the scores
		player1.setScore(2); // black
		player2.setScore(2); // white

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

	public ArrayList<ArrayList<Integer>> getValidMoves(int boardsize, int playerID) {
		// Array met mogelijke zetten
		ArrayList<ArrayList<Integer>> validMoves = new ArrayList<ArrayList<Integer>>();

		// alle mogelijke zetten opslaan in arraylist
		for (int x = 0; x < boardsize; x++) {
			for (int y = 0; y < boardsize; y++) {

				// check of het veld leeg is (volle vakken zijn nooit geldig)
				if (board.getPiece(x, y) == 0) {
					ArrayList<Integer> tempList = new ArrayList<Integer>();
					ArrayList<Integer> tempAdd = new ArrayList<Integer>();
					// check in elke richting of de zet geldig is:
					// noordwest
					tempAdd = checkDirection(x, y, -1, -1, playerID);
					if (tempAdd.isEmpty() == false) {
						tempList.addAll(tempAdd);
					}
					// noord
					tempAdd = checkDirection(x, y, 0, -1, playerID);
					if (tempAdd.isEmpty() == false) {
						tempList.addAll(tempAdd);
					}
					// noordoost
					tempAdd = checkDirection(x, y, 1, -1, playerID);
					if (tempAdd.isEmpty() == false) {
						tempList.addAll(tempAdd);
					}
					// oost
					tempAdd = checkDirection(x, y, 1, 0, playerID);
					if (tempAdd.isEmpty() == false) {
						tempList.addAll(tempAdd);
					}
					// zuidoost
					tempAdd = checkDirection(x, y, 1, 1, playerID);
					if (tempAdd.isEmpty() == false) {
						tempList.addAll(tempAdd);
					}
					// zuid
					tempAdd = checkDirection(x, y, 0, 1, playerID);
					if (tempAdd.isEmpty() == false) {
						tempList.addAll(tempAdd);
					}
					// zuidwest
					tempAdd = checkDirection(x, y, -1, 1, playerID);
					if (tempAdd.isEmpty() == false) {
						tempList.addAll(tempAdd);
					}
					// west
					tempAdd = checkDirection(x, y, -1, 0, playerID);
					if (tempAdd.isEmpty() == false) {
						tempList.addAll(tempAdd);
					}

					if (tempList.isEmpty() == false) {
						boolean add = false;

						for (int i = 0; i < validMoves.size(); i++) {
							if (validMoves.get(i).equals(tempList)) {
								add = false;
								break;
							} else {
								add = true;
							}
						}

						if (add || validMoves.isEmpty()) {
							validMoves.add(tempList);
						}
					}
				}
			}
		}

		return validMoves;
	}

	public ArrayList<Integer> checkDirection(int x, int y, int intX, int intY, int playerID) {
		ArrayList<Integer> fields = new ArrayList<Integer>();

		// Richting increment
		int yd = intY;
		int xd = intX;
		int i = 0;
		boolean loop = true;

		// out of bounds check
		if ((y + intY < 0 || x + intX < 0 || y + intY > board.getBoardSize() - 1
				|| x + intX > board.getBoardSize() - 1) == false) {
			// check of er een tegenstander aan het veld is
			if (board.getPiece(y + intY, x + intX) != playerID && board.getPiece(y + intY, x + intX) != 0) {
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
						else if (board.getPiece(y + intY, x + intX) == playerID) {
							for (int j = 0; j <= i + 1; j++) {
								int newX = x + (j * xd);
								int newY = y + (j * yd);

								fields.add(newX);
								fields.add(newY);
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

	public void setMove(int input, ArrayList<ArrayList<Integer>> validMoves, int playerID) {
		int row = 0;
		int col = 0;
		int boardSize = board.getBoardSize();

		// Convert from 1D to 2D
		if ((input % boardSize) > 0) {
			row = input / boardSize;
			col = input % boardSize;
		} else {
			row = input / boardSize;
			col = 0;
		}

		for (int i = 0; i < validMoves.size(); i++) {
			if (validMoves.get(i).get(0).equals(col) && validMoves.get(i).get(1).equals(row)) {

			}
		}

		// Check if the new move is valid
		boolean validMove = false;
		for (int check = 0; check < validMoves.size(); check++) {

			// Check if the players input corresponds with one of the valid moves...
			if (validMoves.get(check).get(0).equals(col) && validMoves.get(check).get(1).equals(row)) {
				validMove = true;

				System.out.println("Places to change: " + chopped(validMoves.get(check), 2));

				for (int check2 = 0; check2 * 2 < validMoves.get(check).size(); check2++) {
					int colChange = validMoves.get(check).get(check2 * 2);
					int rowChange = validMoves.get(check).get((check2 * 2) + 1);

					try {
						board.setPiece(rowChange, colChange, playerID);
					} catch (Exception e) {
						e.printStackTrace();
					}

					// score update
					if (playerID == 1) { // if player == 1 or black
						if (check2 != 0) {
							player1.decrementScore();
						}
						player2.incrementScore();
					} else { // else player == 2 or while
						if (check2 != 0) {
							player2.decrementScore();
						}
						player1.incrementScore();
					}
				}
			}
		}

		if (!validMove) {
			System.out.println("Not a valid move!");
		} else {
			if (playerID == 1) {
				debugMove(player2.id);
			} else {
				debugMove(player1.id);
			}
		}
	}

	public void makeMove(Player player) {
		int input = 0;

		// Check which player is playing
		if (player.id == 1 && player.type.equals(PlayerType.HUMAN)) {
			input = scanInput.nextInt();

			player1.setTurn(false);
			player2.setTurn(true);
		} else {
			// AI has to make a move
			// input = player.ai.calculateMove(board, player);
			input = scanInput.nextInt();

			player1.setTurn(true);
			player2.setTurn(false);
		}

		// Get all the valid moves if there are any
		ArrayList<ArrayList<Integer>> validMoves = getValidMoves(board.getBoardSize(), player.id);

		if (!validMoves.isEmpty()) {
			setMove(input, validMoves, player.id);
			// Reset winnercount
			noWinnerCount = 0;
		} else {
			// 1 player can't move, if this counter reaches 2, that means both players can't move and the game will end
			noWinnerCount++;
		}
		// If both players can't move, end the game
		if(noWinnerCount == 2) {
			noWinner = false;
			if (player2.getScore() > player1.getScore()) {
				System.out.println("White wins!");
			} else if (player2.getScore() < player1.getScore()) {
				System.out.println("Black wins!");
			} else {
				System.out.println("Draw!");
			}
		}
	}

	// This input needs to come from the GUI
	public void consoleInput() {
		System.out.println("Player: 1 is black");
		System.out.println("Player: 2 is white");
		debugMove(player1.id);

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

	public void debugMove(int playerID) {
		// Show updated score
		System.out.println("Black: " + player1.getScore() + "  White: " + player2.getScore());

		// update board
		board.debugBoard();

		// Get all the valid moves if there are any
		ArrayList<ArrayList<Integer>> validMoves = getValidMoves(board.getBoardSize(), playerID);

		System.out.println("Player: " + playerID + ", make a turn:");
		//System.out.println("Valid moves: " + validMoves);

		// Show the valid moves
		System.out.print("Valid moves:    ");
		for (int i = 0; i < validMoves.size(); i++) {
			for (List<Integer> intList : chopped(validMoves.get(i), 2)) {
				System.out.print((intList.get(0) * board.getBoardSize()) + intList.get(1) + " ");
			}
		}
		System.out.println();

		// THE GREAT FILTER
		System.out.print("Filtered moves: ");
		for (int i = 0; i < validMoves.size(); i++) {
			for (List<Integer> intList : chopped(validMoves.get(i), 2)) {
				int input = (intList.get(0) * board.getBoardSize()) + intList.get(1);

				for (int j = 0; j < validMoves.size(); j++) {
					int row = 0;
					int col = 0;
					int boardSize = board.getBoardSize();

					// Convert from 1D to 2D
					if ((input % boardSize) > 0) {
						row = input / boardSize;
						col = input % boardSize;
					} else {
						row = input / boardSize;
						col = 0;
					}

					if (validMoves.get(j).get(0).equals(col) && validMoves.get(j).get(1).equals(row)) {
						System.out.print(input + " ");
					}
				}
			}
		}
		System.out.println();
		// ################
	}

	// https://stackoverflow.com/questions/2895342/java-how-can-i-split-an-arraylist-in-multiple-small-arraylists
	// chops a list into non-view sublists of length L
	static <T> List<List<T>> chopped(List<T> list, final int L) {
		List<List<T>> parts = new ArrayList<List<T>>();
		final int N = list.size();
		for (int i = 0; i < N; i += L) {
			parts.add(new ArrayList<T>(list.subList(i, Math.min(N, i + L))));
		}
		return parts;
	}
}
