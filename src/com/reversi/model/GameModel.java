package com.reversi.model;

import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Queue;

import com.reversi.client.Parser.ArgumentKey;
import com.reversi.client.Parser.ServerCommand;

import com.reversi.model.Game.GameMode;
import com.reversi.model.Game.GameType;
import com.reversi.model.Player.PlayerType;

public class GameModel extends Model {

	private boolean running;
	private TicTacToe ticTacToe;
	private Reversi reversi;
	
	private int player1Score;
	private int player2Score;

	private GameType currentGameType;
	private GameMode currentGameMode;

	private Queue<HashMap<ArgumentKey, String>> challenges;

	public GameModel() {
		ticTacToe = null;
		reversi = null;
		
		player1Score = 0;
		player2Score = 0;

		currentGameMode = null;
		currentGameType = null;

		challenges = new LinkedList<>();
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

	public void startGame(GameMode gameMode, GameType gameType) {
		currentGameMode = gameMode;
		currentGameType = gameType;

		switch (gameType) {
		case REVERSI:
			if (gameMode.equals(GameMode.SINGLEPLAYER)) {
				// reversi = new Reversi(GameMode.SINGLEPLAYER);
				break;
			} else {
				// reversi = new Reversi(GameMode.ONLINE);				
				break;
			}
		case TICTACTOE:
			if (gameMode.equals(GameMode.SINGLEPLAYER)) {
				ticTacToe = new TicTacToe(GameMode.SINGLEPLAYER);
				break;
			} else {
				ticTacToe = new TicTacToe(GameMode.ONLINE);
				client.sendCommand("subscribe Tic-tac-toe");
				break;
			}
		default:
			throw new IllegalStateException();
		} 

		//notifyView();
	}

	public void endGame(String[] arguments) {
		if(arguments != null) {
			player1Score = Integer.parseInt(arguments[0]);
			player2Score = Integer.parseInt(arguments[1]);
		}

		currentGameMode = null;
		currentGameType = null;

		reversi = null;
		ticTacToe = null;

		notifyView();
	}

	// A move set by the player or server...
	public void setMove(String move) {
		int intMove = Integer.valueOf(move);
		switch (currentGameType) {
		case REVERSI:
			if (currentGameMode.equals(GameMode.ONLINE)) {
				if (reversi == null) {
					// If the set move function is called and there is no game then it is always the
					// server who starts from here
					reversi = new Reversi(GameMode.ONLINE, PlayerType.SERVER);
				}
				try {
					Player[] players = reversi.getPlayers();
					if (players[0].hasTurn()) {
						reversi.makeMove(players[0], intMove);
					} else {
						reversi.makeMove(players[1], intMove);
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
				break;
			} else { // Offline

			}
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

		notifyView();
	}

	public void getMove() {
		switch (currentGameType) {
		case REVERSI:
			if (currentGameMode.equals(GameMode.ONLINE)) {
				if (reversi == null) {
					// If the set move function is called and there is no game then it is always the
					// AI who starts from here
					reversi = new Reversi(GameMode.ONLINE, PlayerType.AI);
				}
				try {
					Player[] players = reversi.getPlayers();
					if(players[0].hasTurn()) {
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
				break;
			} else { // Offline

			}
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

		notifyView();
	}

	public void login(String[] arguments) {
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

		notifyView();
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

		notifyView();
	}

	// Add new challenge to a queue of challenges
	public void addNewServerChallenge(String[] arguments) {
		HashMap<ArgumentKey, String> map = new HashMap<>();

		map.put(ArgumentKey.CHALLENGER, arguments[0]);
		map.put(ArgumentKey.CHALLENGENUMBER, arguments[1]);
		map.put(ArgumentKey.GAMETYPE, arguments[2]);

		challenges.add(map);
		notifyView();
	}

	// TODO a better way to get challenges from model
	// Return the top of the queue and remove it from the queue
	public HashMap<ArgumentKey, String> getChallenge() {
		if (!challenges.isEmpty()) {
			// return challenges.remove();
			return null;
		}
		return null;
	}

	// Returns true if model has a challenge
	public boolean hasChallenge() {
		if (!challenges.isEmpty()) {
			return true;
		}
		return false;
	}

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

	public int[] getPlayerScores() {
		int[] scores = new int[2];
		scores[0] = player1Score;
		scores[1] = player2Score;
		return scores;
	}

}