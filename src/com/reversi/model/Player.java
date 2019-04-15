package com.reversi.model;

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

	public Player(PlayerType type, int color) {
		this.type = type;
		this.color = color;
		score = 0;
		name = "Computer";
		hasTurn = false;
		
		if (color == 1){
			opponent = 2;
		}
		else {
			opponent = 1;
		}
		
		if(type.equals(PlayerType.AI)) {
			ai = new AI();
		}
	}
	
	public PlayerType getType() {
		return type;
	}
	
	public int getColor() {
		return color;
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getScore() {
		return score;
	}
	
	public void setScore(int score) {
		this.score = score;
	}
	
	public void setTurn(boolean hasTurn) {
		System.out.println(name + " has turn " + hasTurn);
		this.hasTurn = hasTurn;
	}
	
	public boolean hasTurn() {
		return hasTurn;
	}
	
	public void incrementScore() {
		score++;
	}

	public void decrementScore() {
		score--;
	}
}