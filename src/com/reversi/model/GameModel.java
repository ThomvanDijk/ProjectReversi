package com.reversi.model;

import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Queue;

import com.reversi.client.Parser.ArgumentKey;

import com.reversi.model.Game.GameMode;
import com.reversi.model.Game.GameType;
import com.reversi.model.Player.PlayerType;

import javafx.application.Platform;
import javafx.concurrent.Task;

/**
 * This class manages the different games that are being played.
 * 
 * @author Thom van Dijk
 * @version 1.0
 * @since 12-04-2019
 */
public class GameModel extends Model {

	private TicTacToe ticTacToe;
	private Reversi reversi;

	private GameType currentGameType; // Tic-tac-toe or reversi
	private GameMode currentGameMode; // Singleplayer or online

	private Queue<HashMap<ArgumentKey, String>> challenges;
	
	private String loginName;
	private String opponentName;

	public GameModel() {
		ticTacToe = null;
		reversi = null;

		currentGameMode = null;
		currentGameType = null;

		challenges = new LinkedList<>();
		
		loginName = "You";
	}

	public void subscribeToGame(GameType gameType) {
		switch (gameType) {
		case REVERSI:
			client.sendCommand("subscribe Reversi");
			break;
		case TICTACTOE:
			client.sendCommand("subscribe Tic-tac-toe");
			break;
		default:
			throw new IllegalStateException();
		}
	}

	public void startGame(GameMode gameMode, GameType gameType, String[] players) {
		PlayerType startPlayer = PlayerType.AI; // Our AI
		
		currentGameMode = gameMode;
		currentGameType = gameType;
		
		// If players is null then it is a singleplayer game
		if(players != null) {
			opponentName = players[1];
			
			// Check if opponent is also the player to move
			if(opponentName.equals(players[0])) {
				startPlayer = PlayerType.SERVER;
			}
		}

		switch (gameType) {
		case REVERSI:
			if (gameMode.equals(GameMode.SINGLEPLAYER)) {
				reversi = new Reversi(GameMode.SINGLEPLAYER, null);
				reversi.player1.setName(loginName);
				break;
			} else {
				reversi = new Reversi(GameMode.ONLINE, startPlayer);
				reversi.player1.setName(loginName);
				reversi.player2.setName(opponentName);
				break;
			}
		case TICTACTOE:
			if (gameMode.equals(GameMode.SINGLEPLAYER)) {
				ticTacToe = new TicTacToe(GameMode.SINGLEPLAYER);
				reversi.player1.setName(loginName);
				break;
			} else {
				ticTacToe = new TicTacToe(GameMode.ONLINE);
				reversi.player1.setName(loginName);
				reversi.player2.setName(opponentName);
				break;
			}
		default:
			throw new IllegalStateException();
		}

		notifyViews();
	}

	// Arguments from server giving the final points
	public void endGame() {
		currentGameMode = null;
		currentGameType = null;

		reversi = null;
		ticTacToe = null;

		notifyViews();
	}

