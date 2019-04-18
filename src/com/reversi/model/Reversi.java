package com.reversi.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

import com.reversi.model.Player.PlayerType;

/**
 * The Reversi class contains all the rules of reversi and handles some game
 * specific moves.
 * 
 * @author  Thom van Dijk
 * @author  Sebastiaan van Vliet
 * @version 1.0
 * @since   12-04-2019
 */
public class Reversi extends Game {

	public static final int BLACK = 1;
	public static final int WHITE = 2;

	/**
	 * De constructor vraagt de game modus op en de speler die start. In deze klasse
	 * word het gametype altijd overschreven met Reversi.
	 * 
	 * @param gameMode    De game modus van het spel.
	 * @param startPlayer De speler die start.
	 */
	public Reversi(GameMode gameMode) {
		super(GameType.REVERSI);

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

	public void startGame(GameMode gameMode, PlayerType startPlayer) {
		// Set which player is AI, SERVER or HUMAN also set the correct color for the
		// start player
		if (gameMode.equals(GameMode.SINGLEPLAYER)) {
			player1 = new Player(PlayerType.HUMAN, BLACK);
			player2 = new Player(PlayerType.AI, WHITE);
			player2.ai.setReversi(this);

			player1.setTurn(true);
			player1.setName("You"); // Default name for single player
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
	}

	/**
	 * Deze kijkt voor 1 bracket of het een steen op de coordinaten van x en y een
	 * geldige zet is.
	 *
	 * @param  x        Coordinaat x van de zet die gechecked moet worden
	 * @param  y        Coordinaat y van de zet die gechecked moet worden
	 * @param  intX     De richting van X (1 = oost, -1 = west)
	 * @param  intY     De richting van Y (1 = noord, -1 = zuid)
	 * @param  playerID Het ID van de speler die de zet wilt doen
	 * @param  b        Het huidige board waar op gespeeld wordt
	 * @return          Stuur zet terug met mogelijke stenen die overgenomen zouden
	 *                  worden als er een zet zou gedaan worden op deze plek.
	 */
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
					// Zo lang er 1 bracket beschikbaar is, wordt de zet toegevoegd aan validMoves
					if (tempList.isEmpty() == false) {
						validMoves.add(tempList);
					}
				}
			}
		}

		return validMoves;
	}

	/**
	 * Deze functie kijkt voor 1 bracket of het plaatsen van een steen op de
	 * coordinaten van x en y een geldige zet is voor die bracket.
	 *
	 * @param  x        Coordinaat x van de zet die gechecked moet worden
	 * @param  y        Coordinaat y van de zet die gechecked moet worden
	 * @param  intX     De richting van X (1 = oost, -1 = west)
	 * @param  intY     De richting van Y (1 = noord, -1 = zuid)
	 * @param  playerID Het ID van de speler die de zet wilt doen
	 * @param  b        Het huidige board waar op gespeeld wordt
	 * @return          Stuur zet terug met mogelijke stenen die overgenomen zouden
	 *                  worden als er een zet zou gedaan worden op deze plek.
	 */
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

	/**
	 * Deze functie voert de zet uit die meegegeven wordt.
	 *
	 * @param  input      De move die gedaan moet worden
	 * @param  validMoves Alle geldige moves die de speler op dat moment kan doen
	 * @param  playerID   Het ID van de speler die de zet doet
	 * @param  b          Het huidige board waar op gespeeld wordt
	 * @return            Stuur terug of het een geldige zet was of niet.
	 */
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

				for (int check2 = 0; check2 * 2 < validMoves.get(check).size(); check2++) {
					int colChange = validMoves.get(check).get(check2 * 2);
					int rowChange = validMoves.get(check).get((check2 * 2) + 1);

					try {
						b.setPiece(rowChange, colChange, playerID);
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}
			// score update
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

	/**
	 * Deze functie wordt gebruikt om door de AI, als hij gebruik maakt van minimax,
	 * om vooruit te kijken.
	 *
	 * @param  player De speler die de zet wilt doen
	 * @param  move   De zet die minimax wilt proberen
	 * @param  b      Het huidige board waar minimax op aan het simuleren is
	 * @return        De status van het board, voor de volgende tak van minimax
	 */
	public Board makeForwardMove(Player player, int move, Board b) {
		if (!player1.getType().equals(PlayerType.HUMAN)) {
			player1.setTurn(true);
			player2.setTurn(false);
		}

		int input = 0;
		boolean validMove = false;

		// Get all the valid moves if there are any
		ArrayList<ArrayList<Integer>> validMoves = getValidMoves(b, player.color);

		// As long as the input isn't correct, this will loop
		while (validMove == false) {
			// Check if there are any possible moves
			if (!validMoves.isEmpty()) {
				input = move;

				validMove = setMove(input, validMoves, player.color, b);
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
			if (player.color == 1) {
				player1.setTurn(false);
				player2.setTurn(true);
			} else {
				player1.setTurn(true);
				player2.setTurn(false);
			}
		}

		return b;
	}

	/**
	 * Deze functie wordt gebruikt om een zet van de AI te spelen.
	 *
	 * @param player De speler die de zet wilt doen
	 */
	public int makeAIMove(Player player) {
		int input = -1;
		boolean validMove = false;

		System.out.println("Current player is: " + player.color);

		// Get all the valid moves if there are any
		ArrayList<ArrayList<Integer>> validMoves = getValidMoves(board, player.color);
		printValidMoves(validMoves, board);
		// As long as the input isn't correct, this will loop
		while (validMove == false) {
			// Check if there are any possible moves
			if (!validMoves.isEmpty()) {
				// Als er een blocking move gedaan kan worden, wordt dit gedaan. Dit heeft
				// nummer 1 prioriteit.
				int blockMove = player.ai.blockingMove(validMoves, board, player);
				if (blockMove != -1) {
					input = blockMove;
					// Random zet, gebeurt op dit moment nooit
				} else if (turn < 0) {
					input = player.ai.random(board, player);
					// Zet gebaseerd op boardweight, gebeurt op dit moment nooit
				} else if (turn < 0) {
					input = player.ai.boardWeighting(board, player);
					// Minimax, 8 diep, gebeurt tussen beurt 0 en 30
				} else if (turn < 30) {
					input = player.ai.minimaxAvailableMoves(board, player, 0, 8, 0, 0, Integer.MIN_VALUE,
							Integer.MAX_VALUE, 0);
					// Minimax, 9 diep, gebeurt tussen beurt 30 en 40
				} else if (turn < 40) {
					input = player.ai.minimaxAvailableMoves(board, player, 0, 9, 0, 0, Integer.MIN_VALUE,
							Integer.MAX_VALUE, 0);
					// Minimax, 10 diep, gebeurt tussen beurt 40 en 45
				} else if (turn < 45) {
					input = player.ai.minimaxAvailableMoves(board, player, 0, 10, 0, 0, Integer.MIN_VALUE,
							Integer.MAX_VALUE, 0);
					// Minimax, 16 diep, gebeurt tussen beurt 45 en 60
				} else {
					input = player.ai.minimaxTest(board, player, 0, 16, 0, 0, Integer.MIN_VALUE, Integer.MAX_VALUE, 0);
				}

				// Voer de move die gevonden is uit.
				validMove = setMove(input, validMoves, player.color, board);

				if (validMove == true) {
					turn++;
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

		// Input is last player player
		ArrayList<ArrayList<Integer>> validMovesOpponent = getValidMoves(board, player.opponent);
		if (validMovesOpponent.size() > 0) {
			switchTurn(player);
		}

		return input;
	}

	/**
	 * Deze functie wordt gebruikt om een zet van de server te spelen.
	 *
	 * @param player De speler die de zet wilt doen
	 * @param input  De zet die gespeeld wordt
	 */
	public void makeMove(Player player, int input) {
		// Get all the valid moves if there are any
		ArrayList<ArrayList<Integer>> validMoves = getValidMoves(this.board, player.color);
		printValidMoves(validMoves, board);
		if (!validMoves.isEmpty()) {
			if (setMove(input, validMoves, player.color, this.board)) {
				turn++;
			}

			// Reset noWinnerCount
			noWinnerCount = 0;
		} else {
			// Player can't move, if this counter reaches 2, that means both players can't
			// move and the game will end
			noWinnerCount++;
			System.out.println("Out of moves + Count = " + noWinnerCount);
		}

		// Input is last player player
		ArrayList<ArrayList<Integer>> validMovesOpponent = getValidMoves(board, player.opponent);
		if (validMovesOpponent.size() > 0) {
			switchTurn(player);
		}
	}

	/**
	 * Deze functie zorgt ervoor dat de volgende speler de beurt krijgt.
	 *
	 * @param player De speler die op dat moment aan de beurt is
	 */
	public void switchTurn(Player player) {
		// debugMove(player.color, board);

		// If one player can't make a move, switch who's turn it is...
		if (noWinnerCount == 1) {
			// This player can't make a move so set it's turn to false
			player.setTurn(true);

			// Now set the other player's turn to true
			if (player.color == player1.color) {
				player2.setTurn(false);
			} else {
				player1.setTurn(false);
			}
		} else if (noWinnerCount == 3) {
			// If both players can't move, end the game
			if (player2.getScore() > player1.getScore()) {
				System.out.println("Black wins!");
			} else if (player2.getScore() < player1.getScore()) {
				System.out.println("White wins!");
			} else {
				System.out.println("Draw!");
			}
		} else {
			// Switch turns
			if (player1.hasTurn()) {
				player1.setTurn(false);
				player2.setTurn(true);
			} else {
				player1.setTurn(true);
				player2.setTurn(false);
			}
		}
	}

	/**
	 * Deze functie printte de mogelijke zetten in de console, voordat er een GUI
	 * aanwezig was.
	 *
	 * @param validMoves Alle mogelijke zetten op dat moment
	 * @param b          Het bord waarop dat moment gespeeld wordt
	 */
	public void printValidMoves(ArrayList<ArrayList<Integer>> validMoves, Board b) {
		System.out.print("Valid moves: ");
		for (int i = 0; i < validMoves.size(); i++) {
			int row = chopped(validMoves.get(i), 2).get(0).get(0);
			int col = chopped(validMoves.get(i), 2).get(0).get(1);

			System.out.print((col * b.getBoardSize()) + row + " ");

		}
		System.out.println("Turn: " + turn);
		System.out.println();
	}

	/**
	 * Deze functie werd gebruikt om het scoreboard en statistieken uit te printen
	 * in de console, voordat er een GUI gebouwd was.
	 *
	 * @param playerID Het ID van de speler die op dat moment aan de beurt is
	 * @param playerID b Het bord waarop dat moment gespeeld wordt
	 */
	public void debugMove(int playerID, Board b) {
		// Show updated score
		System.out.println("Black: " + player2.getScore() + "  White: " + player1.getScore());

		// Show board in console
		board.debugBoard();

		// Get all the valid moves if there are any
		ArrayList<ArrayList<Integer>> validMoves = getValidMoves(b, playerID);

		System.out.println("Player: " + playerID + ", make a move:");

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

	/**
	 * Deze functie geeft de score terug van een speler.
	 *
	 * @param  Het ID van de speler wiens score opgevraagt wordt.
	 * @return     De score van de speler wiens ID opgegeven is, min de score van de
	 *             tegenstander.
	 */
	public int calculateScore(int playerID) {
		if (playerID == 1) {
			return (player1.getScore() - player2.getScore());
		} else {
			return (player2.getScore() - player1.getScore());
		}
	}
}