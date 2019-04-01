package com.reversi.controller;

public class Player {
	
	private int score;
	private boolean hasTurn;

	public Player() {
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
