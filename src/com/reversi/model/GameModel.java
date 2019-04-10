package com.reversi.model;

import com.reversi.model.Game.GameMode;
import com.reversi.model.Game.GameType;
import com.reversi.model.Player.PlayerType;

public class GameModel extends Model {

	private boolean running;
	private TicTacToe ticTacToe;
	private Reversi reversi;

	private GameType currentGameType;
	private GameMode currentGameMode;

	public GameModel() {
		// reversi = new Reversi(GameMode.SINGLEPLAYER);
		// currentGameType = GameType.NOGAME;
		ticTacToe = null;
		reversi = null;
	}

	// Send a notify to do something here
	public void setState() {

	}

	public void update(int serverMove, int enemyScore, int playerScore) {
		// TODO implement
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
				client.sendCommand("subscribe Reversi");
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

		notifyView();
	}
	
	public void endGame() {
		currentGameMode = null;
		currentGameType = null;
		
		reversi = null;
		ticTacToe = null;
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
					if(players[0].hasTurn()) {
						reversi.makeSimpleMove(players[0], intMove);
					} else {
						reversi.makeSimpleMove(players[1], intMove);
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
					//ticTacToe.setMove(intMove, playerID);
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
						client.sendCommand("move " + reversi.makeMove(players[0]));
					} else {
						client.sendCommand("move " + reversi.makeMove(players[1]));
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
					//ticTacToe.setMove(intMove, playerID);
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

	public void login(String[] arguments) {
		client.login(arguments);
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

	public Player[] getPlayer() {
		Player[] players = new Player[2];
		switch (currentGameType) {
		case REVERSI:
			players[0] = reversi.player1;
			players[1] = reversi.player2;
			return players;
		case TICTACTOE:
			players[0] = ticTacToe.player1;
			players[1] = ticTacToe.player2;
			return players;
		default:
			return null;
		}
	}

}
