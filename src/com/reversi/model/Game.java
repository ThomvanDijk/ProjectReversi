package com.reversi.model;

/**
 * Dit is de abstracte klasse waar Reversi en TicTacToe uit erfen.
 * Als er in de toekomst spellen bij komen, gaan deze ook gebruik maken
 * van deze abstracte klasse.
 * 
 * @author  Thom van Dijk
 * @version 1.0
 * @since   1.0
 */

public abstract class Game {

	public enum GameType {
		NOGAME, REVERSI, TICTACTOE;
	}

	public enum GameMode {
		SINGLEPLAYER, ONLINE;
	}

	private final GameType gameType;
	protected Board board;
	protected boolean noWinner;
	protected int noWinnerCount;
	protected Player player1;
	protected Player player2;
	protected int turn;

	/**
	 * De constructor van game wilt het type en de modus van het spel weten.
	 * 
	 * @param gameType Het type van het spel (Reversi/TicTacToe
	 * @param gameMode De modus van het spel (Singleplayer/Multiplayer)
	 */
	public Game(GameType gameType) {
		this.gameType = gameType;

		noWinnerCount = 0;

//		if (gameMode.equals(GameMode.SINGLEPLAYER)) {
//			player1 = new Player(PlayerType.HUMAN, 1);
//			player2 = new Player(PlayerType.AI, 2);
//		} else {
//			player1 = new Player(PlayerType.AI, 2);
//			player2 = new Player(PlayerType.SERVER, 1);
//		}

//		player1.setTurn(true);
//		player2.setTurn(false);

		createBoard();
	}
	
	/**
	 * Getter voor spelers in de game
	 * @return  Spelers in de game
	 */
	public Player[] getPlayers() {
		return new Player[] { player1, player2 };
	}

	/**
	 * Deze functie maakt een bord, afhankelijk van de gametype.
	 */
	private void createBoard() {
		switch (gameType) {
		case REVERSI:
			board = new Board(8, gameType);
			break;
		case TICTACTOE:
			board = new Board(3, gameType);
			break;
		default:
			throw new IllegalStateException();
		}
	}

}