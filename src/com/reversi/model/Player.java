package com.reversi.model;

/**
 * This class will save everything that has something to do with a player.
 * 
 * @author  Thom van Dijk
 * @author  Sebastiaan van Vliet
 * @version 1.0
 * @since   12-04-2019
 */
public class Player {

	public enum PlayerType {
		HUMAN, AI, SERVER
	}

	protected final PlayerType type;
	protected final int color;
	private int score;
	private String name;
	private boolean hasTurn;
	protected int opponent;
	protected AI ai = null;

	/**
	 * De constructor voor player wilt het type speler (Human, AI or Server) weten
	 * en de kleur (wit of zwart) van de speler.
	 * 
	 * @param type  Het soort type van de speler, Human, AI of Server
	 * @param color  De kleur van de speler
	 */
	public Player(PlayerType type, int color) {
		this.type = type;
		this.color = color;
		score = 0;
		name = "Computer";
		hasTurn = false;

		if (color == 1) {
			opponent = 2;
		} else {
			opponent = 1;
		}

		if (type.equals(PlayerType.AI)) {
			ai = new AI();
		}
	}

	/**
	 * @return Krijg het type terug
	 */
	public PlayerType getType() {
		return type;
	}

	/**
	 * @return Krijg de kleur van de speler terug
	 */
	public int getColor() {
		return color;
	}

	/**
	 * @return Krijg de naam terug van de speler
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name Zet de naam van de speler
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return Krijg de score terug van de speler
	 */
	public int getScore() {
		return score;
	}

	/**
	 * @param score Zet de score van de speler
	 */
	public void setScore(int score) {
		this.score = score;
	}

	/**
	 * @param hasTurn Verander of de speler wel of niet de beurt heeft
	 */
	public void setTurn(boolean hasTurn) {
		this.hasTurn = hasTurn;
	}

	/**
	 * @return Krijg terug of de speler de beurt heeft
	 */
	public boolean hasTurn() {
		return hasTurn;
	}

	/**
	 * Verhoog de score van de speler met 1
	 */
	public void incrementScore() {
		score++;
	}

	/**
	 * Verlaag de score van de speler met 1
	 */
	public void decrementScore() {
		score--;
	}
}