	// A move set by the player or server...
	public void setMove(String move) {
		int intMove = Integer.valueOf(move);
		switch (currentGameType) {
		case REVERSI:
			Player[] players = reversi.getPlayers();
			
			try {
				if (players[0].hasTurn()) {
					reversi.makeMove(players[0], intMove);
				} else {
					reversi.makeMove(players[1], intMove);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			break;
		case TICTACTOE:
			if (currentGameMode.equals(GameMode.ONLINE)) {
				try {
					// ticTacToe.setMove(intMove, playerID);
				} catch (Exception e) {
					e.printStackTrace();
				}
			} else {// Offline

			}
			break;
		default:
			throw new IllegalStateException();
		}
		
		notifyViews();
		
		// If single player request a move from the AI
		if (currentGameMode.equals(GameMode.SINGLEPLAYER)) {
			getMove();
		}
	}

	public void getMove() {
		switch (currentGameType) {
		case REVERSI:
			Player[] players = reversi.getPlayers();
			
			if (currentGameMode.equals(GameMode.ONLINE)) {
				try {
					
					if (players[0].hasTurn()) {
						int move = reversi.makeAIMove(players[0]);
						if (move >= 0) {
							client.sendCommand("move " + move);
						}
					} else {

						int move = reversi.makeAIMove(players[1]);
						if (move >= 0) {
							client.sendCommand("move " + move);
						}
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			} else { // Offline
				try {
					if (players[0].hasTurn()) {
						reversi.makeAIMove(players[0]);
					} else {
						reversi.makeAIMove(players[1]);
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			break;
		case TICTACTOE:
			if (currentGameMode.equals(GameMode.ONLINE)) {
				try {
					// ticTacToe.setMove(intMove, playerID);
				} catch (Exception e) {
					e.printStackTrace();
				}
			} else {// Offline

			}
			break;
		default:
			System.out.println("No current game!");
		}

		notifyViews();
	}

	public void login(String[] arguments) {
		loginName = arguments[0];
		client.login(arguments);
	}

	public void requestPlayerlist() {
		client.sendCommand("get playerlist");
	}

	// Challenge other player
	public void challengePlayer(String[] arguments) {
		// Make the first letter upper case so we don't have to type that
		String cap = arguments[1].substring(0, 1).toUpperCase() + arguments[1].substring(1);
		client.sendCommand("challenge \"" + arguments[0] + "\" \"" + cap + "\"");
	}

	// Accept a challenge and remove it
	public void acceptChallenge(int challengeNumber) {
		// Use Iterator to remove items from challenges while looping through them
		Iterator<HashMap<ArgumentKey, String>> i = challenges.iterator();
		while (i.hasNext()) {
			HashMap<ArgumentKey, String> chal = i.next();

			if (Integer.parseInt(chal.get(ArgumentKey.CHALLENGENUMBER)) == challengeNumber) {
				client.sendCommand("challenge accept " + challengeNumber);
				i.remove();
			}
		}

		notifyViews();
	}

	// Remove a challenge because challenge is cancelled
	public void removeChallenge(int challengeNumber) {
		// Use Iterator to remove items from challenges while looping through them
		Iterator<HashMap<ArgumentKey, String>> i = challenges.iterator();
		while (i.hasNext()) {
			HashMap<ArgumentKey, String> chal = i.next();

			if (Integer.parseInt(chal.get(ArgumentKey.CHALLENGENUMBER)) == challengeNumber) {
				i.remove();
			}
		}

		notifyViews();
	}

	/**
	 * This function creates a map containing three entries and adds it to the
	 * challenges queue.
	 * 
	 * @param arguments A string array containing three elements and in this order:
	 *                  the challenger, challenge number and game type.
	 */
	public void addChallenge(String[] arguments) {
		HashMap<ArgumentKey, String> map = new HashMap<>();

		map.put(ArgumentKey.CHALLENGER, arguments[0]);
		map.put(ArgumentKey.CHALLENGENUMBER, arguments[1]);
		map.put(ArgumentKey.GAMETYPE, arguments[2]);

		challenges.add(map);
		notifyViews();
	}
	
	public GameMode getCurrentGameMode() {
		return currentGameMode;
	}
	
	public GameType getCurrentGameType() {
		return currentGameType;
	}

	/**
	 * The challenges queue contains all the requested challenges.
	 * 
	 * @return The whole challenges queue.
	 */
	public Queue<HashMap<ArgumentKey, String>> getChallenges() {
		return challenges;
	}

	/**
	 * The challenges queue contains all the requested challenges.
	 * 
	 * @return The challenge at the head of the queue.
	 */
	public HashMap<ArgumentKey, String> getFirstChallenge() {
		return challenges.peek();
	}

	/**
	 * Returns current game board.
	 * 
	 * @return The currently used board as 2D int array.
	 */
	public int[][] getBoard() {
		switch (currentGameType) {
		case REVERSI:
			return reversi.board.getBoard();
		case TICTACTOE:
			return ticTacToe.board.getBoard();
		default:
			return null;
		}
	}

	/**
	 * Element[0] is player 1 and element[1] is player 2.
	 * 
	 * @return Array of players.
	 */
	public Player[] getPlayers() {
		switch (currentGameType) {
		case REVERSI:
			return reversi.getPlayers();
		case TICTACTOE:
			return ticTacToe.getPlayers();
		default:
			return null;
		}
	}

}