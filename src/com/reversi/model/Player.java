package com.reversi.model;

public class Player {
	
	public enum PlayerType {
		HUMAN, AI, SERVER
	}
	
	protected final PlayerType type;
	protected final int id;
	private int score;
	private boolean hasTurn;

	public Player(PlayerType type, int id) {
		this.type = type;
		this.id = id;
		
		score = 0;
		hasTurn = false;	
	}
	
	public int getScore() {
		return score;
	}
	
	public void setScore(int score) {
		this.score = score;
	}
	
	public void setTurn(boolean hasTurn) {
		this.hasTurn = hasTurn;
	}
	
	public boolean hasTurn() {
		return hasTurn;
	}

}
