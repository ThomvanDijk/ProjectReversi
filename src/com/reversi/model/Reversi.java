package com.reversi.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

import com.reversi.main.Main;
import com.reversi.model.Player.PlayerType;

public class Reversi extends Game {

	public static final int BLACK = 1;
	public static final int WHITE = 2;

	private Scanner scanInput;

	public Reversi(GameMode gameMode, PlayerType startPlayer) {
		super(GameType.REVERSI, gameMode);

		// Set which player is AI, SERVER or HUMAN also set the correct color for the start player
		if (gameMode.equals(GameMode.SINGLEPLAYER)) {
			player1 = new Player(PlayerType.HUMAN, BLACK);
			player2 = new Player(PlayerType.AI, WHITE);
			player2.ai.setReversi(this);
		} else { // The start player must be black
			if (startPlayer.equals(PlayerType.SERVER)) {
				player1 = new Player(PlayerType.AI, WHITE);
				player2 = new Player(PlayerType.SERVER, BLACK); // Start player
				
				player2.setTurn(true);
			} else {
				player1 = new Player(PlayerType.AI, BLACK); // Start player
				player2 = new Player(PlayerType.SERVER, WHITE);
				
				player1.setTurn(true);
			}
			
			player1.ai.setReversi(this);
		}

		// Set the scores
		player1.setScore(2);
		player2.setScore(2);

		// Set turn
		turn = 0;

		// Set the first pieces
		try {
			board.setPiece(3, 3, WHITE);
			board.setPiece(3, 4, BLACK);
			board.setPiece(4, 3, BLACK);
			board.setPiece(4, 4, WHITE);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public Player[] getPlayers() {
		return new Player[] {player1, player2};
	}

	public ArrayList<ArrayList<Integer>> getValidMoves(Board b, int playerID) {
		// Array met mogelijke zetten
		ArrayList<ArrayList<Integer>> validMoves = new ArrayList<ArrayList<Integer>>();

		// alle mogelijke zetten opslaan in arraylist
		int boardsize = b.getBoardSize();
		for (int x = 0; x < boardsize; x++) {
			for (int y = 0; y < boardsize; y++) {

				// check of het veld leeg is (volle vakken zijn nooit geldig)
				if (b.getPiece(y, x) == 0) {
					ArrayList<Integer> tempList = new ArrayList<Integer>();
					ArrayList<Integer> tempAdd = new ArrayList<Integer>();
					// check in elke richting of de zet geldig is:
					// noordwest
					tempAdd = checkDirection(x, y, -1, -1, playerID, b);
					if (tempAdd.isEmpty() == false) {
						tempList.addAll(tempAdd);
					}
					// noord
					tempAdd = checkDirection(x, y, 0, -1, playerID, b);
					if (tempAdd.isEmpty() == false) {
						tempList.addAll(tempAdd);
					}
					// noordoost
					tempAdd = checkDirection(x, y, 1, -1, playerID, b);
					if (tempAdd.isEmpty() == false) {
						tempList.addAll(tempAdd);
					}
					// oost
					tempAdd = checkDirection(x, y, 1, 0, playerID, b);
					if (tempAdd.isEmpty() == false) {
						tempList.addAll(tempAdd);
					}
					// zuidoost
					tempAdd = checkDirection(x, y, 1, 1, playerID, b);
					if (tempAdd.isEmpty() == false) {
						tempList.addAll(tempAdd);
					}
					// zuid
					tempAdd = checkDirection(x, y, 0, 1, playerID, b);
					if (tempAdd.isEmpty() == false) {
						tempList.addAll(tempAdd);
					}
					// zuidwest
					tempAdd = checkDirection(x, y, -1, 1, playerID, b);
					if (tempAdd.isEmpty() == false) {
						tempList.addAll(tempAdd);
					}
					// west
					tempAdd = checkDirection(x, y, -1, 0, playerID, b);
					if (tempAdd.isEmpty() == false) {
						tempList.addAll(tempAdd);
					}

					if (tempList.isEmpty() == false) {
						/*
						 * for (int i = 2; i < tempList.size(); i = i+2) { if ((tempList.get(0) ==
						 * tempList.get(i)) && (tempList.get(1) == tempList.get(i+1))){
						 * tempList.remove(i); tempList.remove(i+1); } }
						 */
						validMoves.add(tempList);
					}
				}
			}
		}

		return validMoves;
	}

	public ArrayList<Integer> checkDirection(int x, int y, int intX, int intY, int playerID, Board b) {
		ArrayList<Integer> fields = new ArrayList<Integer>();

		// Richting increment
		int yd = intY;
		int xd = intX;
		int i = 0;
		boolean loop = true;

		// out of bounds check
		if ((y + intY < 0 || x + intX < 0 || y + intY > b.getBoardSize() - 1
				|| x + intX > b.getBoardSize() - 1) == false) {
			// check of er een tegenstander aan het veld is
			if (b.getPiece(y + intY, x + intX) != playerID && b.getPiece(y + intY, x + intX) != 0) {
				// increment de richting
				intY = intY + yd;
				intX = intX + xd;
				if ((y + intY < 0 || x + intX < 0 || y + intY > b.getBoardSize() - 1
						|| x + intX > b.getBoardSize() - 1) == false) {

					// loop totdat er geen tegenstander stukken meer zijn in deze richting
					while (loop == true) {

						// als er een leeg vakje gevonden is, is de richting niet geldig voor deze zet.
						if (b.getPiece(y + intY, x + intX) == 0) {
							loop = false;
						}
						// als er een vakje met een eigen steen gevonden wordt, is het wel een geldige
						// zet.
						else if (b.getPiece(y + intY, x + intX) == playerID) {
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
						if (y + intY < 0 || x + intX < 0 || y + intY > b.getBoardSize() - 1
								|| x + intX > b.getBoardSize() - 1) {
							loop = false;
						}
						i++;
					}
				}

			}
		}

		return fields;
	}

	public boolean setMove(int input, ArrayList<ArrayList<Integer>> validMoves, int playerID, Board b) {
		int row = 0;
		int col = 0;
		int boardSize = b.getBoardSize();

		// Convert from 1D to 2D
		if ((input % boardSize) > 0) {
			row = input / boardSize;
			col = input % boardSize;
		} else {
			row = input / boardSize;
			col = 0;
		}

		// Check if the new move is valid
		boolean validMove = false;
		for (int check = 0; check < validMoves.size(); check++) {

			// Check if the players input corresponds with one of the valid moves...
			if (validMoves.get(check).get(0).equals(col) && validMoves.get(check).get(1).equals(row)) {
				validMove = true;

				// System.out.println("Places to change: " + chopped(validMoves.get(check), 2));

				for (int check2 = 0; check2 * 2 < validMoves.get(check).size(); check2++) {
					int colChange = validMoves.get(check).get(check2 * 2);
					int rowChange = validMoves.get(check).get((check2 * 2) + 1);

					try {
						b.setPiece(rowChange, colChange, playerID);
					} catch (Exception e) {
						e.printStackTrace();
					}

					// score update
					/*
					 * if (playerID == 1) { // if player == 1 or black if (check2 != 0) {
					 * player1.decrementScore(); } player2.incrementScore(); } else { // else player
					 * == 2 or while if (check2 != 0) { player2.decrementScore(); }
					 * player1.incrementScore(); }
					 */
				}
			}
			// new score update
			int scoreWhite = 0;
			int scoreBlack = 0;
			int[][] scoreCheck = b.getBoard();
			for (int i = 0; i < boardSize; i++) {
				for (int j = 0; j < boardSize; j++) {
					if (scoreCheck[i][j] == 2) {
						scoreWhite++;
					} else if (scoreCheck[i][j] == 1) {
						scoreBlack++;
					}
				}
			}
			player1.setScore(scoreWhite);
			player2.setScore(scoreBlack);
		}

		if (!validMove) {
			System.out.println("Move " + input + " is not a valid move!");

			try {
				TimeUnit.SECONDS.sleep(1);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}

			return false;
		}

		return true;
	}

	// Used by AI
	public Board makeForwardMove(Player player, int move, Board b) {
		int input = 0;
		boolean validMove = false;

		// Get all the valid moves if there are any
		ArrayList<ArrayList<Integer>> validMoves = getValidMoves(b, player.id);

		// As long as the input isn't correct, this will loop
		while (validMove == false) {
			// Check if there are any possible moves
			if (!validMoves.isEmpty()) {
				input = move;

				validMove = setMove(input, validMoves, player.id, b);
				// Reset noWinnerCount
				noWinnerCount = 0;
			} else {
				// 1 player can't move, if this counter reaches 2, that means both players can't
				// move and the game will end
				noWinnerCount++;
				System.out.println("Out of moves + Count = " + noWinnerCount);
				validMove = true;
			}
		}
		// If both players can't move, end the game
		if (noWinnerCount == 3) {
			noWinner = false;
			if (player2.getScore() > player1.getScore()) {
				System.out.println("White wins!");
			} else if (player2.getScore() < player1.getScore()) {
				System.out.println("Black wins!");
			} else {
				System.out.println("Draw!");
			}
		}
		// If one player can't make a move, switch who's turn it is
		if (noWinnerCount == 1) {
			System.out.println("Out of moves");
			if (player.id == 1) {
				player1.setTurn(true);
				player2.setTurn(false);

			} else {
				player1.setTurn(false);
				player2.setTurn(true);

			}

		}

		return b;
	}

	// Used to make a move done by server or player
	public void makeSimpleMove(Player player, int input) {
		// Get all the valid moves if there are any
		ArrayList<ArrayList<Integer>> validMoves = getValidMoves(this.board, player.id);
		
		// TODO check if the move is correct
		setMove(input, validMoves, player.id, this.board);
		
		// Switch turns
		if (player1.hasTurn()) {
			player1.setTurn(false);
			player2.setTurn(true);
		} else {
			player1.setTurn(true);
			player2.setTurn(false);
		}
	}
	
	public int makeMove(Player player) {	
		int input = 0;
		boolean validMove = false;
		
		// Get all the valid moves if there are any
		ArrayList<ArrayList<Integer>> validMoves = getValidMoves(board, player.id);
		
		// As long as the input isn't correct, this will loop
		while (validMove == false) {
			// Check if there are any possible moves
			if (!validMoves.isEmpty()) {
				if (player.id == BLACK && player.type.equals(PlayerType.HUMAN)) {
					input = scanInput.nextInt();
				} else {
					if (turn < 10) {
						input = player.ai.boardWeighting(board, player);
					} else if (turn < 50) {
						input = player.ai.minimaxAvailableMoves(board, player, 0, 5, 0, 0);
					} else {
						input = player.ai.minimax(board, player, 0, 14, 0, 0);
					}
					// input = player.ai.boardWeighting(b, player);
					System.out.println("PLayer: " + player.id + " (AI) is doing the following move: " + input);

				}
				validMove = setMove(input, validMoves, player.id, board);
				if (validMove == true) {
					turn++;

					if (player.id == BLACK) {
						debugMove(player2.id, board);
					} else {
						debugMove(player1.id, board);
					}

				}
				// Reset noWinnerCount
				noWinnerCount = 0;
			} else {
				// 1 player can't move, if this counter reaches 2, that means both players can't
				// move and the game will end
				noWinnerCount++;
				System.out.println("Out of moves + Count = " + noWinnerCount);
				validMove = true;
			}
		}
		// If both players can't move, end the game
		if (noWinnerCount == 3) {
			noWinner = false;
			if (player2.getScore() > player1.getScore()) {
				System.out.println("Black wins!");
			} else if (player2.getScore() < player1.getScore()) {
				System.out.println("White wins!");
			} else {
				System.out.println("Draw!");
			}
		}
		// If one player can't make a move, switch who's turn it is
		if (noWinnerCount == 1) {
			System.out.println("Out of moves");
			if (player.id == 1) {
				player1.setTurn(true);
				player2.setTurn(false);
				debugMove(player2.id, board);
			} else {
				player1.setTurn(false);
				player2.setTurn(true);
				debugMove(player1.id, board);
			}

		}
		
		// Switch turns
		if (player1.hasTurn()) {
			player1.setTurn(false);
			player2.setTurn(true);
		} else {
			player1.setTurn(true);
			player2.setTurn(false);
		}
		
		return input;
	}
	
	public void debugMove(int playerID, Board b) {
		// Show updated score
		System.out.println("Black: " + player2.getScore() + "  White: " + player1.getScore());

		// Show board in console
		board.debugBoard();

		// Get all the valid moves if there are any
		ArrayList<ArrayList<Integer>> validMoves = getValidMoves(b, playerID);

		System.out.println("Player: " + playerID + ", make a move:");
		//System.out.println("Valid moves: " + validMoves);

		// Show the valid moves
		System.out.print("Valid moves: ");
		
		for (int i = 0; i < validMoves.size(); i++) {
			int row = chopped(validMoves.get(i), 2).get(0).get(0);
			int col = chopped(validMoves.get(i), 2).get(0).get(1);

			System.out.print((col * b.getBoardSize()) + row + " ");
		}
		
		System.out.println();
	}
	
	// This input needs to come from the GUI
	public void consoleInput() {
		scanInput = new Scanner(System.in);
		
		System.out.println("Player: 1 is black");
		System.out.println("Player: 2 is white");
		debugMove(player1.id, board);

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

	public int calculateValueDiff(int playerID) {
		if (playerID == 1) {
			return (player1.getScore() - player2.getScore());
		} else {
			return (player2.getScore() - player1.getScore());
		}
	}
}
